import java.util.Collection;
import java.util.TreeMap;
import java.time.*;

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
        LocalTime localTime = LocalTime.of(0,0,0,0);
        LocalDateTime since = LocalDateTime.of(date, localTime);
        return entries.tailMap(since, true).values();
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        // write your code here
        LocalTime beginningOfDay = LocalTime.of(0, 0, 0, 0);
        LocalTime endingOfDay = LocalTime.of(23, 59, 59, 999999999);
        LocalDateTime leftTimeBorder = LocalDateTime.of(from, beginningOfDay);
        LocalDateTime rightTimeBorder = LocalDateTime.of(to, endingOfDay);
        return entries.subMap(leftTimeBorder, true, rightTimeBorder, true).values();
    }

    Entry last() {
        // write your code here
        return entries.get(entries.lastKey());
    }
}

