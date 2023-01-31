package major_project;

import javafx.application.Application;
import javafx.stage.Stage;
import major_project.model.apis.HolidaysAPIManager;
import major_project.model.apis.SQLManager;
import major_project.model.apis.TwilioAPIManager;
import major_project.model.calendar.CalendarModel;
import major_project.model.calendar.CalendarModelDummy;
import major_project.model.calendar.CalendarModelOnline;
import major_project.model.sms.SMSModel;
import major_project.model.sms.SMSModelDummy;
import major_project.model.sms.SMSModelOnline;

import java.util.List;

public class App extends Application {
    private CalendarModel calendarModel;
    private SMSModel smsModel;
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
                calendarModel = new CalendarModelOnline(new HolidaysAPIManager(), new SQLManager());
                smsModel = new SMSModelOnline(new TwilioAPIManager());
                break;
            case "offline online":
                calendarModel = new CalendarModelDummy();
                smsModel = new SMSModelOnline(new TwilioAPIManager());
                break;
            case "online offline":
                calendarModel = new CalendarModelOnline(new HolidaysAPIManager(), new SQLManager());
                smsModel = new SMSModelDummy();
                break;
            case "offline offline":
                calendarModel = new CalendarModelDummy();
                smsModel = new SMSModelDummy();
                break;
            default:
                System.out.println("Invalid argument. Please use either online/offline to specify mode.");
                System.exit(0);

        }

        viewManager = new ViewManager(stage, calendarModel, smsModel, viewWidth, viewHeight);
        stage.setTitle("Holidays Lookup");
        stage.show();

    }

    public void main(String[] args) {
        launch(args);

    }

}
