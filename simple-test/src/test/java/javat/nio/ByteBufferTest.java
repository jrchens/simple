package javat.nio;

import java.nio.ByteBuffer;

import org.junit.Test;

import com.google.common.base.Charsets;

public class ByteBufferTest {
    @Test
    public void testByteToString() throws Exception {
	int capacity = 1;
	byte b = 97;
	
	ByteBuffer bb = ByteBuffer.allocate(capacity);
	bb.put(b);
	bb.flip();
	System.out.println(String.valueOf(Charsets.UTF_8.decode(bb)));
	
	Byte.toString(b);
	
	System.out.println("a".getBytes()[0]);
    }
}
