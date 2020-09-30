import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.*;

public class AccountTest {
    @Test
    void addReturnsTrueIfAmountIsPositive() {
        //given
        long id = 0;
        double amount = 10;
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(id, transactionManager);
        //when
        boolean isTrue = acc.add(amount);
        //then
        assertTrue(isTrue);
    }
    @Test
    void addReturnsFalseIfAmountIsNegative() {
        //given
        long id = 0;
        double amount = -10;
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(id, transactionManager);
        //when
        boolean isFalse = acc.add(amount);
        //then
        assertFalse(isFalse);
    }
    @Test
    void balanceOnReturnsBalanceOnDate() throws InterruptedException {
        //given
        long id = 0;
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(id, transactionManager);
        double amount = 10;
        acc.addCash(amount);
        Thread.sleep(100);
        acc.add(amount);
        Thread.sleep(100);
        acc.rollbackLastTransaction();
        Thread.sleep(100);
        acc.addCash(amount);
        Thread.sleep(100);
        //when
        double expectedBalance = 20;
        double actualBalance = acc.balanceOn(LocalDate.now());
        //then
        assertEquals(expectedBalance, actualBalance);
    }
    @Test
    void withdrawReturnsTrueIfAmountIsPositiveAndAvailable() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(0, transactionManager);
        Account beneficiary = new Account(1, transactionManager);
        double amount = 100;
        originator.add(amount);
        //when
        boolean isTrue = originator.withdraw(amount / 2, beneficiary);
        //then
        assertTrue(isTrue);
    }
    @Test
    void withdrawReturnsFalseIfAmountIsNegativeOrUnavailable() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(0, transactionManager);
        Account beneficiary = new Account(1, transactionManager);
        double amount = 100;
        originator.add(amount);
        //when
        boolean isFalse = originator.withdraw(amount * 2, beneficiary);
        //then
        assertFalse(isFalse);
    }
    @Test
    void withdrawCashReturnsTrueIfAmountIsPositiveAndAvailable() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(0, transactionManager);
        double amount = 100;
        acc.add(amount);
        //when
        boolean isTrue = acc.withdrawCash(amount / 2);
        //then
        assertTrue(isTrue);
    }
    @Test
    void withdrawCashReturnsFalseIfAmountIsNegativeOrUnavailable() {
        //given
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(0, transactionManager);
        double amount = 100;
        //when
        boolean isFalse = acc.withdrawCash(amount);
        //then
        assertFalse(isFalse);
    }
    @Test
    void addCashReturnsTrueIfAmountIsPositive() {
        //given
        long id = 0;
        double amount = 10;
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(id, transactionManager);
        //when
        boolean isTrue = acc.addCash(amount);
        assertTrue(isTrue);
    }
    @Test
    void addCashReturnsFalseIfAmountIsNegative() {
        //given
        long id = 0;
        double amount = -10;
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(id, transactionManager);
        //when
        boolean isFalse = acc.addCash(amount);
        //then
        assertFalse(isFalse);
    }
    @Test
    void rollbackLastTransactionCanselsLastTransaction() throws InterruptedException {
        //given
        long id = 0;
        double amount = 10;
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(id, transactionManager);
        acc.add(amount);
        Thread.sleep(100);
        acc.rollbackLastTransaction();
        //when
        double currentBalance = acc.balanceOn(LocalDate.now());
        double expectedBalance = 0.0;
        //then
        assertEquals(expectedBalance, currentBalance);
    }
    @Test
    void historyReturnsOperationsPerformedBetweenGivenDates() throws InterruptedException {
        //given
        long id = 0;
        double amount = 10;
        TransactionManager transactionManager = new TransactionManager();
        Account acc = new Account(id, transactionManager);
        acc.add(amount);
        Thread.sleep(100);
        acc.add(amount);
        Thread.sleep(100);
        acc.addCash(amount);
        Thread.sleep(100);
        acc.rollbackLastTransaction();
        Thread.sleep(100);
        acc.withdrawCash(amount);
        Thread.sleep(100);
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);
        //when
        double expectedNumberOfEntries = 5;
        double actualNumberOfEntries = acc.history(yesterday, tomorrow).toArray().length;
        //then
        assertEquals(expectedNumberOfEntries, actualNumberOfEntries);
    }
}
