package org.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

public class JsoupTest {

    // private final static Logger logger =
    // LoggerFactory.getLogger(JsoupTest.class);

    @Test
    public void testParseHTML() throws Exception {
	OutputStream os = null;
	Workbook wb = null;
	try {
	    List<List<String>> trList = Lists.newArrayList();

	    Document doc = Jsoup.parse(ResourceUtils.getFile("classpath:DATA.HTML"), null);
	    Elements trs = doc.select("TR");

	    int rows = trs.size();
	    for (int i = 0; i < rows; i++) {
		Elements tds = trs.get(i).select("TD");
		int cells = tds.size();
		List<String> tdList = Lists.newArrayList();
		for (int j = 0; j < cells; j++) {
		    tdList.add(StringUtils.trimWhitespace(tds.get(j).text()));
		}
		trList.add(tdList);
	    }

	    wb = new HSSFWorkbook();
	    Sheet sheet = wb.createSheet();

	    int start = 0;
	    for (List<String> list : trList) {
		Row row = sheet.createRow(start);
		int column = 0;
		for (String td : list) {
		    Cell cell = row.createCell(column, Cell.CELL_TYPE_STRING);
		    cell.setCellValue(td);
		    column++;
		}
		start++;
	    }

	    os = new FileOutputStream(new File("/usr/local/tmpdir", "DATA.xls"));
	    wb.write(os);
	} catch (Exception e) {
	    throw e;
	} finally {
	    try {
		os.close();
		wb.close();
	    } catch (Exception e2) {
	    }
	}
    }
}
