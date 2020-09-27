import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    @Test
    void withdrawReturnFalseWhenNoMoneyOnAccount() {
        //given
        Account account = new Account(1);
        int amount = 10;
        //when
        boolean isFalse = account.withdraw(amount);
        //then
        assertFalse(isFalse);
    }
    @Test
    void withdrawReturnFalseWhenMoneyIsNegative() {
        //given
        Account account = new Account(1);
        int amount = 10;
        //when
        boolean isFalse = account.withdraw(-1 * amount);
        //then
        assertFalse(isFalse);
    }
    @Test
    void withdrawReturnTrueWhenMoneyIsLessThenBalance() {
        // given
        Account account = new Account(1);
        int amount = 10;
        account.add(amount);
        //when
        boolean isTrue = account.withdraw(amount * 0.5);
        //then
        assertTrue(isTrue);
    }

    @Test
    void addReturnTrueWhenAmountIsPositive() {
        // given
        Account account = new Account(1);
        int amount = 10;
        //when
        boolean isTrue = account.add(amount);
        //then
        assertTrue(isTrue);
    }
    @Test
    void addReturnFalseWhenAmountIsNegative() {
        //given
        Account account = new Account(1);
        int amount = 10;
        //when
        boolean isFalse = account.add(-1 * amount);
        //then
        assertFalse(isFalse);
    }
}
