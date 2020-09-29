import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;
import java.time.*;

public class TransactionManagerTest {
    @Test
    void findAllTransactionsByAccountReturnsTransactionsCorrespondingToGivenAccount() {
        //given
        double amount = 0;
        long accountId = 0;
        Account originator = new Account(accountId, new TransactionManager());
        Account beneficiary1 = new Account(accountId, new TransactionManager());
        Account beneficiary2 = new Account(accountId, new TransactionManager());
        Account beneficiary3 = new Account(accountId, new TransactionManager());

        int k = 5;
        int l = 10;
        int n = 15;

        TransactionManager transactionManager = new TransactionManager();

        for (int i = 0; i <= k - 1; i++) {
            transactionManager.executeTransaction(transactionManager.createTransaction(amount, originator, beneficiary1));
        }
        for (int i = 0; i <= l - 1; i++) {
            transactionManager.executeTransaction(transactionManager.createTransaction(amount, originator, beneficiary2));
        }
        for (int i = 0; i <= n - 1; i++) {
            transactionManager.executeTransaction(transactionManager.createTransaction(amount, originator, beneficiary3));
        }

        Collection<Transaction> transactions1 = transactionManager.findAllTransactionsByAccount(beneficiary1);
        Collection<Transaction> transactions2 = transactionManager.findAllTransactionsByAccount(beneficiary2);
        Collection<Transaction> transactions3 = transactionManager.findAllTransactionsByAccount(beneficiary3);
        //when
        int[] actualNumbersOfTransactions = {transactions1.toArray().length,
                transactions2.toArray().length, transactions3.toArray().length};
        int[] expectedNumbersOfTransactions = {k, l, n};
        //then
        assertArrayEquals(expectedNumbersOfTransactions, actualNumbersOfTransactions);
    }
}
