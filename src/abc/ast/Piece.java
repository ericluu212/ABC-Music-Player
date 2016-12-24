package abc.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;

/**
 * An immutable data type representing a piece of music. 
*/
public class Piece implements Music{
    
    private final List<Voice> voices;
    
    //Abstraction function
    //  Represents a piece of music with voices that each have some melody. The voices can be of different duration
    //Rep invariant
    //  voices != null
    //  values in voice != null
    //  voices.size() > 0
    //Rep exposure
    //  All fields are private and final. 
    //  voice is mutable, but this class has no mutators methods, and all return values of methods are immutable.
    
    public Piece(List<Voice> voices){
        this.voices = voices;
        checkRep();
    }
    
    /**
     * Asserts check rep
     */
    private void checkRep(){
        assert this.voices != null;
        assert this.voices.size() > 0;
        for (Voice v: this.voices){
            assert v != null;
        }
    }
    
    @Override
    public double duration() {
        double maxDuration = 0;
        for (Voice v : voices){
            if(v.duration() >= maxDuration){
              maxDuration = v.duration();  
            }
        }
        return maxDuration;
    }

    @Override
    public void play(SequencePlayer player, double start) {
        for(Voice v : voices){
            v.play(player, start);
        }
    }

    @Override
    public Music transpose(int semitonesUp) {
        List<Voice> transposed = new ArrayList<>();
        for(Voice v : voices){
            transposed.add( (Voice) v.transpose(semitonesUp));
        }
        return new Piece(transposed);
    }

    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp) {
        List<Voice> transposedPitch = new ArrayList<>();
        for(Voice v : voices){
            transposedPitch.add( (Voice) v.transposePitch(pitch, semitonesUp));
        }
        return new Piece(transposedPitch);
    }
    
    @Override
    public int hashCode(){
        int hash = 0;
        for(Voice v : voices){
            hash += v.hashCode();
        }
        return hash;
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Piece instances representing the same piece of music
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Piece)) return false;
        Piece thatPiece = (Piece) obj;
        
        if(!(this.voices.size() == thatPiece.voices.size())){
            return false;
        }
        
        for(int i = 0; i < this.voices.size(); i++){
            if(!(this.voices.get(i).equals(thatPiece.voices.get(i)))){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Outputs a String representation of a Chord instance: The voices in the piece and their corresponding 
     * melodic lines, each separate by newlines
     * @return String representation of the Chord instance suitable for reading
     */
    @Override
    public String toString(){
        String piece = "";
        for (Voice v : voices){
            piece += v.toString();
            piece += "\n";
        }
        return piece;
    }

}
