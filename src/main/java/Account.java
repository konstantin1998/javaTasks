import java.time.LocalDate;

public interface Account {
    double balanceOn(LocalDate date);
    void addEntry(Entry entry);
}
