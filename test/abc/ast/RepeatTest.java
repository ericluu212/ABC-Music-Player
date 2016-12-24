package abc.ast;

import abc.sound.*;
import static org.junit.Assert.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * 
 * Tests for the Repeat concrete variant of the Music data type.
 *
 */
public class RepeatTest {
    
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
    
    //Repeat instances
    protected static final Repeat ZERO_DURATION_REPEAT = new Repeat(ZERO_DURATION_MEASURE);
    protected static final Repeat WHOLE_DURATION_REPEAT = new Repeat(WHOLE_DURATION_MEASURE);
    protected static final Repeat DECIMAL_DURATION_REPEAT = new Repeat(DECIMAL_DURATION_MEASURE);
    protected static final Repeat ANOTHER_DECIMAL_DURATION_REPEAT = new Repeat(DECIMAL_DURATION_MEASURE);
    protected static final Repeat THIRD_DECIMAL_DURATION_REPEAT = new Repeat(DECIMAL_DURATION_MEASURE);
    
    protected static final Repeat DIFFERENT_END_REPEAT = new Repeat(WHOLE_DURATION_MEASURE, DECIMAL_DURATION_MEASURE, ANOTHER_WHOLE_DURATION_MEASURE);
    protected static final Repeat DIFFERENT_END_REPEAT_SECOND_END_NOTE = new Repeat(WHOLE_DURATION_MEASURE, DECIMAL_DURATION_MEASURE, MIDDLE_G_NOTE);
    protected static final Repeat DIFFERENT_END_ZERO_DURATION_REPEAT = new Repeat(ZERO_DURATION_MEASURE, 
            ZERO_DURATION, CONCAT_ZERO_DURATION_ZERO_DURATION);
    /*
     * Testing strategy for duration():
     *      duration is zero
     *      duration is whole number
     *      duration is not whole number
     */    
    //covers zero duration
    @Test
    public void zeroDuration(){
        assertEquals("Duration should be 0.0", 0, ZERO_DURATION_REPEAT.duration(), 0);
    }
    //covers whole number duration
    @Test
    public void wholeNumberDuration(){
        assertEquals("Duration should be 32.0", 32.0, WHOLE_DURATION_REPEAT.duration(), 0);
    }
    //covers not whole number duration
    @Test
    public void notWholeNumbberDuration(){
        assertEquals("Duration should be 33.28", 33.28, DECIMAL_DURATION_REPEAT.duration(), 0);
    }
    
    /*
     * Testing strategy for transpose(int semitones):
     *      semitones is 0, >0, <0
     */
    //covers semitones = 0
    @Test
    public void zeroTranspose(){
        Repeat same = (Repeat) DECIMAL_DURATION_REPEAT.transpose(0);
        assertEquals("The original and transpose should be equal", DECIMAL_DURATION_REPEAT, same);
    }
    //covers semitones > 0
    @Test
    public void positiveTranspose(){
        Repeat transposedPos = (Repeat) WHOLE_DURATION_REPEAT.transpose(1);
        Repeat expected = new Repeat(WHOLE_DURATION_MEASURE.transpose(1));
        assertEquals("Expected transpose positive failed", expected, transposedPos);
    }
    //covers semitones < 0
    @Test
    public void negativeTranspose(){
        Repeat transposedNeg = (Repeat) DIFFERENT_END_REPEAT.transpose(-2);
        Repeat expected = new Repeat(WHOLE_DURATION_MEASURE.transpose(-2), 
                DECIMAL_DURATION_MEASURE.transpose(-2), ANOTHER_WHOLE_DURATION_MEASURE.transpose(-2));
        assertEquals("Expected transpose negative failed", expected, transposedNeg);
    }
    
    /*
     * Testing strategy for transposePitch(Pitch pitch, int semitones):
     *      semitones is 0, >0, <0
     *      Pitch: 
     *          Repeat has matching pitch, does not have matching pitch
     */
    //covers semitones = 0, no matching pitch
    @Test
    public void transposePitchZeroSemitonesNoMatchingPitch(){
        Repeat same = (Repeat) ZERO_DURATION_REPEAT.transposePitch(C_FLAT, 0);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_REPEAT, same);
    }
    //covers semitones = 0, matching pitch
    @Test
    public void transposePitchZeroSemitonesMatchingPitch(){
        Repeat same = (Repeat) WHOLE_DURATION_REPEAT.transposePitch(MIDDLE_C, 0);
        assertEquals("The original and transpose should be equal", WHOLE_DURATION_REPEAT, same);
    }
    //covers semitones > 0, no matching pitch
    @Test
    public void transposePitchPositiveSemitonesNoMatchingPitch(){
        Repeat same = (Repeat) ZERO_DURATION_REPEAT.transposePitch(C_FLAT, 10);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_REPEAT, same);
    }
    //covers semitones > 0, matching pitch
    @Test
    public void transposePitchPositiveSemitonesMatchingPitch(){
        Repeat diff = (Repeat) DECIMAL_DURATION_REPEAT.transposePitch(MIDDLE_C, 10);
        Repeat expected = new Repeat(DECIMAL_DURATION_MEASURE.transposePitch(MIDDLE_C, 10));
        assertEquals("Expected transposePitch positive, matching Pitch failed", expected, diff);
    }
    //covers semitones < 0, no matching pitch
    @Test
    public void transposePitchNegativeSemitonesNoMatchingPitch(){
        Repeat same = (Repeat) ZERO_DURATION_REPEAT.transposePitch(C_FLAT, -3);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_REPEAT, same);
    }
    //covers semitones < 0, matching pitch
    @Test
    public void transposePitchNegativeSemitonesMatchingPitch(){
        Repeat diff = (Repeat) WHOLE_DURATION_REPEAT.transposePitch(MIDDLE_C, -3);
        Repeat expected = new Repeat(WHOLE_DURATION_MEASURE.transposePitch(MIDDLE_C, -3));
        assertEquals("Expected transposePitch negative, matching Pitch failed", expected, diff);
    }
    
    /*
     * Testing strategy for toString():
     *     duration: 0, >0
     *     Repeat has same ending, different ending
     */
    //covers duration = 0, same ending
    @Test
    public void stringZeroDurationSameEnding(){
        assertEquals("String should be |: |z0.0 z0.0 z0.0 z0.0| [1 z0.0 :| [2 z0.0", 
                "|: |z0.0 z0.0 z0.0 z0.0| [1 z0.0 :| [2 z0.0", ZERO_DURATION_REPEAT.toString());
    }
    //covers duration > 0, same ending
    @Test
    public void stringPiDurationSameEnding(){
        assertEquals("String should be |: |B,5.0 z1.0 C5.0 B,5.0| [1 z0.0 :| [2 z0.0", 
                "|: |B,5.0 z1.0 C5.0 B,5.0| [1 z0.0 :| [2 z0.0", WHOLE_DURATION_REPEAT.toString());
    }
    //covers duration = 0, different ending
    @Test
    public void stringZeroDurationDifferentEnding(){
        assertEquals("String should be |: |z0.0 z0.0 z0.0 z0.0| [1 z0.0 :| [2 z0.0 z0.0", 
                "|: |z0.0 z0.0 z0.0 z0.0| [1 z0.0 :| [2 z0.0 z0.0", DIFFERENT_END_ZERO_DURATION_REPEAT.toString());
    }
    //covers duration > 0, different ending
    @Test
    public void stringPiDurationDifferentEnding(){
        assertEquals("String should be |: |B,5.0 z1.0 C5.0 B,5.0| [1 |C5.0 B,5.0 z3.14 G3.5| :| [2 |B,5.0 z1.0 C5.0 B,5.0|",
                "|: |B,5.0 z1.0 C5.0 B,5.0| [1 |C5.0 B,5.0 z3.14 G3.5| :| [2 |B,5.0 z1.0 C5.0 B,5.0|", DIFFERENT_END_REPEAT.toString());
    }

    /*
     * Testing strategy for equals(Object obj):
     *     Obj: Not instance of Repeat
     *          Is instance of Repeat:
     *              The Musics in each of the two Repeats being compared are not equal
     *              The Musics in each of the two Repeats being compared equal
     *     Equality must also be transitive, reflexive, and symmetric
     */
    //covers obj not instance of Repeat
    @Test
    public void equalsObjNotInstance()
    {
        assertEquals("Should be false", false, ZERO_DURATION_REPEAT.equals(ZERO_DURATION));
    }
    //covers obj is instance of Repeat, respective Musics are not equal
    @Test
    public void equalsDifferentDuration(){
        assertEquals("Should be false", false, ZERO_DURATION_REPEAT .equals(WHOLE_DURATION_REPEAT));
    }
    //covers obj is instance of Repeat, respective Musics are equal
    @Test
    public void equalsSameDuration(){
        assertEquals("Should be true", true, DECIMAL_DURATION_REPEAT.equals(ANOTHER_DECIMAL_DURATION_REPEAT));
    }
    //covers transitivity, reflexivity, and symmetry
    public void equalsTransitiveReflexiveSymmetric(){
        //reflexive
        assertEquals("Should be true", true, WHOLE_DURATION_REPEAT.equals(WHOLE_DURATION_REPEAT));
        
        //symmetry
        assertEquals("Should be true", true, DECIMAL_DURATION_REPEAT.equals(ANOTHER_DECIMAL_DURATION_REPEAT));
        assertEquals("Should be true", true, ANOTHER_DECIMAL_DURATION_REPEAT.equals(DECIMAL_DURATION_REPEAT));
        //transitivity
        assertEquals("Should be true", true, DECIMAL_DURATION_REPEAT.equals(ANOTHER_DECIMAL_DURATION_REPEAT));
        assertEquals("Should be true", true, ANOTHER_WHOLE_DURATION_MEASURE.equals(THIRD_DECIMAL_DURATION_REPEAT));
        assertEquals("Should be true", true, DECIMAL_DURATION_REPEAT.equals(THIRD_DECIMAL_DURATION_REPEAT));
    }
  
    /*
     * Testing strategy for hashCode():
     *     Equal Repeat instances will have same hashCode
     */
    @Test
    public void equalsSameHashCode(){
        assertEquals("Should be true", true, DECIMAL_DURATION_REPEAT.equals(ANOTHER_DECIMAL_DURATION_REPEAT));
        assertEquals("Hashcode should be same", DECIMAL_DURATION_REPEAT.hashCode(), ANOTHER_DECIMAL_DURATION_REPEAT.hashCode());
    }
}
