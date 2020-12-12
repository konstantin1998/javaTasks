import org.junit.jupiter.api.Test;
import report.Report;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalisystTest {
    @Test
    public void getReportCheckIfAnalysistMakesRightReport() throws IOException {
        String reportPath = System.getProperty("user.dir") + "\\src\\test\\resources\\report.csv";
        writeReportForAnalysis(reportPath);

        String completeReportPath = System.getProperty("user.dir") + "\\src\\test\\resources\\completeReport.csv";
        Analysist analysist = new Analysist(reportPath, new ReaderGenerator());
        Report completeReport = analysist.getReport(completeReportPath);
        writeReport(completeReportPath, completeReport);

        List<List<String>> expectedReportTable = new ArrayList<>();
        expectedReportTable.add(Arrays.asList("1", "6"));
        expectedReportTable.add(Arrays.asList("2", "15"));
        expectedReportTable.add(Arrays.asList("3", "24"));

        List<List<String>> actualReportTable = readFromCSV(completeReportPath);
        actualReportTable.sort(Comparator.comparing(o -> o.get(0)));

        boolean isTrue = compareTables(expectedReportTable, actualReportTable);

        assertTrue(isTrue);
    }

    private void writeReportForAnalysis(String reportPath) {
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
        writeReport(reportPath, report);
    }

    private void writeReport(String reportPath, Report report) {
        FileOutputStream fos = null;
        try {
            fos  = new FileOutputStream(reportPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        report.writeTo(fos);
    }

    private boolean compareTables(List<List<String>> expectedReportTable, List<List<String>> actualReportTable) {
        boolean isTrue = true;
        for (int i = 0; i < actualReportTable.size(); i++) {
            for (int j = 0; j < actualReportTable.get(i).size(); j++) {
                isTrue = isTrue && (actualReportTable.get(i).get(j).equals(expectedReportTable.get(i).get(j)));
            }
        }
        return isTrue;
    }

    private List<List<String>> readFromCSV(String filePath) throws IOException {
        List<List<String>> records = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            records.add(Arrays.asList(line.split(",")));
        }
        return records;
    }

}
