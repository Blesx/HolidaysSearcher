package holidays_searcher;

import javafx.stage.Stage;
import holidays_searcher.model.calendar.CalendarModel;
import holidays_searcher.model.sms.SMSModel;
import holidays_searcher.view.Window;
import holidays_searcher.view.WorldMapWindow;

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
