package abc.ast;

import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

import abc.sound.SequencePlayer;

/**
 * 
 * Subclass of ChordTest to test the play and tupletPlay methods, which uses a SequencePlayer
 * @category no_didit
 *
 */
public class ChordTestPlay extends ChordTest{
        
    private static final int BEATS_PER_MINUTE = 100;
    private static final int TICKS_PER_BEAT = 24;
    private static final int ZERO_START = 0;
    private static final int NONZERO_START = 3;
    
   /*
    * Testing strategy for play(SequencePlayer player, double start):
    *      Chord followed by Note
    *      Note followed by one Chord followed by Note
    *      Note followed by multiple Chords followed by Note
    *      
    *  Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
    *       for correct duration
    */
   
   //Tests play of a Chord followed by Note
   @Test
   public void testPlayChordNote() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       CHORD_MIDDLE_C_AND_C_FLAT.play(player, start);
       MIDDLE_C_NOTE.play(player, start + CHORD_MIDDLE_C_AND_C_FLAT.duration());
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchChordMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordMiddleC =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordMiddleC = (int)((MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Chord's MIDDLE_C_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchChordMiddleC));
       assertTrue(events[0].contains(expectedStartChordMiddleC));
       
       String expectedPitchChordCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordCFlat =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordCFlat = (int)((C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test another NOTE_ON event because Chord's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_ON"));
       assertTrue(events[1].contains(expectedPitchChordCFlat));
       assertTrue(events[1].contains(expectedStartChordCFlat));
 
       //Test NOTE_OFF event Chord's C_FLAT_NOTE
       assertTrue(events[2].contains("NOTE_OFF"));
       assertTrue(events[2].contains(expectedPitchChordCFlat));
       assertTrue(events[2].contains(expectedDurationChordCFlat));
       
       //Test NOTE_OFF event Chord's MIDDLE_C_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchChordMiddleC));
       assertTrue(events[3].contains(expectedDurationChordMiddleC));
       
       String expectedPitchNote = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + CHORD_MIDDLE_C_AND_C_FLAT.duration() ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + CHORD_MIDDLE_C_AND_C_FLAT.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchNote));
       assertTrue(events[4].contains(expectedStartNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchChordMiddleC));
       assertTrue(events[5].contains(expectedDurationNote));
       
   }
   
   //Tests play of a Note followed by one Chord followed by Note
   @Test
   public void testPlayNoteSingleChordNote() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       MIDDLE_G_NOTE.play(player, start);
       CHORD_MIDDLE_C_AND_C_FLAT.play(player, start + MIDDLE_G_NOTE.duration());
       MIDDLE_C_NOTE.play(player, start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration());
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchMiddleG = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartMiddleG =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationMiddleG = (int)((MIDDLE_G_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of MIDDLE_G_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchMiddleG));
       assertTrue(events[0].contains(expectedStartMiddleG));
       
       //Test NOTE_OFF event of MIDDLE_G_NOTE
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitchMiddleG));
       assertTrue(events[1].contains(expectedDurationMiddleG));
       
       //Test the chord
       String expectedPitchChordMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordMiddleC =  (int)((start + MIDDLE_G_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationChordMiddleC = (int)((start + MIDDLE_G_NOTE.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Chord's MIDDLE_C_NOTE
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedPitchChordMiddleC));
       assertTrue(events[2].contains(expectedStartChordMiddleC));
       
       String expectedPitchChordCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordCFlat =  (int)((start + MIDDLE_G_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationChordCFlat = (int)((start + MIDDLE_G_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test another NOTE_ON event because Chord's C_FLAT_NOTE
       assertTrue(events[3].contains("NOTE_ON"));
       assertTrue(events[3].contains(expectedPitchChordCFlat));
       assertTrue(events[3].contains(expectedStartChordCFlat));
 
       //Test NOTE_OFF event Chord's C_FLAT_NOTE
       assertTrue(events[4].contains("NOTE_OFF"));
       assertTrue(events[4].contains(expectedPitchChordCFlat));
       assertTrue(events[4].contains(expectedDurationChordCFlat));
       
       //Test NOTE_OFF event Chord's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchChordMiddleC));
       assertTrue(events[5].contains(expectedDurationChordMiddleC));
       
       //Now test the note after the chord
       String expectedPitchNote = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration() ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchNote));
       assertTrue(events[6].contains(expectedStartNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchChordMiddleC));
       assertTrue(events[7].contains(expectedDurationNote));
       
   }
   
   //Tests play of a Note followed by multiple Chords followed by Note
   @Test
   public void testPlayNoteMultipleChordsNote() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       MIDDLE_G_NOTE.play(player, start);
       CHORD_MIDDLE_C_AND_C_FLAT.play(player, start + MIDDLE_G_NOTE.duration());
       CHORD_NONZERO_DURATION_ONE_NOTE.play(player, start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration());
       MIDDLE_C_NOTE.play(player, start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration() + CHORD_NONZERO_DURATION_ONE_NOTE.duration());
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       String expectedPitchMiddleG = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartMiddleG =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationMiddleG = (int)((MIDDLE_G_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of MIDDLE_G_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchMiddleG));
       assertTrue(events[0].contains(expectedStartMiddleG));
       
       //Test NOTE_OFF event of MIDDLE_G_NOTE
       assertTrue(events[1].contains("NOTE_OFF"));
       assertTrue(events[1].contains(expectedPitchMiddleG));
       assertTrue(events[1].contains(expectedDurationMiddleG));
       
       //Test the first Chord
       String expectedPitchChordMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordMiddleC =  (int)((start + MIDDLE_G_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationChordMiddleC = (int)((start + MIDDLE_G_NOTE.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Chord's MIDDLE_C_NOTE
       assertTrue(events[2].contains("NOTE_ON"));
       assertTrue(events[2].contains(expectedPitchChordMiddleC));
       assertTrue(events[2].contains(expectedStartChordMiddleC));
       
       String expectedPitchChordCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordCFlat =  (int)((start + MIDDLE_G_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationChordCFlat = (int)((start + MIDDLE_G_NOTE.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
       
       //Test another NOTE_ON event because Chord's C_FLAT_NOTE
       assertTrue(events[3].contains("NOTE_ON"));
       assertTrue(events[3].contains(expectedPitchChordCFlat));
       assertTrue(events[3].contains(expectedStartChordCFlat));
 
       //Test NOTE_OFF event Chord's C_FLAT_NOTE
       assertTrue(events[4].contains("NOTE_OFF"));
       assertTrue(events[4].contains(expectedPitchChordCFlat));
       assertTrue(events[4].contains(expectedDurationChordCFlat));
       
       //Test NOTE_OFF event Chord's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchChordMiddleC));
       assertTrue(events[5].contains(expectedDurationChordMiddleC));
       
       
       //Now test the second Chord, which has just 1 note
       String expectedPitchSingleNoteChord = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartSingleNoteChord =  (int)((start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration()) * ticksPerBeat) + "";
       String expectedDurationSingleNoteChord = (int)((start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration() + C_FLAT_NOTE.duration())* ticksPerBeat) + "";
                    
       //Test NOTE_ON event single note Chord's C_FLAT_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchSingleNoteChord));
       assertTrue(events[6].contains(expectedStartSingleNoteChord));
       
       //Test NOTE_OFF event single note Chord's C_FLAT_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchSingleNoteChord));
       assertTrue(events[7].contains(expectedDurationSingleNoteChord));
       
       
       //Now test the last note that comes after the chords
       String expectedPitchLastNote = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartLastNote =  (int)((start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration() + CHORD_NONZERO_DURATION_ONE_NOTE.duration()) * ticksPerBeat) + "";
       String expectedDurationLastNote = (int)((start + MIDDLE_G_NOTE.duration() + CHORD_MIDDLE_C_AND_C_FLAT.duration() + CHORD_NONZERO_DURATION_ONE_NOTE.duration() + MIDDLE_C_NOTE.duration())* ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[8].contains("NOTE_ON"));
       assertTrue(events[8].contains(expectedPitchLastNote));
       assertTrue(events[8].contains(expectedStartLastNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[9].contains("NOTE_OFF"));
       assertTrue(events[9].contains(expectedPitchLastNote));
       assertTrue(events[9].contains(expectedDurationLastNote));
   }
   
   /*
    * Testing strategy for tupletPlay(SequencePlayer player, double start, int length):
    *      Chord followed by Note
    *      Length = 2, 3, 4
    *      
    *  Effectively, we compare to SequencePlayer.toString(), check if our additions to player are inserted in correct order 
    *       for correct duration. The method is almost identical to play except for the duration scaling done in tupletPlay
    */
   
   //Tests tupletPlay of a Chord followed by Note, length = 2
   @Test
   public void testTupletPlayChordNoteLengthTwo() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       int length = 2;
       double durationFactor = 1.5;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       CHORD_MIDDLE_C_AND_C_FLAT.tupletPlay(player, start, length);
       MIDDLE_C_NOTE.tupletPlay(player, start + CHORD_MIDDLE_C_AND_C_FLAT.duration() * durationFactor, length);
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       
       //Test the Chord in the Tuplet
       String expectedPitchChordMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordMiddleC =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordMiddleC = (int)((MIDDLE_C_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Chord's MIDDLE_C_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchChordMiddleC));
       assertTrue(events[0].contains(expectedStartChordMiddleC));
       
       String expectedPitchChordCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordCFlat =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordCFlat = (int)((C_FLAT_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test another NOTE_ON event because Chord's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_ON"));
       assertTrue(events[1].contains(expectedPitchChordCFlat));
       assertTrue(events[1].contains(expectedStartChordCFlat));

       //Test NOTE_OFF event Chord's C_FLAT_NOTE
       assertTrue(events[2].contains("NOTE_OFF"));
       assertTrue(events[2].contains(expectedPitchChordCFlat));
       assertTrue(events[2].contains(expectedDurationChordCFlat));
       
       //Test NOTE_OFF event Chord's MIDDLE_C_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchChordMiddleC));
       assertTrue(events[3].contains(expectedDurationChordMiddleC));
       
       //Now test the Note in the Tuplet
       String expectedPitchTupletNote = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + (MIDDLE_C_NOTE.duration() * durationFactor) ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + (MIDDLE_C_NOTE.duration() + MIDDLE_C_NOTE.duration()) * durationFactor)
               * ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchTupletNote));
       assertTrue(events[4].contains(expectedStartNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchTupletNote));
       assertTrue(events[5].contains(expectedDurationNote));   
   }
   
   //Tests tupletPlay of a Chord followed by Note, length = 3
   @Test
   public void testTupletPlayChordNoteLengthThree() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       int length = 3;
       double durationFactor = 2.0/3.0;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       CHORD_MIDDLE_C_AND_C_FLAT.tupletPlay(player, start, length);
       MIDDLE_C_NOTE.tupletPlay(player, start + CHORD_MIDDLE_C_AND_C_FLAT.duration() * durationFactor, length);
       MIDDLE_G_NOTE.tupletPlay(player, start + CHORD_MIDDLE_C_AND_C_FLAT.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor, length);
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       //Test the Chord in the Tuplet
       String expectedPitchChordMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordMiddleC =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordMiddleC = (int)((MIDDLE_C_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Chord's MIDDLE_C_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchChordMiddleC));
       assertTrue(events[0].contains(expectedStartChordMiddleC));
       
       String expectedPitchChordCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordCFlat =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordCFlat = (int)((C_FLAT_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test another NOTE_ON event because Chord's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_ON"));
       assertTrue(events[1].contains(expectedPitchChordCFlat));
       assertTrue(events[1].contains(expectedStartChordCFlat));

       //Test NOTE_OFF event Chord's C_FLAT_NOTE
       assertTrue(events[2].contains("NOTE_OFF"));
       assertTrue(events[2].contains(expectedPitchChordCFlat));
       assertTrue(events[2].contains(expectedDurationChordCFlat));
       
       //Test NOTE_OFF event Chord's MIDDLE_C_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchChordMiddleC));
       assertTrue(events[3].contains(expectedDurationChordMiddleC));
       
       
       //Now test the first Note in the Tuplet
       String expectedPitchTupletNote = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + MIDDLE_C_NOTE.duration() * durationFactor ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + (MIDDLE_C_NOTE.duration() + MIDDLE_C_NOTE.duration()) * durationFactor)
               * ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchTupletNote));
       assertTrue(events[4].contains(expectedStartNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchTupletNote));
       assertTrue(events[5].contains(expectedDurationNote));   
       
       
       //Now test the second Note in the Tuplet
       String expectedPitchTupletSecondNote = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartSecondNote =  (int)((start + MIDDLE_C_NOTE.duration() * durationFactor 
               + MIDDLE_C_NOTE.duration() * durationFactor ) * ticksPerBeat) + "";
       String expectedDurationSecondNote = (int)((start + MIDDLE_C_NOTE.duration() * durationFactor 
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor) * ticksPerBeat)-1 + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchTupletSecondNote));
       assertTrue(events[6].contains(expectedStartSecondNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchTupletSecondNote));
       assertTrue(events[7].contains(expectedDurationSecondNote));  
   }
   
   //Tests tupletPlay of a Chord followed by Note, length = 4
   @Test
   public void testTupletPlayChordNoteLengthFour() throws MidiUnavailableException, InvalidMidiDataException{
       int ticksPerBeat = 1000;
       int beatsPerMinute = 1000;
       double start = 0.0;
       int length = 4;
       double durationFactor = 0.75;
       
       SequencePlayer player = new SequencePlayer(beatsPerMinute, ticksPerBeat);
       CHORD_MIDDLE_C_AND_C_FLAT.tupletPlay(player, start, length);
       MIDDLE_C_NOTE.tupletPlay(player, start + CHORD_MIDDLE_C_AND_C_FLAT.duration() * durationFactor, length);
       MIDDLE_G_NOTE.tupletPlay(player, start + CHORD_MIDDLE_C_AND_C_FLAT.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor, length);
       C_FLAT_NOTE.tupletPlay(player, start + CHORD_MIDDLE_C_AND_C_FLAT.duration() * durationFactor
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor, length);
       
       String piece = player.toString();
       String[] events = piece.split("\\r\\n|\\n|\\r");
       
       //Test the Chord in the Tuplet
       String expectedPitchChordMiddleC = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordMiddleC =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordMiddleC = (int)((MIDDLE_C_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test NOTE_ON event of Chord's MIDDLE_C_NOTE
       assertTrue(events[0].contains("NOTE_ON"));
       assertTrue(events[0].contains(expectedPitchChordMiddleC));
       assertTrue(events[0].contains(expectedStartChordMiddleC));
       
       String expectedPitchChordCFlat = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartChordCFlat =  (int)((start) * ticksPerBeat) + "";
       String expectedDurationChordCFlat = (int)((C_FLAT_NOTE.duration() * durationFactor)* ticksPerBeat) + "";
       
       //Test another NOTE_ON event because Chord's C_FLAT_NOTE
       assertTrue(events[1].contains("NOTE_ON"));
       assertTrue(events[1].contains(expectedPitchChordCFlat));
       assertTrue(events[1].contains(expectedStartChordCFlat));

       //Test NOTE_OFF event Chord's C_FLAT_NOTE
       assertTrue(events[2].contains("NOTE_OFF"));
       assertTrue(events[2].contains(expectedPitchChordCFlat));
       assertTrue(events[2].contains(expectedDurationChordCFlat));
       
       //Test NOTE_OFF event Chord's MIDDLE_C_NOTE
       assertTrue(events[3].contains("NOTE_OFF"));
       assertTrue(events[3].contains(expectedPitchChordMiddleC));
       assertTrue(events[3].contains(expectedDurationChordMiddleC));
       
       
       //Now test the first Note in the Tuplet
       String expectedPitchTupletNote = MIDDLE_C_NOTE.getPitch().toMidiNote() + "";
       String expectedStartNote =  (int)((start + MIDDLE_C_NOTE.duration() * durationFactor ) * ticksPerBeat) + "";
       String expectedDurationNote = (int)((start + (MIDDLE_C_NOTE.duration() + MIDDLE_C_NOTE.duration()) * durationFactor)
               * ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[4].contains("NOTE_ON"));
       assertTrue(events[4].contains(expectedPitchTupletNote));
       assertTrue(events[4].contains(expectedStartNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[5].contains("NOTE_OFF"));
       assertTrue(events[5].contains(expectedPitchTupletNote));
       assertTrue(events[5].contains(expectedDurationNote));   
       
       
       //Now test the second Note in the Tuplet
       String expectedPitchTupletSecondNote = MIDDLE_G_NOTE.getPitch().toMidiNote() + "";
       String expectedStartSecondNote =  (int)((start + MIDDLE_C_NOTE.duration() * durationFactor 
               + MIDDLE_C_NOTE.duration() * durationFactor ) * ticksPerBeat) + "";
       String expectedDurationSecondNote = (int)((start + MIDDLE_C_NOTE.duration() * durationFactor 
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor) * ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[6].contains("NOTE_ON"));
       assertTrue(events[6].contains(expectedPitchTupletSecondNote));
       assertTrue(events[6].contains(expectedStartSecondNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[7].contains("NOTE_OFF"));
       assertTrue(events[7].contains(expectedPitchTupletSecondNote));
       assertTrue(events[7].contains(expectedDurationSecondNote));  
       
       
       //Now test the third Note in the Tuplet
       String expectedPitchTupletThirdNote = C_FLAT_NOTE.getPitch().toMidiNote() + "";
       String expectedStartThirdNote =  (int)((start + MIDDLE_C_NOTE.duration() * durationFactor 
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor) * ticksPerBeat)+ "";
       String expectedDurationThirdNote = (int)((start + MIDDLE_C_NOTE.duration() * durationFactor 
               + MIDDLE_C_NOTE.duration() * durationFactor + MIDDLE_G_NOTE.duration() * durationFactor
               + C_FLAT_NOTE.duration() * durationFactor) * ticksPerBeat) + "";
                 
       //Test NOTE_ON event Note's MIDDLE_C_NOTE
       assertTrue(events[8].contains("NOTE_ON"));
       assertTrue(events[8].contains(expectedPitchTupletThirdNote));
       assertTrue(events[8].contains(expectedStartThirdNote));
       
       //Test NOTE_OFF event Note's MIDDLE_C_NOTE
       assertTrue(events[9].contains("NOTE_OFF"));
       assertTrue(events[9].contains(expectedPitchTupletThirdNote));
       assertTrue(events[9].contains(expectedDurationThirdNote));  
   }
}