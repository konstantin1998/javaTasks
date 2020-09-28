import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocalDateTimeTest {
    @Test
    void compareToReturnsNegativeWhenFirstDateIsEarlier() {
        //given
        int year = 2020;
        int month = 9;
        int day = 28;
        int hour1 = 12;
        int hour2 = 13;
        int minute = 0;
        LocalDateTime before = new LocalDateTime(new LocalDate(year, month, day), hour1, minute);
        LocalDateTime after = new LocalDateTime(new LocalDate(year, month, day), hour2, minute);
        //when
        int result = before.compareTo(after);
        //then
        assertEquals(-1, result);
    }
    @Test
    void compareToReturnsPositiveWhenFirstDateIsLater() {
        //given
        int year = 2020;
        int month = 9;
        int day = 28;
        int hour1 = 12;
        int hour2 = 13;
        int minute = 0;
        LocalDateTime before = new LocalDateTime(new LocalDate(year, month, day), hour1, minute);
        LocalDateTime after = new LocalDateTime(new LocalDate(year, month, day), hour2, minute);
        //when
        int result = after.compareTo(before);
        //then
        assertEquals(1, result);
    }
    @Test
    void compareToReturnsZeroWhenFirstDateIsLater() {
        //given
        int year = 2020;
        int month = 9;
        int day = 28;
        int hour = 12;
        int minute = 0;
        LocalDateTime before = new LocalDateTime(new LocalDate(year, month, day), hour, minute);
        LocalDateTime after = new LocalDateTime(new LocalDate(year, month, day), hour, minute);
        //when
        int result = after.compareTo(before);
        //then
        assertEquals(0, result);
    }
}
