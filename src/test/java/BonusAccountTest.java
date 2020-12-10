import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class BonusAccountTest {
    @Test
    void balanceOnReturnsBalanceOnGivenDate() {
        //given
        double rate = 0.01;
        BonusAccount bonusAccount = new BonusAccount(rate);
        DebitCard originator = new DebitCard(1, new TransactionManager(), bonusAccount);
        DebitCard beneficiary = new DebitCard(2, new TransactionManager());
        int withdrawTransactionsNumber = 5;
        double amountToWithdraw = 100.0;
        originator.add(withdrawTransactionsNumber * amountToWithdraw);
        for (int i = 0; i <= withdrawTransactionsNumber - 1; i++) {
            originator.withdraw(amountToWithdraw, beneficiary);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //when
        double expectedBonusPoints = withdrawTransactionsNumber * amountToWithdraw * rate;
        double actualBonusPoints = bonusAccount.balanceOn(LocalDate.now());
        //then
        assertEquals(expectedBonusPoints, actualBonusPoints);
    }
}
