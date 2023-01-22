package major_project;

import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import major_project.model.calendar.input.InputCalendar;
import major_project.model.calendar.output.OutputCalendar;
import major_project.model.chooseForfeit.ChooseForfeit;
import major_project.model.chooseForfeit.ChooseForfeitDefault;
import major_project.view.ChooseForfeitWindow;
import major_project.view.Window;
import major_project.view.WorldMapWindow;

public class ViewManager {
    private final Stage stage;
    private final InputCalendar inputModel;
    private final OutputCalendar outputModel;
    private final MediaPlayer mediaPlayer;
    private final int viewWidth;
    private final int viewHeight;

    private Window currentView;

    private final int defaultFontSize = 20;

    public ViewManager(Stage stage, InputCalendar inputModel, OutputCalendar outputModel, MediaPlayer mediaPlayer, int viewWidth, int viewHeight) {
        this.stage = stage;
        this.inputModel = inputModel;
        this.outputModel = outputModel;
        this.mediaPlayer = mediaPlayer;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        currentView = new ChooseForfeitWindow(this, new ChooseForfeitDefault(), mediaPlayer);
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

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;

    }

}
