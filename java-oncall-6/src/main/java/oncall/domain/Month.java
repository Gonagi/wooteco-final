package oncall.domain;

import static oncall.constants.constants.COMMA;

import java.util.Arrays;
import javax.swing.ComponentInputMap;

public enum Month {
    JANUARY(1, 31),
    FEBRUARY(2, 28),
    MARCH(3, 31),
    APRIL(4, 30),
    MAY(5, 31),
    JUNE(6, 30),
    JULY(7, 31),
    AUGUST(8, 31),
    SEPTEMBER(9, 30),
    OCTOBER(10, 31),
    NOVEMBER(11, 30),
    DECEMBER(12, 31),
    NOTHING(0, 0);

    private final int month;
    private final int day;

    Month(final int month, final int day) {
        this.month = month;
        this.day = day;
    }

    public static Month from(final String inputMonthDayOfWeek) {
        String[] split = inputMonthDayOfWeek.split(COMMA);
        int month = Integer.parseInt(split[0]);

        return Arrays.stream(values())
                .filter(m -> m.matchingMonth(month))
                .findFirst()
                .orElse(NOTHING);
    }

    public static boolean matchingStatutoryHolidays(final int month, final int day) {
        if ((month == 1 && day == 1) || (month == 3 && day == 1) || (month == 5 && day == 5) || (month == 6 && day == 6)
                || (month == 8 && day == 15) || (month == 10 && day == 3) || (month == 10 && day == 9) || (month == 12
                && day == 25))
            return true;
        return false;
    }

    private boolean matchingMonth(final int month) {
        return this.month == month;
    }

    public int getDay() {
        return day;
    }
}
