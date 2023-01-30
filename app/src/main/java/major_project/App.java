package major_project;

import javafx.application.Application;
import javafx.stage.Stage;
import major_project.model.HolidaysAPIManager;
import major_project.model.SQLManager;
import major_project.model.TwilioAPIManager;
import major_project.model.calendar.input.InputCalendar;
import major_project.model.calendar.input.InputCalendarOffline;
import major_project.model.calendar.input.InputCalendarOnline;
import major_project.model.calendar.output.OutputCalendar;
import major_project.model.calendar.output.OutputCalendarOffline;
import major_project.model.calendar.output.OutputCalendarOnline;

import java.util.List;

public class App extends Application {
    private InputCalendar inputModel;
    private OutputCalendar outputModel;
    private ViewManager viewManager;

    private final int viewWidth = 1080;
    private final int viewHeight = 720;

    @Override
    public void start(Stage stage) {
        // gets parameters
        List<String> parameters = getParameters().getRaw();

        if (parameters.size() != 2) {
            System.out.println("Wrong number of arguments.");
            System.exit(0);

        }

        String arg = parameters.get(0) + " " + parameters.get(1);
        System.out.println("arg:" + arg);

        // sets up input and output models
        switch (arg) {
            case "online online":
                inputModel = new InputCalendarOnline(new HolidaysAPIManager(), new SQLManager());
                outputModel = new OutputCalendarOnline(new TwilioAPIManager());
                break;
            case "offline online":
                inputModel = new InputCalendarOffline();
                outputModel = new OutputCalendarOnline(new TwilioAPIManager());
                break;
            case "online offline":
                inputModel = new InputCalendarOnline(new HolidaysAPIManager(), new SQLManager());
                outputModel = new OutputCalendarOffline();
                break;
            case "offline offline":
                inputModel = new InputCalendarOffline();
                outputModel = new OutputCalendarOffline();
                break;
            default:
                System.out.println("Invalid argument. Please use either online/offline to specify mode.");
                System.exit(0);

        }

        viewManager = new ViewManager(stage, inputModel, outputModel, viewWidth, viewHeight);
        stage.setTitle("Holidays Lookup");
        stage.show();

    }

    public void main(String[] args) {
        launch(args);

    }

}
