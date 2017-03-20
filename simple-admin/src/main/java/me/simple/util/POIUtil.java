package me.simple.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

public class POIUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat();
    private static final String dateFormatPattern = "yyyy-MM-dd";
    private static final DecimalFormat numberFormat = new DecimalFormat();
    private static final String numberFormatPattern = "#0.##";
    
    /**
     * The following patterns are used in {@link #isADateFormat(int, String)}
     */
    private static final Pattern date_ptrn1 = Pattern.compile("^\\[\\$\\-.*?\\]");
    private static final Pattern date_ptrn2 = Pattern.compile("^\\[[a-zA-Z]+\\]");
    private static final Pattern date_ptrn3a = Pattern.compile("[yYmMdDhHsS\u5e74\u6708\u65e5\u65f6\u5206\u79d2]"); // 年月日时分秒
    private static final Pattern date_ptrn3b = Pattern.compile("^[\\[\\]yYmMdDhHsS\u5e74\u6708\u65e5\u65f6\u5206\u79d2\\-T/,. :\"\\\\]+0*[ampAMP/]*$");
    //  elapsed time patterns: [h],[m] and [s]
    private static final Pattern date_ptrn4 = Pattern.compile("^\\[([hH]+|[mM]+|[sS]+)\\]");

    public static boolean isValidRow(Row row) {
	if (row == null) {
	    return false;
	}
	for (Cell cell : row) {
	    if (cell != null && StringUtils.hasText(getCellStringValue(cell, null))) {
		return true;
	    }
	}
	return false;
    }
    
    /**
     * copy form org.apache.poi.ss.usermodel.DateUtil.isADateFormat(int formatIndex, String formatString)
     * @param formatIndex
     * @param formatString
     * @return
     */
    private static boolean isADateFormat(int formatIndex, String formatString) {
        // First up, is this an internal date format?
//        if(isInternalDateFormat(formatIndex)) {
//            cache(formatString, formatIndex, true);
//            return true;
//        }
	
        // If we didn't get a real string, don't even cache it as we can always find this out quickly
//        if(formatString == null || formatString.length() == 0) {
//            return false;
//        }

        // check the cache first
//        if (isCached(formatString, formatIndex)) {
//            return lastCachedResult.get();
//        }

        String fs = formatString;
        /*if (false) {
            // Normalize the format string. The code below is equivalent
            // to the following consecutive regexp replacements:

             // Translate \- into just -, before matching
             fs = fs.replaceAll("\\\\-","-");
             // And \, into ,
             fs = fs.replaceAll("\\\\,",",");
             // And \. into .
             fs = fs.replaceAll("\\\\\\.",".");
             // And '\ ' into ' '
             fs = fs.replaceAll("\\\\ "," ");

             // If it end in ;@, that's some crazy dd/mm vs mm/dd
             //  switching stuff, which we can ignore
             fs = fs.replaceAll(";@", "");

             // The code above was reworked as suggested in bug 48425:
             // simple loop is more efficient than consecutive regexp replacements.
        }*/
        StringBuilder sb = new StringBuilder(fs.length());
        for (int i = 0; i < fs.length(); i++) {
            char c = fs.charAt(i);
            if (i < fs.length() - 1) {
                char nc = fs.charAt(i + 1);
                if (c == '\\') {
                    switch (nc) {
                        case '-':
                        case ',':
                        case '.':
                        case ' ':
                        case '\\':
                            // skip current '\' and continue to the next char
                            continue;
                    }
                } else if (c == ';' && nc == '@') {
                    i++;
                    // skip ";@" duplets
                    continue;
                }
            }
            sb.append(c);
        }
        fs = sb.toString();

        // short-circuit if it indicates elapsed time: [h], [m] or [s]
        if(date_ptrn4.matcher(fs).matches()){
//            cache(formatString, formatIndex, true);
            return true;
        }

        // If it starts with [$-...], then could be a date, but
        //  who knows what that starting bit is all about
        fs = date_ptrn1.matcher(fs).replaceAll("");
        // If it starts with something like [Black] or [Yellow],
        //  then it could be a date
        fs = date_ptrn2.matcher(fs).replaceAll("");
        // You're allowed something like dd/mm/yy;[red]dd/mm/yy
        //  which would place dates before 1900/1904 in red
        // For now, only consider the first one
        if(fs.indexOf(';') > 0 && fs.indexOf(';') < fs.length()-1) {
           fs = fs.substring(0, fs.indexOf(';'));
        }

        // Ensure it has some date letters in it
        // (Avoids false positives on the rest of pattern 3)
        if (! date_ptrn3a.matcher(fs).find()) {
           return false;
        }
        
        // If we get here, check it's only made up, in any case, of:
        //  y m d h s - \ / , . : [ ] T
        // optionally followed by AM/PM

        boolean result = date_ptrn3b.matcher(fs).matches();
        // cache(formatString, formatIndex, result);
        return result;
    }
    
    public static boolean isDateCell(Cell cell) {
	if (cell == null) {
	    return false;
	}
	int type = cell.getCellType();
	if (type == Cell.CELL_TYPE_NUMERIC) {
	    CellStyle cellStyle = cell.getCellStyle();
	    short format = cellStyle.getDataFormat();
	    String formatString = cellStyle.getDataFormatString();
	    if (DateUtil.isCellDateFormatted(cell) || isADateFormat(format, formatString)) {
		return true;
	    }
	}
	return false;
    }

    public static String getCellStringValue(Cell cell, String pattern) {
	if (cell == null) {
	    return "";
	}
	String value = null;
	int type = cell.getCellType();
	if (type == Cell.CELL_TYPE_NUMERIC) {
	    double _value = cell.getNumericCellValue();
	    CellStyle cellStyle = cell.getCellStyle();
	    String formatString = cellStyle.getDataFormatString();
	    short format = cellStyle.getDataFormat();
	    /*
	     * boolean isDate = false; if("General".equals(formatString) ||
	     * "@".equals(formatString)){ formatString = "#0.##"; }else
	     * if(formatString.indexOf(";@") != -1 &&
	     * (formatString.indexOf("yy") != -1 || formatString.indexOf("m") !=
	     * -1 || formatString.indexOf("d") != -1 ||
	     * formatString.indexOf("h") != -1 || formatString.indexOf("m") !=
	     * -1 || formatString.indexOf("s") != -1)){ isDate = true; }
	     */
	    if (DateUtil.isCellDateFormatted(cell) || DateUtil.isCellInternalDateFormatted(cell)
		    || DateUtil.isADateFormat(format, formatString) || DateUtil.isInternalDateFormat(format)
	    /*
	     * || isDate || DateUtil.isValidExcelDate(_value)
	     */) {
		if (StringUtils.hasText(pattern)) {
		    dateFormat.applyPattern(pattern);
		} else {
		    dateFormat.applyPattern(dateFormatPattern);
		}
		value = dateFormat.format(DateUtil.getJavaDate(_value));
	    } else {
		if (StringUtils.hasText(pattern)) {
		    numberFormat.applyPattern(pattern);
		} else {
		    numberFormat.applyPattern(numberFormatPattern);
		}
		value = numberFormat.format(cell.getNumericCellValue());
	    }
	} else if (type == Cell.CELL_TYPE_STRING) {
	    value = cell.getStringCellValue();
	}
	// cell.setCellType(Cell.CELL_TYPE_STRING);
	return StringUtils.trimWhitespace(ObjectUtils.getDisplayString(value));
    }

    public static List<String[]> getSheetRow2(Sheet sheet) {
	List<String[]> instants = Lists.newArrayList();
	if (sheet == null) {
	    return instants;
	}
	
	int rowNum = sheet.getLastRowNum();
	int[] cellNums = {0,0};
	if (rowNum > 1) {
	    for (int i = 0; i < 2; i++) {
		Row row = sheet.getRow(i);
		// int cellNum = row.getLastCellNum();
		List<String> rowData = Lists.newArrayList();
		 int index = 0;
		for (Cell cell : row) {
		    String value = getCellStringValue(cell, null);
		    if (!StringUtils.hasText(value)) {
			throw new RuntimeException("row format error , cell value is empty");
		    } else {
			rowData.add(value);
		    }
		     index++;
		}
		cellNums[i] = index;
		instants.add(rowData.toArray(new String[] {}));
	    }
	    if(cellNums[0] != cellNums[1]){
		throw new RuntimeException("row format error , different columns of rows");
	    }
	}
	
	return instants;
    }
}
