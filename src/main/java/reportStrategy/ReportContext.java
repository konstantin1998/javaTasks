package reportStrategy;

import report.Report;

import java.util.List;

public class ReportContext {
    private final Strategy strategy;

    ReportContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public Report getReport(List<List<String>> reportTable) {
        return strategy.getReport(reportTable);
    }
}
