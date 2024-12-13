package oncall.domain;

import java.util.HashSet;
import java.util.Set;

public final class PublicHoliday {
    private final Set<Date> publicHolidays;

    public PublicHoliday() {
        this.publicHolidays = new HashSet<>();
        publicHolidays.add(Date.of(Month.JANUARY, 1));
        publicHolidays.add(Date.of(Month.MARCH, 1));
        publicHolidays.add(Date.of(Month.MAY, 5));
        publicHolidays.add(Date.of(Month.JUNE, 6));
        publicHolidays.add(Date.of(Month.AUGUST, 15));
        publicHolidays.add(Date.of(Month.OCTOBER, 3));
        publicHolidays.add(Date.of(Month.OCTOBER, 9));
        publicHolidays.add(Date.of(Month.DECEMBER, 25));
    }

    public boolean isPublicHoliday(final Date date) {
        return publicHolidays.contains(date);
    }
}
