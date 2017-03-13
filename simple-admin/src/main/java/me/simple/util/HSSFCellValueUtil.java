package me.simple.util;

import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class HSSFCellValueUtil {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * process number(date),string cell
     * @param cell
     * @return
     */
    public String getCellStringValue(Cell cell) {
	if (cell == null) {
	    return "";
	}
	String value = null;
	int type = cell.getCellType();
	if (type == Cell.CELL_TYPE_NUMERIC) {
	    double _value = cell.getNumericCellValue();
	    if (DateUtil.isCellDateFormatted(cell)) {
		value = dateFormat.format(DateUtil.getJavaDate(_value));
	    } else {
		value = String.valueOf(cell.getNumericCellValue());
	    }
	} else if (type == Cell.CELL_TYPE_STRING) {
	    value = cell.getStringCellValue();
	}
	return value;
    }
}
