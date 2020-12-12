package reportStrategy;

import report.Report;

import java.util.List;

public interface Strategy {
    Report getReport(List<List<String>> reportTable);
}
