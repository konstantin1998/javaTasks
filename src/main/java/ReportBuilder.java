import java.lang.reflect.Field;
import java.util.*;

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
        Field[] fields = entity.getClass().getDeclaredFields();
        ArrayList<String> columnNames = new ArrayList<>();
        if (fieldsToColumns == null) {
            for (Field field : fields) {
                columnNames.add(field.getName());
            }
        } else {
            for (Field field : fields) {
                String fieldName = field.getName();
                String columnName = fieldsToColumns.get(fieldName);
                columnNames.add(Objects.requireNonNullElse(columnName, fieldName));
            }
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

        if (type.equals("csv")) {
            return new CSVreport(reportTable);
        }
        return new ExcelReport(reportTable);
    }
}
