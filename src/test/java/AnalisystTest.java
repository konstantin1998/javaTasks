import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalisystTest {
    @Test
    public void getReportCheckIfAnalysistMakesRightReport() throws IOException {
        Purchase[] purchases = {
            new Purchase(1, 1),
            new Purchase(1, 2),
            new Purchase(1, 3),
            new Purchase(2, 4),
            new Purchase(2, 5),
            new Purchase(2, 6),
            new Purchase(3, 7),
            new Purchase(3, 8),
            new Purchase(3, 9)
        };

        ReportBuilder<Purchase> reportBuilder = new ReportBuilder<>();
        Report report = reportBuilder.generate(Arrays.asList(purchases));
        String reportPath = System.getProperty("user.dir") + "\\src\\test\\resources\\report.csv";
        FileOutputStream fos = null;
        try {
            fos  = new FileOutputStream(reportPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        report.writeTo(fos);

        String completeReportPath = System.getProperty("user.dir") + "\\src\\test\\resources\\completeReport.csv";
        Analysist analysist = new Analysist(reportPath, new ReaderGenerator());

        Report completeReport = analysist.getReport(completeReportPath);
        try {
            fos  = new FileOutputStream(completeReportPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        completeReport.writeTo(fos);

        ArrayList<String[]> expectedReportTable = new ArrayList<>();
        expectedReportTable.add(new String[] {"1", "6"});
        expectedReportTable.add(new String[]{"2", "15"});
        expectedReportTable.add(new String[]{"3", "24"});

        ArrayList<String[]> actualReportTable = readFromCSV(completeReportPath);
        actualReportTable.sort(Comparator.comparing(o -> o[0]));

        boolean isTrue = true;
        for (int i = 0; i < actualReportTable.size(); i++) {
            for (int j = 0; j < actualReportTable.get(i).length; j++) {
                isTrue = isTrue && (actualReportTable.get(i)[j].equals(expectedReportTable.get(i)[j]));
            }
        }

        assertTrue(isTrue);
    }

    private ArrayList<String[]> readFromCSV(String filePath) throws IOException {
        ArrayList<String[]> records = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            records.add(line.split(","));
        }
        return records;
    }
}
