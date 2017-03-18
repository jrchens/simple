package javat.text;

import org.junit.Test;

public class RegexTest {
    @Test
    public void testIndexOf(){
	String str = "许  文,潘  群";
	System.out.println(str.indexOf("\u3000"));
	System.out.println(str.indexOf("\u0020"));
	System.out.println(Integer.toHexString(' '));
    }
}
