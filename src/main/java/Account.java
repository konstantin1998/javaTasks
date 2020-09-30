import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;

    public Account(long id, TransactionManager transactionManager) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.entries = new Entries();
    }

    public boolean withdraw(double amount, Account beneficiary) {
        double currentBalance = balanceOn(LocalDate.now());
        if (amount > 0 && currentBalance - amount >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, beneficiary);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    public boolean add(double amount) {
        // write your code here
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(-1 * amount,this, null);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    public boolean withdrawCash(double amount) {
        // write your code here
        double currentBalance = balanceOn(LocalDate.now());
        if (amount > 0 && currentBalance - amount >= 0) {
            Transaction transaction = transactionManager.createTransaction(amount, this, null);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    public boolean addCash(double amount) {
        // write your code here
        if (amount > 0) {
            Transaction transaction = transactionManager.createTransaction(-1 * amount, this, null);
            transactionManager.executeTransaction(transaction);
            return true;
        }
        return false;
    }

    public double balanceOn(LocalDate date) {
        double balance = 0;
        int minimalPossibleInt = -2147483648;
        LocalDate beginningOfTimes = LocalDate.of(minimalPossibleInt, Month.JANUARY, 0);
        for (Entry entry : (Entry[]) entries.betweenDates(beginningOfTimes, date).toArray()) {
            balance += entry.getAmount();
        }
        return balance;
    }

    public Collection<Entry> history(LocalDate from, LocalDate to) {
        // write your code here
        return entries.betweenDates(from, to);
    }

    public void rollbackLastTransaction() {
        // write your code here
        Transaction[] transactions = (Transaction[]) transactionManager.findAllTransactionsByAccount(this).toArray();
        Transaction lastTransaction = transactions[transactions.length - 1];
        transactionManager.rollbackTransaction(lastTransaction);
    }

    void addEntry(Entry entry) {
        entries.addEntry(entry);
    }
}
