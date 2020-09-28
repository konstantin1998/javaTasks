import java.time.*;
public class Entry {
    private final Account account;
    private final Transaction transaction;
    private final double amount;
    private final LocalDateTime time;
    Entry(Account account, Transaction transaction, double amount, LocalDateTime time){
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    LocalDateTime getTime() {
        return this.time;
    }

}

