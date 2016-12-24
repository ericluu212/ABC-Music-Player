package abc.sound;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import org.junit.Test;

/**
 * Testing music playing. Can't be done on didit
 * @category no_didit
 */
public class SequencePlayerTest {
    
    /**
     *  Tests for warmup #2
     *  Purpose: To play the pieces 1 and 2. See SequencePlayer.main() for a reference.  
     */
    
    @Test
    public void testPlayPiece1(){
        try{
            final int beatsPerMin = 140;
            final int ticksPerBeat = 24;
            SequencePlayer player = new SequencePlayer(beatsPerMin, ticksPerBeat);
            
            player.addNote(new Pitch('C').toMidiNote(), 0, 24);
            player.addNote(new Pitch('C').toMidiNote(), 24, 24);
            player.addNote(new Pitch('C').toMidiNote(), 48, 18);
            player.addNote(new Pitch('D').toMidiNote(), 66, 6);
            player.addNote(new Pitch('E').toMidiNote(), 72, 24);
            
            player.addNote(new Pitch('E').toMidiNote(), 96, 18);
            player.addNote(new Pitch('D').toMidiNote(), 114, 6);
            player.addNote(new Pitch('E').toMidiNote(), 120, 18);
            player.addNote(new Pitch('F').toMidiNote(), 138, 6);
            player.addNote(new Pitch('G').toMidiNote(), 144, 48);
            
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 192, 8);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 200, 8);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 208, 8);
            player.addNote(new Pitch('G').toMidiNote(), 216, 8);
            player.addNote(new Pitch('G').toMidiNote(), 224, 8);
            player.addNote(new Pitch('G').toMidiNote(), 232, 8);
            player.addNote(new Pitch('E').toMidiNote(), 240, 8);
            player.addNote(new Pitch('E').toMidiNote(), 248, 8);
            player.addNote(new Pitch('E').toMidiNote(), 256, 8);
            player.addNote(new Pitch('C').toMidiNote(), 264, 8);
            player.addNote(new Pitch('C').toMidiNote(), 272, 8);
            player.addNote(new Pitch('C').toMidiNote(), 280, 8);
            
            player.addNote(new Pitch('G').toMidiNote(), 288, 18);
            player.addNote(new Pitch('F').toMidiNote(), 306, 6);
            player.addNote(new Pitch('E').toMidiNote(), 312, 18);
            player.addNote(new Pitch('D').toMidiNote(), 330, 6);
            player.addNote(new Pitch('C').toMidiNote(), 336, 48);
            
            System.out.println(player);
            
            player.play();

            try{
                Thread.sleep(10000L);
            } catch(InterruptedException ie){
                ie.printStackTrace();
            }
            
        } catch(MidiUnavailableException mue){
            mue.printStackTrace();
        } catch(InvalidMidiDataException imde){
            imde.printStackTrace();
        }
        
    }   
    
    
    @Test
    public void testPlayPiece2(){
        try{
            final int beatsPerMin = 200;
            final int ticksPerBeat = 24;
            SequencePlayer player = new SequencePlayer(beatsPerMin, ticksPerBeat);
            
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 0, 12);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 0, 12);
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 12, 12);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 12, 12);            
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 36, 12);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 36, 12);            
            player.addNote(new Pitch('F').transpose(1).toMidiNote(), 60, 12);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 60, 12);
            player.addNote(new Pitch('F').toMidiNote(), 72, 24);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 72, 24);
            
            player.addNote(new Pitch('G').toMidiNote(), 96, 24);
            player.addNote(new Pitch('B').toMidiNote(), 96, 24);
            player.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 96, 24);
            player.addNote(new Pitch('G').toMidiNote(), 144, 24);
            
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 192, 36);
            player.addNote(new Pitch('G').toMidiNote(), 228, 12);
            player.addNote(new Pitch('E').toMidiNote(), 264, 24);
            
            player.addNote(new Pitch('E').toMidiNote(), 288, 12);
            player.addNote(new Pitch('A').toMidiNote(), 300, 24);
            player.addNote(new Pitch('B').toMidiNote(), 324, 24);
            player.addNote(new Pitch('B').transpose(-1).toMidiNote(), 348, 12);
            player.addNote(new Pitch('A').toMidiNote(), 360, 24);
            
            player.addNote(new Pitch('G').toMidiNote(), 384, 16);
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 400, 16);
            player.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 416, 16);
            player.addNote(new Pitch('A').transpose(Pitch.OCTAVE).toMidiNote(), 432, 24);
            player.addNote(new Pitch('F').transpose(Pitch.OCTAVE).toMidiNote(), 456, 12);
            player.addNote(new Pitch('G').transpose(Pitch.OCTAVE).toMidiNote(), 468, 12);
            
            player.addNote(new Pitch('E').transpose(Pitch.OCTAVE).toMidiNote(), 492, 24);
            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 516, 12);
            player.addNote(new Pitch('D').transpose(Pitch.OCTAVE).toMidiNote(), 528, 12);
            player.addNote(new Pitch('B').toMidiNote(), 540, 18);
            
            System.out.println(player);
            
            player.play();

            try{
                Thread.sleep(10000L);
            } catch(InterruptedException ie){
                ie.printStackTrace();
            }
            
        } catch(MidiUnavailableException mue){
            mue.printStackTrace();
        } catch(InvalidMidiDataException imde){
            imde.printStackTrace();
        }
    }
    
    

}
