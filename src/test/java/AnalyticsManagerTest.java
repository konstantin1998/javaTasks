import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyticsManagerTest {
    @Test
    void topTenExpensivePurchasesReturnsTransactionsWithHighestAmount() {
        //given
        long id = 0;
        TransactionManager transactionManager = new TransactionManager();
        AnalyticsManager analyticsManager = new AnalyticsManager(transactionManager);
        Account acc = new Account(id, transactionManager);
        double amount = 150;
        acc.add(amount);
        Double[] prises = {2.0, 3.0, 6.0, 10.0, 15.0, 11.0, 20.0, 9.0, 14.0, 17.0, 19.0, 3.0};
        for (double prise : prises) {
            acc.withdrawCash(prise);
        }
        Transaction[] mostExpensivePurchases = analyticsManager.topTenExpensivePurchases(acc).toArray( new Transaction[0]);
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
        Account acc = new Account(id, transactionManager);
        double amount = 150;
        acc.add(amount);
        Account beneficiary1 = new Account(id, transactionManager);
        Account beneficiary2 = new Account(id, transactionManager);
        Account beneficiary3 = new Account(id, transactionManager);
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
}
