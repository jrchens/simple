package org.apache.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import me.simple.util.POIUtil;

public class XSSFWorkbookTest {

    @Test
    public void testXLSXRead() throws FileNotFoundException, IOException {
	Workbook workbook = null;
	FileInputStream is = null;
	try {
	    is = new FileInputStream(ResourceUtils.getFile("classpath:read.xlsx"));
	    workbook = new XSSFWorkbook(is);

	    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

	    Sheet sheet = workbook.getSheetAt(0);

	    int rows = sheet.getPhysicalNumberOfRows();

	    for (int i = 1; i < rows; i++) {
		Row row = sheet.getRow(i);
		if (row == null) {
		    continue;
		}

		int cells = row.getLastCellNum(); // row.getPhysicalNumberOfCells();
		for (int j = 0; j < cells; j++) {
		    // HSSFCell cell = row.getCell(j);
		    // if (cell == null) {
		    // continue;
		    // }
		    Cell cell = row.getCell(j);
		    CellValue cellValue = evaluator.evaluate(cell);
		    if (cellValue == null) {
			continue;
		    }
		    String value = null;
		    String formatString = null;
		    // CELL_TYPE_STRING
		    // CELL_TYPE_NUMERIC
		    // CELL_TYPE_FORMULA
		    // CELL_TYPE_BOOLEAN
		    // CELL_TYPE_ERROR

		    // cell.getCellType()
		    switch (cellValue.getCellType()) {

		    case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;

		    case HSSFCell.CELL_TYPE_BOOLEAN:
			value = "" + cell.getBooleanCellValue();
			break;

		    case HSSFCell.CELL_TYPE_ERROR:
			value = "" + cell.getErrorCellValue();
			break;
		    // case HSSFCell.CELL_TYPE_FORMULA:
		    // value = "" + cell.getCellFormula() + "=" +
		    // evaluator.evaluateFormulaCell(cell);
		    // break;

		    case HSSFCell.CELL_TYPE_NUMERIC:
			CellStyle cellStyle = cell.getCellStyle();
			if (cellStyle != null) {
			    formatString = cellStyle.getDataFormatString();
			    // formatString =
			    // HSSFDataFormat.getBuiltinFormat(cell.getCellStyle().getDataFormat());
			}
			if (HSSFDateUtil.isCellDateFormatted(cell) || "reserved-0x1f".equals(formatString)) {
			    // reserved-0x1f (international and undocumented)
			    value = "" + new java.sql.Date(cell.getDateCellValue().getTime());
			} else {
			    value = "" + cell.getNumericCellValue();
			}

			break;

		    case HSSFCell.CELL_TYPE_STRING:
			value = "" + cell.getStringCellValue();
			break;

		    default:
		    }

		    System.out.print(String.format("%s,", value));

		}
		System.out.println();
	    }

	} finally {
	    is.close();
	    workbook.close();
	}
    }

    @Test
    public void testReadXLSXFormats() {
	
    }

    @Test
    public void testReadXLSXCell() throws Exception {
	Workbook workbook = null;
	InputStream is = null;
	try {
	    is = new FileInputStream(ResourceUtils.getFile("classpath:data.xlsx"));
	    workbook = new XSSFWorkbook(is);
	    Sheet sheet = workbook.getSheetAt(0);
	    int rows = sheet.getLastRowNum();
	    for (int i = 0; i <= rows; i++) {
		Row row = sheet.getRow(i);
		for (Cell cell : row) {
		    System.out.println(POIUtil.getCellStringValue(cell));
		}
	    }
	} finally {
	    try {
		is.close();
		workbook.close();
	    } catch (Exception e) {
	    }
	}
    }
}
