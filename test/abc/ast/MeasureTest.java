package abc.ast;

import abc.sound.*;

import static org.junit.Assert.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * 
 * Tests for the Measure concrete variant of the Music data type.
 *
 */
public class MeasureTest {
    
    //protected globals
    protected static final int OCTAVE = 12;
    
    protected static final Rest ZERO_DURATION = new Rest(0);
    protected static final Rest ONE_DURATION = new Rest(1.0);
    protected static final Rest PI_DURATION = new Rest(3.14);
    
    protected static final Rest ANOTHER_ZERO_DURATION = new Rest(0.0);
    protected static final Rest ANOTHER_ONE_DURATION = new Rest(1);
    
    protected static final Pitch MIDDLE_C = new Pitch('C'); 
    protected static final Pitch MIDDLE_G = new Pitch('G'); 
    protected static final Pitch C_FLAT = new Pitch('C').transpose(-1);  //Outputs B,
    protected static final Pitch C_SHARP = new Pitch('C').transpose(1); 
    protected static final Pitch C_ONE_OCTAVE_ABOVE = new Pitch('C').transpose(OCTAVE); 
    protected static final Pitch C_ONE_OCTAVE_BELOW = new Pitch('C').transpose(-OCTAVE);
    
    protected static final Note MIDDLE_C_NOTE = new Note(5.0, MIDDLE_C);
    protected static final Note C_FLAT_NOTE = new Note(5.0, C_FLAT);
    protected static final Note MIDDLE_G_NOTE = new Note(3.5, MIDDLE_G);

    protected static final Concat CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    protected static final Concat ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    protected static final Concat THIRD_CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    
    protected static final Concat CONCAT_ZERO_DURATION_C_FLAT_NOTE = new Concat(ZERO_DURATION, C_FLAT_NOTE);
    protected static final Concat CONCAT_MIDDLE_C_NOTE_PI_DURATION = new Concat(MIDDLE_C_NOTE, PI_DURATION);
    protected static final Concat CONCAT_C_FLAT_NOTE_ONE_DURATION = new Concat(C_FLAT_NOTE, ONE_DURATION);
    protected static final Concat CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE = new Concat(MIDDLE_C_NOTE, C_FLAT_NOTE);
    
    //Measure instances
    protected static final Measure ZERO_DURATION_MEASURE = new Measure(new Concat(new Concat(CONCAT_ZERO_DURATION_ZERO_DURATION,
            ZERO_DURATION), ZERO_DURATION), 0.0);
    protected static final Measure WHOLE_DURATION_MEASURE = new Measure(new Concat(new Concat(CONCAT_C_FLAT_NOTE_ONE_DURATION,
            MIDDLE_C_NOTE), C_FLAT_NOTE), 16.0);
    protected static final Measure DECIMAL_DURATION_MEASURE = new Measure(new Concat(new Concat(CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE,
            PI_DURATION), MIDDLE_G_NOTE), 16.64);
    protected static final Measure ANOTHER_WHOLE_DURATION_MEASURE = new Measure(new Concat(new Concat(CONCAT_C_FLAT_NOTE_ONE_DURATION,
            MIDDLE_C_NOTE), C_FLAT_NOTE), 16.0);
    protected static final Measure THIRD_WHOLE_DURATION_MEASURE = new Measure(new Concat(new Concat(CONCAT_C_FLAT_NOTE_ONE_DURATION,
            MIDDLE_C_NOTE), C_FLAT_NOTE), 16.0);
    
    /*
     * Testing strategy for duration():
     *      duration is zero
     *      duration is whole number
     *      duration is not whole number
     */    
    //covers zero duration
    @Test
    public void zeroDuration(){
        assertEquals("Duration should be 0.0", 0, ZERO_DURATION_MEASURE.duration(), 0);
    }
    //covers whole number duration
    @Test
    public void wholeNumberDuration(){
        assertEquals("Duration should be 16.0", 16.0, WHOLE_DURATION_MEASURE.duration(), 0);
    }
    //covers not whole number duration
    @Test
    public void notWholeNumbberDuration(){
        assertEquals("Duration should be 16.64", 16.64, DECIMAL_DURATION_MEASURE.duration(), 0);
    }
    
    /*
     * Testing strategy for transpose(int semitones):
     *      semitones is 0, >0, <0
     */
    //covers semitones = 0
    @Test
    public void zeroTranspose(){
        Measure same = (Measure) ZERO_DURATION_MEASURE.transpose(0);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_MEASURE, same);
    }
    //covers semitones > 0
    @Test
    public void positiveTranspose(){
        Measure transposedPos = (Measure) DECIMAL_DURATION_MEASURE.transpose(1);
        Measure expected = new Measure(new Concat(new Concat(CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE,
                PI_DURATION), MIDDLE_G_NOTE).transpose(1), 16.64);
        assertEquals("Expected transpose positive failed", expected, transposedPos);
    }
    //covers semitones < 0
    @Test
    public void negativeTranspose(){
        Measure transposedNeg = (Measure) WHOLE_DURATION_MEASURE.transpose(-2);
        Measure expected = new Measure(new Concat(new Concat(CONCAT_C_FLAT_NOTE_ONE_DURATION,
                MIDDLE_C_NOTE), C_FLAT_NOTE).transpose(-2), 16.0);
        assertEquals("Expected transpose negative failed", expected, transposedNeg);
    }
    
    /*
     * Testing strategy for transposePitch(Pitch pitch, int semitones):
     *      semitones is 0, >0, <0
     *      Pitch: 
     *          Measure has matching pitch, does not have matching pitch
     */
    //covers semitones = 0, no matching pitch
    @Test
    public void transposePitchZeroSemitonesNoMatchingPitch(){
        Measure same = (Measure) ZERO_DURATION_MEASURE.transposePitch(C_FLAT, 0);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_MEASURE, same);
    }
    //covers semitones = 0, matching pitch
    @Test
    public void transposePitchZeroSemitonesMatchingPitch(){
        Measure same = (Measure) WHOLE_DURATION_MEASURE.transposePitch(MIDDLE_C, 0);
        assertEquals("The original and transpose should be equal", WHOLE_DURATION_MEASURE, same);
    }
    //covers semitones > 0, no matching pitch
    @Test
    public void transposePitchPositiveSemitonesNoMatchingPitch(){
        Measure same = (Measure) ZERO_DURATION_MEASURE.transposePitch(C_FLAT, 10);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_MEASURE, same);
    }
    //covers semitones > 0, matching pitch
    @Test
    public void transposePitchPositiveSemitonesMatchingPitch(){
        Measure diff = (Measure) DECIMAL_DURATION_MEASURE.transposePitch(MIDDLE_C, 10);
        Measure expected = new Measure(new Concat(new Concat(CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE,
                PI_DURATION), MIDDLE_G_NOTE).transposePitch(MIDDLE_C, 10), 16.64);
        assertEquals("Expected transposePitch positive, matching Pitch failed", expected, diff);
    }
    //covers semitones < 0, no matching pitch
    @Test
    public void transposePitchNegativeSemitonesNoMatchingPitch(){
        Measure same = (Measure) ZERO_DURATION_MEASURE.transposePitch(C_FLAT, -3);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_MEASURE, same);
    }
    //covers semitones < 0, matching pitch
    @Test
    public void transposePitchNegativeSemitonesMatchingPitch(){
        Measure diff = (Measure) WHOLE_DURATION_MEASURE.transposePitch(MIDDLE_C, -3);
        Measure expected = new Measure(new Concat(new Concat(CONCAT_C_FLAT_NOTE_ONE_DURATION,
                MIDDLE_C_NOTE), C_FLAT_NOTE).transposePitch(MIDDLE_C, -3), 16.0);
        assertEquals("Expected transposePitch negative, matching Pitch failed", expected, diff);
    }
    
    /*
     * Testing strategy for toString():
     *     duration: 0, >0
     */
    //covers duration = 0
    @Test
    public void stringZeroDuration(){
        assertEquals("String should be |z0.0 z0.0 z0.0 z0.0|", "|z0.0 z0.0 z0.0 z0.0|", ZERO_DURATION_MEASURE.toString());
    }
    //covers duration > 0
    @Test
    public void stringNonzeroDuration(){
        assertEquals("String should be |C5.0 B,5.0 z3.14 G3.5|", "|C5.0 B,5.0 z3.14 G3.5|", DECIMAL_DURATION_MEASURE.toString());
    }

    /*
     * Testing strategy for equals(Object obj):
     *     Obj: Not instance of Measure
     *          Is instance of Measure:
     *              The Musics in each of the two Measures being compared are not equal
     *              The Musics in each of the two Measures being compared equal
     *     Equality must also be transitive, reflexive, and symmetric
     */
    //covers obj not instance of Measure
    @Test
    public void equalsObjNotInstance()
    {
        assertEquals("Should be false", false, ZERO_DURATION_MEASURE.equals(ZERO_DURATION));
    }
    //covers obj is instance of Measure, respective Musics are not equal
    @Test
    public void equalsDifferentDuration(){
        assertEquals("Should be false", false, ZERO_DURATION_MEASURE .equals(WHOLE_DURATION_MEASURE));
    }
    //covers obj is instance of Measure, respective Musics are equal
    @Test
    public void equalsSameDuration(){
        assertEquals("Should be true", true, WHOLE_DURATION_MEASURE.equals(ANOTHER_WHOLE_DURATION_MEASURE));
    }
    //covers transitivity, reflexivity, and symmetry
    public void equalsTransitiveReflexiveSymmetric(){
        //reflexive
        assertEquals("Should be true", true, DECIMAL_DURATION_MEASURE.equals(DECIMAL_DURATION_MEASURE));
        
        //symmetry
        assertEquals("Should be true", true, WHOLE_DURATION_MEASURE.equals(ANOTHER_WHOLE_DURATION_MEASURE));
        assertEquals("Should be true", true, ANOTHER_WHOLE_DURATION_MEASURE.equals(WHOLE_DURATION_MEASURE));
        
        //transitivity
        assertEquals("Should be true", true, WHOLE_DURATION_MEASURE.equals(ANOTHER_WHOLE_DURATION_MEASURE));
        assertEquals("Should be true", true, ANOTHER_WHOLE_DURATION_MEASURE.equals(THIRD_WHOLE_DURATION_MEASURE));
        assertEquals("Should be true", true, WHOLE_DURATION_MEASURE.equals(THIRD_WHOLE_DURATION_MEASURE));
    }
  
    /*
     * Testing strategy for hashCode():
     *     Equal Measure instances will have same hashCode
     */
    @Test
    public void equalsSameHashCode(){
        assertEquals("Should be true", true, WHOLE_DURATION_MEASURE.equals(ANOTHER_WHOLE_DURATION_MEASURE));
        assertEquals("Hashcode should be same", WHOLE_DURATION_MEASURE.hashCode(), ANOTHER_WHOLE_DURATION_MEASURE.hashCode());
    }
}
