package javat.lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterTest {
    private final static Logger logger = LoggerFactory.getLogger(CharacterTest.class);

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
	
	chars = "年月日时分秒".toCharArray();
	for (char c : chars) {
	    logger.error("{}",Integer.toHexString(c));
	}
    }
}
