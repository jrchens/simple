package me.simple.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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
