import reader.MyReader;

import java.util.*;

public class Analysist {
    private final String filePath;
    private final ReaderGenerator readerGenerator;

    Analysist(String filePath, ReaderGenerator readerGenerator) {
        this.filePath = filePath;
        this.readerGenerator = readerGenerator;
    }

    private String extractType(String filePath) {
        System.out.println(filePath);

        if (filePath.endsWith("csv")) {
            return "csv";
        }
        if (filePath.endsWith("excel")) {
            return "excel";
        }
        return null;
    }

    public Report getReport(String pathToFile) {
        MyReader reader = readerGenerator.getReader(filePath);
        List<List<String>> records = reader.read();
        Map<Integer, Integer> customersAndExpenditures = new HashMap<>();
        for (List<String> record : records) {
            try {
                int customerId = Integer.parseInt(record.get(0));
                int purchaseCost = Integer.parseInt(record.get(1));
                if (customersAndExpenditures.containsKey(customerId)) {
                    int expenditures = customersAndExpenditures.get(customerId);
                    customersAndExpenditures.put(customerId, expenditures + purchaseCost);
                } else {
                    customersAndExpenditures.put(customerId, purchaseCost);
                }
            } catch (NumberFormatException ignored) {

            }
        }

        ArrayList<List<String>> reportTable = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : customersAndExpenditures.entrySet()) {
            reportTable.add(Arrays.asList(entry.getKey().toString(), entry.getValue().toString()));
        }

        String type = extractType(pathToFile);

        if (type.equals("csv")) {
            return new CSVreport(reportTable);
        }

        return new ExcelReport(reportTable);
    }
}
