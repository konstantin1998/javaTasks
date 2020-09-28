public class LocalDateTime {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private  final int minute;

    LocalDateTime(LocalDate date, int hour, int minute) {
        this.year = date.getYear();
        this.month = date.getMonth();
        this.day = date.getDay();
        this.hour = hour;
        this.minute = minute;
    }

    int compareTo(LocalDateTime date) {
        int[] currentDate = {year, month, day, hour, minute};
        int[] dateToCompare = {date.getYear(), date.getMonth(),
                date.getDay(), date.getHour(), date.getMinute()};
        for(int i =0; i < currentDate.length; i++) {
            if (currentDate[i] > dateToCompare[i]) {
                return 1;
            }
            if (currentDate[i] < dateToCompare[i]) {
                return -1;
            }
        }
        return 0;
    }

    int getYear() {
        return this.year;
    }

    int getMonth() {
        return this.month;
    }

    int getDay() {
        return this.day;
    }

    int getHour() {
        return this.hour;
    }

    int getMinute() {
        return this.minute;
    }
}
