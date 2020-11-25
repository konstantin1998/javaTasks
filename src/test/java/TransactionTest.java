import org.junit.jupiter.api.function.Executable;
import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @Test
    void excecuteThrowsExceptionIfTransactionIsExecuted() {
        //given
        double amount = 0;
        long id = 0;
        long accountId = 0;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(accountId, transactionManager);
        Account beneficiary = new Account(accountId, transactionManager);
        Transaction transaction = new Transaction(id, amount, originator, beneficiary);
        //when
        Transaction executedTransaction = transaction.execute();
        //then
        assertThrows(IllegalStateException.class, executedTransaction::execute);
    }
    @Test
    void executeExecutesTransaction() {

        double amount = 10;
        long id = 0;
        long accountId = 0;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(accountId, transactionManager);
        originator.add(2 * amount);
        Account beneficiary = new Account(accountId, transactionManager);
        beneficiary.add(2 * amount);
        //when
        Transaction transaction = new Transaction(id, amount, originator, beneficiary);
        transaction = transaction.execute();
        //then
        assertTrue(transaction.isExecuted());
        assertEquals(-1 * amount, originator.getLastEntry().getAmount());
        assertEquals(amount, beneficiary.getLastEntry().getAmount());
    }
    @Test
    void rollbackThrowsExceptionIfTransactionIsRolledBack() {
        //given
        double amount = 0;
        long id = 0;
        long accountId = 0;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(accountId, transactionManager);
        Account beneficiary = new Account(accountId, transactionManager);
        Transaction transaction = new Transaction(id, amount, originator, beneficiary);
        //when
        Transaction rolledBackTransaction = transaction.rollback();
        //then
        assertThrows(IllegalStateException.class, rolledBackTransaction::rollback);
    }
    @Test
    void rollbackRollsBackTransaction() {
        //given
        double amount = 10;
        long id = 0;
        long accountId = 0;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(accountId, transactionManager);
        originator.add(2 * amount);
        Account beneficiary = new Account(accountId, transactionManager);
        beneficiary.add(2 * amount);
        //when
        Transaction transaction = new Transaction(id, amount, originator, beneficiary);
        transaction = transaction.execute();
        transaction = transaction.rollback();
        //then
        assertTrue(transaction.isRolledBack());
        assertEquals(amount, originator.getLastEntry().getAmount());
        assertEquals(-1 * amount, beneficiary.getLastEntry().getAmount());
    }
}
