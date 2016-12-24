package abc.ast;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;

/**
 * Test Note class
 */
public class NoteTest {
    
    /**
     * Note testing strategy:
     * duration(): 0, > 0     
     * transpose(): Transpose by an octave up, octave down, by 0
     * hashCode():
     *      Two notes that are equal have the same hashCode
     * equals(Object obj):
     *      obj not an instance of Note
     *      Two notes that are an octave apart 
     *      Two notes with same pitch and duration
     *      Equality must also be transitive, reflexive, and symmetric
     * toString():
     *      Correct pitch (^C and _D both map to ^C according to Pitch specs)
     *      Correct octave (correct number of , or ')
     *      Correct duration (
     * transposePitch(Pitch p, int semitonesUp):
     *      semitonesUp: 0, >0
     *      Pitch: Same pitch, different pitch 
     */
    
    //protected globals
    protected static final int OCTAVE = 12;
    protected static final Pitch MIDDLE_C = new Pitch('C'); 
    protected static final Pitch C_FLAT = new Pitch('C').transpose(1); 
    protected static final Pitch C_SHARP = new Pitch('C').transpose(-1); 
    protected static final Pitch C_ONE_OCTAVE_ABOVE = new Pitch('C').transpose(OCTAVE); 
    protected static final Pitch C_ONE_OCTAVE_BELOW = new Pitch('C').transpose(-OCTAVE);
    
    protected static final Note MID_C = new Note(1.0, MIDDLE_C);
    protected static final Note ANOTHER_MID_C = new Note(1.0, MIDDLE_C);
    protected static final Note THIRD_MID_C = new Note(1.0, MIDDLE_C);
    protected static final Note C_FLAT_NOTE = new Note(5.0, C_FLAT);
    
    
    //Tests duration == 0
    @Test
    public void testDurationZero(){
        Note n = new Note(0, Pitch.MIDDLE_C);
        assertTrue(0.0 == n.duration());
    }
    
    //Tests duration > 0
    @Test
    public void testDurationNotZero(){
        assertTrue(1.0 == MID_C.duration());
    }
    
    //Tests hashCode same Note
    @Test
    public void testHashCodeSame(){
        Note fSharp = (Note) new Note(0.0, new Pitch('F')).transpose(1);
        Note gFlat = (Note) new Note(0.0, new Pitch('G')).transpose(-1);
        
        assertEquals(fSharp.hashCode(), gFlat.hashCode());
    }
    
    //Tests equals not both Notes
    @Test
    public void testEqualsNotBothNotes(){
        assertFalse(MID_C.equals((double)2));
    }
    
    //Tests equals two different Notes
    @Test
    public void testEqualsDifferent(){
        Note highC = (Note) MID_C.transpose(Pitch.OCTAVE);
        
        assertFalse(MID_C.equals(highC));
    }
    //Tests equals two Notes same pitch and duration
    @Test
    public void testEqualsSame(){
        assertTrue(MID_C.equals(ANOTHER_MID_C));
    }
    //Tests equals for transitivity, reflexivity, and symmetry
    @Test
    public void testEqualsTransitivityReflexivitySymmetry(){
        //reflexive
        assertTrue(MID_C.equals(MID_C));
        
        //symmetric
        assertTrue(MID_C.equals(ANOTHER_MID_C));
        assertTrue(ANOTHER_MID_C.equals(MID_C));
        
        //transitive
        assertTrue(MID_C.equals(ANOTHER_MID_C));
        assertTrue(ANOTHER_MID_C.equals(THIRD_MID_C));
        assertTrue(MID_C.equals(THIRD_MID_C));
    }
    
    //Test transpose up an octave
    @Test
    public void testTransposeOctaveUp(){
        
        Note highC = (Note) MID_C.transpose(Pitch.OCTAVE);
        
        int expectedPitch = MID_C.getPitch().toMidiNote() + Pitch.OCTAVE;
        
        assertEquals(expectedPitch, highC.getPitch().toMidiNote());
    }
    
    //Test transpose down an octave
    @Test
    public void testTransposeOctaveDown(){
        
        Note lowC = (Note) MID_C.transpose(Pitch.OCTAVE * -1);
        
        int expectedPitch = MID_C.getPitch().toMidiNote() - Pitch.OCTAVE;
        
        assertEquals(expectedPitch, lowC.getPitch().toMidiNote());
    }
    
    //Test transpose zero semitones
    @Test
    public void testTransposeZero(){
        
        Note same = (Note) MID_C.transpose(0);
        
        assertEquals(MID_C, same);
    }
    
    //Tests toString correct pitch
    @Test
    public void testToStringPitch(){
        Note same = (Note) MID_C.transpose(1);
        
        assertEquals("^C1.0", same.toString());
    }
    
    //Tests toString correct octave
    @Test
    public void testToStringOctave(){
        Note highHighC = (Note) MID_C.transpose(Pitch.OCTAVE + Pitch.OCTAVE);
        
        assertEquals("C''1.0", highHighC.toString());
    }
    
    //Tests toString correct duration
    @Test
    public void testToStringDuration(){
        Note eighthNoteC = new Note(.5, new Pitch('C'));
        
        assertEquals("C0.5", eighthNoteC.toString());
    }
    
    //Tests transposePitch change by 0
    @Test
    public void testTransposePitchZero(){
        
        Music same = MID_C.transposePitch(new Pitch('C'), 0);
        
        assertEquals(MID_C, same);
    }
    
    //Tests transposePitch differing pitches
    @Test
    public void testTransposePitchDifferentPitch(){
        
        Music same = MID_C.transposePitch(new Pitch('F'), 1);
        
        assertEquals(MID_C, same);
    }
    
    //Tests transposePitch same pitches, change by > 0
    @Test
    public void testTransposePitchSamePitch(){
        
        Note diff = (Note) MID_C.transposePitch(new Pitch('C'), 1);
        
        assertEquals(MID_C.getPitch().toMidiNote() + 1, diff.getPitch().toMidiNote());
    }
}
