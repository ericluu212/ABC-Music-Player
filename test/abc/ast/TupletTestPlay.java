package abc.ast;

import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;

/**
 * 
 * Subclass of TupletTest to test the play method, which uses a SequencePlayer
 * @category no_didit
 *
 */
public class TupletTestPlay extends TupletTest{
        
    private static final int BEATS_PER_MINUTE = 100;
    private static final int TICKS_PER_BEAT = 24;
    private static final int ZERO_START = 0;
    private static final int NONZERO_START = 3;
    
   /*
    * Testing strategy for play(SequencePlayer player, double start):
    *      Tuplet's length is 2, 3, 4
    *      Tuplet followed by Note
    *      
    *  Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
    *       for correct duration
    */
   
   //Tests play of a Duplet followed by Note, tuplet length is 2
   @Test
   public void testPlayTupletNoteLengthTwo() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       int length = 2;
       double durationFactor = 1.5;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.play(player, start);
       MIDDLE_C_NOTE.play(player, start + DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.duration());
       
       String piece = player.toString();
       //System.out.println(piece);
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchDupletMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartDupletMiddleC =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationDupletMiddleC = (int)((MIDDLE_C_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Duplet's C__FLAT_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchDupletMiddleC));
       assertTrue(events[0].contains(expectedStartDupletMiddleC));
       
       //Test NOTE_OFF event of Duplet's C__FLAT_NOTE
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitchDupletMiddleC));
       assertTrue(events[1].contains(expectedDurationDupletMiddleC));
       
       String expectedPitchDupletCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartDupletCFlat =  (int)((start + MIDDLE_C_NOTE.duration() * durationFactor) * ticksPerBeat) + "";
       String expectedDurationDupletCFlat = (int)((start + MIDDLE_C_NOTE.duration() * durationFactor
               + C_FLAT_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
 
       //Test NOTE_ON event Duplet's C_FLAT_NOTE
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedPitchDupletCFlat));
       assertTrue(events[2].contains(expectedStartDupletCFlat));
       
       //Test NOTE_OFF event Duplet's C_FLAT_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchDupletCFlat));
       assertTrue(events[3].contains(expectedDurationDupletCFlat));
       
       String expectedPitchNoteMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.duration() ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + DUPLET_MIDDLE_C_NOTE_C_FLAT_NOTE.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchNoteMiddleC));
       assertTrue(events[4].contains(expectedStartNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchNoteMiddleC));
       assertTrue(events[5].contains(expectedDurationNote));
   }
   
   //Tests play of a Triplet followed by Note, tuplet length is 3
   @Test
   public void testPlayTripletNoteLengthThree() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       int length = 3;
       double durationFactor = 2.0/3.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.play(player, start);
       MIDDLE_C_NOTE.play(player, start + TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.duration());
       
       String piece = player.toString();
       //System.out.println(piece);
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchTripletCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartTripletCFlat =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationTripletCFlat = (int)((start + C_FLAT_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Triplet's C_FLAT_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchTripletCFlat));
       assertTrue(events[0].contains(expectedStartTripletCFlat));
       
       //Test NOTE_OFF event of Triplet's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitchTripletCFlat));
       assertTrue(events[1].contains(expectedDurationTripletCFlat));
       
       String expectedPitchTripletMiddleG = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartTripletMiddleG =  (int)((start + C_FLAT_NOTE.duration() * durationFactor) * ticksPerBeat) + "";
       String expectedDurationTripletMiddleG = (int)((C_FLAT_NOTE.duration() * durationFactor
               + MIDDLE_G_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
 
       //Test NOTE_ON event Triplet's MIDDLE_G_NOTE
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedPitchTripletMiddleG));
       assertTrue(events[2].contains(expectedStartTripletMiddleG));
       
       //Test NOTE_OFF event Triplet's MIDDLE_G_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchTripletMiddleG));
       assertTrue(events[3].contains(expectedDurationTripletMiddleG));
       
       String expectedPitchTripletMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartTripletMiddleC =  (int)((start + C_FLAT_NOTE.duration() * durationFactor
               + MIDDLE_G_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       String expectedDurationTripletMiddleC = (int)((start + TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.duration()) * ticksPerBeat - 1) + "";
       
       //Test NOTE_ON event Triplet's MIDDLE_C_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchTripletMiddleC));
       assertTrue(events[4].contains(expectedStartTripletMiddleC));
       
       //Test NOTE_OFF event Triplet's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchTripletMiddleC));
       assertTrue(events[5].contains(expectedDurationTripletMiddleC));
       
       String expectedPitchNoteMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.duration() ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + TRIPLET_C_FLAT_MIDDLE_G_MIDDLE_C.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchNoteMiddleC));
       assertTrue(events[6].contains(expectedStartNote));
      
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchNoteMiddleC));
       assertTrue(events[7].contains(expectedDurationNote));
   }
   
 //Tests play of a Quadruplet followed by Note, tuplet length is 4
   @Test
   public void testPlayQuadrupletNoteLengthFour() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       int length = 4;
       double durationFactor = 0.75;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       QUADRUPLET_WITH_CHORD.play(player, start);
       MIDDLE_C_NOTE.play(player, start + QUADRUPLET_WITH_CHORD.duration());
       
       String piece = player.toString();
       System.out.println(piece);
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchQuadrupletCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartQuadrupletCFlat =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationQuadrupletCFlat = (int)((start + C_FLAT_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Quadruplet's C_FLAT_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchQuadrupletCFlat));
       assertTrue(events[0].contains(expectedStartQuadrupletCFlat));
       
       //Test NOTE_OFF event of Quadruplet's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitchQuadrupletCFlat));
       assertTrue(events[1].contains(expectedDurationQuadrupletCFlat));
       
       String expectedPitchQuadrupletChordMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartQuadrupletChordMiddleC =  (int)((start + C_FLAT_NOTE.duration() * durationFactor) * ticksPerBeat) + "";
       String expectedDurationQuadrupletChordMiddleC = (int)((C_FLAT_NOTE.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
 
       //Test NOTE_ON event Quadruplet's Chord MIDDLE_C_NOTE
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedPitchQuadrupletChordMiddleC));
       assertTrue(events[2].contains(expectedStartQuadrupletChordMiddleC));
       
       String expectedPitchQuadrupletChordCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartQuadrupletChordCFlat =  (int)((start + C_FLAT_NOTE.duration() * durationFactor) * ticksPerBeat) + "";
       String expectedDurationQuadrupletChordCFlat = (int)((C_FLAT_NOTE.duration() * durationFactor
               + C_FLAT_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event Quadruplet's Chord C_FLAT_NOTE
       assertTrue(events[3].contains("NOTE_ON"));
       assertTrue(events[3].contains(expectedPitchQuadrupletChordCFlat));
       assertTrue(events[3].contains(expectedStartQuadrupletChordCFlat));
       
       //Test NOTE_OFF event Quadruplet's Chord C_FLAT_NOTE
       assertTrue(events[4].contains("NOTE_OFF"));
       assertTrue(events[4].contains(expectedPitchQuadrupletChordCFlat));
       assertTrue(events[4].contains(expectedDurationQuadrupletChordCFlat));
       
       //Test NOTE_OFF event Quadruplet's Chord MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchQuadrupletChordMiddleC));
       assertTrue(events[5].contains(expectedDurationQuadrupletChordMiddleC));
       
       String expectedPitchQuadrupletMiddleG = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartQuadrupletMiddleG = (int)((start + C_FLAT_NOTE.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       String expectedDurationQuadrupletMiddleG = (int)((start + C_FLAT_NOTE.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event Quadruplet's MIDDLE_G_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchQuadrupletMiddleG));
       assertTrue(events[6].contains(expectedStartQuadrupletMiddleG));
       
       //Test NOTE_OFF event Quadruplet's MIDDLE_G_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchQuadrupletMiddleG));
       assertTrue(events[7].contains(expectedDurationQuadrupletMiddleG)); 
       
       String expectedPitchQuadrupletMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartQuadrupletMiddleC = (int)((start + C_FLAT_NOTE.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       String expectedDurationQuadrupletMiddleC = (int)((start + C_FLAT_NOTE.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event Quadruplet's MIDDLE_C_NOTE
       assertTrue(events[8].contains("NOTE_ON"));
       assertTrue(events[8].contains(expectedPitchQuadrupletMiddleC));
       assertTrue(events[8].contains(expectedStartQuadrupletMiddleC));
       
       //Test NOTE_OFF event Quadruplet's MIDDLE_C_NOTE
       assertTrue(events[9].contains("NOTE_OFF"));
       assertTrue(events[9].contains(expectedPitchQuadrupletMiddleC));
       assertTrue(events[9].contains(expectedDurationQuadrupletMiddleC)); 
       
       String expectedPitchNoteMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + QUADRUPLET_WITH_CHORD.duration() ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + QUADRUPLET_WITH_CHORD.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[10].contains("NOTE_ON"));
       assertTrue(events[10].contains(expectedPitchNoteMiddleC));
       assertTrue(events[10].contains(expectedStartNote));
      
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[11].contains("NOTE_OFF"));
       assertTrue(events[11].contains(expectedPitchNoteMiddleC));
       assertTrue(events[11].contains(expectedDurationNote));
   }
}