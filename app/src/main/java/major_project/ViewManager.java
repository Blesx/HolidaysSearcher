package major_project;

import javafx.stage.Stage;
import major_project.model.calendar.CalendarModel;
import major_project.model.sms.SMSModel;
import major_project.view.Window;
import major_project.view.WorldMapWindow;

public class ViewManager {
    private final Stage stage;
    private final CalendarModel calendarModel;
    private final SMSModel smsModel;
    private final int viewWidth;
    private final int viewHeight;

    private Window currentView;

    private final int defaultFontSize = 20;

    public ViewManager(Stage stage, CalendarModel calendarModel, SMSModel smsModel, int viewWidth, int viewHeight) {
        this.stage = stage;
        this.calendarModel = calendarModel;
        this.smsModel = smsModel;
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

    public CalendarModel getInputModelSetCountry(String countryAbv) {
        calendarModel.setCountryAbv(countryAbv);
        return calendarModel;

    }

    public CalendarModel getCalendarModel() {
        return calendarModel;

    }

    public SMSModel getSmsModel() {
        return smsModel;

    }

}
