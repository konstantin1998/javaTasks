import java.util.*;


public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Map<Account, Integer> accountsAndFrequencies = new HashMap<>();
        Collection<Transaction> transactions = transactionManager.findAllTransactionsByAccount(account);

        for (Transaction transaction : transactions) {
            if (transaction.getBeneficiary() != null || transaction.getAmount() > 0) {
                Account acc = transaction.getBeneficiary();
                if (accountsAndFrequencies.containsKey(acc)) {
                    Integer frequency = accountsAndFrequencies.get(acc);
                    accountsAndFrequencies.put(acc, frequency + 1);
                } else {
                    accountsAndFrequencies.put(acc, 1);
                }

            }
        }

        return findMostFrequentAccount(accountsAndFrequencies.entrySet());
    }

    private Account findMostFrequentAccount(Collection<Map.Entry<Account, Integer>> accountsAndFrequencies) {
        Account beneficiary = null;
        Integer maxFrequency = -1;

        for (Map.Entry<Account, Integer> entry : accountsAndFrequencies) {
            Account acc = entry.getKey();
            Integer frequency = entry.getValue();
            if (frequency > maxFrequency) {
                beneficiary = acc;
                maxFrequency = frequency;
            }
        }
        return beneficiary;
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        // write your code here
        int requiredNumberOfTransactions = 10;
        Transaction[] transactions = transactionManager
                .findAllTransactionsByAccount(account)
                .stream()
                .filter((t) -> t.getAmount() > 0)
                .toArray(Transaction[]::new);
        if (transactions.length <= requiredNumberOfTransactions) {
            return Arrays.asList(transactions);
        }
        sortTransactions(transactions);
        return Arrays.asList(transactions).subList(0, requiredNumberOfTransactions);

    }

    private void sortTransactions(Transaction[] transactions) {
        class MyComparator implements Comparator<Transaction> {

            @Override
            public int compare(Transaction o1, Transaction o2) {
                double epsilon = 0.001;
                if (o1.getAmount() - o2.getAmount() > -1 * epsilon && o1.getAmount() - o2.getAmount() < epsilon) {
                    return 0;
                }
                return Double.compare(o2.getAmount(), o1.getAmount());
            }
        }
        Arrays.sort(transactions, new MyComparator());
    }
}
