import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Analysist {
    private final String filePath;
    private final ArrayList<String[]> records;

    Analysist(String filePath) {
        this.filePath = filePath;
        records = new ArrayList<>();
    }

    private String extractType(String filePath) {
        System.out.println(filePath);

        if (filePath.endsWith("csv")) {
            return "csv";
        }
        if (filePath.endsWith("excel")) {
            return "excel";
        }
        return null;
    }

    private void readFromCSV() throws IOException {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            records.add(line.split(","));
        }
    }

    private void readFromExcel() {
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
            records.add(new String[]{customerId, purchaseCost});
        }
    }

    private void readReport() {
        String type = extractType(filePath);

        if (type.equals("csv")) {
            try {
                readFromCSV();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("excel")) {
            readFromExcel();
        }
    }

    public Report getReport (String pathToFile) {
        readReport();
        System.out.println("reading ok");
        HashMap<Integer, Integer> customersAndExpenditures = new HashMap<>();
        for (String[] record : records) {
            try {
                int customerId = Integer.parseInt(record[0]);
                int purchaseCost = Integer.parseInt(record[1]);
                if (customersAndExpenditures.containsKey(customerId)) {
                    int expenditures = customersAndExpenditures.get(customerId);
                    customersAndExpenditures.put(customerId, expenditures + purchaseCost);
                } else {
                    customersAndExpenditures.put(customerId, purchaseCost);
                }
            } catch (NumberFormatException ignored) {

            }
        }

        ArrayList<String[]> reportTable = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : customersAndExpenditures.entrySet()) {
            reportTable.add(new String[]{entry.getKey().toString(), entry.getValue().toString()});
        }

        String type = extractType(pathToFile);

        if (type.equals("csv")) {
            return new CSVreport(reportTable);
        }

        return new ExcelReport(reportTable);
    }
}
