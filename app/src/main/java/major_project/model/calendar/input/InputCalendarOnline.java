package major_project.model.calendar.input;

import major_project.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InputCalendarOnline implements InputCalendar {
    private final MusicPlayer musicPlayer;
    private final WordMatcher wordMatcher;
    private final HolidaysAPIManager holidaysAPIManager;
    private final SQLManager sqlManager;
    private String countryAbv;

    // dates
    private final LocalDate minDate = LocalDate.of(1970, 1, 1);
    private final LocalDate maxDate = LocalDate.of(2037, 12, 31);
    private LocalDate currentDate = LocalDate.now();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public InputCalendarOnline(HolidaysAPIManager holidaysAPIManager, SQLManager sqlManager) {
        wordMatcher = new WordMatcher();
        musicPlayer = new MusicPlayer();
        this.holidaysAPIManager = holidaysAPIManager;
        this.sqlManager = sqlManager;

    }

    @Override
    public String getCountryAbv() {
        return countryAbv;

    }

    @Override
    public void setCountryAbv(String countryAbv) {
        this.countryAbv = countryAbv;

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
    public LocalDate getMinDate() {
        return minDate;

    }

    @Override
    public LocalDate getMaxDate() {
        return maxDate;

    }

    @Override
    public LocalDate getCurrentDate() {
        return currentDate;

    }

    @Override
    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;

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

    @Override
    public MusicPlayer getMusicPlayer() {
        return musicPlayer;

    }

    @Override
    public WordMatcher getWordMatcher() {
        return wordMatcher;

    }

}
