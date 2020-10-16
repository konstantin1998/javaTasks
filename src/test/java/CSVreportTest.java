import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class CSVreportTest {
    @Test
    public void writeToCheckIfReportIsWrittenToFileCorrectly() throws IOException {
        //given
        ArrayList<Person> persons = new ArrayList<>();
        int age = 20;
        String name1 = "person1";
        String name2 = "person2";
        persons.add(new Person(age, name1));
        persons.add(new Person(age, name2));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("age", "AGE");
        hashMap.put("name", "NAME");

        ReportBuilder<Person> reportBuilder = new ReportBuilder<>(hashMap);
        Report report = reportBuilder.generate(persons);
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data.csv";
        FileOutputStream fos = null;
        try {
            fos  = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        report.writeTo(fos);

        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        //when
        ArrayList<Person> actualPersons = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            Person person = new Person(Integer.parseInt(fields[0]), fields[1]);
            actualPersons.add(person);
        }
        //then
        assertArrayEquals(persons.toArray(new Person[0]), actualPersons.toArray(new Person[0]));
    }
}
