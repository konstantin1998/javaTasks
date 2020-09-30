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
    void excecuteExequtesTransaction() {
        //given
        double amount = 0;
        long id = 0;
        long accountId = 0;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(accountId, transactionManager);
        Account beneficiary = new Account(accountId, transactionManager);
        //when
        Transaction transaction = new Transaction(id, amount, originator, beneficiary);
        //then
        assertDoesNotThrow((Executable) transaction::execute, "transaction is already executed");
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
        double amount = 0;
        long id = 0;
        long accountId = 0;
        TransactionManager transactionManager = new TransactionManager();
        Account originator = new Account(accountId, transactionManager);
        Account beneficiary = new Account(accountId, transactionManager);
        //when
        Transaction transaction = new Transaction(id, amount, originator, beneficiary);
        //then
        assertDoesNotThrow((Executable) transaction::rollback, "transaction is already executed");
    }
}
