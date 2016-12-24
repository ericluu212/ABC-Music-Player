package abc.ast;

import abc.sound.*;
import static org.junit.Assert.*;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * tutututututututu
 * Tests for the Concat concrete variant of the Music data type.
 *
 */
public class ConcatTest {
    
    //protected globals
    protected static final int OCTAVE = 12;
    
    protected static final Rest ZERO_DURATION = new Rest(0);
    protected static final Rest ONE_DURATION = new Rest(1.0);
    protected static final Rest PI_DURATION = new Rest(3.14);
    
    protected static final Rest ANOTHER_ZERO_DURATION = new Rest(0.0);
    protected static final Rest ANOTHER_ONE_DURATION = new Rest(1);
    
    protected static final Pitch MIDDLE_C = new Pitch('C'); 
    protected static final Pitch C_FLAT = new Pitch('C').transpose(-1); //Outputs B,
    protected static final Pitch C_SHARP = new Pitch('C').transpose(1); 
    protected static final Pitch C_ONE_OCTAVE_ABOVE = new Pitch('C').transpose(OCTAVE); 
    protected static final Pitch C_ONE_OCTAVE_BELOW = new Pitch('C').transpose(-OCTAVE);
    
    protected static final Note MIDDLE_C_NOTE = new Note(5.0, MIDDLE_C);
    protected static final Note C_FLAT_NOTE = new Note(5.0, C_FLAT);

    protected static final Concat CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    protected static final Concat ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    protected static final Concat THIRD_CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    
    protected static final Concat CONCAT_ZERO_DURATION_C_FLAT_NOTE = new Concat(ZERO_DURATION, C_FLAT_NOTE);
    protected static final Concat CONCAT_MIDDLE_C_NOTE_PI_DURATION = new Concat(MIDDLE_C_NOTE, PI_DURATION);
    protected static final Concat CONCAT_C_FLAT_NOTE_ONE_DURATION = new Concat(C_FLAT_NOTE, ONE_DURATION);
    protected static final Concat CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE = new Concat(MIDDLE_C_NOTE, C_FLAT_NOTE);
    
    /*
     * Testing strategy for duration():
     *      duration is zero
     *      duration is whole number
     *      duration is not whole number
     */    
    //covers zero duration
    @Test
    public void zeroDuration(){
        assertEquals("Duration should be 0.0", 0, CONCAT_ZERO_DURATION_ZERO_DURATION.duration(), 0);
    }
    //covers whole number duration
    @Test
    public void wholeNumberDuration(){
        assertEquals("Duration should be 5.0", 5.0, CONCAT_ZERO_DURATION_C_FLAT_NOTE.duration(), 0);
    }
    //covers not whole number duration
    @Test
    public void notWholeNumbberDuration(){
        assertEquals("Duration should be 8.14", 8.14, CONCAT_MIDDLE_C_NOTE_PI_DURATION.duration(), 0);
    }
    
    /*
     * Testing strategy for transpose(int semitones):
     *      semitones is 0, >0, <0
     */
    //covers semitones = 0
    @Test
    public void zeroTranspose(){
        Concat same = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transpose(0);
        assertEquals("The original and transpose should be equal", CONCAT_MIDDLE_C_NOTE_PI_DURATION, same);
    }
    //covers semitones > 0
    @Test
    public void positiveTranspose(){
        Concat transposedPos = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transpose(1);
        Concat expected = new Concat(MIDDLE_C_NOTE.transpose(1), PI_DURATION.transpose(1));
        assertEquals("Expected transpose positive failed", expected, transposedPos);
    }
    //covers semitones < 0
    @Test
    public void negativeTranspose(){
        Concat transposedNeg = (Concat) CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE.transpose(-2);
        Concat expected = new Concat(MIDDLE_C_NOTE.transpose(-2), C_FLAT_NOTE.transpose(-2));
        assertEquals("Expected transpose negative failed", expected, transposedNeg);
    }
    
    /*
     * Testing strategy for transposePitch(Pitch pitch, int semitones):
     *      semitones is 0, >0, <0
     *      Pitch: 
     *          Concat has matching pitch, does not have matching pitch
     */
    //covers semitones = 0, no matching pitch
    @Test
    public void transposePitchZeroSemitonesNoMatchingPitch(){
        Concat same = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transposePitch(C_FLAT, 0);
        assertEquals("The original and transpose should be equal", CONCAT_MIDDLE_C_NOTE_PI_DURATION, same);
    }
    //covers semitones = 0, matching pitch
    @Test
    public void transposePitchZeroSemitonesMatchingPitch(){
        Concat same = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transposePitch(MIDDLE_C, 0);
        assertEquals("The original and transpose should be equal", CONCAT_MIDDLE_C_NOTE_PI_DURATION, same);
    }
    //covers semitones > 0, no matching pitch
    @Test
    public void transposePitchPositiveSemitonesNoMatchingPitch(){
        Concat same = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transposePitch(C_FLAT, 10);
        assertEquals("The original and transpose should be equal", CONCAT_MIDDLE_C_NOTE_PI_DURATION, same);
    }
    //covers semitones > 0, matching pitch
    @Test
    public void transposePitchPositiveSemitonesMatchingPitch(){
        Concat diff = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transposePitch(MIDDLE_C, 10);
        Concat expected = new Concat(MIDDLE_C_NOTE.transposePitch(MIDDLE_C, 10), PI_DURATION.transposePitch(MIDDLE_C, 10));
        assertEquals("Expected transposePitch positive, matching Pitch failed", expected, diff);
    }
    //covers semitones < 0, no matching pitch
    @Test
    public void transposePitchNegativeSemitonesNoMatchingPitch(){
        Concat same = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transposePitch(C_FLAT, -3);
        assertEquals("The original and transpose should be equal", CONCAT_MIDDLE_C_NOTE_PI_DURATION, same);
    }
    //covers semitones < 0, matching pitch
    @Test
    public void transposePitchNegativeSemitonesMatchingPitch(){
        Concat diff = (Concat) CONCAT_MIDDLE_C_NOTE_PI_DURATION.transposePitch(MIDDLE_C, -3);
        Concat expected = new Concat(MIDDLE_C_NOTE.transposePitch(MIDDLE_C, -3), PI_DURATION.transposePitch(MIDDLE_C, -3));
        assertEquals("Expected transposePitch negative, matching Pitch failed", expected, diff);
    }
    
    /*
     * Testing strategy for toString():
     *     duration: 0, >0
     */
    //covers duration = 0
    @Test
    public void stringZeroDuration(){
        assertEquals("String should be z0.0 z0.0", "z0.0 z0.0", CONCAT_ZERO_DURATION_ZERO_DURATION.toString());
    }
    //covers duration > 0
    @Test
    public void stringNonzeroDuration(){
        assertEquals("String should be B,5.0 z1.0", "B,5.0 z1.0", CONCAT_C_FLAT_NOTE_ONE_DURATION.toString());
    }

    /*
     * Testing strategy for equals(Object obj):
     *     Obj: Not instance of Concat
     *          Is instance of Concat:
     *              The two concatenated Musics in each of the two Concats being compared are not equal
     *              The two concatenated Musics in each of the two Concats being compared equal
     *     Equality must also be transitive, reflexive, and symmetric
     */
    //covers obj not instance of Concat
    @Test
    public void equalsObjNotInstance()
    {
        assertEquals("Should be false", false, CONCAT_C_FLAT_NOTE_ONE_DURATION.equals(ZERO_DURATION));
    }
    //covers obj is instance of Concat, concated Musics are not equal
    @Test
    public void equalsDifferentDuration(){
        assertEquals("Should be false", false, CONCAT_ZERO_DURATION_ZERO_DURATION .equals(CONCAT_C_FLAT_NOTE_ONE_DURATION));
    }
    //covers obj is instance of Concat, concated Musics are equal
    @Test
    public void equalsSameDuration(){
        assertEquals("Should be true", true, CONCAT_ZERO_DURATION_ZERO_DURATION.equals(ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION));
    }
    //covers transitivity, reflexivity, and symmetry
    public void equalsTransitiveReflexiveSymmetric(){
        //reflexive
        assertEquals("Should be true", true, ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION.equals(ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION));
        
        //symmetry
        assertEquals("Should be true", true, CONCAT_ZERO_DURATION_ZERO_DURATION.equals(ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION));
        assertEquals("Should be true", true, ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION.equals(CONCAT_ZERO_DURATION_ZERO_DURATION));
        
        //transitivity
        assertEquals("Should be true", true, CONCAT_ZERO_DURATION_ZERO_DURATION.equals(ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION));
        assertEquals("Should be true", true, ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION.equals(THIRD_CONCAT_ZERO_DURATION_ZERO_DURATION));
        assertEquals("Should be true", true, CONCAT_ZERO_DURATION_ZERO_DURATION.equals(THIRD_CONCAT_ZERO_DURATION_ZERO_DURATION));
    }
  
    /*
     * Testing strategy for hashCode():
     *     Equal Concat instances will have same hashCode
     */
    @Test
    public void equalsSameHashCode(){
        assertEquals("Should be true", true, CONCAT_ZERO_DURATION_ZERO_DURATION.equals(CONCAT_ZERO_DURATION_ZERO_DURATION));
        assertEquals("Hashcode should be same", CONCAT_ZERO_DURATION_ZERO_DURATION.hashCode(), ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION.hashCode());
    }
}
