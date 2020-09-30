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
        TransactionManager transactionManager = new TransactionManager();
        Account beneficiary = new Account(accountId, transactionManager);
        Account originator1 = new Account(accountId, transactionManager);
        Account originator2 = new Account(accountId, transactionManager);
        Account originator3 = new Account(accountId, transactionManager);

        int k = 5;
        int l = 10;
        int n = 15;

        for (int i = 0; i <= k - 1; i++) {
            transactionManager.executeTransaction(transactionManager.createTransaction(amount, originator1, beneficiary));
        }
        for (int i = 0; i <= l - 1; i++) {
            transactionManager.executeTransaction(transactionManager.createTransaction(amount, originator2, beneficiary));
        }
        for (int i = 0; i <= n - 1; i++) {
            transactionManager.executeTransaction(transactionManager.createTransaction(amount, originator3, beneficiary));
        }

        Collection<Transaction> transactions1 = transactionManager.findAllTransactionsByAccount(originator1);
        Collection<Transaction> transactions2 = transactionManager.findAllTransactionsByAccount(originator2);
        Collection<Transaction> transactions3 = transactionManager.findAllTransactionsByAccount(originator3);
        //when
        int[] actualNumbersOfTransactions = {transactions1.toArray().length,
                transactions2.toArray().length, transactions3.toArray().length};
        int[] expectedNumbersOfTransactions = {k, l, n};
        //then
        assertArrayEquals(expectedNumbersOfTransactions, actualNumbersOfTransactions);
    }
}
