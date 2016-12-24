package abc.ast;

import abc.sound.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * 
 * Tests for the Chord concrete variant of the Music data type.
 *
 */
public class ChordTest {
    
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
    
    protected static final Note ZERO_DURATION_NOTE = new Note(0, MIDDLE_C);
    protected static final Note MIDDLE_C_NOTE = new Note(5.0, MIDDLE_C);
    protected static final Note MIDDLE_G_NOTE = new Note(3.5, MIDDLE_G);
    protected static final Note C_FLAT_NOTE = new Note(1.0, C_FLAT);
    protected static final Note C_ONE_OCTAVE_BELOW_NOTE = new Note(7.0, C_ONE_OCTAVE_BELOW);
    
    protected static final List<Note> LIST_MIDDLE_C_AND_C_FLAT = Arrays.asList(MIDDLE_C_NOTE, C_FLAT_NOTE);
    protected static final List<Note> LIST_MIDDLE_G_AND_C_ONE_OCTAVE_BELOW_NOTE = Arrays.asList(MIDDLE_G_NOTE,
            C_ONE_OCTAVE_BELOW_NOTE);
    protected static final List<Note> ZERO_DURATION_LIST = Arrays.asList(ZERO_DURATION_NOTE, C_FLAT_NOTE);
    protected static final List<Note> ZERO_DURATION_ONE_NOTE_LIST = Arrays.asList(ZERO_DURATION_NOTE);
    protected static final List<Note> ZERO_DURATION_MULTIPLE_NOTE_LIST = Arrays.asList(ZERO_DURATION_NOTE, ZERO_DURATION_NOTE);
    protected static final List<Note> NONZERO_DURATION_ONE_LIST = Arrays.asList(C_FLAT_NOTE);
    
    protected static final Chord CHORD_MIDDLE_C_AND_C_FLAT = new Chord(LIST_MIDDLE_C_AND_C_FLAT, MIDDLE_C_NOTE.duration());
    protected static final Chord ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT = new Chord(LIST_MIDDLE_C_AND_C_FLAT, MIDDLE_C_NOTE.duration());
    protected static final Chord THIRD_CHORD_MIDDLE_C_AND_C_FLAT = new Chord(LIST_MIDDLE_C_AND_C_FLAT, MIDDLE_C_NOTE.duration());

    protected static final Chord CHORD_MIDDLE_G_AND_C_ONE_OCTAVE_BELOW_NOTE = new Chord(LIST_MIDDLE_G_AND_C_ONE_OCTAVE_BELOW_NOTE,
            MIDDLE_G_NOTE.duration());
    protected static final Chord ZERO_DURATION_CHORD = new Chord(ZERO_DURATION_LIST, ZERO_DURATION_NOTE.duration());
    
    protected static final Chord CHORD_ZERO_DURATION_ONE_NOTE = new Chord(ZERO_DURATION_ONE_NOTE_LIST, ZERO_DURATION_NOTE.duration());
    protected static final Chord CHORD_ZERO_DURATION_MULTIPLE_NOTES = new Chord(ZERO_DURATION_MULTIPLE_NOTE_LIST, ZERO_DURATION_NOTE.duration());
    protected static final Chord CHORD_NONZERO_DURATION_ONE_NOTE = new Chord(NONZERO_DURATION_ONE_LIST, C_FLAT_NOTE.duration());
    
    /*
     * Testing strategy for duration():
     *      duration is zero
     *      duration is whole number
     *      duration is not whole number
     */    
    //covers zero duration
    @Test
    public void zeroDuration(){
        assertEquals("Duration should be 0.0", 0, ZERO_DURATION_CHORD.duration(), 0);
    }
    //covers whole number duration
    @Test
    public void wholeNumberDuration(){
        assertEquals("Duration should be 5.0", 5.0, CHORD_MIDDLE_C_AND_C_FLAT.duration(), 0);
    }
    //covers not whole number duration
    @Test
    public void notWholeNumbberDuration(){
        assertEquals("Duration should be 3.5", 3.5, CHORD_MIDDLE_G_AND_C_ONE_OCTAVE_BELOW_NOTE.duration(), 0);
    }
    
    /*
     * Testing strategy for transpose(int semitones):
     *      semitones is 0, >0, <0
     */
    //covers semitones = 0
    @Test
    public void zeroTranspose(){
        Chord same = (Chord) CHORD_MIDDLE_C_AND_C_FLAT.transpose(0);
        assertEquals("The original and transpose should be equal", CHORD_MIDDLE_C_AND_C_FLAT, same);
    }
    //covers semitones > 0
    @Test
    public void positiveTranspose(){
        Chord transposedPos = (Chord) CHORD_MIDDLE_C_AND_C_FLAT.transpose(1);
        List<Note> inputList = Arrays.asList((Note)MIDDLE_C_NOTE.transpose(1), (Note)C_FLAT_NOTE.transpose(1));
        Chord expected = new Chord(inputList, MIDDLE_C_NOTE.transpose(1).duration());
        assertEquals("Expected transpose positive failed", expected, transposedPos);
    }
    //covers semitones < 0
    @Test
    public void negativeTranspose(){
        Chord transposedNeg = (Chord) ZERO_DURATION_CHORD.transpose(-2);
        List<Note> inputList = Arrays.asList((Note)ZERO_DURATION_NOTE.transpose(-2), (Note)C_FLAT_NOTE.transpose(-2));
        Chord expected = new Chord(inputList, ZERO_DURATION_NOTE.transpose(-2).duration());
        assertEquals("Expected transpose negative failed", expected, transposedNeg); 
    }
    
    /*
     * Testing strategy for transposePitch(Pitch pitch, int semitones):
     *      semitones is 0, >0, <0
     *      Pitch: 
     *          Chord has matching pitch, does not have matching pitch
     */
    //covers semitones = 0, no matching pitch
    @Test
    public void transposePitchZeroSemitonesNoMatchingPitch(){
        Chord same = (Chord) CHORD_MIDDLE_G_AND_C_ONE_OCTAVE_BELOW_NOTE.transposePitch(C_FLAT, 0);
        assertEquals("The original and transpose should be equal", CHORD_MIDDLE_G_AND_C_ONE_OCTAVE_BELOW_NOTE, same);
    }
    //covers semitones = 0, matching pitch
    @Test
    public void transposePitchZeroSemitonesMatchingPitch(){
        Chord same = (Chord) CHORD_MIDDLE_C_AND_C_FLAT.transposePitch(MIDDLE_C, 0);
        assertEquals("The original and transpose should be equal", CHORD_MIDDLE_C_AND_C_FLAT, same);
    }
    //covers semitones > 0, no matching pitch
    @Test
    public void transposePitchPositiveSemitonesNoMatchingPitch(){
        Chord same = (Chord) CHORD_MIDDLE_C_AND_C_FLAT.transposePitch(MIDDLE_G, 10);
        assertEquals("The original and transpose should be equal", CHORD_MIDDLE_C_AND_C_FLAT, same);
    }
    //covers semitones > 0, matching pitch
    @Test
    public void transposePitchPositiveSemitonesMatchingPitch(){
        Chord diff = (Chord) CHORD_MIDDLE_C_AND_C_FLAT.transposePitch(MIDDLE_C, 10);
        List<Note> inputList = Arrays.asList((Note)MIDDLE_C_NOTE.transposePitch(MIDDLE_C, 10), (Note)C_FLAT_NOTE.transposePitch(MIDDLE_C, 10));
        Chord expected = new Chord(inputList, MIDDLE_C_NOTE.transposePitch(MIDDLE_C, 10).duration());
        assertEquals("Expected transposePitch positive, matching Pitch failed", expected, diff);
    }
    //covers semitones < 0, no matching pitch
    @Test
    public void transposePitchNegativeSemitonesNoMatchingPitch(){
        Chord same = (Chord) CHORD_MIDDLE_C_AND_C_FLAT.transposePitch(MIDDLE_G, -3);
        assertEquals("The original and transpose should be equal", CHORD_MIDDLE_C_AND_C_FLAT, same);
    }
    //covers semitones < 0, matching pitch
    @Test
    public void transposePitchNegativeSemitonesMatchingPitch(){
        Chord diff = (Chord) ZERO_DURATION_CHORD.transposePitch(C_FLAT, -3);
        List<Note> inputList = Arrays.asList((Note)ZERO_DURATION_NOTE.transposePitch(C_FLAT, -3), (Note)C_FLAT_NOTE.transposePitch(C_FLAT, -3));
        
        Chord expected = new Chord(inputList, ZERO_DURATION_NOTE.transposePitch(C_FLAT, -3).duration() );
        assertEquals("Expected transposePitch negative, matching Pitch failed", expected, diff);
    }
    
    /*
     * Testing strategy for toString(): 
     *     duration: 0, >0
     *     Chord has 1 note, > 1 note
     */
    //covers duration = 0, 1 note
    @Test
    public void stringZeroDurationOneNote(){
        assertEquals("String should be [C0.0]", "[C0.0]", CHORD_ZERO_DURATION_ONE_NOTE.toString());
    }
    //covers duration = 0, >1 note
    @Test
    public void stringZeroDurationMoreNotes(){
        assertEquals("String should be [C0.0C0.0]", "[C0.0C0.0]", CHORD_ZERO_DURATION_MULTIPLE_NOTES.toString());
    }
    //covers duration > 0, 1 note
    @Test
    public void stringNonzeroDurationOneNote(){
        assertEquals("String should be [B,1.0]", "[B,1.0]", CHORD_NONZERO_DURATION_ONE_NOTE.toString());
    }
    //covers duration > 0, >1 note
    @Test
    public void stringNonzeroDurationMoreNotes(){
        assertEquals("String should be [C5.0B,1.0]", "[C5.0B,1.0]", CHORD_MIDDLE_C_AND_C_FLAT.toString());
    }

    /*
     * Testing strategy for equals(Object obj):
     *     Obj: Not instance of Chord
     *          Is instance of Chord:
     *              The Notes in each of the two Chords being compared are not equal
     *              The Notes in each of the two Chords being compared equal
     *              
     *     Equality must also be transitive, reflexive, and symmetric
     */
    //covers obj not instance of Chord
    @Test
    public void equalsObjNotInstance()
    {
        assertEquals("Should be false", false, ZERO_DURATION_CHORD.equals(ZERO_DURATION));
    }
    //covers obj is instance of Chord, Notes in the two chords are not equal
    @Test
    public void equalsDifferentDuration(){
        assertEquals("Should be false", false, ZERO_DURATION_CHORD.equals(CHORD_MIDDLE_C_AND_C_FLAT));
    }
    //covers obj is instance of Chord, Notes in the two chords are equal
    @Test
    public void equalsSameDuration(){
        assertEquals("Should be true", true, CHORD_MIDDLE_C_AND_C_FLAT.equals(ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT));
    }
    //covers transitivity, reflexivity, and symmetry
    public void equalsTransitiveReflexiveSymmetric(){
        //reflexive
        assertEquals("Should be true", true, CHORD_MIDDLE_C_AND_C_FLAT.equals(CHORD_MIDDLE_C_AND_C_FLAT));
        
        //symmetry
        assertEquals("Should be true", true, CHORD_MIDDLE_C_AND_C_FLAT.equals(ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT));
        assertEquals("Should be true", true, ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT.equals(CHORD_MIDDLE_C_AND_C_FLAT));
        
        //transitivity
        assertEquals("Should be true", true, CHORD_MIDDLE_C_AND_C_FLAT.equals(ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT));
        assertEquals("Should be true", true, ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT.equals(THIRD_CHORD_MIDDLE_C_AND_C_FLAT));
        assertEquals("Should be true", true, CHORD_MIDDLE_C_AND_C_FLAT.equals(THIRD_CHORD_MIDDLE_C_AND_C_FLAT));
    }
  
    /*
     * Testing strategy for hashCode():
     *     Equal Chord instances will have same hashCode
     */
    @Test
    public void equalsSameHashCode(){
        assertEquals("Should be true", true, CHORD_MIDDLE_C_AND_C_FLAT.equals(ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT));
        assertEquals("Hashcode should be same", CHORD_MIDDLE_C_AND_C_FLAT.hashCode(), ANOTHER_CHORD_MIDDLE_C_AND_C_FLAT.hashCode());
    }
}
