package com.google.common.io;

import java.io.File;
import java.io.FileInputStream;
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
    public void testHash() throws Exception {
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
    
    

    @Test
    public void testFileHash() throws Exception {
	String baseDir = "/usr/local/tmpdir";
	String[] filenames = {"00043.pdf","00147.pdf","00166.pdf"};
	for (String filename : filenames) {
	    File file = new File(baseDir,filename);
	    logger.info("file : {}, sha1 hash: {}",filename,Files.hash(file, Hashing.sha1()).toString());
	}
//	file : 00043.pdf, sha1 hash: 8fe00e92bb10cf934755faa662aa5c5e9443be85
//	file : 00147.pdf, sha1 hash: f39c9433508e491dbfde03c67e3df6ed70cb3a54
//	file : 00166.pdf, sha1 hash: 66fe4d9398c982426ef3d11a383b20ed145ba70e
    }
}
