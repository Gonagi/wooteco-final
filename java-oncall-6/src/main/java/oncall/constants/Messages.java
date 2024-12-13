package oncall.constants;

public enum Messages {
    // message
    INPUT_MONTH_DAY_OF_WEEK("비상 근무를 배정할 월과 시작 요일을 입력하세요> "),
    INPUT_WEEKDAY_NICKNAMES("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> "),
    INPUT_HOLIDAY_NICKNAMES("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> "),
    MONTH_DAY_DAY_OF_WEEK_NICKNAME("%d월 %d일 %s %s\n"),

    // error
    INVALID_INPUT_VALUE("유효하지 않은 입력 값입니다. 다시 입력해 주세요.");

    private final String message;

    Messages(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorMessage() {
        return "[ERROR] " + message;
    }
}
