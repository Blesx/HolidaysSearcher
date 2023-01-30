package major_project.model.calendar.input;

import major_project.model.Holiday;
import major_project.model.MusicPlayer;
import major_project.model.WordMatcher;

import java.time.LocalDate;
import java.util.List;

public interface InputCalendar {
    /**
     * Gets currently selected country in abbreviated form
     */
    String getCountryAbv();

    /**
     * Sets the country to extract holidays from
     */
    void setCountryAbv(String countryAbv);

    /**
     * Checks the chosen date if there is a holiday
     */
    boolean checkDateForHoliday(LocalDate date);

    /**
     * Gets the list of holidays on the chosen date
     */
    List<Holiday> getHolidaysOnDay(LocalDate date);

    /**
     * Gets the earliest date a user can search
     */
    LocalDate getMinDate();

    /**
     * Gets the latest date a user can search
     */
    LocalDate getMaxDate();

    /**
     * Gets the 1st of the month and year chosen by the user. Default is the current date.
     */
    LocalDate getCurrentDate();

    /**
     * Sets the date to the 1st of the month and year chosen by the user
     */
    void setCurrentDate(LocalDate date);

    /**
     * Gets a list of all holidays discovered by the user in the month
     */
    List<Holiday> getHolidaysInMonth();

    /**
     * Empties the tracked holidays in the previous month whenever a new date is chosen by the user
     */
    void resetHolidays();

    /**
     * Checks if the date was already checked
     */
    boolean dateIsChecked(LocalDate date);

    /**
     * Checks if an already checked date has a holiday/holidays
     */
    boolean dateHasHoliday(LocalDate date);

    /**
     * Deletes an entry from the sql
     */
    void deleteRecord(LocalDate date);

    /**
     * Gets the music player
     */
    MusicPlayer getMusicPlayer();

    /**
     * Gets the word matcher
     */
    WordMatcher getWordMatcher();

}
