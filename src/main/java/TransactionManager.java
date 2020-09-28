import java.util.ArrayList;
import java.util.HashMap;

public class TransactionManager {
    /**
     * Creates and stores transactions
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    private HashMap<Account, ArrayList<Transaction>> transactions;

    TransactionManager() {
        transactions = new HashMap<Account, ArrayList<Transaction>>();
    }

    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        // write your code here
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        // write your code here
    }


    public void rollbackTransaction(Transaction transaction) {
        // write your code here
    }

    public void executeTransaction(Transaction transaction) {
        // write your code here
    }
}

