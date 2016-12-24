package abc.ast;

import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;

/**
 * 
 * Subclass of PieceTest to test the play method, which uses a SequencePlayer
 * @category no_didit
 *
 */
public class PieceTestPlay extends PieceTest{
        
    private static final int BEATS_PER_MINUTE = 100;
    private static final int TICKS_PER_BEAT = 24;
    private static final int ZERO_START = 0;
    private static final int NONZERO_START = 3;
    
   /*
    * Testing strategy for play(SequencePlayer player, double start):
    *      Piece has 1 voice, >1 voice
    *      
    *  Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
    *       for correct duration
    */
   
   //Tests play of a Piece with 1 voice
   @Test
   public void testPlayVoiceOneBar() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       WHOLE_DURATION_PIECE_ONE_VOICE.play(player, start);
       
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
   
   //Tests play of a Piece with >1 voice
   @Test
   public void testPlayPieceMoreThanOneVoice() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       PIECE_TWO_VOICES_MIDDLE_LOWER.play(player, start);
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       System.out.println(piece);
       
       String expectedPitchFirstNoteFirstVoice = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstNoteFirstVoice =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationFirstNoteFirstVoice = (int)((start + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Piece's First Voice's C_FLAT_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchFirstNoteFirstVoice));
       assertTrue(events[0].contains(expectedStartFirstNoteFirstVoice));
       
       String expectedPitchFirstNoteSecondVoice = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFirstNoteSecondVoice =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationFirstNoteSecondVoice = (int)((start + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Piece's Second Voice's MIDDLE_C_NOTE
       assertTrue(events[1].contains("NOTE_ON"));
       assertTrue(events[1].contains(expectedPitchFirstNoteSecondVoice));
       assertTrue(events[1].contains(expectedStartFirstNoteSecondVoice));
       
       //Test NOTE_OFF event of Piece's First Voice's C_FLAT_NOTE
       assertTrue(events[2].contains("NOTE_OFF"));
       assertTrue(events[2].contains(expectedPitchFirstNoteFirstVoice));
       assertTrue(events[2].contains(expectedStartFirstNoteFirstVoice));
       
       //Test NOTE_OFF event of Piece's First Voice's C_FLAT_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchFirstNoteSecondVoice));
       assertTrue(events[3].contains(expectedStartFirstNoteSecondVoice));
       
       String expectedPitchSecondNoteSecondVoice = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartSecondNoteSecondVoice =  (int)((start + MIDDLE_C_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationSecondNoteSecondVoice = (int)((start + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Piece's Second Voice's C_FLAT_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchSecondNoteSecondVoice));
       assertTrue(events[4].contains(expectedStartSecondNoteSecondVoice));
       
       String expectedPitchThirdNoteFirstVoice = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartThirdNoteFirstVoice =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration()) * ticksPerBeat) + "";
       String expectedDurationThirdNoteFirstVoice = (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Piece's First Voice's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_ON"));
       assertTrue(events[5].contains(expectedPitchThirdNoteFirstVoice));
       assertTrue(events[5].contains(expectedStartThirdNoteFirstVoice));
       
       //Test NOTE_OFF event of Piece's Second Voice's C_FLAT_NOTE
       assertTrue(events[6].contains("NOTE_OFF"));
       assertTrue(events[6].contains(expectedPitchSecondNoteSecondVoice));
       assertTrue(events[6].contains(expectedDurationSecondNoteSecondVoice));
       
       //Test NOTE_OFF event of Piece's First Voice's MIDDLE_C_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchThirdNoteFirstVoice));
       assertTrue(events[7].contains(expectedDurationThirdNoteFirstVoice));
       
       String expectedPitchFourthNoteFirstVoice = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFourthNoteFirstVoice =  (int)((start + C_FLAT_NOTE.duration() + ONE_DURATION.duration() + MIDDLE_C_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationFourthNoteFirstVoice = (int)((start + WHOLE_DURATION_MEASURE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Piece's First Voice's C_FLAT_NOTE
       assertTrue(events[8].contains("NOTE_ON"));
       assertTrue(events[8].contains(expectedPitchFourthNoteFirstVoice));
       assertTrue(events[8].contains(expectedStartFourthNoteFirstVoice));
       
       String expectedPitchFourthNoteSecondVoice = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartFourthNoteSecondVoice =  (int)((start + MIDDLE_C_NOTE.duration() + C_FLAT_NOTE.duration() + PI_DURATION.duration()) * ticksPerBeat) + "";
       String expectedDurationFourthNoteSecondVoice = (int)((start + DECIMAL_DURATION_MEASURE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Piece's Second Voice's MIDDLE_G_NOTE
       assertTrue(events[9].contains("NOTE_ON"));
       assertTrue(events[9].contains(expectedPitchFourthNoteSecondVoice));
       assertTrue(events[9].contains(expectedStartFourthNoteSecondVoice));
       
       //Test NOTE_OFF event of Piece's First Voice's C_FLAT_NOTE
       assertTrue(events[10].contains("NOTE_OFF"));
       assertTrue(events[10].contains(expectedPitchFourthNoteFirstVoice));
       assertTrue(events[10].contains(expectedDurationFourthNoteFirstVoice));
       
       //Test NOTE_OFF event of Piece's Second Voice's MIDDLE_G_NOTE
       assertTrue(events[11].contains("NOTE_OFF"));
       assertTrue(events[11].contains(expectedPitchFourthNoteSecondVoice));
       assertTrue(events[11].contains(expectedDurationFourthNoteSecondVoice));
   }
}