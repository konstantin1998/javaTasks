import java.time.LocalDate;
import java.time.Month;

public class BonusAccount implements Account{
    private final double rate;
    private final Entries entries;
    public BonusAccount(double rate) {
        this.rate = rate;
        this.entries = new Entries();
    }

    @Override
    public double balanceOn(LocalDate date) {
        double balance = 0;
        int minimalPossibleYear = -999999999;
        LocalDate beginningOfTimes = LocalDate.of(minimalPossibleYear, Month.JANUARY, 1);
        for (Entry entry : entries.betweenDates(beginningOfTimes, date).toArray(new Entry[0])) {
            balance += entry.getAmount() * rate;
        }
        return balance;
    }

    @Override
    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }
}
