package reportStrategy;

import report.CsvReport;
import report.ExcelReport;

public class ReportManager {

    public ReportContext getContext(String type) {
        if (type.equals("csv")) {
            return new ReportContext(new CsvStrategy());
        }
        return new ReportContext(new ExelStrategy());
    }
}
