package com.google.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
//import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteStreamsTest {
    private static final Logger logger = LoggerFactory.getLogger(ByteStreamsTest.class);

    @Test
    public void testoByteArray() {
	FileInputStream in = null;
	try {
	    in = new FileInputStream(new File("C:/Users/Chen/Pictures/s/094451_mWok_1000238.jpg"));
	    ByteStreams.toByteArray(in);
	    logger.info("input stream is closed: {}", in.available() > 0);
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("FileNotFoundException", e);
	} catch (IOException e) {
	    throw new RuntimeException("IOException", e);
	} finally {
	    try {
		if (null != in) {
		    in.close();
		}
	    } catch (IOException e) {
	    }
	}
    }

}
