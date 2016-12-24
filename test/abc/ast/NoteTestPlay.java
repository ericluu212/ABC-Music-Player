package abc.ast;

import static org.junit.Assert.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;

/**
 * Subclass of NoteTest to test the play and tupletPlay methods, which uses a SequencePlayer
 * @category no_didit
 */

public class NoteTestPlay extends NoteTest{
    
    /**
     * play(): Compare to SequencePlayer.toString(), check if note's event is correct, 
     *          multiple note events are in correct order
     */
    
    public static final Note NO_DURATION_NOTE = new Note(0, Pitch.MIDDLE_C);
    public static final Note MID_C = new Note(2.0, Pitch.MIDDLE_C);
    
    //Tests play of a single note zero duration
    @Test
    public void testPlaySingleNoteZeroDuration() throws MidiUnavailableException, InvalidMidiDataException{
        int ticksPerBeat = 1000;
        int beatsPerMinute = 1000;
        double start = 0.0;
        
        SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
        NO_DURATION_NOTE.play(player, start);
        
        String piece = player.toString();
        String[] events = piece.split("\\r\\n|\\n|\\r");
        
        String expectedPitch = NO_DURATION_NOTE.getPitch().toMidiNote() + "";
        String expectedStart =  (int)(start * ticksPerBeat) + "";
        String expectedDuration = (int)(start * ticksPerBeat) + "";
        
        //Test NOTE_ON event
        assertTrue(events[0].contains("NOTE_ON"));
        assertTrue(events[0].contains(expectedPitch));
        assertTrue(events[0].contains(expectedStart));
        
        //Test NOTE_OFF event
        assertTrue(events[1].contains("NOTE_OFF"));
        assertTrue(events[1].contains(expectedPitch));
        assertTrue(events[1].contains(expectedDuration));
    }
    
    //Tests play of a single note nonzero duration
    @Test
    public void testPlaySingleNoteNonzeroDuration() throws MidiUnavailableException, InvalidMidiDataException{
        int ticksPerBeat = 1000;
        int beatsPerMinute = 1000;
        double start = 0.0;
        
        SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
        MID_C.play(player, start);
        
        String piece = player.toString();
        String[] events = piece.split("\\r\\n|\\n|\\r");
        
        String expectedPitch = MID_C.getPitch().toMidiNote() + "";
        String expectedStart =  (int)(start * ticksPerBeat) + "";
        String expectedDuration = (int)(MID_C.duration() * ticksPerBeat) + "";
        
        //Test NOTE_ON event
        assertTrue(events[0].contains("NOTE_ON"));
        assertTrue(events[0].contains(expectedPitch));
        assertTrue(events[0].contains(expectedStart));
        
        //Test NOTE_OFF event
        assertTrue(events[1].contains("NOTE_OFF"));
        assertTrue(events[1].contains(expectedPitch));
        assertTrue(events[1].contains(expectedDuration));
    }
    
    //Tests play of multiple notes
    @Test
    public void testPlayMultipleNotes() throws MidiUnavailableException, InvalidMidiDataException{
        int ticksPerBeat = 1000;
        int beatsPerMinute = 1000;
        double start = 0.0;
        
        SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
        MID_C.play(player, start);
        MID_C.play(player, start + MID_C.duration());
        
        String piece = player.toString();
        String[] events = piece.split("\\r\\n|\\n|\\r");
        
        //Same pitch for both notes: Middle C
        String expectedPitch = MID_C.getPitch().toMidiNote() + "";
        
        String expectedStartNote1 =  (int)(start * ticksPerBeat) + "";
        
        //Also the tick when Note 1 turns off
        String expectedStartNote2 = (int)(MID_C.duration() * ticksPerBeat) + "";
        
        String expectedEndNote2 = (int)(MID_C.duration() * ticksPerBeat * 2) + "";
        
        //Test NOTE_ON of event 1
        assertTrue(events[0].contains("NOTE_ON"));
        assertTrue(events[0].contains(expectedPitch));
        assertTrue(events[0].contains(expectedStartNote1));
        
        //Test NOTE_OFF of event 1
        assertTrue(events[1].contains("NOTE_OFF"));
        assertTrue(events[1].contains(expectedPitch));
        assertTrue(events[1].contains(expectedStartNote2));
        
        //Test NOTE_ON of event 2
        assertTrue(events[2].contains("NOTE_ON"));
        assertTrue(events[2].contains(expectedPitch));
        assertTrue(events[2].contains(expectedStartNote2));
        
        //Test NOTE_OFF of event2
        assertTrue(events[3].contains("NOTE_OFF"));
        assertTrue(events[3].contains(expectedPitch));
        assertTrue(events[3].contains(expectedEndNote2));
    }
    
    /**
     * Testing strategy for tupletPlay(SequencePlayer player, double start, int length):
     *      Note followed by Note
     *      Length = 2, 3, 4
     *      
     *  Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
     *       for correct duration. The method is almost identical to play except for the duration scaling done in tupletPlay
     */
    //Tests tupletPlay of a Note followed by Note, length = 2
    @Test
    public void testTupletPlayLengthTwo() throws MidiUnavailableException, InvalidMidiDataException{
        int ticksPerBeat = 1000;
        int beatsPerMinute = 1000;
        double start = 0.0;
        int length = 2;
        double durationFactor = 1.5;
        
        SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
        MID_C.tupletPlay(player, start, length);
        MID_C.tupletPlay(player, start + MID_C.duration() * durationFactor, length);
        
        String piece = player.toString();
        String[] events = piece.split("\\r\\n|\\n|\\r");
        
        //Same pitch for both notes: Middle C
        String expectedPitch = MID_C.getPitch().toMidiNote() + "";
        
        String expectedStartNote1 =  (int)(start * ticksPerBeat) + "";
        
        //Also the tick when Note 1 turns off
        String expectedStartNote2 = (int)(MID_C.duration() * durationFactor * ticksPerBeat) + "";
        
        String expectedEndNote2 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2) + "";
        
        //Test NOTE_ON of event 1
        assertTrue(events[0].contains("NOTE_ON"));
        assertTrue(events[0].contains(expectedPitch));
        assertTrue(events[0].contains(expectedStartNote1));
        
        //Test NOTE_OFF of event 1
        assertTrue(events[1].contains("NOTE_OFF"));
        assertTrue(events[1].contains(expectedPitch));
        assertTrue(events[1].contains(expectedStartNote2));
        
        //Test NOTE_ON of event 2
        assertTrue(events[2].contains("NOTE_ON"));
        assertTrue(events[2].contains(expectedPitch));
        assertTrue(events[2].contains(expectedStartNote2));
        
        //Test NOTE_OFF of event2
        assertTrue(events[3].contains("NOTE_OFF"));
        assertTrue(events[3].contains(expectedPitch));
        assertTrue(events[3].contains(expectedEndNote2));
    }
    
    //Tests tupletPlay of a Note followed by Note, length = 3
    @Test
    public void testTupletPlayLengthThree() throws MidiUnavailableException, InvalidMidiDataException{
        int ticksPerBeat = 1000;
        int beatsPerMinute = 1000;
        double start = 0.0;
        int length = 3;
        double durationFactor = 2.0/3.0;
        
        SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
        MID_C.tupletPlay(player, start, length);
        MID_C.tupletPlay(player, start + MID_C.duration() * durationFactor, length);
        C_FLAT_NOTE.tupletPlay(player, start + MID_C.duration() * durationFactor + MID_C.duration() * durationFactor, length);
        
        String piece = player.toString();
        String[] events = piece.split("\\r\\n|\\n|\\r");
        
        //Same pitch for both notes: Middle C
        String expectedPitch = MID_C.getPitch().toMidiNote() + "";
        
        String expectedStartNote1 =  (int)(start * ticksPerBeat) + "";
        
        //Also the tick when Note 1 turns off
        String expectedStartNote2 = (int)(MID_C.duration() * durationFactor * ticksPerBeat) + "";
        
        String expectedEndNote2 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2) + "";
        
        //Also the tick when Note 2 turns off
        String expectedPitchThirdNote = C_FLAT_NOTE.getPitch().toMidiNote() + "";
        String expectedStartNote3 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2) + "";
        String expectedEndNote3 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2 
                + C_FLAT_NOTE.duration() * durationFactor * ticksPerBeat)-1 + "";

        //Test NOTE_ON of event 1
        assertTrue(events[0].contains("NOTE_ON"));
        assertTrue(events[0].contains(expectedPitch));
        assertTrue(events[0].contains(expectedStartNote1));
        
        //Test NOTE_OFF of event 1
        assertTrue(events[1].contains("NOTE_OFF"));
        assertTrue(events[1].contains(expectedPitch));
        assertTrue(events[1].contains(expectedStartNote2));
        
        //Test NOTE_ON of event 2
        assertTrue(events[2].contains("NOTE_ON"));
        assertTrue(events[2].contains(expectedPitch));
        assertTrue(events[2].contains(expectedStartNote2));
        
        //Test NOTE_OFF of event 2
        assertTrue(events[3].contains("NOTE_OFF"));
        assertTrue(events[3].contains(expectedPitch));
        assertTrue(events[3].contains(expectedEndNote2));
        
        //Test NOTE_ON of event 3
        assertTrue(events[4].contains("NOTE_ON"));
        assertTrue(events[4].contains(expectedPitchThirdNote));
        assertTrue(events[4].contains(expectedStartNote3));
        
        //Test NOTE_OFF of event 3
        assertTrue(events[5].contains("NOTE_OFF"));
        assertTrue(events[5].contains(expectedPitchThirdNote));
        assertTrue(events[5].contains(expectedEndNote3));
    }
    
  //Tests tupletPlay of a Note followed by Note, length = 4
    @Test
    public void testTupletPlayLengthFour() throws MidiUnavailableException, InvalidMidiDataException{
        int ticksPerBeat = 1000;
        int beatsPerMinute = 1000;
        double start = 0.0;
        int length = 4;
        double durationFactor = 0.75;
        
        SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
        MID_C.tupletPlay(player, start, length);
        MID_C.tupletPlay(player, start + MID_C.duration() * durationFactor, length);
        C_FLAT_NOTE.tupletPlay(player, start + MID_C.duration() * durationFactor + MID_C.duration() * durationFactor, length);
        C_FLAT_NOTE.tupletPlay(player, start + MID_C.duration() * durationFactor + MID_C.duration() * durationFactor
                + C_FLAT_NOTE.duration() * durationFactor, length);
        
        String piece = player.toString();
        String[] events = piece.split("\\r\\n|\\n|\\r");
        
        //Same pitch for both notes: Middle C
        String expectedPitch = MID_C.getPitch().toMidiNote() + "";
        
        String expectedStartNote1 =  (int)(start * ticksPerBeat) + "";
        
        //Also the tick when Note 1 turns off
        String expectedStartNote2 = (int)(MID_C.duration() * durationFactor * ticksPerBeat) + "";
        
        String expectedEndNote2 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2) + "";
        
        //Also the tick when Note 2 turns off
        String expectedPitchThirdNote = C_FLAT_NOTE.getPitch().toMidiNote() + "";
        String expectedStartNote3 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2) + "";
        String expectedEndNote3 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2 
                + C_FLAT_NOTE.duration() * durationFactor * ticksPerBeat) + "";
        
        //Also the tick when Note 3 turns off
        String expectedPitchFourthNote = C_FLAT_NOTE.getPitch().toMidiNote() + "";
        String expectedStartNote4 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2 
                + C_FLAT_NOTE.duration() * durationFactor * ticksPerBeat) + "";
        String expectedEndNote4 = (int)(MID_C.duration() * durationFactor * ticksPerBeat * 2 
                + C_FLAT_NOTE.duration() * durationFactor * ticksPerBeat * 2) + "";

        //Test NOTE_ON of event 1
        assertTrue(events[0].contains("NOTE_ON"));
        assertTrue(events[0].contains(expectedPitch));
        assertTrue(events[0].contains(expectedStartNote1));
        
        //Test NOTE_OFF of event 1
        assertTrue(events[1].contains("NOTE_OFF"));
        assertTrue(events[1].contains(expectedPitch));
        assertTrue(events[1].contains(expectedStartNote2));
        
        //Test NOTE_ON of event 2
        assertTrue(events[2].contains("NOTE_ON"));
        assertTrue(events[2].contains(expectedPitch));
        assertTrue(events[2].contains(expectedStartNote2));
        
        //Test NOTE_OFF of event 2
        assertTrue(events[3].contains("NOTE_OFF"));
        assertTrue(events[3].contains(expectedPitch));
        assertTrue(events[3].contains(expectedEndNote2));
        
        //Test NOTE_ON of event 3
        assertTrue(events[4].contains("NOTE_ON"));
        assertTrue(events[4].contains(expectedPitchThirdNote));
        assertTrue(events[4].contains(expectedStartNote3));
        
        //Test NOTE_OFF of event 3
        assertTrue(events[5].contains("NOTE_OFF"));
        assertTrue(events[5].contains(expectedPitchThirdNote));
        assertTrue(events[5].contains(expectedEndNote3));
        
        //Test NOTE_ON of event 4
        assertTrue(events[6].contains("NOTE_ON"));
        assertTrue(events[6].contains(expectedPitchFourthNote));
        assertTrue(events[6].contains(expectedStartNote4));
        
        //Test NOTE_OFF of event 4
        assertTrue(events[7].contains("NOTE_OFF"));
        assertTrue(events[7].contains(expectedPitchFourthNote));
        assertTrue(events[7].contains(expectedEndNote4));
    }
}
