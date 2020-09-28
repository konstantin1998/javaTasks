import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

public class EntryTest {
    @Test
    void betweenDatesReturnsEntriesBetweenGivenDates() {
        //given
        int n = 10;
        int[] days = {10, 12, 9, 13, 8, 15, 16, 17, 4, 5};
        int year = 2020;
        int month = 9;
        int hour = 12;
        int minute = 30;
        LocalDateTime[] dates = new LocalDateTime[n];
        for (int i = 0; i < days.length; i++) {
            dates[i] = new LocalDateTime(new LocalDate(year, month, days[i]), hour, minute);
        }
        Entries entriesObject = new Entries();
        Entry[] entriesArray = new Entry[n];
        for (int i = 0; i < entriesArray.length; i++) {
            entriesArray[i] = new Entry(null, null, 0, dates[i]);
            entriesObject.addEntry(entriesArray[i]);
        }
        //when
        Collection<Entry> selectedEntries = entriesObject.betweenDates(
                new LocalDate(year, month, 12),
                new LocalDate(year, month, 16));
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
        int month = 9;
        int hour = 12;
        int minute = 30;
        LocalDateTime[] dates = new LocalDateTime[n];
        for (int i = 0; i < days.length; i++) {
            dates[i] = new LocalDateTime(new LocalDate(year, month, days[i]), hour, minute);
        }
        Entries entriesObject = new Entries();
        Entry[] entriesArray = new Entry[n];
        for (int i = 0; i < entriesArray.length; i++) {
            entriesArray[i] = new Entry(null, null, 0, dates[i]);
            entriesObject.addEntry(entriesArray[i]);
        }
        //when
        Collection<Entry> selectedEntries = entriesObject.from(new LocalDate(year, month, 12));
        //then
        assertArrayEquals(
                new Entry[] {entriesArray[1], entriesArray[3], entriesArray[5], entriesArray[6], entriesArray[7]},
                selectedEntries.toArray());
    }
    @Test
    void lastReturnsLastEntry() {
        //given
        int year = 2020;
        int month = 9;
        int day = 28;
        int hour = 12;
        int minute = 30;
        LocalDateTime date = new LocalDateTime(new LocalDate(year, month, day), hour, minute);
        Entry entry = new Entry(null, null, 0, date);
        Entries entries = new Entries();
        entries.addEntry(entry);
        //when
        Entry lastEntry = entries.last();
        //then
        assertEquals(entry, lastEntry);
    }
}

