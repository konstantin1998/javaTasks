import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    @Test
    void openAccountReturnTrueWhenNoActiveAccount() {
        //given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        //when
        boolean isTrue = customer.openAccount(id);
        //then
        assertTrue(isTrue);
    }
    @Test
    void openAccountReturnFalseWhenActiveAccount() {
        //given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        //when
        boolean isFalse = customer.openAccount(id);
        //then
        assertFalse(isFalse);
    }

    @Test
    void closeAccountReturnFalseWhenNoActiveAccount() {
        //given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        //when
        boolean isFalse = customer.closeAccount();
        //then
        assertFalse(isFalse);
    }
    @Test
    void closeAccountReturnTrueWhenActiveAccount() {
        //given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        //when
        boolean isTrue = customer.closeAccount();
        //then
        assertTrue(isTrue);
    }

    @Test
    void fullNameFormsFullName() {
        //given
        Customer customer = new Customer("abstract", "customer");
        // when
        String actualFullName = "abstract customer";
        //then
        assertEquals(customer.fullName(), actualFullName);
    }

    @Test
    void withdrawReturnTrueWhenMoneyAreAvaiable() {
        // given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        int amountOfMoney = 100;
        customer.addMoneyToCurrentAccount(amountOfMoney);
        // when
        boolean isTrue = customer.withdrawFromCurrentAccount(amountOfMoney * 0.5);
        // then
        assertTrue(isTrue);
    }
    @Test
    void withdrawReturnFalseWhenMoneyAreNotEnaugh() {
        //given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        int amountOfMoney = 100;
        customer.addMoneyToCurrentAccount(amountOfMoney);
        //when
        boolean isFasle = customer.withdrawFromCurrentAccount(amountOfMoney * 1.5);
        //then
        assertFalse(isFasle);
    }

    @Test
    void addMoneyReturnFalseWhenNoActiveAccount() {
        //given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        int amountOfMoney = 100;
        //when
        boolean isFasle = customer.addMoneyToCurrentAccount(amountOfMoney);
        //then
        assertFalse(isFasle);
    }
    @Test
    void addMoneyReturnTrueWhenActiveAccount() {
        //given
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        int amountOfMoney = 100;
        customer.openAccount(id);
        //when
        boolean isTrue = customer.addMoneyToCurrentAccount(amountOfMoney);
        //then
        assertTrue(isTrue);
    }
}
