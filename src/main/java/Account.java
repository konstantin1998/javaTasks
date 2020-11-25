import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        Collection<Entry> selectedEntries = entries.entriesUpToDate(date);
        for (Entry entry : selectedEntries) {
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
        List<Transaction> transactions = new ArrayList<>(transactionManager.findAllTransactionsByAccount(this));
        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        transactionManager.rollbackTransaction(lastTransaction);
    }

    void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    public Entry getLastEntry() {
        return entries.last();
    }
}
