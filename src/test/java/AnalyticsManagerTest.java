import org.testng.annotations.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticsManagerTest {
    @Test
    void topTenExpensivePurchasesReturnsTransactionsWithHighestAmount() {
        //given
        long id = 0;
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard acc = new DebitCard(id, transactionManager);
        ArrayList<Double> prises = new ArrayList<>(Arrays.asList(2.0, 3.0, 6.0, 10.0, 15.0, 11.0, 20.0, 9.0, 14.0, 17.0, 19.0, 3.0));
        prepareCard(acc, prises);

        Collection<Transaction> mostExpensivePurchases = analyticsManager.topTenExpensivePurchases(acc);
        //when
        int requiredNumberOfTransactions = 10;
        ArrayList<Double> actualPrises = getActualPrises(mostExpensivePurchases);
        prises.sort((o1, o2) -> (int) (o2 - o1));

        ArrayList<Double> expectedPrises = new ArrayList<>(prises.subList(0, requiredNumberOfTransactions));
        //then
        assertArrayEquals(expectedPrises.toArray(new Double[0]), actualPrises.toArray(new Double[0]));
    }

    private void prepareCard(DebitCard card, Collection<Double> prises) {
        double amount = 150;
        card.add(amount);
        for (double prise : prises) {
            card.withdrawCash(prise);
        }
    }

    private ArrayList<Double> getActualPrises(Collection<Transaction> transactions) {
        ArrayList<Double> actualPrises = new ArrayList<>();
        for (Transaction transaction: transactions) {
            actualPrises.add(transaction.getAmount());
        }
        return actualPrises;
    }

    @Test
    void mostFrequentBeneficiaryOfAccountReturnsMostFrequentBeneficiary() {
        //given
        long id = 0;
        TransactionManager transactionManager = new TransactionManager();
        DebitCard acc = new DebitCard(id, transactionManager);
        double amount = 150;
        acc.add(amount);
        Account beneficiary1 = new DebitCard(id, transactionManager);
        Account beneficiary2 = new DebitCard(id, transactionManager);
        Account beneficiary3 = new DebitCard(id, transactionManager);
        int numberOfTransactions1 = 5;
        int numberOfTransactions2 = 10;
        int numberOfTransactions3 = 15;

        withdraw(acc, beneficiary1, numberOfTransactions1);
        withdraw(acc, beneficiary2, numberOfTransactions2);
        withdraw(acc, beneficiary3, numberOfTransactions3);
        //when
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account mostFrequentBeneficiary = analyticsManager.mostFrequentBeneficiaryOfAccount(acc);
        //then
        assertEquals(beneficiary3, mostFrequentBeneficiary);
    }

    private void withdraw(DebitCard acc, Account beneficiary, int numberOfTransactions) {
        double moneyToWithdraw = 1;
        for (int i = 0; i < numberOfTransactions; i++) {
            acc.withdraw(moneyToWithdraw, beneficiary);
        }
    }

    @Test
    void overallBalanceOfAccountsReturnsSumOfBalancesOfGivenAccounts() {
        //given
        long id = 0;
        TransactionManager transactionManager = new TransactionManager();
        ArrayList<DebitCard> debitCards = new ArrayList<>();
        int numberOfAccounts = 5;
        double amount = 150;
        for (int i = 0; i < numberOfAccounts; i++) {
            DebitCard card = new DebitCard(id, transactionManager);
            card.add(amount);
            debitCards.add(card);
            id++;
        }
        //when
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        double totalBalance = analyticsManager.overallBalanceOfAccounts(debitCards);
        //then
        assertEquals(numberOfAccounts * amount, totalBalance);
    }

    @Test
    void uniqueKeysOfReturnsUniqueKeys() {
        //given
        long id = 0;
        IntegerAccountKeyExtractor keyGenerator = new IntegerAccountKeyExtractor();
        TransactionManager transactionManager = new TransactionManager();
        ArrayList<DebitCard> debitCards = new ArrayList<>();
        int numberOfAccounts = 5;
        ArrayList<Integer> expectedKeys = new ArrayList<>();
        for (int i = 0; i < numberOfAccounts; i++) {
            DebitCard card = new DebitCard(id, transactionManager);
            debitCards.add(card);
            debitCards.add(card);
            expectedKeys.add(card.hashCode());
            id++;
        }
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        //when
        ArrayList<Integer> keys = new ArrayList<>(analyticsManager.uniqueKeysOf(debitCards, keyGenerator));
        expectedKeys.sort((Integer::compareTo));
        keys.sort((Integer::compareTo));
        //then
        assertEquals(expectedKeys, keys);
    }


    @Test
    void accountsRangeFromReturnsSortedListOfAccounts() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        ArrayList<DebitCard> debitCards = getCards(transactionManager);

        Comparator<Account> comparator = Comparator.comparingInt(Object::hashCode);
        debitCards.sort(comparator);
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        //when
        ArrayList<Account> actualAccounts = new ArrayList<>(
                analyticsManager.accountsRangeFrom(debitCards, debitCards.get(0), comparator)
        );
        ArrayList<Account> expectedAccounts = new ArrayList<>(debitCards);
        //then
        assertEquals(expectedAccounts, actualAccounts);
    }

    private ArrayList<DebitCard> getCards(TransactionManager transactionManager) {
        long id = 0;
        ArrayList<DebitCard> debitCards = new ArrayList<>();
        int numberOfAccounts = 5;
        for (int i = 0; i < numberOfAccounts; i++) {
            DebitCard card = new DebitCard(id, transactionManager);
            debitCards.add(card);
            id++;
        }
        return debitCards;
    }
}
