import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;


public class ExcelReport implements Report{
    private final Workbook book;

    ExcelReport(ArrayList<String[]> reportTable) {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet();
        for (int i = 0; i < reportTable.size(); i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < reportTable.get(i).length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(reportTable.get(i)[j]);
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

    @Override
    public void writeTo(OutputStream os) {
        byte[] bytes = asBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        try {
            bos.write(bytes);
            bos.writeTo(os);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
