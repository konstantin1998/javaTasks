import java.time.LocalDate;
import java.util.Collection;

public interface Account {
    double balanceOn(LocalDate date);

    void addEntry(Entry entry);

    Collection<Entry> history(LocalDate from, LocalDate to);
}
