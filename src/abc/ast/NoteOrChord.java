package abc.ast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import abc.sound.Pitch;
import abc.sound.SequencePlayer;
import lib6005.parser.GrammarCompiler;
import lib6005.parser.ParseTree;
import lib6005.parser.Parser;
import lib6005.parser.UnableToParseException;

/**
 * An interface to represent a single note or chord in music sheet. Interfaces are immutable by default.
 */
public interface NoteOrChord extends Music{
    
    /**
     * Play this note or chord in the context of tuplets, whose durations are defined in the ABC Player specifications page
     * @param player Sequence player that plays this music
     * @param start when to play, requires start >= 0
     * @param length the length of the tuplet in which the note or chord will be played
     */
    public void tupletPlay(SequencePlayer player, double start, int length);
}