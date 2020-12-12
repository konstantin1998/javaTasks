package reader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader implements MyReader {
    private final List<List<String>> records;
    private final String filePath;

    public CsvReader(String filePath) {
        records = new ArrayList<>();
        this.filePath = filePath;
    }

    @Override
    public List<List<String>> read(){
        File file = new File(filePath);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new java.io.FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (true) {
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line != null) {
                records.add(Arrays.asList(line.split(",")));
            } else {
                break;
            }
        }
        return records;
    }


}
