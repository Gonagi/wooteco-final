package oncall.controller;

import static oncall.utils.ExceptionRetryHandler.retryOnException;

import java.util.List;
import oncall.domain.Date;
import oncall.domain.DayOfWeek;
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
        DayOfWeek dayOfWeek = DayOfWeek.from(inputMonthDayOfWeek);

        TimeTable timeTable = retryOnException(() -> {
            List<String> weekDayNickNames = inputView.inputWeekDayNickNames();
            List<String> holidayNickNames = inputView.inputHolidayNickNames();
            return TimeTable.of(weekDayNickNames, holidayNickNames);
        });

        for (int d = 1; d <= month.getDay(); d++) {
            DayOfWeek nextDay = DayOfWeek.getNextDayOfWeek(dayOfWeek);
            if (publicHoliday.isPublicHoliday(Date.of(month, d))) {
                outputView.printPublicHoliday(month.getMonth(), d, dayOfWeek.getDayOfWeek(),
                        timeTable.getHolidaysNickName());
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
            } else if (dayOfWeek.isHoliday()) {
                outputView.printDay(month.getMonth(), d, dayOfWeek.getDayOfWeek(), timeTable.getHolidaysNickName());
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
                outputView.printDay(month.getMonth(), d, dayOfWeek.getDayOfWeek(), timeTable.getWeekDaysNickName());
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

            dayOfWeek = nextDay;
        }
    }

    private boolean isRedDay(final DayOfWeek nextDay, final Month month, final int d) {
        return nextDay.isHoliday() || publicHoliday.isPublicHoliday(Date.of(month, d + 1));
    }
}
