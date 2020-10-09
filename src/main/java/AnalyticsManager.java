import java.time.LocalDate;
import java.util.*;

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
        //System.out.println("Total transactions:" + transactions.size());
        TreeMap<Double, Transaction> purchases = new TreeMap<Double, Transaction>();
        for(Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount > 0) {
                purchases.put(amount, transaction);
            }
        }
        //System.out.println("Positive transactions:" + purchases.size());
        Transaction[] transactionsSortedByPrise = purchases.descendingMap().values().toArray(new Transaction[0]);
        ArrayList<Transaction> mostExpensivePurchases = new ArrayList<Transaction>();
        mostExpensivePurchases.addAll(Arrays.asList(transactionsSortedByPrise)
                .subList(0, Math.min(requiredNumberOfTransactions, transactionsSortedByPrise.length)));
        //System.out.println("Returned transactions:" + mostExpensivePurchases.size());
        return (Collection<Transaction>)mostExpensivePurchases;
    }

    public double overallBalanceOfAccounts(List<? extends Account> accounts) {
        double balance = 0;
        for (Account account : accounts) {
            balance += account.balanceOn(LocalDate.now());
        }
        return balance;
    }

    public<K> Set<K> uniqueKeysOf(List<? extends Account> accounts, KeyExtractor<? extends K, ? super Account> keyExtractor) {
        Set<K> set = new HashSet<>();
        for (Account account : accounts) {
            set.add(keyExtractor.extract(account));
        }
        return set;
    }

    public List<Account> accountsRangeFrom(List<? extends Account> accounts, Account minAccount, Comparator<? super Account> comparator) {
        ArrayList<Account> accs = new ArrayList<>(accounts);
        accs.sort(comparator);
        int lastIndex = accs.lastIndexOf(minAccount);
        return accs.subList(Math.max(0, lastIndex), accs.size());
    }

}
