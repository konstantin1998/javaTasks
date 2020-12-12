import reader.CsvReader;
import reader.ExelReader;
import reader.MyReader;

public class ReaderGenerator {

    public MyReader getReader(String filePath) {

        if (filePath.endsWith("csv")) {
            return  new CsvReader(filePath);
        }
        if (filePath.endsWith("excel")) {
            return new ExelReader(filePath);
        }
        return null;
    }
}
