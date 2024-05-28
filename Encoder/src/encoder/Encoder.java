package encoder;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import java.lang.StringBuilder;

public class Encoder {
	private static BidiMap<Character, Integer> referenceTable = new TreeBidiMap<Character, Integer>(); //Use a 2 way map to easily convert indices back and forth
	static {/*generate the offset table, explicitly map the reference table
			probably less efficient than using byte conversion but avoids any headaches from character encoding, etc */	
		referenceTable.put('A', 0);
		referenceTable.put('B', 1);
		referenceTable.put('C', 2);
		referenceTable.put('D', 3);
		referenceTable.put('E', 4);
		referenceTable.put('F', 5);
		referenceTable.put('G', 6);
		referenceTable.put('H', 7);
		referenceTable.put('I', 8);
		referenceTable.put('J', 9);
		referenceTable.put('K', 10);
		referenceTable.put('L', 11);
		referenceTable.put('M', 12);
		referenceTable.put('N', 13);
		referenceTable.put('O', 14);
		referenceTable.put('P', 15);
		referenceTable.put('Q', 16);
		referenceTable.put('R', 17);
		referenceTable.put('S', 18);
		referenceTable.put('T', 19);
		referenceTable.put('U', 20);
		referenceTable.put('V', 21);
		referenceTable.put('W', 22);
		referenceTable.put('X', 23);
		referenceTable.put('Y', 24);
		referenceTable.put('Z', 25);
		referenceTable.put('0', 26);
		referenceTable.put('1', 27);
		referenceTable.put('2', 28);
		referenceTable.put('3', 29);
		referenceTable.put('4', 30);
		referenceTable.put('5', 31);
		referenceTable.put('6', 32);
		referenceTable.put('7', 33);
		referenceTable.put('8', 34);
		referenceTable.put('9', 35);
		referenceTable.put('(', 36);
		referenceTable.put(')', 37);
		referenceTable.put('*', 38);
		referenceTable.put('+', 39);
		referenceTable.put('-', 41);
		referenceTable.put('.', 42);
		referenceTable.put('/', 43);
	}
	
	public String encode(String plainText) {//use a default offset of B to demonstrate
		char offset = 'B';
		return encode(plainText, offset);
	}
	
	public String encode(String plainText, Character offset) {//overloaded method which can choose own offset
		StringBuilder encodedSb = new StringBuilder();		
		
		//first char is the offset
		//check for valid character belonging to the reference table or whitespace, eg. no lowercase letters, etc.
		if (offset.toString().matches("[a-zA-Z0-9/(()*+,-./)\s]+")) {
			encodedSb.append(Character.toUpperCase(offset));
		}
		else {
			return "Invalid Offset";
		}
		
		for (int i = 0; i < plainText.length();i++) {
			if (plainText.substring(i, i+1).equals(" ")) {//spaces should be added as is
				encodedSb.append(" ");
			}
			else if (!plainText.substring(i, i+1).matches("[a-zA-Z0-9/(()*+,-./)]+")) {//if at any time encounter illegal characters
				return "Invalid String";
			}
			else {
				encodedSb.append(getOffsetCharEncode(Character.toUpperCase(offset), Character.toUpperCase(plainText.charAt(i))));
			}
		}
		return encodedSb.toString();
	}
	
	public String decode(String encodedText) {
		StringBuilder encodedSb = new StringBuilder();
		//for an encoded text, the first character indicates what the offset is, must be upper case
		Character offset = Character.toUpperCase(encodedText.charAt(0)); 
		
		//first char is the offset
		//check for valid character belonging to the reference table, eg. no lowercase letters, etc.
		if (!offset.toString().matches("[A-Z0-9/(()*+,-./)]+")) {
			return "Invalid Offset";
		}
		
		for (int i = 1; i < encodedText.length();i++) {//ignore first character because it is just encoding
			if (encodedText.substring(i, i+1).equals(" ")) {//spaces should be added as is
				encodedSb.append(" ");
			}
			else if (!encodedText.substring(i, i+1).matches("[a-zA-Z0-9/(()*+,-./)]+")) {//if at any time encounter illegal characters
				return "Invalid String";
			}
			else {
				encodedSb.append(getOffsetCharDecode(offset, Character.toUpperCase(encodedText.charAt(i))));
			}
		}
		
		return encodedSb.toString();
	}
	
	private Character getOffsetCharEncode(char offset, char currentChar) {
		//find offset value, use offset to get char
		int offsetValue = Encoder.referenceTable.get(offset);
		int index = Encoder.referenceTable.get(currentChar);
		int newIndex = index-offsetValue;
		if (newIndex < 0) {
			newIndex += 44; //wrap around to 43
		}
		return Encoder.referenceTable.inverseBidiMap().get(newIndex);
	}
	
	
	private Character getOffsetCharDecode(char offset, char currentChar) {
		//find offset value, use offset to get char
		int offsetValue = Encoder.referenceTable.get(offset);
		int index = Encoder.referenceTable.get(currentChar);
		int newIndex = index+offsetValue;
		if (newIndex > 43) {
			newIndex -= 44; //wrap around to 0
		}
				
		return  Encoder.referenceTable.inverseBidiMap().get(newIndex);
	}
	

	public static void main(String[] args) {//for testing and demonstration
		// Show HELLO WORLD, also further test with other offset
		Encoder encoder = new Encoder();
		
		System.out.println(encoder.encode("HELLO WORLD")); //test default with 'B'
		System.out.println(encoder.decode(encoder.encode("HELLO WORLD")));
		
		System.out.println(encoder.encode("HELLO WORLD", 'F')); //test with 'F'
		System.out.println(encoder.decode(encoder.encode("HELLO WORLD", 'F')));
		
		System.out.println(encoder.encode("SOME OTHER STRING", '9')); //test with '9'
		System.out.println(encoder.decode(encoder.encode("SOME OTHER STRING", '9')));
		
		System.out.println(encoder.encode("lower will become upper", '/')); //test lower case string, should convert to uppercase
		System.out.println(encoder.decode(encoder.encode("lower will become upper", '/')));
		
		System.out.println(encoder.encode("hello world", 'b')); //test with 'b', if offset was lower case
		System.out.println(encoder.decode("bgdkkn vnqkc")); //test if an encoded message had lower case
		
		System.out.println(encoder.encode("^^^", 'B')); //test illegal string
		System.out.println(encoder.encode("HELLO WORLD", '^')); //test illegal offset
	}
}
