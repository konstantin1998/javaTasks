package report;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import report.Report;

import java.io.*;
import java.util.List;


public class ExcelReport implements Report {
    private Workbook book;

    public ExcelReport(List<List<String>> reportTable) {
        configureExelBook(reportTable);
    }

    private void configureExelBook(List<List<String>> reportTable) {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet();
        for (int i = 0; i < reportTable.size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < reportTable.get(i).size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(reportTable.get(i).get(j));
            }
        }
        this.book = book;
    }

    @Override
    public byte[] asBytes() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            book.write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

}
