package abc.ast;

import java.util.ArrayList;
import java.util.List;

import abc.sound.Pitch;
import abc.sound.SequencePlayer;

/**
 * 
 * An immutable data type representing a tuplet of ordered notes or chords in music sheet.
 *
 */
public class Tuplet implements Music {

    private final List<NoteOrChord> sequence;
    private final int length;
    
    // Abstraction function:
    //   Represents a tuplet containing length number of notes and/or chords. The notes and/or chords to play are
    //   represented by sequence.
    // Rep invariant:
    //   values in sequence != null
    //   sequence != null
    //   length >= 2
    //   sequence.size() == length
    // Safety from rep exposure:
    //   all fields are private, final, and immutable. notes is never
    //   sequence is mutable, but it is never modified or returned in the methods of this class.
    //   Return values are immutable
    
    /**
     * Make a new Tuplet of a certain length.
     * @param notes notes and chords in the Tuplet
     * @param length the length of the Tuplet
     */
    public Tuplet(List<NoteOrChord> notes, int length) {
        this.sequence = notes;
        this.length = length;
        checkRep();
    }
    
    //Asserts rep invariant
    private void checkRep(){
        assert this.sequence != null;
        assert this.length >= 2;
        assert this.sequence.size() == this.length;
        for (NoteOrChord m: this.sequence){
            assert m != null;
        }
    }
    
    @Override
    public double duration() {
        double durationFactor;
        double totalDuration = 0;
        
        switch(this.length){
            case 2: 
                durationFactor = 1.5;
                break;
            case 3: 
                durationFactor = 2.0/3.0;
                break;
            case 4: 
                durationFactor = 0.75;
                break;
            default: 
                throw new RuntimeException("Unreachable");
        }        
        
        for(NoteOrChord m: sequence){
            totalDuration += m.duration() * durationFactor;
        }
        
        return totalDuration;
    }
    
    private List<NoteOrChord> getSequence(){
        return this.sequence;
    }
    
    /**
     * Gets the number of consecutive groups of notes to be played in Tuplet instance
     * @return the number of consecutive groups of notes in Tuplet instance
     */
    public int getLength(){
        return this.length;
    }
    
    @Override
    public void play(SequencePlayer player, double start) {
        double durationFactor;
        switch(this.getLength()){
            case 2: 
                durationFactor = 1.5;
                break;
            case 3: 
                durationFactor = 2.0/3.0;
                break;
            case 4: 
                durationFactor = 0.75;
                break;
            default: 
                throw new RuntimeException("Unreachable");
        }     
        double nextStart = 0;
        for(NoteOrChord m: sequence){
            m.tupletPlay(player, start + nextStart, this.getLength());
            nextStart += (m.duration() * durationFactor); //When played from tuplet, the duration has to be scaled
        }
    }

    @Override
    public Music transpose(int semitonesUp) {
        List<NoteOrChord> transposed = new ArrayList<>();
        
        for(NoteOrChord m: sequence){
            transposed.add( (NoteOrChord) m.transpose(semitonesUp));
        }
        
        return new Tuplet(transposed, this.length);
    }

    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp) {
        List<NoteOrChord> transposedPitch = new ArrayList<>();
        
        for(NoteOrChord m: sequence){
            transposedPitch.add( (NoteOrChord) m.transposePitch(pitch, semitonesUp));
        }
        
        return new Tuplet(transposedPitch, this.length);
    }

    @Override
    public int hashCode() {
        int code = 0;
        for (NoteOrChord m: this.getSequence()){
            code += m.hashCode();
        }
        return code;
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Tuplet instances representing the same Tuplet in music,
     *          meaning they represent the same consecutive groups of notes to be played
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tuplet)) return false;
        Tuplet thatTuplet = (Tuplet) obj;
        List<NoteOrChord> thisSequence = this.getSequence();
        List<NoteOrChord> thatTupletSequence = thatTuplet.getSequence();
        if (this.getLength() != thatTuplet.getLength()){
            return false;
        }
        else {
            for (int i = 0; i < thisSequence.size(); i++){
                if (!thisSequence.get(i).equals(thatTupletSequence.get(i))){
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Outputs a String representation of a Tuplet instance: "(" symbol followed by 
     * the number consecutive groups of notes to be played in the Tuplet instance 
     * followed by those groups of notes in String form.
     * @return String representation of the Tuplet instance suitable for reading
     */
    @Override
    public String toString() {
        String output = "(" + this.getLength();
        for (NoteOrChord m: this.getSequence()){
            output += m.toString();
        }
        return output;
    }
}