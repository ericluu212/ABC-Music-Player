/////////////////////////////////////////////////////////////
// Music

// WHITESPACE is explicit in the body, don't automatically ignore it
abc_tune ::= abc_header abc_music;


@skip WHITESPACE{
	abc_header ::= (parameter ":" parameter_info NEWLINE)+;
}
parameter ::= "X" | "T" | "C" | "M" | "L" | "Q" | "V" | "K";
parameter_info ::= text | DIGIT | meter_fraction ("=" DIGIT+)?;

meter_fraction ::= DIGIT+ "/" DIGIT+;

abc_music ::= abc_line+;
abc_line ::= line_of_music | comment;
line_of_music ::= (measure | repeat)* NEWLINE?; 
measure ::= element* barline? WHITESPACE*;
repeat ::= repeat_start? (measure | comment | NEWLINE)* nth_repeat? repeat_end (comment | NEWLINE)* (nth_repeat nth_end)?;

element ::= note_element | tuplet_element | NEWLINE | WHITESPACE; 

note_element ::= note | chord;

// note is either a pitch or a rest
note ::= note_or_rest note_length?;
note_or_rest ::= pitch | rest;
pitch ::= accidental? basenote octave?;
octave ::= "'"+ | ","+;
note_length ::= (numerator)? ("/" (denominator)?)?;
numerator ::= (DIGIT+);
denominator ::= (DIGIT+);
// "^" is sharp, "_" is flat, and "=" is neutral
accidental ::= "^" | "^^" | "_" | "__" | "=";

basenote ::= "C" | "D" | "E" | "F" | "G" | "A" | "B"
        | "c" | "d" | "e" | "f" | "g" | "a" | "b";

rest ::= "z";

// tuplets
tuplet_element ::= tuplet_spec note_element+;
tuplet_spec ::= "(" DIGIT;

// chords
chord ::= "[" note+ "]";

barline ::= "|" | repeat_start;
repeat_start ::= "||" | "[|" | "|]" | "|:";
repeat_end ::= element* ":|" WHITESPACE*;
nth_repeat ::= "[1" | "[2";
nth_end ::= measure;

// A voice field might reappear in the middle of a piece
// to indicate the change of a voice
field_voice ::= "V:" text end_of_line;

/////////////////////////////////////////////////////
// General

comment ::= "%" text? NEWLINE;
end_of_line ::= comment | NEWLINE;

text ::= .+;
DIGIT ::= [0-9];
NEWLINE ::= "\n" | "\r" "\n"?;
WHITESPACE ::= " " | "\t";