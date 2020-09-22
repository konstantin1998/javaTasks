import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    void withdrawReturnFalseWhenNoMoneyOnAccount() {
        Account account = new Account(1);
        int amount = 10;
        assertFalse(account.withdraw(amount));
    }
    @Test
    void withdrawReturnFalseWhenMoneyIsNegative() {
        Account account = new Account(1);
        int amount = 10;
        assertFalse(account.withdraw(-1 * amount));
    }
    @Test
    void withdrawReturnTrueWhenMoneyIsLessThenBalance() {
        Account account = new Account(1);
        int amount = 10;
        account.add(amount);
        assertTrue(account.withdraw(amount * 0.5));
    }

    @Test
    void addReturnTrueWhenAmountIsPositive() {
        Account account = new Account(1);
        int amount = 10;
        assertTrue(account.add(amount));
    }
    @Test
    void addReturnFalseWhenAmountIsNegative() {
        Account account = new Account(1);
        int amount = 10;
        assertFalse(account.add(-1 * amount));
    }
}
