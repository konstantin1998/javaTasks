package report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface Report {
    byte[] asBytes();

    default void writeTo(OutputStream os) {
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
