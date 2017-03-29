package org.apache.commons.net.ftp;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPClientTest {
    private final static Logger logger = LoggerFactory.getLogger(FTPClientTest.class);

    @Test
    public void testListFiles() throws Exception {
	// http://commons.apache.org/proper/commons-net/examples/ftp/FTPClientExample.java
	FTPClient ftp = new FTPClient();
	// FTPClientConfig config = new FTPClientConfig();
	ftp.connect("192.168.201.126", 21);
	int reply = ftp.getReplyCode();
	
	if (!FTPReply.isPositiveCompletion(reply)) {
	    ftp.disconnect();
	}
	boolean b = ftp.login("jrchens", "Xxcdy@110609");
	logger.info("login {}",b ? "success" : "error");
	
	
	FTPFile[] fs = ftp.listFiles("w"); 
	Arrays.sort(fs, new Comparator<FTPFile>(){
	    @Override
	    public int compare(FTPFile o1, FTPFile o2) {
		return Long.valueOf(o2.getTimestamp().getTimeInMillis()).compareTo(o1.getTimestamp().getTimeInMillis());
	    }
	});
	
	logger.info("filename: {}",fs[0].getName());
	
//	for (FTPFile ftpFile : fs) {
//	    System.out.println(String.format("%s, %d", ftpFile.getName(),ftpFile.getTimestamp().getTimeInMillis()));
//	}
	
	fs = ftp.listFiles(); 
	Arrays.sort(fs, new Comparator<FTPFile>(){
	    @Override
	    public int compare(FTPFile o1, FTPFile o2) {
		return Long.valueOf(o2.getTimestamp().getTimeInMillis()).compareTo(o1.getTimestamp().getTimeInMillis());
	    }
	});
	logger.info("filename: {}",fs[0].getName());
	
	ftp.disconnect();
    }

}
