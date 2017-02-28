package com.google.common.hash;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Test;
//import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;

public class HashingTest {
    private static final Logger logger = LoggerFactory.getLogger(HashingTest.class);

    @Test
    public void testHmacSha512() {
	String password = "123456";
	String username = "admin";
	SecretKey key = new SecretKeySpec(username.getBytes(Charsets.UTF_8), "HmacSHA512");
	String pwd = Hashing.hmacSha512(key).hashBytes(password.getBytes(Charsets.UTF_8)).toString();

	String sec = "0f6b6faebd3bed09de0ef528d53623013d8c4e7a25370379162b9f9f6bbb4bcd1bd017b3cb3ade8a06aa0afa5cbcb0880aec1368236b252a846c15f9eca36da1";

	Assert.assertEquals(pwd, sec);
    }
    
    

    @Test
    public void testCRC32() {
	int input = 100;
	String crc32 = Hashing.crc32().hashInt(input).toString();
	logger.info(crc32);
	Assert.assertTrue(crc32.length() == 8);
    }
    
    @Test
    public void testCRC32C() {
	int input = 100;
	String crc32c = Hashing.crc32c().hashInt(input).toString();
	logger.info(crc32c);
	Assert.assertTrue(crc32c.length() == 8);
    }
}
