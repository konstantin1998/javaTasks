import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class CSVreport implements Report{
    private final ArrayList<String[]> reportTable;

    CSVreport(ArrayList<String[]> reportTable) {
        this.reportTable = reportTable;
    }

    @Override
    public byte[] asBytes() {
        String[] rows = new String[reportTable.size()];
        for(int i = 0; i < reportTable.size(); i++) {
            rows[i] = String.join(",", reportTable.get(i));
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
