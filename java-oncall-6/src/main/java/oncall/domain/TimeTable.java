package oncall.domain;

import static oncall.constants.Messages.INVALID_INPUT_VALUE;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class TimeTable {
    private final Queue<String> weekDays;
    private final Queue<String> holidays;

    private TimeTable(final Queue<String> weekDays, final Queue<String> holidays) {
        this.weekDays = weekDays;
        this.holidays = holidays;
    }

    public static TimeTable of(final List<String> weekDays, final List<String> holidays) {
        checkSize(weekDays, holidays);
        Queue<String> inputWeekDays = new LinkedList<>(weekDays);
        Queue<String> inputHolidays = new LinkedList<>(holidays);

        validateBothSize(inputWeekDays, inputHolidays);

        return new TimeTable(inputWeekDays, inputHolidays);
    }

    private static void validateBothSize(final Queue<String> inputWeekDays, final Queue<String> inputHolidays) {
        if (!inputWeekDays.containsAll(inputHolidays)) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }

    private static void checkSize(final List<String> weekDays, final List<String> holidays) {
        Set<String> allNickNames = new HashSet<>();
        allNickNames.addAll(weekDays);
        allNickNames.addAll(holidays);

        if (allNickNames.size() < 5 || allNickNames.size() > 35) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }

    public String rotationWeekDays() {
        String name = weekDays.poll();
        weekDays.add(name);
        return name;
    }

    public String rotationHolidays() {
        String name = holidays.poll();
        holidays.add(name);
        return name;
    }

    public void changeWeekDays(final String nickName) {
        if (Objects.equals(nickName, weekDays.peek())) {
            rotationWeekDays();
        }
    }

    public void changeHolidays(final String nickName) {
        if (Objects.equals(nickName, holidays.peek())) {
            rotationHolidays();
        }
    }

    public String getWeekDaysNickName() {
        return weekDays.peek();
    }

    public String getHolidaysNickName() {
        return holidays.peek();
    }
}
