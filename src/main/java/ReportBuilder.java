import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ReportBuilder<T> implements ReportGenerator<T>{
    private String[] columnNames;
    private HashMap<String, String> fieldsToColumns;
    private final ArrayList<String[]> reportTable;
    private final String type;

    ReportBuilder(HashMap<String, String> fieldsToColumns) {
        this.fieldsToColumns = fieldsToColumns;
        reportTable = new ArrayList<>();
        type = "csv";
    }

    ReportBuilder(HashMap<String, String> fieldsToColumns, String type) {
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
        String[] columnNames = new String[fields.length];

        if (fieldsToColumns == null) {
            for (int i = 0; i < fields.length; i++) {
                columnNames[i] = fields[i].getName();
            }
        } else {
            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                String columnName = fieldsToColumns.get(fieldName);
                columnNames[i] = Objects.requireNonNullElse(columnName, fieldName);
            }
        }

        this.columnNames = columnNames;
        reportTable.add(this.columnNames);
    }

    @Override
    public Report generate(List<T> entities) {
        for (T entity : entities) {
            Field[] fields = entity.getClass().getDeclaredFields();
            String[] fieldsValues = new String[fields.length];
            if (columnNames == null) {
                extractColumnNames(entity);
            }

            for (int i = 0; i < fields.length; i++) {
                try {
                    fieldsValues[i] = String.valueOf(fields[i].get(entity));
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
