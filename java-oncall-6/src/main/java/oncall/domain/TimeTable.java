package oncall.domain;

import static oncall.constants.Messages.INVALID_INPUT_VALUE;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        return new TimeTable(inputWeekDays, inputHolidays);
    }
    
    public String getWeekDaysNickName() {
        String nickName = weekDays.poll();
        weekDays.add(nickName);
        return nickName;
    }

    public String getHolidaysNickName() {
        String nickName = holidays.poll();
        holidays.add(nickName);
        return nickName;
    }

    private static void checkSize(final List<String> weekDays, final List<String> holidays) {
        if (weekDays.size() + holidays.size() < 5 || weekDays.size() + holidays.size() > 35) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }
}
