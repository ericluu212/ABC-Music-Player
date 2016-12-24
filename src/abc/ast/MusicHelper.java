package abc.ast;

import java.util.Arrays;

import abc.sound.Pitch;


/**
 * An helper class with methods used in Music.java for reading, parsing, and playing .abc files
 */
public class MusicHelper {

    protected MusicHelper() {};
    
    //Helper methods
    
    /**
     * Converts a String in the format "(Number)/(Number)", "Number/", "/Number", "/", "", Number
     * into a double
     * @param fraction String representing number to convert into a double; assumes (Number) is not "0"
     *      if provided as a denominator
     * @return double representation of fraction
     */
    public static double fractionToDouble(String fraction){
        if(fraction.contains("/")){
            String[] ratio = fraction.split("/");
            if(ratio.length == 0){
                return 0.5;
            }
            else{
                if(fraction.endsWith("/")){
                        return Double.parseDouble(ratio[0]) / 2.0; 
                }
                if(fraction.startsWith("/")){
                    return 1.0 / Double.parseDouble(ratio[1]);
                }
                else{
                    return Double.parseDouble(ratio[0]) / Double.parseDouble(ratio[1]);
                }
            }
        }
        
        if(fraction.isEmpty()){
            return 1.0;
        }   
        else{
            return Double.parseDouble(fraction);
        }
        
    }
    
    /**
     * Creates a note 
     * @param accidental String representing the accidental of this note
     * @param basenote String representing the base pitch of this note
     * @param octave String representing what octave this note is in
     * @param duration double representing how long this note lasts
     * @return a note with the correct pitch and duration
     */
    public static Note constructNote(String accidental, String basenote, String octave, double duration){
        
        int accidentalTranspose = handleAccidental(accidental);
        int octaveTranspose = handleOctave(octave);
        
        Pitch p = handlePitch(basenote);
        return new Note(duration, p.transpose(accidentalTranspose).transpose(octaveTranspose));
    }
    
    private static Pitch handlePitch(String basenote){
        switch(basenote){
            case("a"):
                return new Pitch('A').transpose(Pitch.OCTAVE);
            case("b"):
                return new Pitch('B').transpose(Pitch.OCTAVE);
            case("c"):
                return new Pitch('C').transpose(Pitch.OCTAVE);
            case("d"):
                return new Pitch('D').transpose(Pitch.OCTAVE);
            case("e"):
                return new Pitch('E').transpose(Pitch.OCTAVE);
            case("f"):
                return new Pitch('F').transpose(Pitch.OCTAVE);
            case("g"):
                return new Pitch('G').transpose(Pitch.OCTAVE);
            default:
                return new Pitch(basenote.charAt(0));
            
        }
    }
    private static int handleAccidental(String accidental){
        switch(accidental){
        
            case("^"):
                return 1;
            case("^^"):
                return 2;
            case("_"):
                return -1;
            case("__"):
                return -2;
            case("="):
                return 0;
            default:
                return 0;
        }
    }
    
    private static int handleOctave(String octave){
        String noWhitespace = octave.replaceAll("\\s", "");
        int octaveTranspose = 0;
        for(char c : noWhitespace.toCharArray()){
            if(c == '\''){
                octaveTranspose += Pitch.OCTAVE;
            }
            if(c == ','){
                octaveTranspose -= Pitch.OCTAVE;
            }

        }
        return octaveTranspose;
    }
}
