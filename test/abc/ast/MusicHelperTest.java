package abc.ast;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.File;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import abc.ast.Music.AbcBodyGrammar;
import abc.ast.Music.AbcHeaderGrammar;
import abc.sound.Pitch;
import abc.sound.SequencePlayer;
import lib6005.parser.ParseTree;

/**
 * Tests for the tests in the MusicHelper.java class 
 *
 */
public class MusicHelperTest {
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Testing strategy for fractionToDouble(String fraction):
     *      fraction is in the following possible formats: "(Number)/(Number)", "Number/", "/Number", "/", "", Number
     */    
    
    //covers fraction is (Number)/(Number)
    @Test
    public void fractionToDoubleNumDenom(){
        double converted = MusicHelper.fractionToDouble("1/4");
        double expected = 0.25;
        assertEquals("Convered value should be 0.25", expected, converted, 0.0);
    }
    //covers fraction is Number/
    @Test
    public void fractionToDoubleNumOnly(){
        double converted = MusicHelper.fractionToDouble("2/");
        double expected = 1.0;
        assertEquals("Convered value should be 1.0", expected, converted, 0.0);
    }
    //covers fraction is /Number
    @Test
    public void fractionToDoubleDenomOnly(){
        double converted = MusicHelper.fractionToDouble("/8");
        double expected = 0.125;
        assertEquals("Convered value should be 0.125", expected, converted, 0.0);
    }
    //covers fraction is "/"
    @Test
    public void fractionToDoubleNoNumNoDenom(){
        double converted = MusicHelper.fractionToDouble("/");
        double expected = 0.5;
        assertEquals("Convered value should be 0.5", expected, converted, 0.0);
    }
    //covers fraction is ""
    @Test
    public void fractionToDoubleEmptyString(){
        double converted = MusicHelper.fractionToDouble("");
        double expected = 1.0;
        assertEquals("Convered value should be 1.0", expected, converted, 0.0);
    }
    //covers fraction is Number
    @Test
    public void fractionToDoubleSingleNumber(){
        double converted = MusicHelper.fractionToDouble("5");
        double expected = 5;
        assertEquals("Convered value should be 5", expected, converted, 0.0);
    }
    
    /*
     * Testing strategy for constructNote(String accidental, String basenote, String octave, double duration):
     *      accidental: is "^", "^^", "_", "__", "=", none of the provided
     *      basenote: lower case letter (a to g), not lower case letter
     *      octave: has "'", has ",", has neither
     *      duration: is 0, > 0
     */   
    //take the max --> 5 cases
    
    //covers accidental is "=", lower case letter, has neither "'" nor ",", duration is 0
    @Test
    public void noteNaturalLowerCaseNoOctaveZeroDuration(){
        Note result = MusicHelper.constructNote("=", "g", "", 0);
        Pitch toUse = new Pitch('G').transpose(Pitch.OCTAVE);
        int accidentalTranspose = 0;
        int octaveTranspose = 0;
        Note expected = new Note(0, toUse.transpose(accidentalTranspose).transpose(octaveTranspose));
        assertEquals("Note constructed not as expected", expected, result);
    }
    //covers accidental is not provided, not lower case letter, has "'", duration is >0
    @Test
    public void noteNoAccidentalNotLowerCaseOctaveAboveNonzeroDuration(){
        Note result = MusicHelper.constructNote("", "G", "'", 1.0);
        Pitch toUse = new Pitch('G');
        int accidentalTranspose = 0;
        int octaveTranspose = Pitch.OCTAVE;
        Note expected = new Note(1.0, toUse.transpose(accidentalTranspose).transpose(octaveTranspose));
        assertEquals("Note constructed not as expected", expected, result);
    }
    //covers accidental is "^", not lower case letter, has ",", duration is >0
    @Test
    public void noteSharpNotLowerCaseOctaveBelowNonzeroDuration(){
        Note result = MusicHelper.constructNote("^", "A", ",", 12.0);
        Pitch toUse = new Pitch('A');
        int accidentalTranspose = 1;
        int octaveTranspose = -Pitch.OCTAVE;
        Note expected = new Note(12.0, toUse.transpose(accidentalTranspose).transpose(octaveTranspose));
        assertEquals("Note constructed not as expected", expected, result);
    }
    //covers accidental is "^^", not lower case letter, has "'", duration is >0
    @Test
    public void noteDoubleSharpNotLowerCaseTwoOctavesAboveNonzeroDuration(){
        Note result = MusicHelper.constructNote("^^", "A", "''", 12.0);
        Pitch toUse = new Pitch('A');
        int accidentalTranspose = 2;
        int octaveTranspose = Pitch.OCTAVE * 2;
        Note expected = new Note(12.0, toUse.transpose(accidentalTranspose).transpose(octaveTranspose));
        assertEquals("Note constructed not as expected", expected, result);
    }
    //covers accidental is "_", not lower case letter, has ",", duration is >0
    @Test
    public void noteFlatNotLowerCaseTwoOctavesBelowNonzeroDuration(){
        Note result = MusicHelper.constructNote("_", "C", ",,", 3.0);
        Pitch toUse = new Pitch('C');
        int accidentalTranspose = -1;
        int octaveTranspose = -Pitch.OCTAVE * 2;
        Note expected = new Note(3.0, toUse.transpose(accidentalTranspose).transpose(octaveTranspose));
        assertEquals("Note constructed not as expected", expected, result);
    }
    //covers accidental is "__", lower case letter, has neither "'" nor ",", duration is 0
    @Test
    public void noteDoubleFlatNotLowerCaseTwoOctavesBelowNonzeroDuration(){
        Note result = MusicHelper.constructNote("__", "e", "", 0.0);
        Pitch toUse = new Pitch('E').transpose(Pitch.OCTAVE);
        int accidentalTranspose = -2;
        int octaveTranspose = 0;
        Note expected = new Note(0.0, toUse.transpose(accidentalTranspose).transpose(octaveTranspose));
        assertEquals("Note constructed not as expected", expected, result);
    }
}
