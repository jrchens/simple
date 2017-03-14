package javat.lang;

import org.junit.Test;

public class StringTest {


    @Test
    public void testSpecialCharacter(){
	char space = ' '; // 32
	char entireSpace = '　'; // 12288
	char tab = '	'; // 9
	char[] chars = {space,entireSpace,tab};
	for (char c : chars) {
	    System.out.println(Integer.valueOf(c));
	}
	char entireSpaceChar = 12288;
	String entireSpaceString = String.valueOf(entireSpaceChar);
	System.out.println(String.format("|%s|", entireSpaceString));
    }
    
    
}
