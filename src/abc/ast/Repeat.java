package abc.ast;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;

/**
 * An immutable data type representing a section of music that gets played again.
 *
 */
public class Repeat implements Music{
    
    private final Music repeated;
    private final Music firstEnd;
    private final Music secondEnd;
    
    //Abstraction function
    //  Represents a section of music that gets repeated. Differing
    //  endings are represented as different sections of music
    //Rep invariant
    //  repeated != null
    //  firstEnd != null
    //  secondEnd != null
    //Rep Exposure
    //  All fields are private, final, and immutable. Return values are immutable
    
    /**
     * Constructs a Repeat with the same ending
     * @param m Music that's always repeated
     */
    public Repeat(Music m){
        repeated = m;
        firstEnd = new Rest(0);
        secondEnd = new Rest(0);
        checkRep();
    }
    
    /**
     * Constructs a Repeat with multiple endings
     * @param m Music that's always repeated regardless of ending
     * @param first Music representing the first ending
     * @param second Music representing the second ending
     */
    public Repeat(Music m, Music first, Music second){
        repeated = m;
        firstEnd = first;
        secondEnd = second;
        checkRep();
    }
    /**
     * Asserts rep invariant
     */
    private void checkRep(){
        assert repeated != null;
        assert firstEnd != null;
        assert secondEnd != null;
    }
    
    private Music getRepeated(){
        return this.repeated;
    }
    
    private Music getFirstEnd(){
        return this.firstEnd;
    }
    
    private Music getSecondEnd(){
        return this.secondEnd;
    }
    
    @Override
    public double duration() {
        return this.getRepeated().duration() + this.getFirstEnd().duration() 
                + this.getRepeated().duration() + this.getSecondEnd().duration();
    }

    @Override
    public void play(SequencePlayer player, double start) {
        Music repeated = this.getRepeated();
        Music firstEnd = this.getFirstEnd();
        Music secondEnd = this.getSecondEnd();
        
        repeated.play(player, start);
        firstEnd.play(player, start + repeated.duration());
        repeated.play(player, start + repeated.duration() + firstEnd.duration());
        secondEnd.play(player, start + repeated.duration() + firstEnd.duration() + repeated.duration());
    }

    @Override
    public Music transpose(int semitonesUp) {
        return new Repeat(this.getRepeated().transpose(semitonesUp), this.getFirstEnd().transpose(semitonesUp), 
                this.getSecondEnd().transpose(semitonesUp));
    }
    
    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp) {
        return new Repeat(this.getRepeated().transposePitch(pitch, semitonesUp  ), this.getFirstEnd(), this.getSecondEnd());
    }
    
    @Override
    public int hashCode(){
        Double duration = this.duration();
        return duration.hashCode();
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Repeat instances representing the same section 
     *          Music to be repeated in a piece.
     */
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Repeat)) return false;
        Repeat thatRepeat = (Repeat) obj;
        return this.getRepeated().equals(thatRepeat.getRepeated()) 
                && this.getFirstEnd().equals(thatRepeat.getFirstEnd()) 
                && this.getSecondEnd().equals(thatRepeat.getSecondEnd());
    }
    
    /**
     * Outputs a String representation of a Repeat instance in the following form:
     * |: *Music that is repeated* [1 *First End* :| *Music that is repeated* [2 *Second End*
     * @return String representation of the Repeat instance suitable for reading
     */
    @Override
    public String toString(){
        String repeatString = "|: ";
        repeatString = repeatString + this.getRepeated().toString() + " ";
        
        //firstEnd
        repeatString = repeatString + "[1 " + this.getFirstEnd().toString() + " :| ";
        
        //secondEnd
        repeatString = repeatString + "[2 " + this.getSecondEnd();
        
        return repeatString;
    }
}
