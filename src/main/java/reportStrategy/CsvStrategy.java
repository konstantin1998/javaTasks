package reportStrategy;

import report.CsvReport;
import report.Report;

import java.util.List;

public class CsvStrategy implements Strategy{
    @Override
    public Report getReport(List<List<String>> reportTable) {
        return new CsvReport(reportTable);
    }
}
