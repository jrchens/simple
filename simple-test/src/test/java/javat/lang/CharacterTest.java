package javat.lang;

import org.junit.Test;

public class CharacterTest {


    @Test
    public void testSpecialCharacter(){
	// CJK Symbols and Punctuation
	char start = 0x3000;
	char end   = 0x303f;
	for (char i = start; i <= end; i++) {
	    System.out.println(String.valueOf(i));
	    System.out.println(Integer.valueOf(i));
	}
    }
    
    @Test
    public void testCharacterToHex(){
	// CJK Symbols and Punctuation
	char[] chars = {',','，'};
	// 002c , ff0c
	for (char c : chars) {
		System.out.println(String.format("%s,%s,%s", String.valueOf(c),Integer.valueOf(c),Integer.toHexString(c)));
	}
	
	// System.out.println(String.format("%s , %s", "\u002c","\uff0c"));
    }
    
    
}
