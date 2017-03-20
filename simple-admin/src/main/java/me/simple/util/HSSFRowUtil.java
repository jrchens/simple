package me.simple.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.StringUtils;

public class HSSFRowUtil {

    public static boolean isValidRow(Row row){
	if(row == null){
	    return false;
	}
	for (Cell cell : row) {
	    if(cell != null && StringUtils.hasText(HSSFCellValueUtil.getCellStringValue(cell))){
		return true;
	    }
	}
	return false;
    }
}
