public class LocalDate {
    private final int year;
    private final int month;
    private final int day;

    LocalDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    /*
    int compareTo(LocalDateTime date) {
        int[] currentDate = {year, month, day};
        int[] dateToCompare = {date.getYear(), date.getMonth(),
                date.getDay()};
        for(int i =0; i < currentDate.length; i++) {
            if (currentDate[i] > dateToCompare[i]) {
                return 1;
            }
            if (currentDate[i] < dateToCompare[i]) {
                return -1;
            }
        }
        return 0;
    }*/

    int getYear() {
        return this.year;
    }

    int getMonth() {
        return this.month;
    }

    int getDay() {
        return this.day;
    }

}
