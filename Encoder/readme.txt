Simple encoder done quickly, using a 2 way map to reference needed offset values
Uses BidiMap from Apache Commons Collections, imported using Maven

Following the Reference Table, encoder will add the offset character at the front, then translate to the Shifted Table
As the Reference Table does not contain lower case letters, all lower case letters are automatically converted to upper case

-----------------------------------------------------------------------------------------------------------------------------------------

Following test cases are included in main, with explanation:
1) "HELLO WORLD" with offset 'B', encode and decode -> matches result in document

2) "HELLO WORLD" with offset 'F', encode and decode -> matches result in document

3) "SOME OTHER STRING" with offset '9', encode and decode -> show that code can work on non-prescribed plainText and offset

4) "lower will become upper" with offset '/', encode and decode -> demonstrate the upper case conversion, especially when decoded

5) "hellow world" with offset 'b', encode -> demonstrate encoding with lower case plainText and offset, output will always be upper case though

6) "bgdkkn vnqkc", decode -> demonstrate decoding into HELLO WORLD even if encodedText is lowercase, output will always be upper case though

7) "^^^" with offset 'B', encode -> demonstrate invalid string with illegal characters not in Reference Table

8) "HELLO WORLD" with offset '^', encode -> demonstrate invalid offset with illegal character not in Reference Table


-----------------------------------------------------------------------------------------------------------------------------------------
Methods

---------------------
As per constraints:
public String encode(String plainText) 
-> is a wrapper for "encode(String plainText, Character offset)" with default encoding of offset 'B'

public String decode(String encodedText) 
-> decodes given encodedText using first character as offset; does input verification and uppercase conversion

---------------------

Other Methods:

public String encode(String plainText, Character offset) 
-> encodes given plainText using given offset; does input verification and uppercase conversion

private static BidiMap<Character, Integer> referenceTable = new TreeBidiMap<Character, Integer>() 
-> 2 way map used to easily find offsets, populated by static block, can be updated to fit any kind of offset shifting encryption

private Character getOffsetCharEncode(char offset, char currentChar) 
-> logic implementation for encoding

private Character getOffsetCharDecode(char offset, char currentChar) 
-> logic implemenation for decoding

main 
-> declares an instance of Encoder and runs test cases, outputting to console