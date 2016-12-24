package abc.ast;

import abc.sound.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * 
 * Tests for the Piece concrete variant of the Music data type.
 *
 */
public class PieceTest {
    
    //protected globals
    protected static final int OCTAVE = 12;
    protected static final String UPPER = "UPPER";
    protected static final String LOWER = "LOWER";
    protected static final String MIDDLE = "MIDDLE";
    
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
    
    protected static final Note ZERO_DURATION_NOTE = new Note(0, MIDDLE_C);
    protected static final Note MIDDLE_C_NOTE = new Note(5.0, MIDDLE_C);
    protected static final Note C_FLAT_NOTE = new Note(5.0, C_FLAT);
    protected static final Note MIDDLE_G_NOTE = new Note(3.5, MIDDLE_G);
    protected static final Note C_ONE_OCTAVE_BELOW_NOTE = new Note(7.0, C_ONE_OCTAVE_BELOW);

    protected static final Concat CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    protected static final Concat ANOTHER_CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    protected static final Concat THIRD_CONCAT_ZERO_DURATION_ZERO_DURATION = new Concat(ZERO_DURATION, ZERO_DURATION);
    
    protected static final Concat CONCAT_ZERO_DURATION_C_FLAT_NOTE = new Concat(ZERO_DURATION, C_FLAT_NOTE);
    protected static final Concat CONCAT_MIDDLE_C_NOTE_PI_DURATION = new Concat(MIDDLE_C_NOTE, PI_DURATION);
    protected static final Concat CONCAT_C_FLAT_NOTE_ONE_DURATION = new Concat(C_FLAT_NOTE, ONE_DURATION);
    protected static final Concat CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE = new Concat(MIDDLE_C_NOTE, C_FLAT_NOTE);
    
    protected static final List<Note> MIDDLE_C_AND_C_FLAT = Arrays.asList(MIDDLE_C_NOTE, C_FLAT_NOTE);
    protected static final Chord CHORD_MIDDLE_C_AND_C_FLAT = new Chord(MIDDLE_C_AND_C_FLAT, MIDDLE_C_NOTE.duration());
    protected static final Chord CHORD_NONZERO_DURATION_ONE_NOTE = new Chord(MIDDLE_C_AND_C_FLAT, MIDDLE_C_NOTE.duration());
  
    //Useful NoteOrChord Lists
    protected static final List<NoteOrChord> TWO_LIST_MIDDLE_C_AND_C_FLAT = Arrays.asList(MIDDLE_C_NOTE, C_FLAT_NOTE);
    protected static final List<NoteOrChord> THREE_LIST_C_FLAT_MIDDLE_G_MIDDLE_C = Arrays.asList(C_FLAT_NOTE, MIDDLE_G_NOTE, MIDDLE_C_NOTE);
    protected static final List<NoteOrChord> FOUR_LIST_C_OCTAVE_BELOW_C_FLAT_MIDDLE_C_MIDDLE_G = Arrays.asList(C_ONE_OCTAVE_BELOW_NOTE, 
            C_FLAT_NOTE, MIDDLE_C_NOTE, MIDDLE_G_NOTE);
    protected static final List<NoteOrChord> ZERO_DURATION_MULTIPLE_NOTE_LIST = Arrays.asList(ZERO_DURATION_NOTE, ZERO_DURATION_NOTE);
    protected static final List<NoteOrChord> FOUR_LIST_WITH_CHORD = Arrays.asList(C_FLAT_NOTE, CHORD_MIDDLE_C_AND_C_FLAT, 
            MIDDLE_G_NOTE, MIDDLE_C_NOTE);
    
    //Tuplet instances
    protected static final Tuplet ZERO_DURATION_DUPLET = new Tuplet(ZERO_DURATION_MULTIPLE_NOTE_LIST, 2);
    protected static final Tuplet DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE = new Tuplet(TWO_LIST_MIDDLE_C_AND_C_FLAT, 2);
    protected static final Tuplet TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C = new Tuplet(THREE_LIST_C_FLAT_MIDDLE_G_MIDDLE_C, 3);
    protected static final Tuplet QUADRUPLET_C_OCTAVE_BELOW_C_FLAT_MIDDLE_C_MIDDLE_G = new Tuplet(FOUR_LIST_C_OCTAVE_BELOW_C_FLAT_MIDDLE_C_MIDDLE_G, 4);
    
    protected static final Tuplet QUADRUPLET_WITH_CHORD = new Tuplet(FOUR_LIST_WITH_CHORD, 4);
    protected static final Tuplet ANOTHER_QUADRUPLET_WITH_CHORD = new Tuplet(FOUR_LIST_WITH_CHORD, 4);
    protected static final Tuplet THIRD_QUADRUPLET_WITH_CHORD = new Tuplet(FOUR_LIST_WITH_CHORD, 4);
  
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
    
    //Voice instances
    protected static final Voice ZERO_DURATION_VOICE_UPPER = new Voice(ZERO_DURATION_MEASURE, UPPER);
    protected static final Voice VOICE_ONE_BAR_UPPER = new Voice(WHOLE_DURATION_MEASURE, UPPER);
    protected static final Voice VOICE_ONE_BAR_MIDDLE = new Voice(WHOLE_DURATION_MEASURE, MIDDLE);
    protected static final Voice VOICE_ONE_BAR_LOWER = new Voice(DECIMAL_DURATION_MEASURE, LOWER);
    
    protected static final Voice ANOTHER_VOICE_ONE_BAR_UPPER = new Voice(WHOLE_DURATION_MEASURE, UPPER);
    protected static final Voice THIRD_VOICE_ONE_BAR_UPPER = new Voice(WHOLE_DURATION_MEASURE, UPPER);
    protected static final Voice VOICE_TWO_BAR_MIDDLE = new Voice(new Concat(WHOLE_DURATION_MEASURE,
            DECIMAL_DURATION_MEASURE), MIDDLE);
    protected static final Voice VOICE_THREE_BAR_LOWER = new Voice(new Concat(new Concat(WHOLE_DURATION_MEASURE,
            DECIMAL_DURATION_MEASURE), ZERO_DURATION_MEASURE), LOWER);
    protected static final Voice VOICE_TWO_BAR_LOWER = new Voice(new Concat(DECIMAL_DURATION_MEASURE,
            WHOLE_DURATION_MEASURE), LOWER);
    
    //Lists of Voices
    protected static final List<Voice> LIST_ONE_VOICE_ZERO_DURATION = Arrays.asList(ZERO_DURATION_VOICE_UPPER);
    protected static final List<Voice> LIST_TWO_VOICES_ZERO_DURATION = Arrays.asList(ZERO_DURATION_VOICE_UPPER,ZERO_DURATION_VOICE_UPPER);
    protected static final List<Voice> LIST_ONE_VOICE_WHOLE_DURATION = Arrays.asList(VOICE_ONE_BAR_UPPER);
    protected static final List<Voice> LIST_TWO_VOICES_WHOLE_DURATION = Arrays.asList(VOICE_ONE_BAR_UPPER, ANOTHER_VOICE_ONE_BAR_UPPER);
    protected static final List<Voice> LIST_ONE_VOICE_DECIMAL_DURATION = Arrays.asList(new Voice(DECIMAL_DURATION_MEASURE, "LOWER"));
    protected static final List<Voice> LIST_TWO_VOICES_DECIMAL_DURATION = Arrays.asList(VOICE_TWO_BAR_MIDDLE, VOICE_THREE_BAR_LOWER);
    
    protected static final List<Voice> LIST_TWO_VOICES_MIDDLE_LOWER = Arrays.asList(VOICE_ONE_BAR_MIDDLE, VOICE_ONE_BAR_LOWER);
    
    //Piece instances
    protected static final Piece ZERO_DURATION_PIECE_ONE_VOICE = new Piece(LIST_ONE_VOICE_ZERO_DURATION);
    protected static final Piece ZERO_DURATION_PIECE_TWO_VOICES = new Piece(LIST_TWO_VOICES_ZERO_DURATION);
    protected static final Piece WHOLE_DURATION_PIECE_ONE_VOICE = new Piece(LIST_ONE_VOICE_WHOLE_DURATION);
    protected static final Piece WHOLE_DURATION_PIECE_TWO_VOICES = new Piece(LIST_TWO_VOICES_WHOLE_DURATION);
    protected static final Piece DECIMAL_DURATION_PIECE_ONE_VOICE = new Piece(LIST_ONE_VOICE_DECIMAL_DURATION);
    protected static final Piece DECIMAL_DURATION_PIECE_TWO_VOICES = new Piece(LIST_TWO_VOICES_DECIMAL_DURATION);
    protected static final Piece ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES = new Piece(LIST_TWO_VOICES_DECIMAL_DURATION);
    protected static final Piece THIRD_DECIMAL_DURATION_PIECE_TWO_VOICES = new Piece(LIST_TWO_VOICES_DECIMAL_DURATION);
    
    protected static final Piece PIECE_TWO_VOICES_MIDDLE_LOWER = new Piece(LIST_TWO_VOICES_MIDDLE_LOWER);
    /*
     * Testing strategy for duration():
     *      duration is zero, whole number, not whole number
     *      The Piece has 1 voice, >1 voice
     */    
    //covers zero duration, 1 voice
    @Test
    public void zeroDurationOneVoice(){
        assertEquals("Duration should be 0.0", 0, ZERO_DURATION_PIECE_ONE_VOICE.duration(), 0);
    }
    //covers zero duration, >1 voice
    @Test
    public void zeroDurationMoreThanOneVoice(){
        assertEquals("Duration should be 0.0", 0, ZERO_DURATION_PIECE_TWO_VOICES.duration(), 0);
    }
    //covers whole number duration, 1 voice
    @Test
    public void wholeDurationOneVoice(){
        assertEquals("Duration should be 16.0", 16.0, WHOLE_DURATION_PIECE_ONE_VOICE.duration(), 0);
    }
    //covers whole number duration, >1 voice
    @Test
    public void wholeDurationMoreThanOneVoice(){
        assertEquals("Duration should be 16.0", 16.0, WHOLE_DURATION_PIECE_TWO_VOICES.duration(), 0);
    }
    //covers not whole number duration, 1 voice
    @Test
    public void notWholeDurationOneVoice(){
        assertEquals("Duration should be 16.64", 16.64, DECIMAL_DURATION_PIECE_ONE_VOICE.duration(), 0.01);
    }
    //covers not whole number duration, >1 voice
    @Test
    public void notWholeDurationMoreThanOneVoice(){
        assertEquals("Duration should be 32.64", 32.64, DECIMAL_DURATION_PIECE_TWO_VOICES.duration(), 0.01);
    }
    
    /*
     * Testing strategy for transpose(int semitones):
     *      semitones is 0, >0, <0
     */
    //covers semitones = 0
    @Test
    public void zeroTranspose(){
        Piece same = (Piece) ZERO_DURATION_PIECE_ONE_VOICE.transpose(0); //error
        assertEquals("The original and transpose should be equal", ZERO_DURATION_PIECE_ONE_VOICE, same);
    }
    //covers semitones > 0
    @Test
    public void positiveTranspose(){
        Piece transposedPos = (Piece) WHOLE_DURATION_PIECE_ONE_VOICE.transpose(1);
        List<Voice> inputList = Arrays.asList( (Voice) VOICE_ONE_BAR_UPPER.transpose(1) );
        Piece expected = new Piece(inputList);
        assertEquals("Expected transpose positive failed", expected, transposedPos);
    }
    //covers semitones < 0
    @Test
    public void negativeTranspose(){
        Piece transposedNeg = (Piece) DECIMAL_DURATION_PIECE_TWO_VOICES.transpose(-2);
        List<Voice> inputList = Arrays.asList( (Voice) VOICE_TWO_BAR_MIDDLE.transpose(-2), 
                (Voice) VOICE_THREE_BAR_LOWER.transpose(-2));
        Piece expected = new Piece(inputList);
        assertEquals("Expected transpose negative failed", expected, transposedNeg);
    }
    
    /*
     * Testing strategy for transposePitch(Pitch pitch, int semitones):
     *      semitones is 0, >0, <0
     *      Pitch: 
     *          Piece has matching pitch, does not have matching pitch
     */
    //covers semitones = 0, no matching pitch
    @Test
    public void transposePitchZeroSemitonesNoMatchingPitch(){
        Piece same = (Piece) WHOLE_DURATION_PIECE_ONE_VOICE.transposePitch(C_FLAT, 0);
        assertEquals("The original and transpose should be equal", WHOLE_DURATION_PIECE_ONE_VOICE, same);
    }
    //covers semitones = 0, matching pitch
    @Test
    public void transposePitchZeroSemitonesMatchingPitch(){
        Piece same = (Piece) WHOLE_DURATION_PIECE_ONE_VOICE.transposePitch(MIDDLE_C, 0);
        assertEquals("The original and transpose should be equal", WHOLE_DURATION_PIECE_ONE_VOICE, same);
    }
    //covers semitones > 0, no matching pitch
    @Test
    public void transposePitchPositiveSemitonesNoMatchingPitch(){
        Piece same = (Piece) WHOLE_DURATION_PIECE_ONE_VOICE.transposePitch(MIDDLE_G, 10);
        assertEquals("The original and transpose should be equal", WHOLE_DURATION_PIECE_ONE_VOICE, same);
    }
    //covers semitones > 0, matching pitch
    @Test
    public void transposePitchPositiveSemitonesMatchingPitch(){
        Piece diff = (Piece) WHOLE_DURATION_PIECE_ONE_VOICE.transposePitch(MIDDLE_C, 10);
        List<Voice> inputList = Arrays.asList( (Voice) VOICE_ONE_BAR_UPPER.transposePitch(MIDDLE_C, 10) );
        Piece expected = new Piece(inputList);
        assertEquals("Expected transposePitch positive, matching Pitch failed", expected, diff);
    }
    //covers semitones < 0, no matching pitch
    @Test
    public void transposePitchNegativeSemitonesNoMatchingPitch(){
        Piece same = (Piece) WHOLE_DURATION_PIECE_ONE_VOICE.transposePitch(MIDDLE_G, -3);
        assertEquals("The original and transpose should be equal", WHOLE_DURATION_PIECE_ONE_VOICE, same);
    }
    //covers semitones < 0, matching pitch
    @Test
    public void transposePitchNegativeSemitonesMatchingPitch(){
        Piece diff = (Piece) DECIMAL_DURATION_PIECE_TWO_VOICES.transposePitch(MIDDLE_C, -2);
        List<Voice> inputList = Arrays.asList( (Voice) VOICE_TWO_BAR_MIDDLE.transposePitch(MIDDLE_C, -2), 
                (Voice) VOICE_THREE_BAR_LOWER.transposePitch(MIDDLE_C, -2));
        Piece expected = new Piece(inputList);
        assertEquals("Expected transposePitch positive, matching Pitch failed", expected, diff);
    }
    
    /*
     * Testing strategy for toString():
     *     has 1 voice, >1 voice
     */
    //covers 1 voice
    @Test
    public void stringOneVoice(){
        String expected = VOICE_ONE_BAR_UPPER.toString() + "\n";
        assertEquals("String not as expected", expected, WHOLE_DURATION_PIECE_ONE_VOICE.toString());
    }
    //covers >1 voice
    @Test
    public void stringMoreThanOneVoice(){
        String expected = "";
        for (Voice v: LIST_TWO_VOICES_DECIMAL_DURATION){
            expected += v.toString();
            expected += "\n";
        }
        assertEquals("String not as expected", expected, DECIMAL_DURATION_PIECE_TWO_VOICES.toString());
    }
    
    /*
     * Testing strategy for equals(Object obj):
     *     Obj: Not instance of Piece
     *          Is instance of Piece:
     *              The voices in the two Voices being compared are not equal
     *              The voices in the two Voices being compared equal
     *     Equality must also be transitive, reflexive, and symmetric
     */
    //covers obj not instance of Piece
    @Test
    public void equalsObjNotInstance()
    {
        assertEquals("Should be false", false, DECIMAL_DURATION_PIECE_TWO_VOICES.equals(ZERO_DURATION));
    }
    //covers obj is instance of Piece, different voices
    @Test
    public void equalsSameTypeDifferentName(){
        assertEquals("Should be false", false, WHOLE_DURATION_PIECE_ONE_VOICE.equals(DECIMAL_DURATION_PIECE_TWO_VOICES));
    }
    //covers obj is instance of Piece, same voices
    @Test
    public void equalsSameTypeSameLengthDifferentMusics(){
        assertEquals("Should be true", true, DECIMAL_DURATION_PIECE_TWO_VOICES.equals(ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES));
    }
    //covers transitivity, reflexivity, and symmetry
    public void equalsTransitiveReflexiveSymmetric(){
        //reflexive
        assertEquals("Should be true", true, WHOLE_DURATION_PIECE_ONE_VOICE.equals(WHOLE_DURATION_PIECE_ONE_VOICE));
        
        //symmetry
        assertEquals("Should be true", true, DECIMAL_DURATION_PIECE_TWO_VOICES.equals(ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES));
        assertEquals("Should be true", true, ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES.equals(DECIMAL_DURATION_PIECE_TWO_VOICES));
        //transitivity
        assertEquals("Should be true", true, DECIMAL_DURATION_PIECE_TWO_VOICES.equals(ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES));
        assertEquals("Should be true", true, ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES.equals(THIRD_DECIMAL_DURATION_PIECE_TWO_VOICES));
        assertEquals("Should be true", true, DECIMAL_DURATION_PIECE_TWO_VOICES.equals(THIRD_DECIMAL_DURATION_PIECE_TWO_VOICES));       
    }
  
    /*
     * Testing strategy for hashCode():
     *     Equal Piece instances will have same hashCode
     */
    @Test
    public void equalsSameHashCode(){
        assertEquals("Should be true", true, DECIMAL_DURATION_PIECE_TWO_VOICES.equals(ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES));
        assertEquals("Hashcode should be same", DECIMAL_DURATION_PIECE_TWO_VOICES.hashCode(), ANOTHER_DECIMAL_DURATION_PIECE_TWO_VOICES.hashCode());
    }
}