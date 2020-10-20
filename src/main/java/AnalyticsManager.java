import bsh.servlet.SimpleTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Collection<Transaction> transactions = transactionManager.findAllTransactionsByAccount(account);

        HashMap<Account, Integer> accountsAndFrequencies = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getBeneficiary() != null && transaction.getAmount() > 0) {
                Account beneficiary = transaction.getBeneficiary();
                if (!accountsAndFrequencies.containsKey(beneficiary)) {
                    accountsAndFrequencies.put(beneficiary, 1);
                } else {
                    int oldFrequency = accountsAndFrequencies.get(beneficiary);
                    accountsAndFrequencies.put(beneficiary, oldFrequency + 1);
                }

            }
        }

        return accountsAndFrequencies.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {

        int requiredNumberOfTransactions = 10;
        Collection<Transaction> transactions = transactionManager.findAllTransactionsByAccount(account);

        TreeMap<Double, Transaction> purchases = new TreeMap<Double, Transaction>();
        for(Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount > 0) {
                purchases.put(amount, transaction);
            }
        }

        return purchases
                .entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    double diff = o1.getKey() - o2.getKey();
                    if (diff > 0) {
                        return -1;
                    }
                    if (diff == 0) {
                        return 0;
                    }
                    return 1; })
                .map(Map.Entry::getValue)
                .collect(Collectors.toList())
                .subList(0, Math.min(requiredNumberOfTransactions, purchases.entrySet().size()));
    }

    public double overallBalanceOfAccounts(List<? extends Account> accounts) {

        return accounts
                .stream()
                .mapToDouble((item) -> item.balanceOn(LocalDate.now()))
                .sum();
    }

    public<K> Set<K> uniqueKeysOf(List<? extends Account> accounts, KeyExtractor<? extends K, ? super Account> keyExtractor) {

        return accounts
                .stream()
                .map(keyExtractor::extract)
                .collect(Collectors.toSet());
    }

    public List<Account> accountsRangeFrom(List<? extends Account> accounts, Account minAccount, Comparator<? super Account> comparator) {
        ArrayList<Account> accs = accounts.stream().sorted(comparator).collect(Collectors.toCollection(ArrayList::new));
        int lastIndex = accs.lastIndexOf(minAccount);
        return accs.stream().skip(Math.max(lastIndex, 0)).collect(Collectors.toList());
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<Account> accounts, LocalDate from, LocalDate to) {
        Function<Collection<Entry>, Stream<Entry>> apply = Collection::stream;
        return accounts
                .stream()
                .map((account) -> account.history(from, to))
                .flatMap(apply)
                .filter((item) -> item.getAmount() < 0)
                .min(Comparator.comparingDouble(Entry::getAmount));

    }

}
