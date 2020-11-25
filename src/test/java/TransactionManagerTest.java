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

        int k = 5;
        for (int i = 0; i <= k - 1; i++) {
            transactionManager.executeTransaction(transactionManager.createTransaction(amount, originator1, beneficiary));
        }

        Collection<Transaction> transactions1 = transactionManager.findAllTransactionsByAccount(originator1);
        //when
        int actualNumbersOfTransactions = transactions1.toArray().length;
        //then
        assertEquals(k, actualNumbersOfTransactions);
    }
}
