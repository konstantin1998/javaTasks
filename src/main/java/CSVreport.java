import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class CSVreport implements Report{
    private final List<List<String>> reportTable;

    CSVreport(List<List<String>> reportTable) {
        this.reportTable = reportTable;
    }

    @Override
    public byte[] asBytes() {
        List<String> rows = new ArrayList<String>();
        for (List<String> strings : reportTable) {
            rows.add(String.join(",", strings));
        }
        return String.join("\n", rows).getBytes();
    }

    @Override
    public void writeTo(OutputStream os) {
        byte[] bytes = asBytes();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        try {
            bos.write(bytes);
            bos.writeTo(os);
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}
