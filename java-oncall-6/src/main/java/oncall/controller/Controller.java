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

        for (int day = 1; day <= month.getDay(); day++) {
            processDay(month, day, dayOfWeek, timeTable);
            dayOfWeek = DayOfWeek.getNextDayOfWeek(dayOfWeek);
        }
    }

    private void processDay(final Month month, final int day, final DayOfWeek dayOfWeek, final TimeTable timeTable) {
        if (isRedDay(dayOfWeek, month, day)) {
            redDayProcess(month, day, dayOfWeek, timeTable);
            return;
        }
        weekDayProcess(month, day, dayOfWeek, timeTable);
    }

    private void redDayProcess(final Month month, final int day, final DayOfWeek dayOfWeek, final TimeTable timeTable) {
        if (publicHoliday.isPublicHoliday(Date.of(month, day))) {
            publicHolidayProcess(month, day, dayOfWeek, timeTable);
        }
        holidayProcess(month, day, dayOfWeek, timeTable);
    }

    private void weekDayProcess(final Month month, final int day, final DayOfWeek dayOfWeek,
                                final TimeTable timeTable) {
        outputView.printDay(month.getMonth(), day, dayOfWeek.getDayOfWeek(), timeTable.getWeekDaysNickName());
        if (Month.isLastDay(Date.of(month, day))) {
            return;
        }
        weekdayChangeProcess(month, day, dayOfWeek, timeTable);
    }

    private void holidayProcess(final Month month, final int day, final DayOfWeek dayOfWeek,
                                final TimeTable timeTable) {
        outputView.printDay(month.getMonth(), day, dayOfWeek.getDayOfWeek(), timeTable.getHolidaysNickName());
        if (Month.isLastDay(Date.of(month, day))) {
            return;
        }
        holidayChangeProcess(month, day, dayOfWeek, timeTable);
    }

    private void publicHolidayProcess(final Month month, final int day, final DayOfWeek dayOfWeek,
                                      final TimeTable timeTable) {
        outputView.printPublicHoliday(month.getMonth(), day, dayOfWeek.getDayOfWeek(),
                timeTable.getHolidaysNickName());
        if (Month.isLastDay(Date.of(month, day))) {
            return;
        }
        holidayChangeProcess(month, day, dayOfWeek, timeTable);
    }

    private void weekdayChangeProcess(final Month month, final int day, final DayOfWeek dayOfWeek,
                                      final TimeTable timeTable) {
        String weekDaysNickName = timeTable.getWeekDaysNickName();
        timeTable.rotationWeekDays();
        DayOfWeek nextDay = DayOfWeek.getNextDayOfWeek(dayOfWeek);
        if (isRedDay(nextDay, month, day)) {
            timeTable.changeHolidays(weekDaysNickName);
        } else {
            timeTable.changeWeekDays(weekDaysNickName);
        }
    }

    private void holidayChangeProcess(final Month month, final int day, final DayOfWeek dayOfWeek,
                                      final TimeTable timeTable) {
        String holidaysNickName = timeTable.getHolidaysNickName();
        timeTable.rotationHolidays();
        DayOfWeek nextDay = DayOfWeek.getNextDayOfWeek(dayOfWeek);
        if (isRedDay(nextDay, month, day)) {
            timeTable.changeHolidays(holidaysNickName);
        } else {
            timeTable.changeWeekDays(holidaysNickName);
        }
    }

    private boolean isRedDay(final DayOfWeek nextDay, final Month month, final int day) {
        return nextDay.isHoliday() || publicHoliday.isPublicHoliday(Date.of(month, day + 1));
    }
}
