package javat.io;

import java.io.File;
import java.io.FileFilter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTest {
    private static final Logger logger = LoggerFactory.getLogger(FileTest.class);
    private static final Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testFileList() {
	try {
	    
	    final Calendar year = Calendar.getInstance();
	    year.set(year.get(Calendar.YEAR), 0, 1, 0, 0, 0);
//	    year.set(Calendar.MONTH, 0);
//	    year.set(Calendar.DAY_OF_MONTH,1);
//	    year.set(Calendar.HOUR_OF_DAY, 0);
//	    year.set(Calendar.MINUTE,0);
//	    year.set(Calendar.SECOND,0);
	    
	    logger.info("year: {}",format.format(year.getTime()));
	    
	    File dir = new File("/usr/local/assets/layui/lay/modules");
	    File[] files = dir.listFiles(new FileFilter() {
		@Override
		public boolean accept(File pathname) {
		    return pathname.lastModified() > year.getTimeInMillis();
		}
	    });

	    Arrays.sort(files, new Comparator<File>() {
		@Override
		public int compare(File o1, File o2) {
		    return Long.valueOf(o2.lastModified()).compareTo(o1.lastModified());
		}
	    });
	    
	    // TimeZone.getDefault(),Locale.CHINESE
	    Calendar date = Calendar.getInstance(); // TimeZone.getTimeZone("UTC")
	    for (File file : files) {
		date.setTimeInMillis(file.lastModified());
		if (file.isDirectory()) {
		    logger.info("dir : {}, Last time: {}", file.getName(), format.format(date.getTime()));
		} else {
		    logger.info("file: {}, Last time: {}, size: {} (B)", file.getName(), format.format(date.getTime()),
			    file.length());
		}
	    }

	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

}
