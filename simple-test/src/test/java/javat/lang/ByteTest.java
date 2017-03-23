package javat.lang;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;

public class ByteTest {

    @Test
    public void testStringToByteArray() throws Exception {
	String s = "f75e10a2d1cc40b38ae4f82ba72d8ed1";
	byte[] b = s.getBytes(Charsets.UTF_8);
	for (byte c : b) {
	    System.out.println(Integer.toHexString(c));
	}

	byte[] b2 = { 0x66, 0x37, 0x35, 0x65, 0x31, 0x30, 0x61, 0x32, 0x64, 0x31, 0x63, 0x63, 0x34, 0x30, 0x62, 0x33,
		0x38, 0x61, 0x65, 0x34, 0x66, 0x38, 0x32, 0x62, 0x61, 0x37, 0x32, 0x64, 0x38, 0x65, 0x64, 0x31 };

	String s2 = new String(b2, Charsets.UTF_8);
	Assert.assertEquals(s, String.valueOf(s2));

	// Charset cs = Charsets.UTF_8;
	// ByteBuffer bb = ByteBuffer.allocate (b2.length);
	// bb.put(b2);
	// bb.flip();

	// Assert.assertEquals(s, String.valueOf(cs.decode(bb)));

	// StringBuffer buffer = new StringBuffer();
	// for (byte c : b2) {
	// buffer.append((char)(c)); // Byte.toString(c)
	// }

	// Assert.assertEquals(s, buffer.toString());
    }
}
