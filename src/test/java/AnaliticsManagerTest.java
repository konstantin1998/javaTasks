import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AnaliticsManagerTest {
    @Test
    void topTenExpensivePurchasesReturnsTransactionsWithHighestAmount() {
        //given
        long id = 0;
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        DebitCard acc = new DebitCard(id, transactionManager);
        double amount = 150;
        acc.add(amount);
        Double[] prises = {2.0, 3.0, 6.0, 10.0, 15.0, 11.0, 20.0, 9.0, 14.0, 17.0, 19.0, 3.0};
        for (double prise : prises) {
            acc.withdrawCash(prise);
        }
        Transaction[] mostExpensivePurchases = analyticsManager.topTenExpensivePurchases(acc).toArray(new Transaction[0]);
        //when
        int requiredNumberOfTransactions = 10;
        Double[] actualPrises = new Double[requiredNumberOfTransactions];
        for (int i = 0; i <= requiredNumberOfTransactions - 1; i++) {
            actualPrises[i] = mostExpensivePurchases[i].getAmount();
        }
        Arrays.sort(prises, Collections.reverseOrder());
        Double[] expectedPrises = new Double[requiredNumberOfTransactions];
        System.arraycopy(prises, 0, expectedPrises, 0, requiredNumberOfTransactions);
        //then
        assertArrayEquals(expectedPrises, actualPrises);
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
        double moneyToWithdraw = 1;
        for (int i = 0; i < numberOfTransactions1; i++) {
            acc.withdraw(moneyToWithdraw, beneficiary1);
        }
        acc.withdrawCash(moneyToWithdraw);
        for (int i = 0; i < numberOfTransactions2; i++) {
            acc.withdraw(moneyToWithdraw, beneficiary2);
        }
        acc.withdrawCash(moneyToWithdraw);
        for (int i = 0; i < numberOfTransactions3; i++) {
            acc.withdraw(moneyToWithdraw, beneficiary3);
        }
        acc.withdrawCash(moneyToWithdraw);
        //when
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account mostFrequentBeneficiary = analyticsManager.mostFrequentBeneficiaryOfAccount(acc);
        //then
        assertEquals(beneficiary3, mostFrequentBeneficiary);
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
        ArrayList<Integer> keys = new ArrayList<>((Collection<Integer>) analyticsManager.uniqueKeysOf(debitCards, keyGenerator));
        expectedKeys.sort((Integer::compareTo));
        keys.sort((Integer::compareTo));
        keys.toArray();
        //then
        assertEquals(expectedKeys, keys);
    }

    @Test
    void accountsRangeFromReturnsSortedListOfAccounts() {
        //given
        long id = 0;
        TransactionManager transactionManager = new TransactionManager();
        ArrayList<DebitCard> debitCards = new ArrayList<>();
        int numberOfAccounts = 5;
        for (int i = 0; i < numberOfAccounts; i++) {
            DebitCard card = new DebitCard(id, transactionManager);
            debitCards.add(card);
            id++;
        }
        Comparator<Account> comparator = Comparator.comparingInt(Object::hashCode);
        debitCards.sort(comparator);
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        //when
        ArrayList<Account> actualAccounts = new ArrayList<>(
                (Collection<Account>) analyticsManager.accountsRangeFrom(debitCards, debitCards.get(0), comparator)
        );
        ArrayList<Account> expectedAccounts = new ArrayList<>();
        for (int i = 0; i < numberOfAccounts; i++) {
            expectedAccounts.add((Account)debitCards.get(i));
        }
        //then
        assertEquals(expectedAccounts, actualAccounts);
    }

    private void withdraw(DebitCard originator, DebitCard beneficiary, double[] amountsToWithdraw) {
        for (double amountToWithdraw : amountsToWithdraw) {
            originator.withdraw(amountToWithdraw, beneficiary);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void maxExpenseReturnsEntryWithLargestAmount() {
        //given
        long id = 1;
        TransactionManager transactionManager = new TransactionManager();
        DebitCard beneficiary = new DebitCard(id, transactionManager);
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);

        double amount = 100;
        double[] amountsToWithdraw = new double[]{1.0, 5.0, 10.0, 15.0};

        DebitCard originator1 = new DebitCard(id, transactionManager);
        originator1.addCash(amount);
        withdraw(originator1, beneficiary, amountsToWithdraw);

        DebitCard originator2 = new DebitCard(id, transactionManager);
        originator1.addCash(amount);
        withdraw(originator2, beneficiary, amountsToWithdraw);

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        //when
        Entry mostExpensiveEntry = analyticsManager
                .maxExpenseAmountEntryWithinInterval(
                        Arrays.asList(originator1, originator2),
                        yesterday,
                        today).get();
        //then
        assertEquals(amountsToWithdraw[amountsToWithdraw.length - 1], -1 * mostExpensiveEntry.getAmount());
    }
}
