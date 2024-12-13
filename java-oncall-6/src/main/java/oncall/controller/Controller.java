package oncall.controller;

import static oncall.utils.ExceptionRetryHandler.retryOnException;

import java.util.ArrayList;
import java.util.List;
import oncall.domain.Date;
import oncall.domain.Day;
import oncall.domain.Month;
import oncall.domain.PublicHoliday;
import oncall.domain.TimeTable;
import oncall.view.InputView;
import oncall.view.OutputView;

public class Controller {
    private final InputView inputView;
    private final OutputView outputView;
    private final PublicHoliday publicHoliday;

    public Controller(final InputView inputView, final OutputView outputView, final PublicHoliday publicHoliday) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.publicHoliday = publicHoliday;
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
            if (publicHoliday.isPublicHoliday(Date.of(month, d))) {
                outputView.printPublicHoliday(month.getMonth(), d, day.getDayOfWeek(), timeTable.getHolidaysNickName());
                String holidaysNickName = timeTable.getHolidaysNickName();
                timeTable.rotationHolidays();
                if (Month.isLastDay(Date.of(month, d))) {
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
                if (Month.isLastDay(Date.of(month, d))) {
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
                if (Month.isLastDay(Date.of(month, d))) {
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

    private boolean isRedDay(final Day nextDay, final Month month, final int d) {
        return nextDay.isHoliday() || publicHoliday.isPublicHoliday(Date.of(month, d + 1));
    }

    private Day getNextDay(final List<Day> dayOfWeek, Day day) {
        if (dayOfWeek.indexOf(day) + 1 == 7) {
            day = Day.MONDAY;
        } else {
            day = dayOfWeek.get(dayOfWeek.indexOf(day) + 1);
        }
        return day;
    }
}
