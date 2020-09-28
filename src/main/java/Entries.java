import java.util.Collection;
import java.util.TreeMap;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {
    private final TreeMap<LocalDateTime, Entry> entries;
    Entries() {
        entries = new TreeMap<LocalDateTime, Entry>(LocalDateTime::compareTo);
    }
    void addEntry(Entry entry) {
        entries.put(entry.getTime(), entry);
    }
    Collection<Entry> from(LocalDate date) {
        // write your code here
        int hour = 0;
        int minute = 0;
        LocalDateTime since = new LocalDateTime(date, hour, minute);
        return entries.tailMap(since, true).values();
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        // write your code here
        int fromHour = 0;
        int fromMinute = 0;
        int toHour = 23;
        int toMinute = 59;
        LocalDateTime leftTimeBorder = new LocalDateTime(from, fromHour, fromMinute);
        LocalDateTime rightTimeBorder = new LocalDateTime(to, toHour, toMinute);
        return entries.subMap(leftTimeBorder, true, rightTimeBorder, true).values();
    }

    Entry last() {
        // write your code here
        return entries.get(entries.lastKey());
    }
}

