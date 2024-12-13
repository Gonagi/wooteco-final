package oncall.controller;

import static oncall.utils.ExceptionRetryHandler.retryOnException;

import java.util.List;
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

        retryOnException(() ->{
            List<String> weekDayNickNames = inputView.inputWeekDayNickNames();
            List<String> holidayNickNames = inputView.inputHolidayNickNames();
            return null;
        });
    }
}
