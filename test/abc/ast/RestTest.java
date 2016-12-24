package abc.ast;

import abc.sound.*;
import static org.junit.Assert.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * 
 * Tests for the Rest concrete variant of the Music data type.
 *
 */
public class RestTest {
    
    //protected globals
    protected static final int OCTAVE = 12;
    
    protected static final Rest ZERO_DURATION = new Rest(0);
    protected static final Rest ONE_DURATION = new Rest(1.0);
    protected static final Rest PI_DURATION = new Rest(3.14);
    
    protected static final Rest ANOTHER_ZERO_DURATION = new Rest(0.0);
    protected static final Rest ANOTHER_ONE_DURATION = new Rest(1);
    
    protected static final Rest THIRD_ONE_DURATION = new Rest(1);
    
    protected static final Pitch MIDDLE_C = new Pitch('C'); 
    protected static final Pitch C_FLAT = new Pitch('C').transpose(1); 
    protected static final Pitch C_SHARP = new Pitch('C').transpose(-1); 
    protected static final Pitch C_ONE_OCTAVE_ABOVE = new Pitch('C').transpose(OCTAVE); 
    protected static final Pitch C_ONE_OCTAVE_BELOW = new Pitch('C').transpose(-OCTAVE);
    
    protected static final Note MIDDLE_C_NOTE = new Note(5.0, Pitch.MIDDLE_C);
    protected static final Note C_FLAT_NOTE = new Note(5.0, Pitch.MIDDLE_C);
    
    /*
     * Testing strategy for duration():
     *      duration is zero
     *      duration is whole number
     *      duration is not whole number
     */
    
    //covers zero duration
    @Test
    public void zeroDuration(){
        assertEquals("Duration should be 0", 0, ZERO_DURATION.duration(), 0);
    }
    //covers whole number duration
    @Test
    public void wholeNumberDuration(){
        assertEquals("Duration should be 1.0", 1, ONE_DURATION.duration(), 0);
    }
    //covers not whole number duration
    @Test
    public void notWholeNumbberDuration(){
        assertEquals("Duration should be 3.14", 3.14, PI_DURATION.duration(), 0);
    }
    
    /*
     * Testing strategy for transpose(int semitones):
     *      semitones is 0, >0, <0
     */
    
    //covers semitones = 0
    @Test
    public void zeroDurationTranspose(){
        assertEquals("The original and transpose should be equal", ZERO_DURATION, ZERO_DURATION.transpose(0));
    }
    //covers semitones > 0
    @Test
    public void positiveDurationTranspose(){
        assertEquals("The original and transpose should be equal", ONE_DURATION, ONE_DURATION.transpose(1));
    }
    //covers semitones < 0
    @Test
    public void negativeDurationTranspose(){
        assertEquals("The original and transpose should be equal", PI_DURATION, PI_DURATION.transpose(-2));
    }
    
    /*
     * Testing strategy for transposePitch(Pitch pitch, int semitones):
     *      semitones is 0, >0, <0
     *      Pitch: does not matter because Rests have no pitch property
     */
    //covers semitones = 0
    @Test
    public void transposePitchZeroSemitones(){
        assertEquals("The original and transpose should be equal", ZERO_DURATION, ZERO_DURATION.transposePitch(MIDDLE_C, 0));
    }
    //covers semitones > 0
    @Test
    public void transposePitchPositiveSemitones(){
        assertEquals("The original and transpose should be equal", ONE_DURATION, ONE_DURATION.transposePitch(MIDDLE_C, 1));
    }
    //covers semitones < 0
    @Test
    public void transposePitchNegativeSemitones(){
        assertEquals("The original and transpose should be equal", PI_DURATION, PI_DURATION.transposePitch(MIDDLE_C, -2));
    }
    
    /*
     * Testing strategy for toString():
     *     duration: 0, >0
     */
    //covers duration = 0
    @Test
    public void stringZeroDuration(){
        assertEquals("String should be z0.0", "z0.0", ZERO_DURATION.toString());
    }
    //covers duration > 0
    @Test
    public void stringPiDuration(){
        assertEquals("String should be z3.14", "z3.14", PI_DURATION.toString());
    }

    /*
     * Testing strategy for equals(Object obj):
     *     Obj: Not instance of Rest
     *          Is instance of Rest:
     *              different duration
     *              same duration
     *     Equality must also be transitive, reflexive, and symmetric
     */
     
    //covers obj not instance of rest
    @Test
    public void equalsObjNotInstance(){
        assertEquals("Should be false", false, ZERO_DURATION.equals((double)2));
    }
    //covers obj is instance of rest, different duration
    @Test
    public void equalsDifferentDuration(){
        assertEquals("Should be false", false, ZERO_DURATION.equals(PI_DURATION));
    }
    //covers obj is instance of rest, same duration
    @Test
    public void equalsSameDuration(){
        assertEquals("Should be true", true, ONE_DURATION.equals(ANOTHER_ONE_DURATION));
    }
    //covers transitivity, reflexivity, and symmetry
    public void equalsTransitiveReflexiveSymmetric(){
        //reflexive
        assertEquals("Should be true", true, ONE_DURATION.equals(ONE_DURATION));
        
        //symmetry
        assertEquals("Should be true", true, ONE_DURATION.equals(ANOTHER_ONE_DURATION));
        assertEquals("Should be true", true, ANOTHER_ONE_DURATION.equals(ONE_DURATION));
        
        //transitivity
        assertEquals("Should be true", true, ONE_DURATION.equals(ANOTHER_ONE_DURATION));
        assertEquals("Should be true", true, ANOTHER_ONE_DURATION.equals(THIRD_ONE_DURATION));
        assertEquals("Should be true", true, ONE_DURATION.equals(THIRD_ONE_DURATION));
    }
    
    /*
     * Testing strategy for hashCode():
     *     Equal Rest instances will have same hashCode
     */
    
    //equal Rests should yield same hashCode
    @Test
    public void equalsSameHashCode(){
        assertEquals("Should be true", true, ONE_DURATION.equals(ANOTHER_ONE_DURATION));
        assertEquals("Hashcode should be same", ONE_DURATION.hashCode(), ANOTHER_ONE_DURATION.hashCode());
    }
}
