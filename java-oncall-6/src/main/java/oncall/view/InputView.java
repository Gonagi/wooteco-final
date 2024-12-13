package oncall.view;

import static oncall.constants.Constants.COMMA;
import static oncall.constants.Messages.INPUT_HOLIDAY_NICKNAMES;
import static oncall.constants.Messages.INPUT_MONTH_DAY_OF_WEEK;
import static oncall.constants.Messages.INPUT_WEEKDAY_NICKNAMES;
import static oncall.constants.Messages.INVALID_INPUT_VALUE;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class InputView {

    private static final String MONTH_DAY_OF_WEEK_REGEX = "^(\\d+,{1}[월화수목금토일]{1}){1}";
    private static final String NICKNAMES_REGEX = "([ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+,)*[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]+";


    public String inputMonthAndDayOfWeek() {
        System.out.print(INPUT_MONTH_DAY_OF_WEEK.getMessage());
        String monthDayOfWeek = Console.readLine();
        validateInputMonthAndDayOfWeek(monthDayOfWeek);
        return monthDayOfWeek;
    }

    public List<String> inputWeekDayNickNames() {
        System.out.print(INPUT_WEEKDAY_NICKNAMES.getMessage());
        String weekDayNickNames = Console.readLine();
        validateInputNickNames(weekDayNickNames);
        return getNickNames(weekDayNickNames);
    }

    public List<String> inputHolidayNickNames() {
        System.out.print(INPUT_HOLIDAY_NICKNAMES.getMessage());
        String holidayNickNames = Console.readLine();
        validateInputNickNames(holidayNickNames);
        return getNickNames(holidayNickNames);
    }

    private List<String> getNickNames(final String weekDayNickNames) {
        String[] split = weekDayNickNames.split(COMMA);
        List<String> nickNames = Arrays.asList(split);
        validateNickNamesLength(nickNames);
        validateNickNamesDuplicate(nickNames);
        return nickNames;
    }

    private void validateNickNamesDuplicate(final List<String> nickNames) {
        HashSet<String> uniqueNickNames = new HashSet<>(nickNames);
        if (uniqueNickNames.size() != nickNames.size()) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }

    private void validateNickNamesLength(final List<String> nickNames) {
        nickNames.forEach(nickName -> {
            if (nickName.length() > 5) {
                throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
            }
        });
    }

    private void validateInputNickNames(final String inputWeekDayNickNames) {
        validateNull(inputWeekDayNickNames);
        if (!Pattern.matches(NICKNAMES_REGEX, inputWeekDayNickNames)) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }

    private void validateInputMonthAndDayOfWeek(final String inputMonthDayOfWeek) {
        validateInputPattern(inputMonthDayOfWeek);
        validateMonth(inputMonthDayOfWeek);
    }

    private void validateNull(final String input) {
        Optional.ofNullable(input)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage()));
    }

    private void validateInputPattern(final String inputMonthDayOfWeek) {
        validateNull(inputMonthDayOfWeek);
        if (!Pattern.matches(MONTH_DAY_OF_WEEK_REGEX, inputMonthDayOfWeek)) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }

    private void validateMonth(final String input) {
        String[] split = input.split(COMMA);
        int inputMonth = Integer.parseInt(split[0]);
        if (inputMonth < 1 || inputMonth > 12) {
            throw new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage());
        }
    }
}
