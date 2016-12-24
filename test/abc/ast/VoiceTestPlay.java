package abc.ast;

import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;

/**
 * 
 * Subclass of VoiceTest to test the play method, which uses a SequencePlayer
 * @category no_didit
 *
 */
public class VoiceTestPlay extends VoiceTest{
        
    private static final int BEATS_PER_MINUTE = 100;
    private static final int TICKS_PER_BEAT = 24;
    private static final int ZERO_START = 0;
    private static final int NONZERO_START = 3;
    
   /*
    * Testing strategy for play(SequencePlayer player, double start):
    *      Voice has 1 bar, >1 bar
    *      
    *  Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
    *       for correct duration
    */
   
   //Tests play of a Voice with 1 bar
   @Test
   public void testPlayVoiceOneBar() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       VOICE_ONE_BAR_UPPER.play(player, start);
       
       String piece = player.toString();
       //System.out.println(piece);
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchFirstNoteMeasure = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstNoteMeasure =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationFirstNoteMeasure = (int)((start + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Voice Bar's C_FLAT_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[0].contains(expectedStartFirstNoteMeasure));
       
       //Test NOTE_OFF event of Voice Bar's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitchFirstNoteMeasure));
       assertTrue(events[1].contains(expectedDurationFirstNoteMeasure));
       
       String expectedPitchThirdNoteMeasure = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartThirdNoteMeasure =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()) * ticksPerBeat) + "";
       String expectedDurationThirdNoteMeasure = (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()
                   + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Voice Bar's MIDDLE_C_NOTE
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[2].contains(expectedStartThirdNoteMeasure));
       
       //Test NOTE_OFF event of Voice Bar's MIDDLE_C_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchThirdNoteMeasure));
       assertTrue(events[3].contains(expectedDurationThirdNoteMeasure));
       
       String expectedPitchFourthNoteMeasure = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFourthNoteMeasure =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()
                   + MIDDLE_C_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationFourthNoteMeasure = (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()
                   + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Voice Bar's fourth C_FLAT_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[4].contains(expectedStartFourthNoteMeasure));
       
       //Test NOTE_OFF event of Voice Bar's fourth C_FLAT_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchFourthNoteMeasure));
       assertTrue(events[5].contains(expectedDurationFourthNoteMeasure));
       
   }
   
   //Tests play of a Voice with >1 bar
   @Test
   public void testPlayVoiceMoreThanOneBar() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       VOICE_THREE_BAR_LOWER.play(player, start);
       MIDDLE_C_NOTE.play(player, start + VOICE_THREE_BAR_LOWER.duration());
       
       String piece = player.toString();
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
       
       
       //new Measure(new Concat(new Concat(CONCAT_MIDDLE_C_NOTE_C_FLAT_NOTE, PI_DURATION), MIDDLE_G_NOTE)
       
       String expectedPitchFirstNoteDecimalMeasure = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstNoteDecimalMeasure =  (int)((start + WHOLE_DURATION_MEASURE.duration()) * ticksPerBeat) + "";
       String expectedDurationFirstNoteDecimalMeasure = (int)((start + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of decimal Measure's MIDDLE_C_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchFirstNoteDecimalMeasure));
       assertTrue(events[6].contains(expectedStartFirstNoteDecimalMeasure));
       
       //Test NOTE_OFF event of decimal Measure's MIDDLE_C_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchFirstNoteDecimalMeasure));
       assertTrue(events[7].contains(expectedDurationFirstNoteDecimalMeasure));
       
       String expectedPitchSecondNoteDecimalMeasure = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartSecondNoteDecimalMeasure =  (int)((start + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationSecondNoteDecimalMeasure = (int)((start + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration()
                       + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of decimal Measure's C_FLAT_NOTE
       assertTrue(events[8].contains("NOTE_ON"));
       assertTrue(events[8].contains(expectedPitchSecondNoteDecimalMeasure));
       assertTrue(events[8].contains(expectedStartSecondNoteDecimalMeasure));
       
       //Test NOTE_OFF event of decimal Measure's MIDDLE_C_NOTE
       assertTrue(events[9].contains("NOTE_OFF"));
       assertTrue(events[9].contains(expectedPitchSecondNoteDecimalMeasure));
       assertTrue(events[9].contains(expectedDurationSecondNoteDecimalMeasure));
       
       String expectedPitchFourthNoteDecimalMeasure = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFourthNoteDecimalMeasure =  (int)((start + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration()
                       + C_FLAT_NOTE.duration() + PI_DURATION.duration()) * ticksPerBeat) + "";
       String expectedDurationFourthNoteDecimalMeasure = (int)((start + WHOLE_DURATION_MEASURE.duration() + MIDDLE_C_NOTE.duration()
                       + C_FLAT_NOTE.duration() + PI_DURATION.duration() + MIDDLE_G_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Measure's fourth C_FLAT_NOTE
       assertTrue(events[10].contains("NOTE_ON"));
       assertTrue(events[10].contains(expectedPitchFourthNoteDecimalMeasure));
       assertTrue(events[10].contains(expectedStartFourthNoteDecimalMeasure));
       
       //Test NOTE_OFF event of Measure's fourth C_FLAT_NOTE
       assertTrue(events[11].contains("NOTE_OFF"));
       assertTrue(events[11].contains(expectedPitchFourthNoteDecimalMeasure));
       assertTrue(events[11].contains(expectedDurationFourthNoteDecimalMeasure));
       
       
       //check that MIDDLE_C_NOTE is played at right time
       String expectedPitchMiddleCNote= MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartMiddleCNote =  (int)((start + WHOLE_DURATION_MEASURE.duration() + DECIMAL_DURATION_MEASURE.duration()
       + ZERO_DURATION_MEASURE.duration()) * ticksPerBeat) + "";
       String expectedDurationMiddleCNote  = (int)((start + VOICE_THREE_BAR_LOWER.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of single MIDDLE_C_NOTE
       assertTrue(events[12].contains("NOTE_ON"));
       assertTrue(events[12].contains(expectedPitchMiddleCNote));
       assertTrue(events[12].contains(expectedStartMiddleCNote));
       
       //Test NOTE_OFF event of single MIDDLE_C_NOTE
       assertTrue(events[13].contains("NOTE_OFF"));
       assertTrue(events[13].contains(expectedPitchMiddleCNote));
       assertTrue(events[13].contains(expectedDurationMiddleCNote));
   }
}