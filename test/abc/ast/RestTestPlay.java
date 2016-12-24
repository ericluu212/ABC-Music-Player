package abc.ast;

import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;

/**
 * 
 * Subclass of RestTest to test the play method, which uses a SequencePlayer
 * @category no_didit
 *
 */
public class RestTestPlay extends RestTest{
        
    private static final int BEATS_PER_MINUTE = 100;
    private static final int TICKS_PER_BEAT = 24;
    private static final int ZERO_START = 0;
    private static final int NONZERO_START = 3;
    
   /*
    * Testing strategy for play(SequencePlayer player, double start):
    *      Rest followed by Note
    *      Note followed by one Rest followed by Note
    *      Note followed by multiple Rests followed by Note
    *      
    * Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
    *       for correct duration 
    */
   
   //Tests play of a Rest followed by Note
   @Test
   public void testPlayRestNote() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       ONE_DURATION.play(player, start);
       MIDDLE_C_NOTE.play(player, start + ONE_DURATION.duration());
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitch = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStart =  (int)((start+ONE_DURATION.duration()) * ticksPerBeat) + "";
       String expectedDuration = (int)((MIDDLE_C_NOTE.duration() + ONE_DURATION.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitch));
       assertTrue(events[0].contains(expectedStart));
       
       //Test NOTE_OFF event
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitch));
       assertTrue(events[1].contains(expectedDuration));
   }
   
   //Tests play of a Note followed by one Rest followed by Note
   @Test
   public void testPlayNoteSingleRestNote() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       C_FLAT_NOTE.play(player, start);
       ONE_DURATION.play(player, start + C_FLAT_NOTE.duration());
       MIDDLE_C_NOTE.play(player, start + C_FLAT_NOTE.duration() + ONE_DURATION.duration());
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       System.out.println(piece);
       
       String expectedFirstPitch = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedFirstStart =  (int)((start) * ticksPerBeat) + "";
       String expectedFirstDuration = (int)((C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event first note
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedFirstPitch));
       assertTrue(events[0].contains(expectedFirstStart));
       
       //Test NOTE_OFF event first note
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedFirstPitch));
       assertTrue(events[1].contains(expectedFirstDuration));
       
       String expectedSecondPitch = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedSecondStart =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()) * ticksPerBeat) + "";
       String expectedSecondDuration = (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + 
               MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       
       System.out.println(expectedSecondStart);
       //Test NOTE_ON event second note
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedSecondPitch));
       assertTrue(events[2].contains(expectedSecondStart));
       
       //Test NOTE_OFF event second note
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedSecondPitch));
       assertTrue(events[3].contains(expectedSecondDuration));
   }
   
   //Tests play of a Note followed by multiple Rests followed by Note
   @Test
   public void testPlayNoteMultipleRestsNote() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       C_FLAT_NOTE.play(player, start);
       ONE_DURATION.play(player, start + C_FLAT_NOTE.duration());
       PI_DURATION.play(player, start + C_FLAT_NOTE.duration() + ONE_DURATION.duration());
       MIDDLE_C_NOTE.play(player, start + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + PI_DURATION.duration());
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       System.out.println(piece);
       
       String expectedFirstPitch = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedFirstStart =  (int)((start) * ticksPerBeat) + "";
       String expectedFirstDuration = (int)((C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event first note
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedFirstPitch));
       assertTrue(events[0].contains(expectedFirstStart));
       
       //Test NOTE_OFF event first note
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedFirstPitch));
       assertTrue(events[1].contains(expectedFirstDuration));
       
       String expectedSecondPitch = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedSecondStart =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + PI_DURATION.duration()) * ticksPerBeat) + "";
       String expectedSecondDuration = (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + 
               + PI_DURATION.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       
       System.out.println(expectedSecondStart);
       //Test NOTE_ON event second note
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedSecondPitch));
       assertTrue(events[2].contains(expectedSecondStart));
       
       //Test NOTE_OFF event second note
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedSecondPitch));
       assertTrue(events[3].contains(expectedSecondDuration));
   }
}