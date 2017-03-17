package com.google.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class FilesTest {
    private static final Logger logger = LoggerFactory.getLogger(FilesTest.class);

    String path = "C:/Users/Chen/Pictures/s/v32.mp4";

    @Test
    public void testHash() throws IOException {
	long s = System.currentTimeMillis();
	Runtime runtime = Runtime.getRuntime();
	long ms = runtime.totalMemory() - runtime.freeMemory();

	List<String> hashList = Lists.newArrayList();
	for (int i = 0; i < 50; i++) {
	    // 1836,18453672,21183456,2729784
	    // 1811,18446280,21175424,2729144
	    // String hash = Files.hash(new File(path),
	    // Hashing.sha1()).toString();
	    // hashList.add(hash);

	    InputStream is = new FileInputStream(new File(path));
	    // // 2113,17755208,705138872,687383664
	    // // 2003,18446024,705829184,687383160
	    String hash = Hashing.sha1().hashBytes(ByteStreams.toByteArray(is)).toString();
	    is.close();
	    hashList.add(hash);
	}

	long me = runtime.totalMemory() - runtime.freeMemory();
	long e = System.currentTimeMillis();
	logger.info("{}", e - s);
	logger.info("{},{},{}", ms, me, me - ms);// 2729144,2728112
	logger.info("{}", hashList);
    }
}
