package abc.ast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;
import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

/**
 * An interface to represent a note(s) in music sheet. Concrete variants will implement this interface
 * Interfaces are immutable by default.
 */
public interface Music {
    
    //Data type definition
    //  Music = Note + Rest + Chord + Concat + Measure + Repeat + Tuplet + Voice + Piece
    //  Note = Note(double: d, Pitch: p)
    //  Rest = Rest(double: d)
    //  Chord = Chord(List<Note>: notes)
    //  Tuplet = Tuplet(List<NoteOrChord>: m, int: length)
    //  Repeat = Repeat(Music: repeated, Music: firstEnd, Music: secondEnd)
    //  Concat = Concat(Music: first, Music: second)
    //  Measure = Measure(Music: m, double: d)
    //  Voice = Voice(Music: melody, String: name)
    //  Piece = Piece(List<Voice>: voices)
    
    enum AbcHeaderGrammar{
        ABC_TUNE,
        ABC_HEADER,
        ABC_MUSIC,
        MUSIC_LINES,
        FIELD_NUMBER,
        FIELD_TITLE,
        OTHER_FIELDS,
        FIELD_COMPOSER,
        FIELD_DEFAULT_LENGTH,
        FIELD_METER,
        FIELD_TEMPO,
        FIELD_VOICE,
        FIELD_KEY,
        KEY,
        KEYNOTE,
        BASENOTE,
        KEY_ACCIDENTAL,
        MODE_MINOR,
        METER,
        METER_FRACTION,
        NOTE_LENGTH_STRICT,
        NUMERATOR,
        DENOMINATOR,
        TEMPO,
        MEASURE,
        LINE_OF_MUSIC,
        REPEAT,
        ELEMENT,
        NOTE_ELEMENT,
        NOTE,
        NOTE_OR_REST,
        PITCH,
        OCTAVE,
        NOTE_LENGTH,
        ACCIDENTAL,
        REST,
        TUPLET_ELEMENT,
        TUPLET_SPEC,
        CHORD,
        BARLINE,
        REPEAT_START,
        REPEAT_END,
        NTH_REPEAT,
        NTH_END,
        COMMENT,
        END_OF_LINE,
        TEXT,
        NUMBER,
        DIGIT,
        NEWLINE,
        WHITESPACE
    }
    
    enum AbcBodyGrammar{
        ABC_TUNE,
        ABC_HEADER,
        ABC_MUSIC,
        ABC_LINE,
        PARAMETER,
        PARAMETER_INFO,
        METER_FRACTION,
        MEASURE,
        LINE_OF_MUSIC,
        REPEAT,
        ELEMENT,
        NUMERATOR,
        DENOMINATOR,
        NOTE_ELEMENT,
        NOTE,
        NOTE_OR_REST,
        PITCH,
        OCTAVE,
        NOTE_LENGTH,
        ACCIDENTAL,
        BASENOTE,
        REST,
        TUPLET_ELEMENT,
        TUPLET_SPEC,
        CHORD,
        BARLINE,
        REPEAT_START,
        REPEAT_END,
        NTH_REPEAT,
        NTH_END,
        FIELD_VOICE,
        END_OF_LINE,
        COMMENT,
        TEXT,
        DIGIT,
        NEWLINE,
        WHITESPACE
    }
    
    /**
     * Play an abc file.
     * @param file the abc file to be played.
     */
    public static void playMusic(File file, int ticksPerBeat) {
        Map<String, String> header = parseHeader(file);
        Music music = parseBody(header);
        try {
            SequencePlayer player = new SequencePlayer(Integer.parseInt(header.get("Q2")), ticksPerBeat);
            music.play(player, 0);
            player.play();
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Parse a music file into header and body fields in a map.
     * @param file music file to parse, must have a valid header followed by a valid body as defined in the grammars.
     * @return a map for the input.
     * @throws IllegalArgumentException if the input string is not valid.
     */
    static Map<String, String> parseHeader(File file) {
        String input = "";        
        try{
            
            //Read the given file into a single string.
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while(reader.ready()) {
                input += reader.readLine() + "\n";
            }
            reader.close();
            
            //Make a header map from the input string.
            Parser<AbcHeaderGrammar> headerParser = GrammarCompiler.compile(new File("src/abc/parser/AbcHeader.g"), AbcHeaderGrammar.ABC_TUNE);
            ParseTree<AbcHeaderGrammar> headerTree = headerParser.parse(input);
            Map<String, String> header = buildHeaderAST(headerTree);
                        
            //Update header based on initial input.
            header = updateMissingFields(header);
            header = updateKeySignature(header);
            
            //Print out header fields
            System.out.println(header.get("entire_header"));
            
            return header;
            
        }
        catch(UnableToParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch(IOException e) {
            return null;
        }
    }   
    
    /**
     * Parse a music file.
     * @param header a map containing all of the necessary fields to parse an abc file correctly.
     * @return music AST for the input.
     * @throws IllegalArgumentException if the input string is not valid.
     */
    static Music parseBody(Map<String, String> header) {
        try{
            
            //Make a Music AST from the input and the header map.
            Parser<AbcBodyGrammar> musicParser = GrammarCompiler.compile(new File("src/abc/parser/AbcBody.g"), AbcBodyGrammar.ABC_MUSIC);
            List<Voice> voices = new ArrayList<>();
            Set<String> keys = header.keySet();
            Set<String> inps = new HashSet<>();
            for(String key : keys) {
                if(key.contains("voice_")) {
                    inps.add( header.get(key));
                }
            }
            for(String inp : inps) {
                voices.add((Voice) buildMusicAST(musicParser.parse(inp), header)); //Make a new Voice for each voice parsed by the header.
            }
            Music musicAst = new Piece(voices); //Combine all the Voices into one Piece.
            
            return musicAst;
            
        }
        catch(UnableToParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch(IOException e) {
            return null;
        }
    }   
    /**
     * Updates the header map with the correct fields of the header if they
     * are missing according to the project specification.
     * @param header the current header map
     * @return the updated header map
     */
    static Map<String, String> updateMissingFields(Map<String, String> header) {
        final double meterLimit = 0.75;
        final String defaultMeter = "1.0";
        final String defaultLengthLow = "0.0625";
        final String defaultLengthHigh = "0.125";
        final String defaultTempo = "100";
        final String defaultComposer = "Unknown";
        
        if(!header.containsKey("M")) {
            header.put("M", defaultMeter);
        }
        if(!header.containsKey("L")) {
            double meter = Double.parseDouble(header.get("M"));
            if(meter < meterLimit) {
                header.put("L", defaultLengthLow);
            }
            else {
                header.put("L", defaultLengthHigh);
            }
        }
        if(!header.containsKey("Q1")) {
            header.put("Q1", header.get("L"));
            header.put("Q2", defaultTempo);
        }
        if(!header.containsKey("C")) {
            header.put("C", defaultComposer);
        }
        return header;
    }
    
    /**
     * Updates the header map with the correct notes to be sharped or flattened based on the 
     * key signature.
     * @param header the current header map
     * @return the updated header map
     */
    static Map<String, String> updateKeySignature(Map<String, String> header) {
        switch(header.get("K")) {
        case "Gb":
        case "Ebm":
            header.put("key_C", "_");
        case "Db":
        case "C#":
        case "Bbm":
            header.put("key_G", "_");
        case "Ab":
        case "Fm":
            header.put("key_D", "_");
        case "Eb":
        case "Cm":
            header.put("key_A", "_");
        case "Bb":
        case "Gm":
            header.put("key_E", "_");
        case "F":
        case "Dm":
            header.put("key_B", "_");
        case "C":
            return header;
            
        case "F#":
        case "D#m":
            header.put("key_E", "^");
        case "B":
        case "G#m":
        case "Cb":
            header.put("key_A", "^");
        case "E":
        case "C#m":
            header.put("key_D", "^");
        case "A":
        case "F#m":
            header.put("key_G", "^");
        case "D":
        case "Bm":
            header.put("key_C", "^");
        case "G":
        case "Em":
            header.put("key_F", "^");
        case "Am":
            return header;
        
        default:
            throw new RuntimeException("Incompatible key signature.");
        }
    }
    
    /**
     * Function converts a ParseTree to a map. 
     * @param p ParseTree<AbcHeaderGrammar> that is assumed to have been constructed by the grammar in AbcHeader.g
     * @return the map object containing header information generated from the ParseTree
     */
    static Map<String, String> buildHeaderAST(ParseTree<AbcHeaderGrammar> p) {
        Map<String, String> header = new HashMap<>();
        switch(p.getName()) {
        case ABC_TUNE:
            for(ParseTree<AbcHeaderGrammar> voice : p.childrenByName(AbcHeaderGrammar.ABC_MUSIC)) {
                if(voice.childrenByName(AbcHeaderGrammar.FIELD_VOICE).isEmpty()) {
                    header.put("voice_default", voice.childrenByName(AbcHeaderGrammar.MUSIC_LINES).get(0).getContents());
                }
                else {
                    String voiceName = "voice_" +
                            voice.childrenByName(AbcHeaderGrammar.FIELD_VOICE).get(0).childrenByName(AbcHeaderGrammar.TEXT).get(0).getContents();
                    if(header.containsKey(voiceName)) {
                        header.put(voiceName, header.get(voiceName) + "\n" + voice.childrenByName(AbcHeaderGrammar.MUSIC_LINES).get(0).getContents());
                    }
                    else {
                        header.put(voiceName, voice.childrenByName(AbcHeaderGrammar.MUSIC_LINES).get(0).getContents());
                    }
                }
            }    
            
            Map<String, String> headerMap = buildHeaderAST(p.childrenByName(AbcHeaderGrammar.ABC_HEADER).get(0));
            for(String key : headerMap.keySet()) {
                header.put(key, headerMap.get(key));
            }
            return header;
        case ABC_HEADER:
            header.put("entire_header", p.getContents());
            
            Map<String, String> number = buildHeaderAST(p.childrenByName(AbcHeaderGrammar.FIELD_NUMBER).get(0));
            for(String key : number.keySet()) {
                header.put(key, number.get(key));
            }
            
            Map<String, String> title = buildHeaderAST(p.childrenByName(AbcHeaderGrammar.FIELD_TITLE).get(0));
            for(String key : title.keySet()) {
                header.put(key, title.get(key));
            }
            
            Map<String, String> others;
            for(ParseTree<AbcHeaderGrammar> tree : p.childrenByName(AbcHeaderGrammar.OTHER_FIELDS)) {
                others = buildHeaderAST(tree);
                for(String key : others.keySet()) {
                    header.put(key, others.get(key));
                }
            }
            
            Map<String, String> keySignature = buildHeaderAST(p.childrenByName(AbcHeaderGrammar.FIELD_KEY).get(0));
            for(String key : keySignature.keySet()) {
                header.put(key, keySignature.get(key));
            }
            
            
            return header;
        case ABC_MUSIC:
        case FIELD_NUMBER:
            header.put("X", p.childrenByName(AbcHeaderGrammar.NUMBER).get(0).getContents());
            return header;
        case FIELD_TITLE:
            header.put("T", p.childrenByName(AbcHeaderGrammar.TEXT).get(0).getContents());
            return header;
        case OTHER_FIELDS:
            return buildHeaderAST(p.children().get(0));
        case FIELD_COMPOSER:
            header.put("C", p.childrenByName(AbcHeaderGrammar.TEXT).get(0).getContents());
            return header;
        case FIELD_DEFAULT_LENGTH:
            ParseTree<AbcHeaderGrammar> fraction = p.childrenByName(AbcHeaderGrammar.NOTE_LENGTH_STRICT).get(0);
            double numerator = Double.parseDouble(fraction.childrenByName(AbcHeaderGrammar.NUMERATOR).get(0).getContents());
            double denominator = Double.parseDouble(fraction.childrenByName(AbcHeaderGrammar.DENOMINATOR).get(0).getContents());
            header.put("L", "" + numerator/denominator);
            return header;
        case FIELD_METER:
            ParseTree<AbcHeaderGrammar> meter = p.childrenByName(AbcHeaderGrammar.METER).get(0);
            if(meter.childrenByName(AbcHeaderGrammar.METER_FRACTION).isEmpty()) {
                header.put("M", "1");
            }
            else {
                fraction = meter.childrenByName(AbcHeaderGrammar.METER_FRACTION).get(0);
                numerator = Double.parseDouble(fraction.childrenByName(AbcHeaderGrammar.NUMERATOR).get(0).getContents());
                denominator = Double.parseDouble(fraction.childrenByName(AbcHeaderGrammar.DENOMINATOR).get(0).getContents());
                header.put("M", "" + numerator/denominator);
            }
            return header;
        case FIELD_TEMPO:
            ParseTree<AbcHeaderGrammar> tempo = p.childrenByName(AbcHeaderGrammar.TEMPO).get(0);
            fraction = tempo.childrenByName(AbcHeaderGrammar.METER_FRACTION).get(0);
            numerator = Double.parseDouble(fraction.childrenByName(AbcHeaderGrammar.NUMERATOR).get(0).getContents());
            denominator = Double.parseDouble(fraction.childrenByName(AbcHeaderGrammar.DENOMINATOR).get(0).getContents());
            header.put("Q1", "" + numerator/denominator);
            header.put("Q2", tempo.childrenByName(AbcHeaderGrammar.NUMBER).get(0).getContents());
            return header;
        case FIELD_VOICE:
            ParseTree<AbcHeaderGrammar> voice = p.childrenByName(AbcHeaderGrammar.TEXT).get(0);
            header.put("V", voice.getContents());
            return header;
        case FIELD_KEY:
            ParseTree<AbcHeaderGrammar> key = p.childrenByName(AbcHeaderGrammar.KEY).get(0);
            header.put("K", key.getContents());
            return header;
        //Shouldn't reach these cases
        case KEY:
        case KEYNOTE:
        case BASENOTE:
        case KEY_ACCIDENTAL:
        case MODE_MINOR:
        case METER:
        case METER_FRACTION:
        case NOTE_LENGTH_STRICT:
        case TEMPO:
        case COMMENT:
        case END_OF_LINE:
        case TEXT:
        case NUMBER:
        case DIGIT:
        case NEWLINE:
        case WHITESPACE:
        default:
            throw new RuntimeException("You should never reach here:" + p);
        }
    }
    
    /**
     * Function converts a ParseTree to a Music. 
     * @param p ParseTree<AbcBodyGrammar> that is assumed to have been constructed by the grammar in AbcBody.g
     * @param header Map<String, String> contains rules from header of abc file
     * @return the Music object generated from the ParseTree
     */
    static Music buildMusicAST(ParseTree<AbcBodyGrammar> p, Map<String, String> header) {
        switch(p.getName()){
        
        case ABC_MUSIC:
            Music voice = new Rest(0);
            for(ParseTree<AbcBodyGrammar> line : p.childrenByName(AbcBodyGrammar.ABC_LINE)){
                
                ParseTree<AbcBodyGrammar> child = line.children().get(0);
                
                if(child.getName() == AbcBodyGrammar.LINE_OF_MUSIC){
                    Music m = buildMusicAST(child, header);
                    voice = new Concat(voice, m);
                }
            }
            
            return new Voice(voice, "test");
        case LINE_OF_MUSIC:
            Music m = new Rest(0);
            for(ParseTree<AbcBodyGrammar> child : p.children()) {
                if(child.getName() != AbcBodyGrammar.NEWLINE) {
                    m = new Concat(m, buildMusicAST(child, header));
                }
            }
            return m;
        case ABC_LINE:
            throw new RuntimeException("You should never reach here.");
        
        case ELEMENT:
            Music element = new Rest(0);
            if(p.children().get(0).getName() == AbcBodyGrammar.NOTE_ELEMENT ||
                    p.children().get(0).getName() == AbcBodyGrammar.TUPLET_ELEMENT) {
                element = buildMusicAST(p.children().get(0), header);
            }
            return element;
        case NOTE_ELEMENT:
            return buildMusicAST(p.children().get(0), header);
        
        case REPEAT_END:
            Music end = new Rest(0);
            for(ParseTree<AbcBodyGrammar> elt : p.childrenByName(AbcBodyGrammar.ELEMENT)) {
                end = new Concat(end, buildMusicAST(elt, header));
            }
            return end;
        
        case NTH_END:
            return buildMusicAST(p.children().get(0), header);
        
            
        //Handles note and rest
        case NOTE:
           
            double length = (Double.parseDouble(header.get("L")) / Double.parseDouble(header.get("Q1"))) * MusicHelper.fractionToDouble(p.childrenByName(AbcBodyGrammar.NOTE_LENGTH).get(0).getContents());

            ParseTree<AbcBodyGrammar> noteOrRest = p.childrenByName(AbcBodyGrammar.NOTE_OR_REST).get(0);
            
            //Must be a note
            if(noteOrRest.childrenByName(AbcBodyGrammar.REST).isEmpty()){
                
                ParseTree<AbcBodyGrammar> pitch = noteOrRest.childrenByName(AbcBodyGrammar.PITCH).get(0);
                String basenote = pitch.childrenByName(AbcBodyGrammar.BASENOTE).get(0).getContents();
                
                String octave;
                if(pitch.childrenByName(AbcBodyGrammar.OCTAVE).isEmpty()){
                    octave = "";
                }
                else{
                    octave = pitch.childrenByName(AbcBodyGrammar.OCTAVE).get(0).getContents();
                }
                
                //Handle accidental for this note
                String accidental = "";
                //First get any accidentals specified by key
                if(header.containsKey("key_" + basenote.toUpperCase())){
                    accidental = header.get("key_" + basenote.toUpperCase());
                }
                //Then, get any accidentals specified by previous notes in that measure
                if(header.containsKey("accidental_" + basenote + octave)){
                    accidental = header.get("accidental_" + basenote + octave);
                }
                //Finally, get the accidental of this particular note
                //and then update the header
                if(!(pitch.childrenByName(AbcBodyGrammar.ACCIDENTAL).isEmpty())){
                    accidental = pitch.childrenByName(AbcBodyGrammar.ACCIDENTAL).get(0).getContents();
                    header.put("accidental_" + basenote + octave, accidental);
                }
                
                return MusicHelper.constructNote(accidental, basenote, octave, length);
            }
            else{
                return new Rest(length);
            }
        
        case CHORD:
            List<Note> notes = new ArrayList<>();
            double chordDuration = buildMusicAST(p.children().get(0), header).duration();
            for(ParseTree<AbcBodyGrammar> note : p.children()) {
                notes.add((Note) buildMusicAST(note, header));
            }
            return new Chord(notes, chordDuration);
        case TUPLET_ELEMENT:
            //Handle '(' character.
            String tupletLengthString = p.childrenByName(AbcBodyGrammar.TUPLET_SPEC).get(0).getContents().substring(1);
            int tupletLength = Integer.parseInt(tupletLengthString);
            
            List<NoteOrChord> sequence = new ArrayList<NoteOrChord>();
            for(ParseTree<AbcBodyGrammar> child : p.childrenByName(AbcBodyGrammar.NOTE_ELEMENT)){
                    sequence.add((NoteOrChord) buildMusicAST(child, header));
            }
            return new Tuplet(sequence, tupletLength);
        case MEASURE:
            Music measure = new Rest(0);
            for(ParseTree<AbcBodyGrammar> elt : p.childrenByName(AbcBodyGrammar.ELEMENT)) {
                measure = new Concat(measure, buildMusicAST(elt, header));
            }
            //Remove all accidentals placed in header by the notes in this measure
            Set<String> accidentalsToRemove = header.keySet().stream().filter(s -> s.contains("accidental_")).collect(Collectors.toSet());
            Iterator<String> iter = accidentalsToRemove.iterator();
            while(iter.hasNext()){
                header.remove(iter.next());
            }
            
            return new Measure(measure, Double.parseDouble(header.get("M")));
        case REPEAT:
            Music repeat = new Rest(0);
            // Test for multiple endings
            if(p.childrenByName(AbcBodyGrammar.NTH_REPEAT).isEmpty()) { // no multiple endings
                for(ParseTree<AbcBodyGrammar> measures : p.childrenByName(AbcBodyGrammar.MEASURE)) {
                    repeat = new Concat(repeat, buildMusicAST(measures, header));
                }
                repeat = new Concat(repeat, buildMusicAST(p.childrenByName(AbcBodyGrammar.REPEAT_END).get(0), header));
                return new Repeat(repeat);
            }
            
            else { // two endings
                for(ParseTree<AbcBodyGrammar> measures : p.childrenByName(AbcBodyGrammar.MEASURE)) {
                    repeat = new Concat(repeat, buildMusicAST(measures, header));
                }
                Music first = buildMusicAST(p.childrenByName(AbcBodyGrammar.REPEAT_END).get(0), header);
                Music second = buildMusicAST(p.childrenByName(AbcBodyGrammar.NTH_END).get(0), header);
                
                return new Repeat(repeat, first, second);
            }
        //Don't care for the rest of these cases
        case PARAMETER:
        case PARAMETER_INFO:
        case METER_FRACTION:
        case OCTAVE:
        case ACCIDENTAL:
        case BASENOTE:
        case BARLINE:
        case NTH_REPEAT:
        case REPEAT_START:
        case FIELD_VOICE:
        case END_OF_LINE:
        case COMMENT:
        case TEXT:
        case DIGIT:
        case NEWLINE:
        case WHITESPACE:
        case DENOMINATOR:
        case NOTE_LENGTH:
        case NOTE_OR_REST:
        case NUMERATOR:
        case PITCH:
        case REST:
        case TUPLET_SPEC:
        default:
            throw new RuntimeException("You should never reach here." + p);
            
        }
    }
    
    /**
     * Gets the length of some singular note in music
     * @return a double representing the length of the note
     */
    public double duration();
    
    /**
     * Play this piece
     * @param player Sequence player that plays this music
     * @param start when to play, requires start >= 0
     */
    public void play(SequencePlayer player, double start);
    
    /**
     * Transpose all notes upward or downward in pitch.
     * @param m music
     * @param semitonesUp semitones by which to transpose
     * @return m' such that for all notes n in m, the corresponding note n' in m has 
     * n'.pitch() == n.pitch().transpose(semitonesUp), and m' is otherwise identical to m
     * 
     */
    public Music transpose(int semitonesUp);
    
    /**
     * Transpose only a given pitch upwards or downwards, leaving the rest unchanged.
     * @param pitch the pitch to transpose
     * @param semitonesUp semitones by which to transpose
     * @return m' such that for all notes n in m, the corresponding note n' in m has 
     * n'.pitch() == n.pitch().transpose(semitonesUp) iff n.pitch == pitch, and m' is otherwise identical to m
     */
    public Music transposePitch(Pitch pitch, int semitonesUp);
}