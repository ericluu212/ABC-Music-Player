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
 * Tests for the Tuplet concrete variant of the Music data type.
 *
 */
public class TupletTest {
    
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
    
    
    
    /*
     * Testing strategy for duration():
     *      duration is zero
     *      duration is not zero:
     *          duplet, triplet, quadruplet
     */    
    //covers zero duration
    @Test
    public void zeroDuration(){
        assertEquals("Duration should be 0.0", 0, ZERO_DURATION_DUPLET.duration(), 0);
    }
    //covers duplet duration
    @Test
    public void dupletDuration(){
        assertEquals("Duration should be 9.0", 9.0, DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.duration(), 0);
    }
    //covers triplet duration
    @Test
    public void tripletDuration(){
        assertEquals("Duration should be 9.5 * 2 / 3", 9.5 * 2 / 3, TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.duration(), 0.01);
    }
    //covers quadruplet duration
    @Test
    public void quadrupletDuration(){
        assertEquals("Duration should be 14.5 * 3 / 4", 14.5* 3 / 4, QUADRUPLET_WITH_CHORD.duration(), 0);
    }
    
    /*
     * Testing strategy for transpose(int semitones):
     *      semitones is 0, >0, <0
     */
    //covers semitones = 0
    @Test
    public void zeroTranspose(){
        Tuplet same = (Tuplet) ZERO_DURATION_DUPLET.transpose(0);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_DUPLET, same);
    }
    //covers semitones > 0
    @Test
    public void positiveTranspose(){
        Tuplet transposedPos = (Tuplet) TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.transpose(1);
        List<NoteOrChord> threeTransposePos = Arrays.asList( (NoteOrChord) C_FLAT_NOTE.transpose(1), 
                (NoteOrChord) MIDDLE_G_NOTE.transpose(1), (NoteOrChord) MIDDLE_C_NOTE.transpose(1));
        Tuplet expected = new Tuplet(threeTransposePos, 3);
        assertEquals("Expected transpose positive failed", expected, transposedPos);
    }
    //covers semitones < 0
    @Test
    public void negativeTranspose(){
        Tuplet transposedNeg = (Tuplet) QUADRUPLET_WITH_CHORD.transpose(-2);
        List<NoteOrChord> fourTransposeNeg = Arrays.asList( (NoteOrChord) C_FLAT_NOTE.transpose(-2), 
                (NoteOrChord) CHORD_MIDDLE_C_AND_C_FLAT.transpose(-2), 
                (NoteOrChord) MIDDLE_G_NOTE.transpose(-2), 
                (NoteOrChord) MIDDLE_C_NOTE.transpose(-2));
        Tuplet expected = new Tuplet(fourTransposeNeg, 4);
        assertEquals("Expected transpose negative failed", expected, transposedNeg);
    }
    
    /*
     * Testing strategy for transposePitch(Pitch pitch, int semitones):
     *      semitones is 0, >0, <0
     *      Pitch: 
     *          Tuplet has matching pitch, does not have matching pitch
     */
    //covers semitones = 0, no matching pitch
    @Test
    public void transposePitchZeroSemitonesNoMatchingPitch(){
        Tuplet same = (Tuplet) ZERO_DURATION_DUPLET.transposePitch(C_FLAT, 0);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_DUPLET, same);
    }
    //covers semitones = 0, matching pitch
    @Test
    public void transposePitchZeroSemitonesMatchingPitch(){
        Tuplet same = (Tuplet) DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.transposePitch(MIDDLE_C, 0);
        assertEquals("The original and transpose should be equal", DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE, same);
    }
    //covers semitones > 0, no matching pitch
    @Test
    public void transposePitchPositiveSemitonesNoMatchingPitch(){
        Tuplet same = (Tuplet) ZERO_DURATION_DUPLET.transposePitch(C_FLAT, 10);
        assertEquals("The original and transpose should be equal", ZERO_DURATION_DUPLET, same);
    }
    //covers semitones > 0, matching pitch
    @Test
    public void transposePitchPositiveSemitonesMatchingPitch(){
        Tuplet diff = (Tuplet) QUADRUPLET_WITH_CHORD.transposePitch(MIDDLE_C, 10);
        List<NoteOrChord> fourTransposePos = Arrays.asList( (NoteOrChord) C_FLAT_NOTE.transposePitch(MIDDLE_C, 10), 
                (NoteOrChord) CHORD_MIDDLE_C_AND_C_FLAT.transposePitch(MIDDLE_C, 10), 
                (NoteOrChord) MIDDLE_G_NOTE.transposePitch(MIDDLE_C, 10), 
                (NoteOrChord) MIDDLE_C_NOTE.transposePitch(MIDDLE_C, 10));
        Tuplet expected = new Tuplet(fourTransposePos, 4);
        assertEquals("Expected transposePitch positive, matching Pitch failed", expected, diff);
    }
    //covers semitones < 0, no matching pitch
    @Test
    public void transposePitchNegativeSemitonesNoMatchingPitch(){
        Tuplet same = (Tuplet) DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.transposePitch(C_ONE_OCTAVE_BELOW, -3);
        assertEquals("The original and transpose should be equal", DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE, same);
    }
    //covers semitones < 0, matching pitch
    @Test
    public void transposePitchNegativeSemitonesMatchingPitch(){
        Tuplet diff = (Tuplet) TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.transposePitch(MIDDLE_C, -3);
        List<NoteOrChord> threeTransposeNeg = Arrays.asList( (NoteOrChord) C_FLAT_NOTE.transposePitch(MIDDLE_C, -3), 
                (NoteOrChord) MIDDLE_G_NOTE.transposePitch(MIDDLE_C, -3), 
                (NoteOrChord) MIDDLE_C_NOTE.transposePitch(MIDDLE_C, -3));
        Tuplet expected = new Tuplet(threeTransposeNeg, 3);
        assertEquals("Expected transposePitch negative, matching Pitch failed", expected, diff);
    }
    
    /*
     * Testing strategy for toString():
     *     Duplet, Triplet, Quadruplet
     *     Has chord, does not have chord
     */
    //covers Duplet, does not have chord
    @Test
    public void stringDupletNoChord(){
        assertEquals("String should be (2C5.0B,1.0", "(2C5.0B,1.0", DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.toString());
    }
    //covers Triplet, does not have chord
    @Test
    public void stringTripletNoChord(){
        assertEquals("String should be (3B,1.0G3.5C5.0", "(3B,1.0G3.5C5.0", TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.toString());
    }
    //covers Quadruplet, does not have chord
    @Test
    public void stringQuadrupletNoChord(){
        assertEquals("String should be (4C,7.0B,1.0C5.0G3.5", "(4C,7.0B,1.0C5.0G3.5", QUADRUPLET_C_OCTAVE_BELOW_C_FLAT_MIDDLE_C_MIDDLE_G.toString());
    }
    //covers Quadruplet, has chord
    @Test
    public void stringQuadrupletHasChord(){
        assertEquals("String should be (4B,1.0[C5.0B,1.0]G3.5C5.0", "(4B,1.0[C5.0B,1.0]G3.5C5.0", QUADRUPLET_WITH_CHORD.toString());
    }

    /*
     * Testing strategy for equals(Object obj):
     *     Obj: Not instance of Tuplet
     *          Is instance of Tuplet:
     *              Not same length (automatically false)
     *              Same length:
     *                  The Musics in the two Tuplets being compared are not equal
     *                  The Musics in the two Tuplets being compared equal
     *     Equality must also be transitive, reflexive, and symmetric
     */
    //covers obj not instance of Tuplet
    @Test
    public void equalsObjNotInstance()
    {
        assertEquals("Should be false", false, DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.equals(ZERO_DURATION));
    }
    //covers obj is instance of Tuplet, not same length
    @Test
    public void equalsSameTypeDifferentLength(){
        assertEquals("Should be false", false, DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.equals(TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C));
    }
    //covers obj is instance of Tuplet, same length, musics in each not equal
    @Test
    public void equalsSameTypeSameLengthDifferentMusics(){
        assertEquals("Should be false", false, DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.equals(ZERO_DURATION_DUPLET));
    }
    //covers obj is instance of Tuplet, same length, musics in each equal
    @Test
    public void equalsSameTypeSameLengthSameMusics(){
        assertEquals("Should be true", true, QUADRUPLET_WITH_CHORD.equals(ANOTHER_QUADRUPLET_WITH_CHORD));
    }
    //covers transitivity, reflexivity, and symmetry
    public void equalsTransitiveReflexiveSymmetric(){
        //reflexive
        assertEquals("Should be true", true, QUADRUPLET_WITH_CHORD.equals(QUADRUPLET_WITH_CHORD));
        
        //symmetry
        assertEquals("Should be true", true, QUADRUPLET_WITH_CHORD.equals(ANOTHER_QUADRUPLET_WITH_CHORD));
        assertEquals("Should be true", true, ANOTHER_QUADRUPLET_WITH_CHORD.equals(QUADRUPLET_WITH_CHORD));
        //transitivity
        assertEquals("Should be true", true, QUADRUPLET_WITH_CHORD.equals(ANOTHER_QUADRUPLET_WITH_CHORD));
        assertEquals("Should be true", true, ANOTHER_QUADRUPLET_WITH_CHORD.equals(THIRD_QUADRUPLET_WITH_CHORD));
        assertEquals("Should be true", true, QUADRUPLET_WITH_CHORD.equals(THIRD_QUADRUPLET_WITH_CHORD));
    }
  
    /*
     * Testing strategy for hashCode():
     *     Equal Tuplet instances will have same hashCode
     */
    @Test
    public void equalsSameHashCode(){
        assertEquals("Should be true", true, QUADRUPLET_WITH_CHORD.equals(ANOTHER_QUADRUPLET_WITH_CHORD));
        assertEquals("Hashcode should be same", QUADRUPLET_WITH_CHORD.hashCode(), ANOTHER_QUADRUPLET_WITH_CHORD.hashCode());
    }
}