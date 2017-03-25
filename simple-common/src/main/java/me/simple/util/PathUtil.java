package me.simple.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PathUtil {
    private static final Logger logger = LoggerFactory.getLogger(PathUtil.class);
    private static final SimpleDateFormat format = new SimpleDateFormat("/yyyy/MM");
    public static final String URL_SEPARATOR = "/";

    public static String imageThumbnail(String url, String dir) {
	String[] seg = url.split(URL_SEPARATOR);
	int len = seg.length - 1;
	StringBuffer urlBuffer = new StringBuffer();
	for (int i = 0; i < len; i++) {
	    urlBuffer.append(seg[i]).append(URL_SEPARATOR);
	}
	urlBuffer.append(dir).append(URL_SEPARATOR).append(seg[len]);
	return urlBuffer.toString();
    }

    public static String getYearMonth() {
	return format.format(Calendar.getInstance().getTime());
    }

    public static void main(String[] args) {
	String url = "http://cms.local.com/upload/product/2/1484640923157.png";
	logger.debug(imageThumbnail(url, "160x160"));
	
	logger.debug(getYearMonth());
    }
}
