package me.simple.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class HSSFCellValueUtil {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * process number(date),string cell
     * @param cell
     * @return
     */
    public static String getCellStringValue(Cell cell) {
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
	    boolean isDate = false;
	    if("General".equals(formatString) || "@".equals(formatString)){
		formatString = "#0.##";
	    }else if(formatString.indexOf(";@") != -1 && 
		    (formatString.indexOf("yy") != -1 
		    || formatString.indexOf("m") != -1 
		    || formatString.indexOf("d") != -1
		    || formatString.indexOf("h") != -1
		    || formatString.indexOf("m") != -1
		    || formatString.indexOf("s") != -1)){
		isDate = true;
	    }
	    if (DateUtil.isCellDateFormatted(cell) || DateUtil.isCellInternalDateFormatted(cell)
		    || DateUtil.isADateFormat(format, formatString)
		    || DateUtil.isInternalDateFormat(format)
		    || isDate 
		    /*|| DateUtil.isValidExcelDate(_value)*/) {
		value = dateFormat.format(DateUtil.getJavaDate(_value));
	    } else {
		DecimalFormat numberFormat = new DecimalFormat(formatString);
		value = numberFormat.format(cell.getNumericCellValue());
	    }
	} else if (type == Cell.CELL_TYPE_STRING) {
	    value = cell.getStringCellValue();
	}
	// cell.setCellType(Cell.CELL_TYPE_STRING);
	return StringUtils.trimWhitespace(ObjectUtils.getDisplayString(value));
    }
}
