package major_project.model.calendar.input;

import major_project.model.Holiday;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InputCalendarOffline implements InputCalendar {
    private String countryAbv;
    private String forfeitWord;

    private final LocalDate minDate = LocalDate.of(1970, 1, 1);
    private final LocalDate maxDate = LocalDate.of(2037, 12, 31);
    private LocalDate currentDate = LocalDate.now();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    private List<Holiday> holidaysRU;
    private List<Holiday> holidaysCN;

    private List<Holiday> datesWithHolidays;
    private List<LocalDate> noHolidays;

    public InputCalendarOffline() {
        datesWithHolidays = new ArrayList<>();
        noHolidays = new ArrayList<>();

        buildRUList();
        buildCNList();

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
        // if date has not been checked
        if (!dateIsChecked(date)) {
            List<Holiday> holidays = checkForHolidays(date);

            if (holidays != null && !holidays.isEmpty()) {
                return true;

            }
            noHolidays.add(date);
            return false;

        }

        return dateHasHoliday(date);

    }

    private List<Holiday> checkForHolidays(LocalDate date) {
        if (countryAbv.equals("RU")) {
            return getHolidaysFromCountry(date, holidaysRU);

        } else if (countryAbv.equals("CN")) {
            return getHolidaysFromCountry(date, holidaysCN);

        }

        return null;

    }

    public List<Holiday> getHolidaysOnDay(LocalDate date) {
        List<Holiday> hols = new ArrayList<>();

        for (Holiday holiday : datesWithHolidays) {
            if (holiday.getDate().equals(date.format(formatter))) {
                hols.add(holiday);

            }

        }

        return hols;

    }

    private List<Holiday> getHolidaysFromCountry(LocalDate date, List<Holiday> listOfHols) {
        List<Holiday> hols = new ArrayList<>();

        for (Holiday holiday : listOfHols) {
            if (holiday.getDate().equals(date.format(formatter))) {
                datesWithHolidays.add(holiday);
                hols.add(holiday);

            }

        }

        return hols;

    }

    private void buildRUList() {
        holidaysRU = new ArrayList<>();
        holidaysRU.add(new Holiday("Happy Day", "", "", "A day for being really happy and stuff...", "RU", "Moscow", "Regional", "1/20/2023", "2023", "1", "20", "Thursday"));
        holidaysRU.add(new Holiday("Sad Day", "", "", "Sometimes being sad needs to be recognized, well who knows.", "RU", "", "National", "1/24/2023", "2023", "1", "24", "Monday"));
        holidaysRU.add(new Holiday("Jubilant Day", "", "", "Wow! This is what happens when happy is on crack!", "RU", "", "Regional", "1/20/2023", "2023", "1", "20", "Thursday"));

    }

    private void buildCNList() {
        holidaysCN = new ArrayList<>();
        holidaysCN.add(new Holiday("China Day", "", "", "Why is this a day? Every day is China day.", "CN", "", "National", "4/1/2022", "2022", "4", "1", "Sunday"));
        holidaysCN.add(new Holiday("Happy China Day", "", "", "The same day as Russia.", "CN", "", "National", "5/20/2022", "2022", "5", "20", "Thursday"));
        holidaysCN.add(new Holiday("Beijing Day", "", "", "When Beijing became the greatest capital in the world.", "CN", "", "Regional", "8/22/1970", "1970", "8", "22", "Monday"));

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

    @Override
    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;

    }

    @Override
    public List<Holiday> getHolidaysInMonth() {
        List<Holiday> holidaysInMonth = new ArrayList<>();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        for (Holiday holiday : datesWithHolidays) {
            int holYear = Integer.parseInt(holiday.getDate_year());
            int holMonth = Integer.parseInt(holiday.getDate_month());

            if (currentYear == holYear && currentMonth == holMonth) {
                holidaysInMonth.add(holiday);

            }

        }

        return holidaysInMonth;

    }

    @Override
    public void resetHolidays() {
        datesWithHolidays = new ArrayList<>();
        noHolidays = new ArrayList<>();

    }

    @Override
    public boolean dateIsChecked(LocalDate date) {
        // check through dates with holidays
        if (dateHasHoliday(date)) {
            return true;

        }

        // check through dates with no holidays
        return dateHasNoHoliday(date);

    }

    public boolean dateHasHoliday(LocalDate date) {
        for (Holiday holiday : datesWithHolidays) {
            if (holiday.getDate().equals(date.format(formatter))) {
                return true;

            }

        }

        return false;

    }

    @Override
    public void deleteRecord(LocalDate date) {


    }

    @Override
    public void setForfeitWord(String word) {
        forfeitWord = word;

    }

    @Override
    public String getForfeitWord() {
        return forfeitWord;

    }

    @Override
    public boolean checkHolidaysMatchForfeit(List<Holiday> holidays) {
        String lowercaseForfeitWord = forfeitWord.toLowerCase();

        for (Holiday holiday : holidays) {
            String holidayName = holiday.getName().toLowerCase();

            if (holidayName.contains(lowercaseForfeitWord)) {
                return true;

            }

        }

        return false;

    }

    public boolean dateHasNoHoliday(LocalDate date) {
        for (LocalDate checkedDate : noHolidays) {
            if (checkedDate.equals(date)) {
                return true;

            }

        }

        return false;

    }

}
