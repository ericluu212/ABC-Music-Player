package abc.ast;

import java.util.List;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;

/**
 * An immutable data type that represents one voice's melody.
 */
public class Voice implements Music{
    
    private final Music melody;
    private final String name;
    
    //Abstraction function
    //  Represents a single voice with melody melody and name name in sheet music
    //Rep invariant
    //  melody != null
    //  name != null
    //Rep exposure
    //  All fields are final, private, and immutable. Return values are immutable
   
    public Voice(Music m, String s){
        this.melody = m;
        this.name = s;
        checkRep();
    }
    
    /**
     * Asserts rep invariant
     */
    private void checkRep(){
        assert melody != null;
        assert name != null;
    }
    
    private Music getMelody(){
        return this.melody;
    }
    
    private String getName(){
        return this.name;
    }
    
    @Override
    public double duration() {
        return this.getMelody().duration();
    }

    @Override
    public void play(SequencePlayer player, double start) {
        this.getMelody().play(player, start);
    }

    @Override
    public Music transpose(int semitonesUp) {
        return new Voice(this.getMelody().transpose(semitonesUp), this.getName());
    }

    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp) {
        return new Voice(this.getMelody().transposePitch(pitch, semitonesUp), this.getName());
    }
    
    @Override
    public int hashCode(){
        return this.getName().hashCode() + this.getMelody().hashCode();
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Voice instances representing the same voice in music,
     *          meaning they have the same melodic lines and the same labels
     */
    @Override 
    public boolean equals(Object obj){
        if (!(obj instanceof Voice)) return false;
        Voice thatVoice = (Voice) obj;
        return this.getMelody().equals(thatVoice.getMelody()) && this.getName().equals(thatVoice.getName());
    }
    
    /**
     * Outputs a String representation of a Voice instance: "V: " followed by name of voice followed by newline character
     * followed by the voice's melodic sequence on a new line 
     * @return String representation of the Voice instance suitable for reading
     */
    @Override
    public String toString(){
        String out = "V: " + this.name + "\n";
        out += this.getMelody().toString();
        return out;
    }
}
