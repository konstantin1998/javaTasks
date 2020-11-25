import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TransactionManager {
    /**
     * Creates and stores transactions
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    private Map<Account, ArrayList<Transaction>> transactions;
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
        return transactions.get(account);
    }

    public void rollbackTransaction(Transaction transaction) {
        Transaction rolledBackTransaction = transaction.rollback();
        add(rolledBackTransaction);
    }

    public void executeTransaction(Transaction transaction) {
        Transaction executedTransaction = transaction.execute();
        add(executedTransaction);
    }

    private void add(Transaction transaction) {
        if (!transactions.containsKey(transaction.getOriginator())) {
            transactions.put(transaction.getOriginator(), new ArrayList<>());
        }
        transactions.get(transaction.getOriginator()).add(transaction);

        if (transaction.getBeneficiary() != null) {
            if (!transactions.containsKey(transaction.getBeneficiary())) {
                transactions.put(transaction.getBeneficiary(), new ArrayList<>());
            }
            transactions.get(transaction.getBeneficiary()).add(transaction);
        }
    }
}

