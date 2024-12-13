package oncall.config;

import oncall.controller.Controller;
import oncall.view.InputView;
import oncall.view.OutputView;

public class AppConfig {
    private final InputView inputview;
    private final OutputView outputView;


    public AppConfig() {
        this.inputview = new InputView();
        this.outputView = new OutputView();
    }

    public Controller controller() {
        return new Controller(inputview, outputView);
    }
}
