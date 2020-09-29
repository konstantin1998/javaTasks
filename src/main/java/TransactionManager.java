import java.util.ArrayList;
import java.util.Collection;
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
    private long id;

    TransactionManager() {
        transactions = new HashMap<Account, ArrayList<Transaction>>();
    }

    public Transaction createTransaction(double amount, Account originator, Account beneficiary) {
        Transaction transaction = new Transaction(id, amount, originator, beneficiary);
        id++;
        return transaction;
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        return (Collection<Transaction>)transactions.get(account);
    }

    public void rollbackTransaction(Transaction transaction) {
        Transaction rolledBackTansaction = transaction.rollback();
        add(rolledBackTansaction);
    }

    public void executeTransaction(Transaction transaction) {
        Transaction executedTransaction = transaction.execute();
        add(executedTransaction);
    }

    private void add(Transaction transaction) {
        if (!transactions.containsKey(transaction.getBeneficiary())) {
            transactions.put(transaction.getBeneficiary(), new ArrayList<>());
        }
        transactions.get(transaction.getBeneficiary()).add(transaction);
    }
}

