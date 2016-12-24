// A subset of abc 2.1 in BNF format

abc_tune ::= abc_header abc_music+;

///////////////////////////////////////////////////////
// Header

// ignore WHITESPACE between terminals in the header

abc_header ::= field_number comment* field_title other_fields* field_key;

@skip WHITESPACE{
	field_number ::= "X:" number end_of_line;
	field_title ::= "T:" text end_of_line;
	other_fields ::= field_composer | field_default_length | field_meter | field_tempo | field_voice | comment;
	field_composer ::= "C:" text end_of_line;
	field_default_length ::= "L:" note_length_strict end_of_line;
	field_meter ::= "M:" meter end_of_line;
	field_tempo ::= "Q:" tempo end_of_line;
	field_voice ::= "V:" text end_of_line;
	field_key ::= "K:" key end_of_line;
}

key ::= keynote mode_minor?;																																																													
keynote ::= basenote key_accidental?;
key_accidental ::= "#" | "b";
mode_minor ::= "m";

meter ::= "C" | "C|" | meter_fraction;
meter_fraction ::= numerator "/" denominator;

note_length_strict ::= numerator "/" denominator;

numerator ::= number;
denominator ::= number;

tempo ::= meter_fraction "=" number;

basenote ::= "C" | "D" | "E" | "F" | "G" | "A" | "B"
        | "c" | "d" | "e" | "f" | "g" | "a" | "b";
        
abc_music ::= field_voice? music_lines;
music_lines ::= (line_of_music | comment)+;
line_of_music ::= (measure | repeat)* NEWLINE?; 
measure ::= element* barline? WHITESPACE*;
repeat ::= element* repeat_end WHITESPACE*;
element ::= nth_repeat | note_element | tuplet_element | NEWLINE | WHITESPACE; 

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

rest ::= "z";

// tuplets
tuplet_element ::= tuplet_spec note_element+;
tuplet_spec ::= "(" DIGIT;

// chords
chord ::= "[" note+ "]";

barline ::= "|" | repeat_start;
repeat_start ::= "||" | "[|" | "|]" | "|:";
repeat_end ::= element* ":|";
nth_repeat ::= "[1" | "[2";
nth_end ::= measure;

/////////////////////////////////////////////////////
// General

comment ::= "%" text? NEWLINE;
text ::= .+;
end_of_line ::= comment | NEWLINE;
number ::= DIGIT+;

DIGIT ::= [0-9];
NEWLINE ::= "\n" | "\r" "\n"?;
WHITESPACE ::= " " | "\t";