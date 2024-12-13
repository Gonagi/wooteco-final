package oncall.domain;

public class Date {
    public Month month;

    public int day;

    private Date(final Month month, final int day) {
        this.month = month;
        this.day = day;
    }

    public static Date of(final Month month, final int day) {
        return new Date(month, day);
    }

    public int getMonth() {
        return month.getMonth();
    }

    public int getDay() {
        return day;
    }
}
