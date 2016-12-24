package abc.ast;

import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;

/**
 * 
 * Subclass of RepeatTest to test the play method, which uses a SequencePlayer
 * @category no_didit
 *
 */
public class RepeatTestPlay extends RepeatTest{
        
    private static final int BEATS_PER_MINUTE = 100;
    private static final int TICKS_PER_BEAT = 24;
    private static final int ZERO_START = 0;
    private static final int NONZERO_START = 3;
    
   /*
    * Testing strategy for play(SequencePlayer player, double start):
    *      Measure followed by Repeat (because that's what happens in actual music)
    *      
    *  Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
    *       for correct duration
    */
   
   //Tests play of a Measure followed by a Repeat
   @Test
   public void testPlayMeasureThenRepeat() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       WHOLE_DURATION_MEASURE.play(player, start);
       DIFFERENT_END_REPEAT_SECOND_END_NOTE.play(player, start+WHOLE_DURATION_MEASURE.duration());
       
       String piece = player.toString();
       System.out.println(piece);
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchFirstNoteMeasure = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstNoteMeasure =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationFirstNoteMeasure = (int)((start + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Measure's C_FLAT_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[0].contains(expectedStartFirstNoteMeasure));
       
       //Test NOTE_OFF event of Measure's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[1].contains(expectedDurationFirstNoteMeasure));
       
       String expectedPitchThirdNoteMeasure = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartThirdNoteMeasure =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()) * ticksPerBeat) + "";
       String expectedDurationThirdNoteMeasure = (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()
                   + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Measure's MIDDLE_C_NOTE
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[2].contains(expectedStartThirdNoteMeasure));
       
       //Test NOTE_OFF event of Measure's MIDDLE_C_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[3].contains(expectedDurationThirdNoteMeasure));
       
       String expectedPitchFourthNoteMeasure = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFourthNoteMeasure =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()
                   + MIDDLE_C_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationFourthNoteMeasure = (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()
                   + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Measure's fourth C_FLAT_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[4].contains(expectedStartFourthNoteMeasure));
       
       //Test NOTE_OFF event of Measure's fourth C_FLAT_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[5].contains(expectedDurationFourthNoteMeasure));
       
       //DIFFERENT_END_REPEAT_SECOND_END_NOTE = new Repeat(WHOLE_DURATION_MEASURE, DECIMAL_DURATION_MEASURE, MIDDLE_C_NOTE);
       String expectedStartWholeMeasureFirstRepeatFirstNote = (int)((start + WHOLE_DURATION_MEASURE.duration())* ticksPerBeat) + "";
       String expectedDurationWholeMeasureFirstRepeatFirstNote = (int)((start + WHOLE_DURATION_MEASURE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First Repeat Measure's C_FLAT_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[6].contains(expectedStartWholeMeasureFirstRepeatFirstNote));
       
       //Test NOTE_OFF event of First Repeat Measure's C_FLAT_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[7].contains(expectedDurationWholeMeasureFirstRepeatFirstNote));
       
       String expectedStartWholeMeasureFirstRepeatThirdNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + C_FLAT_NOTE.duration() + ONE_DURATION.duration())* ticksPerBeat) + "";
       String expectedDurationWholeMeasureFirstRepeatThirdNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First Repeat Measure's MIDDLE_C_NOTE
       assertTrue(events[8].contains("NOTE_ON"));
       assertTrue(events[8].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[8].contains(expectedStartWholeMeasureFirstRepeatThirdNote));
       
       //Test NOTE_OFF event of First Repeat Measure's MIDDLE_C_NOTE
       assertTrue(events[9].contains("NOTE_OFF"));
       assertTrue(events[9].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[9].contains(expectedDurationWholeMeasureFirstRepeatThirdNote));
       
       String expectedStartWholeMeasureFirstRepeatFourthNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       String expectedDurationWholeMeasureFirstRepeatFourthNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First Repeat Measure's fourth C_FLAT_NOTE
       assertTrue(events[10].contains("NOTE_ON"));
       assertTrue(events[10].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[10].contains(expectedStartWholeMeasureFirstRepeatFourthNote));
       
       //Test NOTE_OFF event of First Repeat Measure's fourth C_FLAT_NOTE
       assertTrue(events[11].contains("NOTE_OFF"));
       assertTrue(events[11].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[11].contains(expectedDurationWholeMeasureFirstRepeatFourthNote));
       
       String expectedPitchFirstEndFirstNote = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstEndFirstNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + WHOLE_DURATION_MEASURE.duration())* ticksPerBeat) + "";
       String expectedDurationFirstEndFirstNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First End Measure's MIDDLE_C_NOTE
       assertTrue(events[12].contains("NOTE_ON"));
       assertTrue(events[12].contains(expectedPitchFirstEndFirstNote));
       assertTrue(events[12].contains(expectedStartFirstEndFirstNote));
       
       //Test NOTE_OFF event of First End Measure's MIDDLE_C_NOTE
       assertTrue(events[13].contains("NOTE_OFF"));
       assertTrue(events[13].contains(expectedPitchFirstEndFirstNote));
       assertTrue(events[13].contains(expectedDurationFirstEndFirstNote));
       
       String expectedPitchFirstEndSecondNote = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstEndSecondNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       String expectedDurationFirstEndSecondNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First End Measure's C_FLAT_NOTE
       assertTrue(events[14].contains("NOTE_ON"));
       assertTrue(events[14].contains(expectedPitchFirstEndSecondNote));
       assertTrue(events[14].contains(expectedStartFirstEndSecondNote));
       
       //Test NOTE_OFF event of First End Measure's C_FLAT_NOTE
       assertTrue(events[15].contains("NOTE_OFF"));
       assertTrue(events[15].contains(expectedPitchFirstEndSecondNote));
       assertTrue(events[15].contains(expectedDurationFirstEndSecondNote));
       
       String expectedPitchFirstEndFourthNote = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstEndFourthNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration()
       + PI_DURATION.duration())* ticksPerBeat) + "";
       String expectedDurationFirstEndFourthNote = (int)((start + WHOLE_DURATION_MEASURE.duration() 
       + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration() 
       + PI_DURATION.duration() + MIDDLE_G_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First End Measure's MIDDLE_G_NOTE
       assertTrue(events[16].contains("NOTE_ON"));
       assertTrue(events[16].contains(expectedPitchFirstEndFourthNote));
       assertTrue(events[16].contains(expectedStartFirstEndFourthNote));
       
       //Test NOTE_OFF event of First End Measure's MIDDLE_G_NOTE
       assertTrue(events[17].contains("NOTE_OFF"));
       assertTrue(events[17].contains(expectedPitchFirstEndFourthNote));
       assertTrue(events[17].contains(expectedDurationFirstEndFourthNote));
       
       
       
       String expectedStartWholeMeasureSecondRepeatFirstNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration())* ticksPerBeat) + "";
       String expectedDurationWholeMeasureSecondRepeatFirstNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First Repeat Measure's C_FLAT_NOTE
       assertTrue(events[18].contains("NOTE_ON"));
       assertTrue(events[18].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[18].contains(expectedStartWholeMeasureSecondRepeatFirstNote));
       
       //Test NOTE_OFF event of First Repeat Measure's C_FLAT_NOTE
       assertTrue(events[19].contains("NOTE_OFF"));
       assertTrue(events[19].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[19].contains(expectedDurationWholeMeasureSecondRepeatFirstNote));
       
       String expectedStartWholeMeasureSecondRepeatThirdNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration() + C_FLAT_NOTE.duration()
       + ONE_DURATION.duration())* ticksPerBeat) + "";
       String expectedDurationWholeMeasureSecondRepeatThirdNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration() + C_FLAT_NOTE.duration()
       + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       
       //Test NOTE_ON event of First Repeat Measure's MIDDLE_C_NOTE
       assertTrue(events[20].contains("NOTE_ON"));
       assertTrue(events[20].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[20].contains(expectedStartWholeMeasureSecondRepeatThirdNote));
       
       //Test NOTE_OFF event of First Repeat Measure's MIDDLE_C_NOTE
       assertTrue(events[21].contains("NOTE_OFF"));
       assertTrue(events[21].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[21].contains(expectedDurationWholeMeasureSecondRepeatThirdNote));
       
       String expectedStartWholeMeasureSecondRepeatFourthNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration() + C_FLAT_NOTE.duration()
       + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       String expectedDurationWholeMeasureSecondRepeatFourthNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration() + C_FLAT_NOTE.duration()
       + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of First Repeat Measure's fourth C_FLAT_NOTE
       assertTrue(events[22].contains("NOTE_ON"));
       assertTrue(events[22].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[22].contains(expectedStartWholeMeasureSecondRepeatFourthNote));
       
       //Test NOTE_OFF event of First Repeat Measure's fourth C_FLAT_NOTE
       assertTrue(events[23].contains("NOTE_OFF"));
       assertTrue(events[23].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[23].contains(expectedDurationWholeMeasureSecondRepeatFourthNote));
       
       
       
       //Second ending is MIDDLE_G_NOTE
       String expectedPitchSecondEndGNote = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartSecondEndGNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration() + WHOLE_DURATION_MEASURE.duration())* ticksPerBeat) + "";
       String expectedDurationSecondEndGNote = (int)((start + WHOLE_DURATION_MEASURE.duration()
       + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration() + WHOLE_DURATION_MEASURE.duration()
       + MIDDLE_G_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Repeat Second End's MIDDLE_G_NOTE
       assertTrue(events[24].contains("NOTE_ON"));
       assertTrue(events[24].contains(expectedPitchSecondEndGNote));
       assertTrue(events[24].contains(expectedStartSecondEndGNote));
       
       //Test NOTE_OFF event of Repeat Second End's MIDDLE_G_NOTE
       assertTrue(events[25].contains("NOTE_OFF"));
       assertTrue(events[25].contains(expectedPitchSecondEndGNote));
       assertTrue(events[25].contains(expectedDurationSecondEndGNote));
   }
}