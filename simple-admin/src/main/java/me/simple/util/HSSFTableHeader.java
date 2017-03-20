package me.simple.util;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import me.simple.f1.util.HSSFCellValueUtil;

public class HSSFTableHeader {

    private List<String[]> instants = Lists.newArrayList();

    private HSSFTableHeader() {
    }

    public static HSSFTableHeader getInstance() {
	return new HSSFTableHeader();
    }

    public List<String[]> getInstants() {
	return instants;
    }

    public void setHeader(Sheet sheet) {
	int row = sheet.getLastRowNum();
	if (row > 1) {
	    Row columname = sheet.getRow(0);
	    Row columnComment = sheet.getRow(1);
	    short cnn = columname.getLastCellNum();
//	    short ccn = columnComment.getLastCellNum();
//	    if(cnn == ccn){
		for (short i = 0; i < cnn; i++) {
		    if(columname.getCell(i) == null || !StringUtils.hasText(HSSFCellValueUtil.getCellStringValue(columname.getCell(i)))){
			break;
		    }
		    this.instants.add(i, new String[]{
                        HSSFCellValueUtil.getCellStringValue(columname.getCell(i)),
                        HSSFCellValueUtil.getCellStringValue(columnComment.getCell(i)),
		    });
		}
//	    }else{
//		throw new RuntimeException("xls file header parse error");
//	    }
	}
    }

    public String getColumname(int index) {
	if (this.instants.size() > index) {
	    return this.instants.get(index)[0];
	}
	return null;
    }

    public String getColumnComment(int index) {
	if (this.instants.size() > index) {
	    return this.instants.get(index)[1];
	}
	return null;
    }

    public List<String[]> getTableHeaderRow() {
	return this.instants;
    }
    
    public List<String> getColumnames() {
	List<String> columnames = Lists.newArrayList();
	for (String[] strings : instants) {
	    columnames.add(strings[0]);
	}
	return columnames;
    }

}
