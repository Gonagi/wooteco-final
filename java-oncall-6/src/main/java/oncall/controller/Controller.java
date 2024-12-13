package oncall.controller;

import static oncall.utils.ExceptionRetryHandler.retryOnException;

import java.util.ArrayList;
import java.util.List;
import oncall.domain.Day;
import oncall.domain.Month;
import oncall.domain.TimeTable;
import oncall.view.InputView;
import oncall.view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;

    public Controller(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        String inputMonthDayOfWeek = retryOnException(inputView::inputMonthAndDayOfWeek);
        Month month = Month.from(inputMonthDayOfWeek);
        Day day = Day.from(inputMonthDayOfWeek);

        TimeTable timeTable = retryOnException(() -> {
            List<String> weekDayNickNames = inputView.inputWeekDayNickNames();
            List<String> holidayNickNames = inputView.inputHolidayNickNames();
            return TimeTable.of(weekDayNickNames, holidayNickNames);
        });

        List<Day> dayOfWeek = new ArrayList<>(
                List.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY));

        for (int d = 1; d <= month.getDay(); d++) {
            Day nextDay = getNextDay(dayOfWeek, day);
            if (Month.matchingPublicHolidays(month.getMonth(), d)) {
                outputView.printPublicHoliday(month.getMonth(), d, day.getDayOfWeek(), timeTable.getHolidaysNickName());
                String holidaysNickName = timeTable.getHolidaysNickName();
                timeTable.rotationHolidays();
                if (checkLastDay(month, d)) {
                    return;
                }
                if (isRedDay(nextDay, month, d)) {
                    timeTable.changeHolidays(holidaysNickName);
                } else {
                    timeTable.changeWeekDays(holidaysNickName);
                }
            } else if (day.isHoliday()) {
                outputView.printDay(month.getMonth(), d, day.getDayOfWeek(), timeTable.getHolidaysNickName());
                String holidaysNickName = timeTable.getHolidaysNickName();
                timeTable.rotationHolidays();
                if (checkLastDay(month, d)) {
                    return;
                }
                if (isRedDay(nextDay, month, d)) {
                    timeTable.changeHolidays(holidaysNickName);
                } else {
                    timeTable.changeWeekDays(holidaysNickName);
                }
            } else {
                outputView.printDay(month.getMonth(), d, day.getDayOfWeek(), timeTable.getWeekDaysNickName());
                String weekDaysNickName = timeTable.getWeekDaysNickName();
                timeTable.rotationWeekDays();
                if (checkLastDay(month, d)) {
                    return;
                }
                if (isRedDay(nextDay, month, d)) {
                    timeTable.changeHolidays(weekDaysNickName);
                } else {
                    timeTable.changeWeekDays(weekDaysNickName);
                }
            }

            day = nextDay;
        }
    }

    private static boolean checkLastDay(final Month month, final int d) {
        if (month.getMonth() == 2 && d == 28) {
            return true;
        }

        if ((month.getMonth() == 1 || month.getMonth() == 3 || month.getMonth() == 5 || month.getMonth() == 7
                || month.getMonth() == 8 || month.getMonth() == 10 || month.getMonth() == 12)
                && d == 31) {
            return true;
        }
        if ((month.getMonth() == 4 || month.getMonth() == 6 || month.getMonth() == 9 || month.getMonth() == 11)
                && d == 30) {
            return true;
        }
        return false;
    }

    private static boolean isRedDay(final Day nextDay, final Month month, final int d) {
        return nextDay.isHoliday() || Month.matchingPublicHolidays(month.getMonth(), d + 1);
    }

    private static Day getNextDay(final List<Day> dayOfWeek, Day day) {
        if (dayOfWeek.indexOf(day) + 1 == 7) {
            day = Day.MONDAY;
        } else {
            day = dayOfWeek.get(dayOfWeek.indexOf(day) + 1);
        }
        return day;
    }
}
