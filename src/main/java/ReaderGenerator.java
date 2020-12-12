import reader.CsvReader;
import reader.ExelReader;
import reader.MyReader;

import java.util.ArrayList;
import java.util.List;

public class ReaderGenerator {

    public MyReader getReader(String filePath) {
        System.out.println(filePath);

        if (filePath.endsWith("csv")) {
            return  new CsvReader(filePath);
        }
        if (filePath.endsWith("excel")) {
            return new ExelReader(filePath);
        }
        return null;
    }
}
