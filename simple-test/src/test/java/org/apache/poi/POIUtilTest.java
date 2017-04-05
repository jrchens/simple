package org.apache.poi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.Lists;

import me.simple.util.POIUtil;

public class POIUtilTest {

    private final static Logger logger = LoggerFactory.getLogger(POIUtilTest.class);

    @Test
    public void testGetCellStringValue() throws Exception {
	Workbook wb = null;
	InputStream is = null;
	try {
	    is = new FileInputStream(ResourceUtils.getFile("classpath:read.xls"));
	    wb = new HSSFWorkbook(is);

	    Sheet sheet = wb.getSheetAt(0);
	    for (Row row : sheet) {
		if (POIUtil.isValidRow(row)) {
		    for (Cell cell : row) {
			logger.info("is date cell: {}, cell value: {}", DateUtil.isCellDateFormatted(cell),
				POIUtil.getCellStringValue(cell, null));
		    }
		}
	    }
	} finally {
	    is.close();
	    wb.close();
	}
    }

    @Test
    public void testGetSheetRow2() throws Exception {
	Workbook wb = null;
	InputStream is = null;
	try {
	    is = new FileInputStream(ResourceUtils.getFile("classpath:data.xls"));
	    wb = new HSSFWorkbook(is);

	    Sheet sheet = wb.getSheetAt(0);

	    List<String[]> row2 = POIUtil.getSheetRow2(sheet);
	    for (String[] row : row2) {
		for (String cell : row) {
		    logger.info("cell value: {}", cell);
		}
	    }

	} finally {
	    is.close();
	    wb.close();
	}
    }

    @Test
    public void testIsADateFormat() throws Exception {
	String formatString = "yyyy年MM月dd日";
	logger.info("{} isADateFormat: {}", formatString, POIUtil.isADateFormat(formatString));
    }

    @Test
    public void testGetCellStringValue2() throws Exception {
	Workbook wb = null;
	InputStream is = null;
	try {
	    is = new FileInputStream(ResourceUtils.getFile("classpath:data2.xls"));
	    wb = new HSSFWorkbook(is);

	    Sheet sheet = wb.getSheetAt(0);
	    Row first = sheet.getRow(0);

	    int columnum = 0;
	    List<String> dataTypes = Lists.newArrayList();
	    if (first != null && POIUtil.isValidRow(first)) {
		columnum = first.getLastCellNum();
		for (Cell cell : first) {
		    dataTypes.add(cell.getStringCellValue());
		}
	    }

	    for (Row row : sheet) {
		if (row != null && POIUtil.isValidRow(row)) {
		    if (row.getRowNum() > 0) {
			for (int i = 0; i < columnum; i++) {
			    String value = POIUtil.getCellStringValue(row.getCell(i), dataTypes.get(i));
			    System.out.print(String.format("%s,\t\t\t", value));
			}
			System.out.println();
		    }
		}
	    }

	} finally {
	    is.close();
	    wb.close();
	}
    }
}
