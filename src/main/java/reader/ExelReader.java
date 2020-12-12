package reader;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExelReader implements MyReader {
    private final List<List<String>> records;
    private final String filePath;

    public ExelReader(String filePath) {
        records = new ArrayList<>();
        this.filePath = filePath;
    }

    @Override
    public List<List<String>> read() {
        HSSFWorkbook excelBook = null;
        try {
            excelBook = new HSSFWorkbook(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sheetIndex = 0;
        HSSFSheet sheet = Objects.requireNonNull(excelBook).getSheetAt(sheetIndex);
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            String customerId = row.getCell(0).getStringCellValue();
            String purchaseCost = row.getCell(1).getStringCellValue();

            records.add(Arrays.asList(customerId, purchaseCost));
        }
        return records;
    }
}
