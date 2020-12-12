import report.CsvReport;
import report.ExcelReport;
import report.Report;
import reportStrategy.ReportContext;
import reportStrategy.ReportManager;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ReportBuilder<T> implements ReportGenerator<T>{
    private List<String> columnNames;
    private Map<String, String> fieldsToColumns;
    private final List<List<String>> reportTable;
    private final String type;

    ReportBuilder(Map<String, String> fieldsToColumns) {
        this.fieldsToColumns = fieldsToColumns;
        reportTable = new ArrayList<>();
        type = "csv";
    }

    ReportBuilder(Map<String, String> fieldsToColumns, String type) {
        this.fieldsToColumns = fieldsToColumns;
        reportTable = new ArrayList<>();
        this.type = type;
    }

    ReportBuilder() {
        reportTable = new ArrayList<>();
        type = "csv";
    }

    private void extractColumnNames(T entity) {
        List<Field> fields = Arrays
                .stream(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isSynthetic())
                .collect(Collectors.toList());
        List<String> columnNames;
        if (fieldsToColumns == null) {
            columnNames = fields.stream().map(Field::getName).collect(Collectors.toList());
        } else {
            columnNames = fields
                    .stream()
                    .map((Field field) -> {
                        String fieldName = field.getName();
                        String columnName = fieldsToColumns.get(fieldName);
                        return Objects.requireNonNullElse(columnName, fieldName);})
                    .collect(Collectors.toList());
        }

        this.columnNames = columnNames;
        reportTable.add(this.columnNames);
    }

    @Override
    public Report generate(List<T> entities) {
        for (T entity : entities) {
            Field[] fields = entity.getClass().getDeclaredFields();
            ArrayList<String> fieldsValues = new ArrayList<>();
            if (columnNames == null) {
                extractColumnNames(entity);
            }

            for (Field field : fields) {
                try {
                    fieldsValues.add(String.valueOf(field.get(entity)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            reportTable.add(fieldsValues);
        }

        ReportManager reportManager = new ReportManager();
        ReportContext context = reportManager.getContext(type);
        return context.getReport(reportTable);
    }
}
