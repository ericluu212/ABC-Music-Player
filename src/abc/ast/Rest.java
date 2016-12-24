    package abc.ast;

import abc.sound.*;

/**
 * An immutable data type representing a rest note in music sheet. 
*/
public class Rest implements Music {
    
    private final double duration; 
    
    // Abstraction function
    //    represents one rest of duration duration in music sheet
    // Rep invariant
    //    duration >= 0
    // Safety from rep exposure
    //    all fields are private, final, and immutable. Return values are immutable
    
    /**
     * Make a new instance of Rest
     * @param length the desired length of rest
     */
    public Rest(double duration){
        this.duration = duration;
        checkRep();
    }
    
    private void checkRep(){
        assert duration >= 0;
    }
    
    @Override
    public double duration(){
        return this.duration;
    }
    
    @Override
    public void play(SequencePlayer player, double start){
        return;
    }
    
    @Override
    public Music transpose(int semitonesUp){
        return new Rest(this.duration()); //same duration
    }
    
    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp){
        return new Rest(this.duration()); //same duration
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Rest instances that have same duration
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rest)) return false;
        Rest thatRest = (Rest) obj;
        return this.duration() == thatRest.duration();
    }
    
    @Override
    public int hashCode() {
        Double d = this.duration();
        return d.hashCode();
    }
    
    
    /**
     * Outputs a String representation of a Rest instance: "z" followed by the duration of the rest as a number 
     * @return String representation of Rest instance suitable for reading
     */
    @Override
    public String toString() {
        return "z" + duration; // to be implemented
    }
}