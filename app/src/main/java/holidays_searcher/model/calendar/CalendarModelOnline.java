package holidays_searcher.model.calendar;

import holidays_searcher.model.*;
import holidays_searcher.model.apis.HolidaysAPIManager;
import holidays_searcher.model.apis.SQLManager;

import java.time.LocalDate;
import java.util.List;

public class CalendarModelOnline extends CalendarModel {
    private final HolidaysAPIManager holidaysAPIManager;
    private final SQLManager sqlManager;

    public CalendarModelOnline(HolidaysAPIManager holidaysAPIManager, SQLManager sqlManager) {
        this.holidaysAPIManager = holidaysAPIManager;
        this.sqlManager = sqlManager;

    }

    @Override
    public boolean checkDateForHoliday(LocalDate date) {
        String formattedDate = date.format(formatter);

        if (!sqlManager.dateInDatabase(formattedDate, countryAbv)) {
            List<Holiday> holidays = holidaysAPIManager.sendAPIRequest(date, countryAbv);
            if (holidays != null && !holidays.isEmpty()) {
                sqlManager.cacheHolidays(holidays);
                return true;

            }
            sqlManager.cacheDate(formattedDate, countryAbv);
            return false;

        }

        // if date has holiday
        if (dateHasHoliday(date)) {
            System.out.println("using cache");
            return true;

        }

        return false;

    }

    @Override
    public List<Holiday> getHolidaysOnDay(LocalDate date) {
        String formattedDate = date.format(formatter);
        return sqlManager.getHolidaysOnDay(formattedDate, countryAbv);

    }

    @Override
    public List<Holiday> getHolidaysInMonth() {
        String sYear = String.format("%02d", currentDate.getYear());
        String sMonth = String.format("%02d", currentDate.getMonthValue());
        return sqlManager.getHolidaysInMonth(sYear, sMonth, countryAbv);

    }

    @Override
    public void resetHolidays() {
        sqlManager.resetDatabase();

    }

    @Override
    public boolean dateIsChecked(LocalDate date) {
        String formattedDate = date.format(formatter);
        return sqlManager.dateInDatabase(formattedDate, countryAbv);

    }

    @Override
    public boolean dateHasHoliday(LocalDate date) {
        String formattedDate = date.format(formatter);
        return sqlManager.dateHasHoliday(formattedDate, countryAbv);

    }

    @Override
    public void deleteRecord(LocalDate date) {
        String formattedDate = date.format(formatter);
        sqlManager.deleteEntry(formattedDate, countryAbv);

    }

}
