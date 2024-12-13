package oncall.domain;

import static oncall.constants.Constants.COMMA;

import java.util.Arrays;

public enum Day {
    MONDAY("월", false),
    TUESDAY("화", false),
    WEDNESDAY("수", false),
    THURSDAY("목", false),
    FRIDAY("금", true),
    SATURDAY("토", true),
    SUNDAY("일", true),
    NOTHING("", false);
    private final String dayOfWeek;
    private final boolean isHoliday;

    Day(final String dayOfWeek, final boolean isHoliday) {
        this.dayOfWeek = dayOfWeek;
        this.isHoliday = isHoliday;
    }

    public static Day from(final String inputMonthDayOfWeek) {
        String[] split = inputMonthDayOfWeek.split(COMMA);
        String day = split[1];

        return Arrays.stream(values())
                .filter(d -> d.matchingDay(day))
                .findFirst()
                .orElse(NOTHING);
    }

    private boolean matchingDay(final String day) {
        return this.dayOfWeek == day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isHoliday() {
        return isHoliday;
    }
}
