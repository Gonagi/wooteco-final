package oncall.domain;

import static oncall.constants.Messages.INVALID_INPUT_VALUE;

import java.util.List;

public class TimeTable {
    private final List<String> weekDays;
    private final List<String> holidays;

    private TimeTable(final List<String> weekDays, final List<String> holidays) {
        this.weekDays = weekDays;
        this.holidays = holidays;
    }
    public static TimeTable of(final List<String> weekDays, final List<String> holidays) {
        checkSize(weekDays, holidays);
        return new TimeTable(weekDays, holidays);
    }

    private static void checkSize(final List<String> weekDays, final List<String> holidays) {
        if (weekDays.size() + holidays.size() < 5 || weekDays.size() + holidays.size() > 35) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }
}
