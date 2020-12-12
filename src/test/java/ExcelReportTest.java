import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import report.Report;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelReportTest {
    @Test
    public void writeToCheckIfReportIsWrittenToFileCorrectly() throws IOException {
        //given
        ArrayList<Person> persons = new ArrayList<>();
        int age = 20;
        String name1 = "person1";
        String name2 = "person2";
        persons.add(new Person(age, name1));
        persons.add(new Person(age, name2));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("age", "AGE");
        hashMap.put("name", "NAME");

        ReportBuilder<Person> reportBuilder = new ReportBuilder<>(hashMap, "excel");
        Report report = reportBuilder.generate(persons);
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data.excel";
        FileOutputStream fos = null;
        try {
            fos  = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        report.writeTo(fos);
        //when
        ArrayList<Person> actualPersons = readFromExcel(filePath);
        //then
        assertArrayEquals(persons.toArray(new Person[0]), actualPersons.toArray(new Person[0]));
    }

    private ArrayList<Person> readFromExcel(String filePath) {
        HSSFWorkbook excelBook = null;
        try {
            excelBook = new HSSFWorkbook(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sheetIndex = 0;
        HSSFSheet sheet = Objects.requireNonNull(excelBook).getSheetAt(sheetIndex);
        ArrayList<Person> persons = new ArrayList<>();

        for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            int age = Integer.parseInt(row.getCell(0).getStringCellValue());
            String name = row.getCell(1).getStringCellValue();
            persons.add(new Person(age, name));
        }
        return persons;
    }
}
