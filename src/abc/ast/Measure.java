package abc.ast;

import java.util.List;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;

/**
 * An immutable measure of some music with duration given by a meter. 
 *
 */
public class Measure implements Music {

    private final Music music;
    private final double meter;
    
    // Abstraction function:
    //   Represents a measure with music representing the music to be played in this measure
    //   and meter representing the meter of the piece in which this measure occurs
    // Rep invariant:
    //   music != null
    //   assert meter >= 0
    // Safety from rep exposure:
    //   all fields are private, final, and immutable
    
    /**
     * Make a new measure with some music and a specified meter
     * @param music the music contained in this measure
     * @param meter the meter of the music as a double (e.g. 2/4 = 0.5)
     */
    public Measure(Music music, double meter) {
        this.music = music;
        this.meter = meter;
        checkRep();
    }
    
    private void checkRep(){
        assert this.music != null;
        assert this.meter >= 0;
    }
    
    private Music getMusic(){
        return this.music;
    }
    
    @Override
    public double duration() {
        return this.getMusic().duration();
    }

    @Override
    public void play(SequencePlayer player, double start) {
        this.getMusic().play(player, start);
        
    }

    @Override
    public Music transpose(int semitonesUp) {
        return new Measure(this.getMusic().transpose(semitonesUp), this.duration());
    }

    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp) {
        return new Measure(this.getMusic().transposePitch(pitch, semitonesUp), this.duration());
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Measure instances representing the same measure in music
     *          with same duration
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Measure)) return false;
        Measure thatMeasure = (Measure) obj;
        return this.getMusic().equals(thatMeasure.getMusic()) && this.duration() == thatMeasure.duration();
    }
    
    @Override
    public int hashCode() {
        Double d = this.duration();
        return d.hashCode();
    }
    
    /**
     * Outputs a String representation of a Measure instance: the music to be played in the measure enclosed by "|" symbols
     * @return String representation of the Measure instance suitable for reading
     */
    @Override
    public String toString() {
        return "|" + this.getMusic().toString() + "|"; 
    }
    
}