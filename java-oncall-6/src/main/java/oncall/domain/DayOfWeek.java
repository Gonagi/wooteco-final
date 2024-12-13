package oncall.domain;

import static oncall.constants.Constants.COMMA;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum DayOfWeek {
    MONDAY("월", false),
    TUESDAY("화", false),
    WEDNESDAY("수", false),
    THURSDAY("목", false),
    FRIDAY("금", false),
    SATURDAY("토", true),
    SUNDAY("일", true),
    NOTHING("", false);
    private final String dayOfWeek;
    private final boolean isHoliday;

    DayOfWeek(final String dayOfWeek, final boolean isHoliday) {
        this.dayOfWeek = dayOfWeek;
        this.isHoliday = isHoliday;
    }

    public static DayOfWeek from(final String inputMonthDayOfWeek) {
        String[] split = inputMonthDayOfWeek.split(COMMA);
        String day = split[1];

        return getDayOfWeek(day);
    }

    private static DayOfWeek getDayOfWeek(final String day) {
        return Arrays.stream(values())
                .filter(d -> d.matchingDay(day))
                .findFirst()
                .orElse(NOTHING);
    }

    public static DayOfWeek getNextDayOfWeek(final DayOfWeek dayOfWeek) {
        List<DayOfWeek> dayOfWeeks = Arrays.stream(values())
                .filter(d -> d != NOTHING)
                .toList();
        int curIndex = dayOfWeeks.indexOf(dayOfWeek);
        int nextIndex = (curIndex + 1) % dayOfWeeks.size();

        return values()[nextIndex];
    }

    private boolean matchingDay(final String day) {
        return Objects.equals(getDayOfWeek(), day);
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isHoliday() {
        return isHoliday;
    }
}
