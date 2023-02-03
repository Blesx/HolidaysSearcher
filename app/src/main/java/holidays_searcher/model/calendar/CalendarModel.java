package holidays_searcher.model.calendar;

import holidays_searcher.model.Holiday;
import holidays_searcher.model.MusicPlayer;
import holidays_searcher.model.WordMatcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class CalendarModel {
    protected final MusicPlayer musicPlayer;
    protected final WordMatcher wordMatcher;

    protected String countryAbv;

    // dates
    protected final LocalDate minDate = LocalDate.of(1970, 1, 1);
    protected final LocalDate maxDate = LocalDate.of(2037, 12, 31);
    protected LocalDate currentDate = LocalDate.now();

    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public CalendarModel() {
        musicPlayer = new MusicPlayer();
        wordMatcher = new WordMatcher();

    }

    /**
     * Checks the chosen date if there is a holiday
     */
    public abstract boolean checkDateForHoliday(LocalDate date);

    /**
     * Gets the list of holidays on the chosen date
     */
    public abstract List<Holiday> getHolidaysOnDay(LocalDate date);

    /**
     * Gets a list of all holidays discovered by the user in the month
     */
    public abstract List<Holiday> getHolidaysInMonth();

    /**
     * Empties the tracked holidays in the previous month whenever a new date is chosen by the user
     */
    public abstract void resetHolidays();

    /**
     * Checks if the date was already checked
     */
    public abstract boolean dateIsChecked(LocalDate date);

    /**
     * Checks if an already checked date has a holiday/holidays
     */
    public abstract boolean dateHasHoliday(LocalDate date);

    /**
     * Deletes a record from the tracked dates
     */
    public abstract void deleteRecord(LocalDate date);


    /**
     * Getter & setter methods
     */
    public String getCountryAbv() {
        return countryAbv;

    }

    public void setCountryAbv(String countryAbv) {
        this.countryAbv = countryAbv;

    }

    public LocalDate getMinDate() {
        return minDate;

    }

    public LocalDate getMaxDate() {
        return maxDate;

    }

    public LocalDate getCurrentDate() {
        return currentDate;

    }

    public void setCurrentDate(LocalDate date) {
        this.currentDate = date;

    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;

    }

    public WordMatcher getWordMatcher() {
        return wordMatcher;

    }

}
