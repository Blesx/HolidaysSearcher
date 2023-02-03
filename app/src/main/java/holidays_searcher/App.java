package holidays_searcher;

import javafx.application.Application;
import javafx.stage.Stage;
import holidays_searcher.model.apis.HolidaysAPIManager;
import holidays_searcher.model.apis.SQLManager;
import holidays_searcher.model.apis.TwilioAPIManager;
import holidays_searcher.model.calendar.CalendarModel;
import holidays_searcher.model.calendar.CalendarModelDummy;
import holidays_searcher.model.calendar.CalendarModelOnline;
import holidays_searcher.model.sms.SMSModel;
import holidays_searcher.model.sms.SMSModelDummy;
import holidays_searcher.model.sms.SMSModelOnline;

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
