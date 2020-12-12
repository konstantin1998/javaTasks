package reportStrategy;

import report.ExcelReport;
import report.Report;

import java.util.List;

public class ExelStrategy implements Strategy{
    @Override
    public Report getReport(List<List<String>> reportTable) {
        return new ExcelReport(reportTable);
    }
}
