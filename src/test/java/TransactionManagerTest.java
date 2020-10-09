import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

public class TransactionManagerTest {
    @Test
    void findAllTransactionsByAccountReturnsTransactionsCorrespondingToGivenAccount() {
        //given
        double amount = 0;
        long accountId = 0;
        TransactionManager transactionManager = new TransactionManager();
        DebitCard beneficiary = new DebitCard(accountId, transactionManager);
        DebitCard originator1 = new DebitCard(accountId, transactionManager);
        DebitCard originator2 = new DebitCard(accountId, transactionManager);
        DebitCard originator3 = new DebitCard(accountId, transactionManager);

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
