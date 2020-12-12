package report;

import report.Report;

import java.util.ArrayList;
import java.util.List;

public class CsvReport implements Report {
    private final List<List<String>> reportTable;

    public CsvReport(List<List<String>> reportTable) {
        this.reportTable = reportTable;
    }

    @Override
    public byte[] asBytes() {
        List<String> rows = new ArrayList<>();
        for (List<String> strings : reportTable) {
            rows.add(String.join(",", strings));
        }
        return String.join("\n", rows).getBytes();
    }

}
