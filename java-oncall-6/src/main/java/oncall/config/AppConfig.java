package oncall.config;

import oncall.controller.Controller;
import oncall.domain.PublicHoliday;
import oncall.view.InputView;
import oncall.view.OutputView;

public class AppConfig {
    private final InputView inputview;
    private final OutputView outputView;
    private final PublicHoliday publicHoliday;

    public AppConfig() {
        this.inputview = new InputView();
        this.outputView = new OutputView();
        this.publicHoliday = new PublicHoliday();
    }

    public Controller controller() {
        return new Controller(inputview, outputView, publicHoliday);
    }
}
