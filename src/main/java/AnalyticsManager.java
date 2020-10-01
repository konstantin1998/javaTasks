import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeMap;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Account beneficiary = null;
        double amount = 0;
        Collection<Transaction> transactions = transactionManager.findAllTransactionsByAccount(account);
        for (Transaction transaction : transactions) {
            if (transaction.getBeneficiary() != null || transaction.getAmount() > amount) {
                amount = transaction.getAmount();
                beneficiary = transaction.getBeneficiary();
            }
        }
        return beneficiary;
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        // write your code here
        int requiredNumberOfTransactions = 10;
        Collection<Transaction> transactions = transactionManager.findAllTransactionsByAccount(account);
        TreeMap<Double, Transaction> purchases = new TreeMap<Double, Transaction>();
        for(Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount > 0) {
                purchases.put(amount, transaction);
            }
        }
        Transaction[] transactionsSortedByPrise = purchases.descendingMap().values().toArray(new Transaction[0]);
        ArrayList<Transaction> mostExpensivePurchases = new ArrayList<Transaction>();
        mostExpensivePurchases.addAll(Arrays.asList(transactionsSortedByPrise)
                .subList(0, Math.min(requiredNumberOfTransactions, transactionsSortedByPrise.length)));
        return (Collection<Transaction>)mostExpensivePurchases;
    }
}
