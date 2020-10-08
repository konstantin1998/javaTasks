import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleEntitiesStorageTest {
    @Test
    void savePutsItemToStorage(){
        //given
        KeyGenerator keyGenerator = new KeyGenerator();
        SimpleEntitiesStorage<Integer, String> storage = new SimpleEntitiesStorage<>(keyGenerator);
        String value = "Hello world";

        Integer key = keyGenerator.extract(value);
        storage.save(value);
        //when
        String savedValue = storage.findByKey(key);
        //then
        assertEquals(value, savedValue);
    }

    @Test
    void saveAllPutsItemsToStorage(){
        //given
        KeyGenerator keyGenerator = new KeyGenerator();
        SimpleEntitiesStorage<Integer, String> storage = new SimpleEntitiesStorage<>(keyGenerator);
        String[] values = {"str1", "str2", "str3"};
        storage.saveAll(Arrays.asList(values));
        //when
        String[] savedValues = new String[3];
        for (int i = 0; i < values.length; i++) {
            savedValues[i] = storage.findByKey(keyGenerator.extract(values[i]));
        }
        //then
        assertArrayEquals(values, savedValues);
    }

    @Test
    void findAllReturnsItemsFromStorage(){
        //given
        KeyGenerator keyGenerator = new KeyGenerator();
        SimpleEntitiesStorage<Integer, String> storage = new SimpleEntitiesStorage<>(keyGenerator);
        ArrayList<String> values = new ArrayList<>();
        values.add("str1");
        values.add("str2");
        values.add("str3");
        storage.saveAll((List<String>)values);
        List<String> savedValues = storage.findAll();
        //when
        boolean isTrue = true;
        for (String savedValue : savedValues) {
            isTrue = isTrue && values.contains(savedValue);
        }
        isTrue = isTrue && (values.size() == savedValues.size());
        //then
        assertTrue(isTrue);
    }
    @Test
    void findByKeyReturnsValueByGivenKey() {
        //given
        KeyGenerator keyGenerator = new KeyGenerator();
        SimpleEntitiesStorage<Integer, String> storage = new SimpleEntitiesStorage<>(keyGenerator);
        String[] values = {"str1", "str2", "str3"};
        storage.saveAll(Arrays.asList(values));
        //when
        String actualValue = storage.findByKey(keyGenerator.extract(values[0]));
        //then
        assertEquals(values[0], actualValue);
    }
    @Test
    void deleteByKeyDeletesElementCorrespondingToGivenKey() {
        //given
        KeyGenerator keyGenerator = new KeyGenerator();
        SimpleEntitiesStorage<Integer, String> storage = new SimpleEntitiesStorage<>(keyGenerator);
        String value = "Hello world";
        storage.save(value);
        //when
        storage.deleteByKey(keyGenerator.extract(value));
        String savedValue = storage.findByKey(keyGenerator.extract(value));
        //then
        assertNull(savedValue);
    }
    @Test
    void deleteAllDeletesGivenValues() {
        //given
        KeyGenerator keyGenerator = new KeyGenerator();
        SimpleEntitiesStorage<Integer, String> storage = new SimpleEntitiesStorage<>(keyGenerator);
        ArrayList<String> values = new ArrayList<>();
        values.add("str1");
        values.add("str2");
        values.add("str3");
        storage.saveAll((List<String>)values);
        boolean isTrue = true;
        //when
        storage.deleteAll((List<String>)values);
        for (String value : values) {
            isTrue = isTrue && (storage.findByKey(keyGenerator.extract(value)) == null);
        }
        //then
        assertTrue(isTrue);
    }
}
