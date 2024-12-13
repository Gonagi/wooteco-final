package oncall.view;

import static oncall.constants.Messages.MONTH_DAY_DAY_OF_PUBLIC_HOLIDAY;
import static oncall.constants.Messages.MONTH_DAY_DAY_OF_WEEK_NICKNAME;

public class OutputView {
    public void printDay(final int month, final int day,
                         final String dayOfWeekDay, final String nickName) {
        System.out.printf(MONTH_DAY_DAY_OF_WEEK_NICKNAME.getMessage(), month, day, dayOfWeekDay, nickName);
    }

    public void printPublicHoliday(final int month, final int day,
                                   final String dayOfWeekDay, final String nickName) {
        System.out.printf(MONTH_DAY_DAY_OF_PUBLIC_HOLIDAY.getMessage(), month, day, dayOfWeekDay, nickName);

    }
}
