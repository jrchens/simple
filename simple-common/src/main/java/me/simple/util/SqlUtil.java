package me.simple.util;

import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

public final class SqlUtil {
    private static final Logger logger = LoggerFactory.getLogger(SqlUtil.class);

    public static String countSql(String querySql) {
	return Pattern.compile("^(select).*?(from)", Pattern.CASE_INSENSITIVE).matcher(querySql.toString())
		.replaceFirst("select count(1) from");
    }

    public static String like(String value) {
	return "%" + StringUtils.trimWhitespace(value) + "%";
    }

    public static String toBeanFieldname(String columname) {
	if (!StringUtils.hasText(columname)) {
	    return null;
	}
	if (columname.indexOf('_') != -1) {
	    char[] ca = columname.toCharArray();
	    boolean bool = false;
	    StringBuffer buffer = new StringBuffer();
	    for (char c : ca) {
		if (Character.isWhitespace(c)) {
		    continue;
		}
		if (c == '_') {
		    bool = true;
		    continue;
		}
		if (bool) {
		    buffer.append(Character.toUpperCase(c));
		} else {
		    buffer.append(Character.toLowerCase(c));
		}
		bool = false;
	    }
	    if (buffer.length() > 1)
		return buffer.substring(0, 1).toLowerCase() + buffer.substring(1);
	    else
		return buffer.substring(0, 1).toLowerCase();
	} else {
	    if (columname.length() > 1)
		return columname.substring(0, 1).toLowerCase() + columname.substring(1);
	    else
		return columname.substring(0, 1).toLowerCase();
	}
    }

    public static String getOrder(String value) {
	return value;
    }

    public static String getSort(String value) {
	return value;
    }

    public static String uuid() {
	String uuid = UUID.randomUUID().toString();
	StringBuffer buffer = new StringBuffer();
	buffer.append(uuid.substring(0, 8));
	buffer.append(uuid.substring(9, 13));
	buffer.append(uuid.substring(14, 18));
	buffer.append(uuid.substring(19, 23));
	buffer.append(uuid.substring(24));

	return buffer.toString();
    }

    public static void main(String[] args) {
	logger.debug(countSql("select * from sys_user"));
	logger.debug(HtmlUtils.htmlEscape("select ' from ' delete from "));
    }
}
