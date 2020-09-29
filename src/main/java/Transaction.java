import java.time.LocalDateTime;

public class Transaction {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;

    Transaction(long id, double amount, Account originator, Account beneficiary){
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = false;
        this.rolledBack = false;
    }
    private Transaction(long id, double amount, Account originator, Account beneficiary, boolean executed, boolean rolledBack){
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }
    /**
     * Adding entries to both accounts
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        if (executed) {
            throw new IllegalStateException("transaction is already executed");
        }
        //executed = true;
        originator.addEntry(new Entry(beneficiary, this, amount, LocalDateTime.now()));
        beneficiary.addEntry(new Entry(originator, this, -1 * amount, LocalDateTime.now()));
        return new Transaction(id, amount, originator, beneficiary, true, false);
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        // write your code here
        if (rolledBack) {
            throw new IllegalStateException("transaction is already rolled back");
        }
        //rolledBack = true;
        originator.addEntry(new Entry(beneficiary, this, -1 * amount, LocalDateTime.now()));
        beneficiary.addEntry(new Entry(originator, this, amount, LocalDateTime.now()));
        return new Transaction(id, amount, originator, beneficiary, true, true);
    }

    Account getBeneficiary() {
        return this.beneficiary;
    }
}

