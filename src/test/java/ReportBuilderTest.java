import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportBuilderTest {
    @Test
    public void verifyReportWithoutSpecifiedColumnNames() {
        //given
        ArrayList<Person> people = getPeople();

        ReportBuilder<Person> reportBuilder = new ReportBuilder<>();
        reportBuilder.generate(people);

        ArrayList<List<String>> expectedReportTable = getExpectedTable(Arrays.asList("age", "name"));
        ArrayList<List<String>> actualReportTable = getActualTable(reportBuilder);
        //when
        boolean isTrue = compareTables(expectedReportTable, actualReportTable);
        //then
        assertTrue(isTrue);
    }

    private ArrayList<Person> getPeople() {
        ArrayList<Person> people = new ArrayList<>();
        int age = 20;
        String name1 = "person1";
        String name2 = "person2";
        people.add(new Person(age, name1));
        people.add(new Person(age, name2));
        return people;
    }

    private ArrayList<List<String>> getExpectedTable(List<String> columnNames) {
        ArrayList<List<String>> expectedReportTable = new ArrayList<>();
        expectedReportTable.add(columnNames);
        expectedReportTable.add(Arrays.asList("20", "person1"));
        expectedReportTable.add(Arrays.asList("20", "person2"));
        return expectedReportTable;
    }

    private boolean compareTables(ArrayList<List<String>> expected, ArrayList<List<String>> actual) {
        boolean isTrue = true;
        for (int i = 0; i < expected.size(); i++) {
            for (int j = 0; j < expected.get(0).size(); j++) {
                isTrue = isTrue && expected.get(i).get(j).equals(actual.get(i).get(j));
            }
        }
        return isTrue;
    }

    private ArrayList<List<String>> getActualTable(ReportBuilder<Person> reportBuilder) {
        ArrayList<List<String>> actualReportTable = new ArrayList<>();
        try {
            Field field = reportBuilder.getClass().getDeclaredField("reportTable");
            field.setAccessible(true);
            actualReportTable = (ArrayList<List<String>>) field.get(reportBuilder);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return actualReportTable;
    }

    @Test
    public void verifyReportWithSpecifiedColumnNames() {
        //given
        ArrayList<Person> people = getPeople();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("age", "AGE");
        hashMap.put("name", "NAME");

        ReportBuilder<Person> reportBuilder = new ReportBuilder<>(hashMap);
        reportBuilder.generate(people);

        ArrayList<List<String>> expectedReportTable = getExpectedTable(Arrays.asList("AGE", "NAME"));
        ArrayList<List<String>> actualReportTable = getActualTable(reportBuilder);
        //when
        boolean isTrue = compareTables(expectedReportTable, actualReportTable);
        //then
        assertTrue(isTrue);
    }


}