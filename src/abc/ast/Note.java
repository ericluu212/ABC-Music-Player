package abc.ast;

import abc.sound.*;

/**
 * An immutable data type representing a note in music sheet. 
*/
public class Note implements Music, NoteOrChord {
    
    private final double duration;
    private final Pitch pitch;
    
    // Abstraction function
    //    Represents one note of duration duration in music sheet
    // Rep invariant
    //    duration >= 0
    //    pitch != null  
    // Safety from rep exposure
    //    All fields are private, final, and immutable. All return values are immutable
    
    /**
     * Constructs a note
     * @param duration double in beats. Represents how long the note plays for 
     * @param p Pitch to play
     */
    public Note(double duration, Pitch p){
        this.duration = duration;
        this.pitch = p;
        checkRep();
    }
    
    /**
     * Asserts rep invariant
     */
    private void checkRep(){
        assert this.duration >= 0;
        assert this.pitch != null;
    }
    
    /**
     * Gets the pitch associated with this note
     * @return a Pitch representing the pitch of the note
     */
    public Pitch getPitch(){
        return this.pitch;
    }
    
    /**
     * @return duration of this note
     */
    @Override
    public double duration() {
        return this.duration;
    }
    
    /**
     * Play this note
     */
    @Override
    public void play(SequencePlayer player, double start) {
        int ticksPerBeat = player.getTicksPerBeat();
        int note = this.pitch.toMidiNote();
        player.addNote(note, (int)(start * ticksPerBeat), (int)(this.duration * ticksPerBeat));
        
    }

    @Override
    public Music transpose(int semitonesUp) {
        return new Note(this.duration(), this.getPitch().transpose(semitonesUp));
    }
    
    @Override
    public int hashCode(){
        Double d = this.duration();
        return d.hashCode() + this.getPitch().hashCode();
    }
    
    /**
     * Indicates whether two objects are equal as defined below
     * @param obj any object
     * @return true if and only if this and obj are Note instances with equal pitches and durations
     */
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Note)) return false;
        Note thatNote = (Note) obj;
        return (this.duration() == thatNote.duration()) && (this.getPitch().equals(thatNote.getPitch()));
    }
    
    /**
     * Outputs a String representation of a Note instance: the Note's pitch followed by the duration of the note as a number 
     * @return String representation of the Note instance suitable for reading
     */
    @Override
    public String toString(){
        return pitch.toString() + duration;
    }

    @Override
    public Music transposePitch(Pitch pitch, int semitonesUp) {
        if ( this.getPitch().equals(pitch) ) {
            return new Note(this.duration(), this.getPitch().transpose(semitonesUp));
        }
        return new Note(this.duration(), this.getPitch());
    }

    @Override
    public void tupletPlay(SequencePlayer player, double start, int length) {
        double durationFactor;
        
        switch(length){
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
        Note newNote = new Note(this.duration * durationFactor, this.getPitch());
        newNote.play(player, start);
    }
}