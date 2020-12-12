import report.Report;

import java.util.List;

interface ReportGenerator<T> {
    Report generate(List<T> entities);
}

