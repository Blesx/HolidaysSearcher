package holidays_searcher.model.calendar;

import holidays_searcher.model.Holiday;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarModelDummy extends CalendarModel {
    private List<Holiday> holidaysRU;
    private List<Holiday> holidaysCN;

    private List<Holiday> holidaysFoundList;
    private List<LocalDate> datesWithNoHolidaysList;

    public CalendarModelDummy() {
        holidaysFoundList = new ArrayList<>();
        datesWithNoHolidaysList = new ArrayList<>();

        buildRUList();
        buildCNList();

    }

    @Override
    public boolean checkDateForHoliday(LocalDate date) {
        // if date has not been checked
        if (!dateIsChecked(date)) {
            List<Holiday> holidays = checkForHolidays(date);

            if (holidays != null && !holidays.isEmpty()) {
                return true;

            }
            datesWithNoHolidaysList.add(date);
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

    @Override
    public List<Holiday> getHolidaysOnDay(LocalDate date) {
        List<Holiday> hols = new ArrayList<>();

        for (Holiday holiday : holidaysFoundList) {
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
                holidaysFoundList.add(holiday);
                hols.add(holiday);

            }

        }

        return hols;

    }

    private void buildRUList() {
        holidaysRU = new ArrayList<>();
        holidaysRU.add(new Holiday("Happy Day", "", "", "A day for being really happy and stuff...", "RU", "Moscow", "Regional", "01/20/2023", "2023", "1", "20", "Thursday"));
        holidaysRU.add(new Holiday("Sad Day", "", "", "Sometimes being sad needs to be recognized, well who knows.", "RU", "", "National", "01/24/2023", "2023", "1", "24", "Monday"));
        holidaysRU.add(new Holiday("Jubilant Day", "", "", "Wow! This is what happens when happy is on crack!", "RU", "", "Regional", "01/20/2023", "2023", "1", "20", "Thursday"));

    }

    private void buildCNList() {
        holidaysCN = new ArrayList<>();
        holidaysCN.add(new Holiday("China Day", "", "", "Why is this a day? Every day is China day.", "CN", "", "National", "04/01/2022", "2022", "4", "1", "Sunday"));
        holidaysCN.add(new Holiday("Happy China Day", "", "", "The same day as Russia.", "CN", "", "National", "05/20/2022", "2022", "5", "20", "Thursday"));
        holidaysCN.add(new Holiday("Beijing Day", "", "", "When Beijing became the greatest capital in the world.", "CN", "", "Regional", "08/22/1970", "1970", "8", "22", "Monday"));

    }

    @Override
    public List<Holiday> getHolidaysInMonth() {
        List<Holiday> holidaysInMonth = new ArrayList<>();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        for (Holiday holiday : holidaysFoundList) {
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
        holidaysFoundList = new ArrayList<>();
        datesWithNoHolidaysList = new ArrayList<>();

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

    @Override
    public boolean dateHasHoliday(LocalDate date) {
        for (Holiday holiday : holidaysFoundList) {
            if (holiday.getDate().equals(date.format(formatter))) {
                return true;

            }

        }

        return false;

    }

    public boolean dateHasNoHoliday(LocalDate date) {
        for (LocalDate checkedDate : datesWithNoHolidaysList) {
            if (checkedDate.equals(date)) {
                return true;

            }

        }

        return false;

    }

    @Override
    public void deleteRecord(LocalDate date) {}

    public List<Holiday> getHolidaysFoundList() {
        return holidaysFoundList;

    }

    public List<LocalDate> getDatesWithNoHolidaysList() {
        return datesWithNoHolidaysList;

    }

}
