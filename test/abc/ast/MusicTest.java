package abc.ast;


import org.junit.Test;
import java.io.File;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Tests for the abstract type Music.
 * @category no_didit
 * 
 */
public class MusicTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    
    //Test to make sure the parse can read the file
    @Test
    public void testParseHeaderBodyWithTwoGrammars() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/piece1.abc"), 10000);
       
        try{
            Thread.sleep(10000L);
        } catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    /**
     * Test for each piece of music
     * @throws InvalidMidiDataException 
     * @throws MidiUnavailableException 
     */
    
    @Test
    public void testParseABCSong() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/abc_song.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testFurElise() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/fur_elise.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParseInvention() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/invention.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParseLittleNightMusic() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/little_night_music.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParsePaddy() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/paddy.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParsePiece1() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/piece1.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParsePiece2() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/piece2.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParsePrelude() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/prelude.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParseSample1() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/sample1.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParseSample2() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/sample2.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParseSample3() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/sample3.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParseScale() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/scale.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
    
    @Test
    public void testParseWaxiesDargle() throws MidiUnavailableException, InvalidMidiDataException{
        Music.playMusic(new File("sample_abc/waxies_dargle.abc"), 10000);
        
        try{
           Thread.sleep(15000); 
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
    }
}
