import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ReportBuilderTest {
    @Test
    public void verifyReportWithoutSpecifiedColumnNames() {
        //given
        ArrayList<Person> people = new ArrayList<>();
        int age = 20;
        String name1 = "person1";
        String name2 = "person2";
        people.add(new Person(age, name1));
        people.add(new Person(age, name2));

        ReportBuilder<Person> reportBuilder = new ReportBuilder<>();
        reportBuilder.generate(people);
        ArrayList<String[]> expectedReportTable = new ArrayList<>();
        expectedReportTable.add(new String[]{"age", "name"});
        expectedReportTable.add(new String[]{"20", "person1"});
        expectedReportTable.add(new String[]{"20", "person2"});

        ArrayList<String[]> actualReportTable = new ArrayList<>();
        try {
            Field field = reportBuilder.getClass().getDeclaredField("reportTable");
            field.setAccessible(true);
            actualReportTable = (ArrayList<String[]>) field.get(reportBuilder);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //when
        boolean isTrue = true;
        for (int i = 0; i < expectedReportTable.size(); i++) {
            for (int j = 0; j < expectedReportTable.get(0).length; j++) {
                isTrue = isTrue && expectedReportTable.get(i)[j].equals(actualReportTable.get(i)[j]);
            }
        }
        //then
        assertTrue(isTrue);
    }

    @Test
    public void verifyReportWithSpecifiedColumnNames() {
        //given
        ArrayList<Person> people = new ArrayList<>();
        int age = 20;
        String name1 = "person1";
        String name2 = "person2";
        people.add(new Person(age, name1));
        people.add(new Person(age, name2));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("age", "AGE");
        hashMap.put("name", "NAME");

        ReportBuilder<Person> reportBuilder = new ReportBuilder<>(hashMap);
        reportBuilder.generate(people);
        ArrayList<String[]> expectedReportTable = new ArrayList<>();
        expectedReportTable.add(new String[]{"AGE", "NAME"});
        expectedReportTable.add(new String[]{"20", "person1"});
        expectedReportTable.add(new String[]{"20", "person2"});

        ArrayList<String[]> actualReportTable = new ArrayList<>();
        try {
            Field field = reportBuilder.getClass().getDeclaredField("reportTable");
            field.setAccessible(true);
            actualReportTable = (ArrayList<String[]>) field.get(reportBuilder);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //when
        boolean isTrue = true;
        for (int i = 0; i < expectedReportTable.size(); i++) {
            for (int j = 0; j < expectedReportTable.get(0).length; j++) {
                isTrue = isTrue && expectedReportTable.get(i)[j].equals(actualReportTable.get(i)[j]);
            }
        }
        //then
        assertTrue(isTrue);
    }


}