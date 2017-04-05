package me.simple.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

public class POIUtil {

    private static final String dateFormatPattern = "yyyy-MM-dd";
    private static final String numberFormatPattern = "#0.##";

    private static final DecimalFormat numberFormat = new DecimalFormat(numberFormatPattern);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

    /**
     * The following patterns are used in {@link #isADateFormat(String)}
     */
    private static final Pattern date_ptrn1 = Pattern.compile("^\\[\\$\\-.*?\\]");
    private static final Pattern date_ptrn2 = Pattern.compile("^\\[[a-zA-Z]+\\]");
    private static final Pattern date_ptrn3a = Pattern.compile("[yYmMdDhHsS]");
    private static final Pattern date_ptrn3b = Pattern.compile("^[\\[\\]yYmMdDhHsS\\-T/,. :\"\\\\]+0*[ampAMP/]*$");
    // elapsed time patterns: [h],[m] and [s]
    private static final Pattern date_ptrn4 = Pattern.compile("^\\[([hH]+|[mM]+|[sS]+)\\]");

    public static boolean isValidRow(Row row) {
	if (row == null) {
	    return false;
	}
	for (Cell cell : row) {
	    if (cell != null && getCellValue(cell, null) != null) {
		return true;
	    }
	}
	return false;
    }

    /**
     * copy form org.apache.poi.ss.usermodel.DateUtil.isADateFormat(int
     * formatIndex, String formatString)
     * 
     * @param formatIndex
     * @param formatString
     * @return
     */
    public static boolean isADateFormat(/* int formatIndex, */ String formatString) {
	// First up, is this an internal date format?
	// if(isInternalDateFormat(formatIndex)) {
	// cache(formatString, formatIndex, true);
	// return true;
	// }

	// If we didn't get a real string, don't even cache it as we can always
	// find this out quickly
	// if(formatString == null || formatString.length() == 0) {
	// return false;
	// }

	// check the cache first
	// if (isCached(formatString, formatIndex)) {
	// return lastCachedResult.get();
	// }

	String fs = formatString;
	/*
	 * if (false) { // Normalize the format string. The code below is
	 * equivalent // to the following consecutive regexp replacements:
	 * 
	 * // Translate \- into just -, before matching fs =
	 * fs.replaceAll("\\\\-","-"); // And \, into , fs =
	 * fs.replaceAll("\\\\,",","); // And \. into . fs =
	 * fs.replaceAll("\\\\\\.","."); // And '\ ' into ' ' fs =
	 * fs.replaceAll("\\\\ "," ");
	 * 
	 * // If it end in ;@, that's some crazy dd/mm vs mm/dd // switching
	 * stuff, which we can ignore fs = fs.replaceAll(";@", "");
	 * 
	 * // The code above was reworked as suggested in bug 48425: // simple
	 * loop is more efficient than consecutive regexp replacements. }
	 */
	StringBuilder sb = new StringBuilder(fs.length());
	for (int i = 0; i < fs.length(); i++) {
	    char c = fs.charAt(i);

	    // 年月日时分秒 " '
	    switch (c) {
	    case '\u5e74':
	    case '\u6708':
	    case '\u65e5':
	    case '\u65f6':
	    case '\u5206':
	    case '\u79d2':
		continue;
	    }

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
	if (date_ptrn4.matcher(fs).matches()) {
	    // cache(formatString, formatIndex, true);
	    return true;
	}

	// If it starts with [$-...], then could be a date, but
	// who knows what that starting bit is all about
	fs = date_ptrn1.matcher(fs).replaceAll("");
	// If it starts with something like [Black] or [Yellow],
	// then it could be a date
	fs = date_ptrn2.matcher(fs).replaceAll("");
	// You're allowed something like dd/mm/yy;[red]dd/mm/yy
	// which would place dates before 1900/1904 in red
	// For now, only consider the first one
	if (fs.indexOf(';') > 0 && fs.indexOf(';') < fs.length() - 1) {
	    fs = fs.substring(0, fs.indexOf(';'));
	}

	// Ensure it has some date letters in it
	// (Avoids false positives on the rest of pattern 3)
	if (!date_ptrn3a.matcher(fs).find()) {
	    return false;
	}

	// If we get here, check it's only made up, in any case, of:
	// y m d h s - \ / , . : [ ] T
	// optionally followed by AM/PM

	boolean result = date_ptrn3b.matcher(fs).matches();
	// cache(formatString, formatIndex, result);
	return result;
    }

    public static List<String> getFirstTitleRow(Row row) {
	List<String> titles = Lists.newArrayList();
	if (row != null) {
	    for (Cell cell : row) {
		if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING
			|| !StringUtils.hasText(cell.getStringCellValue())) {
		    break;
		}
		titles.add(cell.getStringCellValue());
	    }
	}
	return titles;
    }

    public static String getCellAddress(Cell cell) {
	CellReference ref = new CellReference(cell);
	return ref.formatAsString();
    }

    /**
     * 
     * @param cell
     * @return #0.## | yyyy-MM-dd | cell.getStringCellValue()
     */
    public static String getCellStringValue(Cell cell, String dataType) {
	String value = null;
	try {
	    int cellType = 3;
	    if (cell != null && (cellType = cell.getCellType()) != Cell.CELL_TYPE_BLANK) {
		if (cellType == Cell.CELL_TYPE_NUMERIC) {
		    double _value = cell.getNumericCellValue();
		    if (DateUtil.isCellDateFormatted(cell)
			    || isADateFormat(cell.getCellStyle().getDataFormatString())) {
			value = dateFormat.format(DateUtil.getJavaDate(_value));
		    } else {
			value = numberFormat.format(_value);
			if (dataType != null && "date".indexOf(dataType) == 0) {
			    Date date = DateUtils.parseDate(numberFormat.format(_value), new String[] { "yyyyMMdd" });
			    value = dateFormat.format(date);
			}
		    }
		} else if (cellType == Cell.CELL_TYPE_STRING) {
		    String _value = cell.getStringCellValue();
		    if (StringUtils.hasText(_value)) {
			if (dataType != null && "date".indexOf(dataType) == 0) {
			    Date date = DateUtils.parseDate(_value, new String[] { "yyyyMMdd", "yyyy/MM/dd",
				    "yyyy-MM-dd", "yyyy.MM.dd", "yyyy年MM月dd日" });
			    value = dateFormat.format(date);
			} else if (dataType != null && "double".indexOf(dataType) == 0) {
			    value = numberFormat.format(_value);
			} else {
			    value = _value;
			}
		    } else {
			value = null;
		    }
		}
	    }
	} catch (Exception e) {
	    throw new IllegalStateException(
		    String.format("illegal cell value (%s), at cell (%s)", value, getCellAddress(cell)));
	}
	// DataFormatter df = new DataFormatter();
	// value = df.formatCellValue(cell);
	// if(!StringUtils.hasText(value)){
	// value = null;
	// }
	return value;
    }

    public static Object getCellValue(Cell cell, String dataType) {
	Object value = null;
	try {
	    int cellType = 3;
	    if (cell != null && (cellType = cell.getCellType()) != Cell.CELL_TYPE_BLANK) {
		if (cellType == Cell.CELL_TYPE_NUMERIC) {
		    double _value = cell.getNumericCellValue();
		    if (DateUtil.isCellDateFormatted(cell)
			    || isADateFormat(cell.getCellStyle().getDataFormatString())) {
			value = DateUtil.getJavaDate(_value);
		    } else {
			if (dataType != null && "date".indexOf(dataType) == 0) {
			    Date d = DateUtils.parseDate(numberFormat.format(_value), new String[] { "yyyyMMdd" });
			    value = new java.sql.Date(d.getTime());
			} else if (dataType != null && "double".indexOf(dataType) == 0) {
			    value = numberFormat.parse(String.valueOf(_value)).doubleValue();
			} else {
			    value = _value;
			}
		    }
		} else if (cellType == Cell.CELL_TYPE_STRING) {
		    String _value = cell.getStringCellValue();
		    if (StringUtils.hasText(_value)) {
			if (dataType != null && "date".indexOf(dataType) == 0) {
			    Date d = DateUtils.parseDate(_value, new String[] { "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd",
				    "yyyy.MM.dd", "yyyy年MM月dd日" });
			    value = new java.sql.Date(d.getTime());
			} else if (dataType != null && "double".indexOf(dataType) == 0) {
			    value = numberFormat.parse(_value).doubleValue();
			} else {
			    value = _value;
			}
		    }
		}
	    }
	} catch (Exception e) {
	    throw new IllegalStateException(
		    String.format("illegal cell value (%s), at cell (%s)", value, getCellAddress(cell)));
	}
	return value;
    }

    public static List<String[]> getSheetRow2(Sheet sheet) {
	List<String[]> instants = Lists.newArrayList();
	if (sheet == null) {
	    return instants;
	}

	int rowNum = sheet.getLastRowNum();
	int[] cellNums = { 0, 0 };
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
	    if (cellNums[0] != cellNums[1]) {
		throw new RuntimeException("row format error , different columns of rows");
	    }
	}

	return instants;
    }

    // public static CellStyle getCellStyle(Workbook wb) {
    // CellStyle style = wb.createCellStyle();
    //
    // short fontHeight = 12;
    // Font font = wb.createFont();
    // font.setColor(Font.COLOR_NORMAL);
    // font.setFontHeightInPoints(fontHeight);
    // font.setFontName("\u5b8b\u4f53");// 宋体
    // style.setFont(font);
    //
    // style.setAlignment(CellStyle.ALIGN_CENTER);
    // style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    //
    // style.setBorderTop(CellStyle.BORDER_THIN);
    // style.setTopBorderColor(Font.COLOR_NORMAL);
    // style.setBorderRight(CellStyle.BORDER_THIN);
    // style.setRightBorderColor(Font.COLOR_NORMAL);
    // style.setBorderBottom(CellStyle.BORDER_THIN);
    // style.setBottomBorderColor(Font.COLOR_NORMAL);
    // style.setBorderLeft(CellStyle.BORDER_THIN);
    // style.setLeftBorderColor(Font.COLOR_NORMAL);
    //
    // style.setWrapText(true);
    //
    // try {
    // wb.close();
    // } catch (Exception e) {
    // }
    //
    // return style;
    // }
}