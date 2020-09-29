import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;
import java.time.*;

public class EntriesTest {
    @Test
    void betweenDatesReturnsEntriesBetweenGivenDates() {
        //given
        int n = 10;
        int[] days = {10, 12, 9, 13, 8, 15, 16, 17, 4, 5};
        int year = 2020;
        Month month = Month.SEPTEMBER;
        int hour = 12;
        int minute = 30;
        int second = 30;
        LocalDateTime[] dates = new LocalDateTime[n];
        for (int i = 0; i < days.length; i++) {
            dates[i] = LocalDateTime.of(year, month, days[i], hour, minute, second);
        }
        Entries entriesObject = new Entries();
        Entry[] entriesArray = new Entry[n];
        for (int i = 0; i < entriesArray.length; i++) {
            entriesArray[i] = new Entry(null, null, 0, dates[i]);
            entriesObject.addEntry(entriesArray[i]);
        }
        //when
        Collection<Entry> selectedEntries = entriesObject.betweenDates(
                LocalDate.of(year, month, 12),
                LocalDate.of(year, month, 16));
        //then
        assertArrayEquals(
                new Entry[] {entriesArray[1], entriesArray[3], entriesArray[5],entriesArray[6]},
                selectedEntries.toArray());
    }
    @Test
    void fromReturnsEntriesAfterGivenDate() {
        //given
        int n = 10;
        int[] days = {10, 12, 9, 13, 8, 15, 16, 17, 4, 5};
        int year = 2020;
        Month month = Month.SEPTEMBER;
        int hour = 12;
        int minute = 30;
        int second = 30;
        LocalDateTime[] dates = new LocalDateTime[n];
        for (int i = 0; i < days.length; i++) {
            dates[i] = LocalDateTime.of(year, month, days[i], hour, minute, second);
        }
        Entries entriesObject = new Entries();
        Entry[] entriesArray = new Entry[n];
        for (int i = 0; i < entriesArray.length; i++) {
            entriesArray[i] = new Entry(null, null, 0, dates[i]);
            entriesObject.addEntry(entriesArray[i]);
        }
        //when
        Collection<Entry> selectedEntries = entriesObject.from(LocalDate.of(year, month, 12));
        //then
        assertArrayEquals(
                new Entry[] {entriesArray[1], entriesArray[3], entriesArray[5], entriesArray[6], entriesArray[7]},
                selectedEntries.toArray());
    }
    @Test
    void lastReturnsLastEntry() {
        //given
        int year = 2020;
        Month month = Month.SEPTEMBER;
        int day = 28;
        int hour = 12;
        int minute = 30;
        int second = 30;
        LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute, second);
        Entry entry = new Entry(null, null, 0, date);
        Entries entries = new Entries();
        entries.addEntry(entry);
        //when
        Entry lastEntry = entries.last();
        //then
        assertEquals(entry, lastEntry);
    }
}

