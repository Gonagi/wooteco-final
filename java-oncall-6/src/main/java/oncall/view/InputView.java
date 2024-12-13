package oncall.view;

import static oncall.constants.Messages.INPUT_MONTH_DAY_OF_WEEK;
import static oncall.constants.Messages.INVALID_INPUT_VALUE;

import camp.nextstep.edu.missionutils.Console;
import java.util.Optional;
import java.util.regex.Pattern;

public class InputView {

    private static final String MONTH_DAY_OF_WEEK_REGEX = "^(\\d+,{1}[월화수목금토일]{1}){1}";
    private static final String COMMA = ",";

    public String inputMonthAndDayOfWeek() {
        System.out.print(INPUT_MONTH_DAY_OF_WEEK.getMessage());
        String inputProducts = Console.readLine();
        validateInputMonthAndDayOfWeek(inputProducts);
        return inputProducts;
    }

    private void validateInputMonthAndDayOfWeek(final String inputMonthDayOfWeek) {
        validateNull(inputMonthDayOfWeek);
        validateInputPattern(inputMonthDayOfWeek);
        validateMonth(inputMonthDayOfWeek);
    }

    private void validateNull(final String input) {
        Optional.ofNullable(input)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_INPUT_VALUE.getErrorMessage()));
    }

    private static void validateInputPattern(final String inputMonthDayOfWeek) {
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
