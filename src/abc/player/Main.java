package abc.player;

import java.io.File;
import java.util.Scanner;

import abc.ast.*;

/**
 * Main entry point of your application.
 */
public class Main {

    /**
     * Plays the input file using Java MIDI API and displays
     * header information to the standard output stream.
     * 
     * (Your code should not exit the application abnormally using
     * System.exit().)
     * 
     * @param file the name of input abc file
     */
    public static void play(String fileName) {
        final int ticksPerBeat = 10000;
        File file = new File(fileName);
        Music.playMusic(file, ticksPerBeat);
        
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Enter the name of the file you'd like played. Make sure it is in the sample_abc folder.");
            String fileName = ("sample_abc/" + in.nextLine() + ".abc");
            if(fileName.equals("sample_abc/q.abc")) {
                break;
            }
            else {
                play(fileName);
            }
        }
        in.close();
    }
}
