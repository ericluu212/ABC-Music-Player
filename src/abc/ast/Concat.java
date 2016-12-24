package abc.ast;

import abc.sound.Pitch;

import abc.sound.SequencePlayer;

/**
 * Concat is an immutable data type that represents two pieces of music
 * that are played sequentially
 */
public class Concat implements Music{
    
    private final Music first;
    private final Music second;
    
    //Abstraction function
    //  Represents two pieces of music that are played one after another
    //Rep invariant
    //  first != null
    //  second != null
    //Rep exposure
    //  All fields are private, final and immutable. All return values are immutable 
    
    /**
     * Asserts rep invariant
     */
    private void checkRep(){
        assert first != null;
        assert second != null;
    }
    
    /**
     * Constructs a Concat object
     * @param first Music that is played first
     * @param second Music that is played second
     */
    public Concat(Music first, Music second){
        this.first = first;
        this.second = second;
        checkRep();
    }
    
    /**
     * Gets the first piece of music being concatenated
     * @return a Music representing the first piece of music being concatenated
     */
    public Music getFirstMusic(){
        return this.first;
    }
    
    /**
     * Gets the second piece of music being concatenated
     * @return a Music representing the second piece of music being concatenated
     */
    public Music getSecondMusic(){
        return this.second;
    }
    
    @Override
    public double duration() {
        return first.duration() + second.duration();
    }

    @Override
    public void play(SequencePlayer player, double start) {
        first.play(player, start);
        second.play(player, start + first.duration());
    }

    @Override
    public Music transpose(int semitonesUp) {
        return new Concat(first.transpose(semitonesUp), second.transpose(semitonesUp));
    }
    
    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp) {
        return new Concat(first.transposePitch(pitch, semitonesUp), second.transposePitch(pitch, semitonesUp));
    }
    
    @Override
    public int hashCode(){
        return first.hashCode() + second.hashCode();
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Concat instances whose first and second respective pieces of Music
     *          played sequentially are equal
     */
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Concat)) return false;
        Concat thatConcat = (Concat) obj;
        return ( this.getFirstMusic().equals(thatConcat.getFirstMusic()) &&
                this.getSecondMusic().equals(thatConcat.getSecondMusic()) );
    }
    
    /**
     * Outputs a String representation of a Concat instance: the two concatenated pieces of Music played 
     * sequentially in String form
     * @return String representation of the Concat instance suitable for reading
     */
    @Override
    public String toString(){
        return first.toString() + " " + second.toString();
    }
}
