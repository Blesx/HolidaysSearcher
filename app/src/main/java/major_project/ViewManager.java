package major_project;

import javafx.stage.Stage;
import major_project.model.calendar.input.InputCalendar;
import major_project.model.calendar.output.OutputCalendar;
import major_project.view.Window;
import major_project.view.WorldMapWindow;

public class ViewManager {
    private final Stage stage;
    private final InputCalendar inputModel;
    private final OutputCalendar outputModel;
    private final int viewWidth;
    private final int viewHeight;

    private Window currentView;

    private final int defaultFontSize = 20;

    public ViewManager(Stage stage, InputCalendar inputModel, OutputCalendar outputModel, int viewWidth, int viewHeight) {
        this.stage = stage;
        this.inputModel = inputModel;
        this.outputModel = outputModel;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        currentView = new WorldMapWindow(this);
        setScene(currentView);

    }

    public void setScene(Window view) {
        stage.setScene(view.getScene());

    }

    public int getViewWidth() {
        return viewWidth;

    }

    public int getViewHeight() {
        return viewHeight;

    }

    public int getDefaultFontSize() {
        return defaultFontSize;

    }

    public InputCalendar getInputModelSetCountry(String countryAbv) {
        inputModel.setCountryAbv(countryAbv);
        return inputModel;

    }

    public InputCalendar getInputModel() {
        return inputModel;

    }

    public OutputCalendar getOutputModel() {
        return outputModel;

    }

}
