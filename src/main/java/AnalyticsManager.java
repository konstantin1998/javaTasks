import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;


public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {

        HashMap<Account, Integer> emptyMap = new HashMap<>();

        BiFunction<HashMap<Account, Integer>, Transaction, HashMap<Account, Integer>> accumulator =
                (HashMap<Account, Integer> accountsAndFrequencies, Transaction transaction) -> {
                    if (transaction.getBeneficiary() != null && transaction.getAmount() > 0) {
                        Account beneficiary = transaction.getBeneficiary();
                        if (!accountsAndFrequencies.containsKey(beneficiary)) {
                            accountsAndFrequencies.put(beneficiary, 1);
                        } else {
                            int oldFrequency = accountsAndFrequencies.get(beneficiary);
                            accountsAndFrequencies.put(beneficiary, oldFrequency + 1);
                        }

                    }
                    return accountsAndFrequencies;
                };

        BinaryOperator<HashMap<Account, Integer>> combiner =
                (HashMap<Account, Integer> dst, HashMap<Account, Integer> src) -> {
                    src.entrySet().stream().peek((entry) -> {
                        dst.put(entry.getKey(), entry.getValue());
                    });
                    return  dst;
                };

        return transactionManager
                .findAllTransactionsByAccount(account)
                .stream()
                .reduce(emptyMap, accumulator, combiner)
                .entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .get().getKey();
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        TreeMap<Double, Transaction> emptyMap = new TreeMap<>();

        BiFunction<TreeMap<Double, Transaction>, Transaction, TreeMap<Double, Transaction>> accumulator =
                (TreeMap<Double, Transaction> purchases, Transaction transaction) -> {
                    double amount = transaction.getAmount();
                    if (amount > 0) {
                        purchases.put(amount, transaction);
                    }
                    return purchases;
                };

        BinaryOperator<TreeMap<Double, Transaction>> combiner =
                (TreeMap<Double, Transaction> dst, TreeMap<Double, Transaction> src) -> {
                    src.entrySet().stream().peek((entry) -> {
                        dst.put(entry.getKey(), entry.getValue());
                    });
                    return  dst;
                };

        int requiredNumberOfTransactions = 10;

        TreeMap<Double, Transaction> goods = transactionManager
                .findAllTransactionsByAccount(account)
                .stream()
                .reduce(emptyMap, accumulator, combiner);

        return goods
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
                .subList(0, Math.min(requiredNumberOfTransactions, goods.entrySet().size()));
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

        return accounts
                .stream()
                .filter((acc) -> comparator.compare(acc, minAccount) >= 0)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<Account> accounts, LocalDate from, LocalDate to) {

        return accounts
                .stream()
                .filter(Objects::nonNull)
                .map((account) -> account.history(from, to))
                .flatMap(Collection::stream)
                .filter((item) -> item.getAmount() < 0)
                .min(Comparator.comparingDouble(Entry::getAmount));
    }
}
