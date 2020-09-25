import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    @Test
    void openAccountReturnTrueWhenNoActiveAccount() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        assertTrue(customer.openAccount(id));
    }
    @Test
    void openAccountReturnFalseWhenActiveAccount() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        assertFalse(customer.openAccount(id));
    }

    @Test
    void closeAccountReturnFalseWhenNoActiveAccount() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        assertFalse(customer.closeAccount());
    }
    @Test
    void closeAccountReturnTrueWhenActiveAccount() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        assertTrue(customer.closeAccount());
    }

    @Test
    void fullNameFormsFullName() {
        Customer customer = new Customer("abstract", "customer");
        assertEquals(customer.fullName(), "abstract customer");
    }

    @Test
    void withdrawReturnTrueWhenMoneyAreAvaiable() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        int amountOfMoney = 100;
        customer.addMoneyToCurrentAccount(amountOfMoney);
        assertTrue(customer.withdrawFromCurrentAccount(amountOfMoney * 0.5));
    }
    @Test
    void withdrawReturnFalseWhenMoneyAreNotEnaugh() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        customer.openAccount(id);
        int amountOfMoney = 100;
        customer.addMoneyToCurrentAccount(amountOfMoney);
        assertFalse(customer.withdrawFromCurrentAccount(amountOfMoney * 1.5));
    }

    @Test
    void addMoneyReturnFalseWhenNoActiveAccount() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        int amountOfMoney = 100;
        assertFalse(customer.addMoneyToCurrentAccount(amountOfMoney));
    }
    @Test
    void addMoneyReturnTrueWhenActiveAccount() {
        Customer customer = new Customer("abstract", "customer");
        long id = 1;
        int amountOfMoney = 100;
        customer.openAccount(id);
        assertTrue(customer.addMoneyToCurrentAccount(amountOfMoney));
    }
}
