package abc.ast;

import abc.sound.*;

import java.util.*;

/**
 * An immutable data type representing one chord in music sheet. 
 * A chord can have notes of different lengths.
*/
public class Chord implements Music, NoteOrChord {
    
    //private fields
    private final List<Note> notes; 
    private final double duration;
    
    // Abstraction function
    //    represents one chord of duration duration in music sheet
    // Rep invariant
    //    notes.size() > 0
    //    duration >= 0
    //    duration == notes.get(0).duration()   //according to specification handout
    //    notes != null
    //    values in notes != null
    // Safety from rep exposure
    //    all fields are private and final
    //    double is immutable
    //    notes is mutable, but no public method in this class returns a reference to notes to the client,
    //          and all return values are immutable
    
    public Chord(List<Note> host, double duration){
        this.duration = duration;
        this.notes = new ArrayList<>();
        for (Note n: host){
            notes.add(n);
        }
        checkRep();
    }
    
    private void checkRep(){
        assert this.notes != null;
        assert this.notes.size() > 0;
        assert duration >= 0;
        for (Note n: this.notes){
            assert n != null;
        }
        assert duration == notes.get(0).duration();
    }
    
    private List<Note> getNotes(){
        return this.notes;
    }
    
    @Override
    public double duration(){
        return this.duration;
    }
    
    @Override
    public void play(SequencePlayer player, double start){
        for (Note note: this.getNotes()){
            note.play(player, start);
        }
    }
    
    @Override
    public Music transpose(int semitonesUp){
        List<Note> inputList = new ArrayList<>();
        double newDuration = this.getNotes().get(0).transpose(semitonesUp).duration();
        for (Note note: this.getNotes()){
            inputList.add((Note)note.transpose(semitonesUp));
        }
        return new Chord(inputList, newDuration);
    }
    
    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp){
        List<Note> inputList = new ArrayList<>();
        double newDuration = this.getNotes().get(0).transpose(semitonesUp).duration();
        for (Note note: this.getNotes()){
            inputList.add((Note)note.transposePitch(pitch, semitonesUp));
        }
        return new Chord(inputList, newDuration);
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Chord instances representing the same chord in music
     *          with same duration
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Chord)) return false;
        Chord thatChord = (Chord) obj;
        List<Note> thisNotes = this.getNotes();
        List<Note> thatChordNotes = thatChord.getNotes();
        if (this.duration() != thatChord.duration() || thisNotes.size() != thatChordNotes.size()){
            return false;
        }
        else{
            for (int i = 0; i < thisNotes.size(); i++){
                if (!thisNotes.get(i).equals(thatChordNotes.get(i))){
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        Double d = this.duration();
        return d.hashCode();
    }
    
    /**
     * Outputs a String representation of a Chord instance: the notes in the chord in String form enclosed in brackets 
     * @return String representation of the Chord instance suitable for reading
     */
    @Override
    public String toString() {
        String out = "[";
        for (Note note: this.getNotes()){
            out += note.toString();
        }
        out += "]";
        return out;
    }
    
    @Override
    public void tupletPlay(SequencePlayer player, double start, int length) {
        double durationFactor;
        
        switch(length){
            case 2: 
                durationFactor = 1.5;
                break;
            case 3: 
                durationFactor = 2.0/3.0;
                break;
            case 4: 
                durationFactor = 0.75;
                break;
            default: 
                throw new RuntimeException("Unreachable");
        }
        List<Note> newNotes = new ArrayList<>();
        for (Note n: this.getNotes()){
            Note newNote = new Note(n.duration() * durationFactor, n.getPitch());
            newNotes.add(newNote);
        }
        Chord newChord = new Chord(newNotes, newNotes.get(0).duration());
        newChord.play(player, start);
    }
}