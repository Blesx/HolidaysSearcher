package holidays_searcher.model.sms;

import holidays_searcher.model.Holiday;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public abstract class SMSModel {
    protected List<Holiday> holidaysInMonth;
    protected YearMonth yearMonth;
    protected String report;

    /**
     * Sends a short report to the phone number via Twilio specified by the user's env variable - TWILIO_API_TO
     */
    public abstract void sendShortReport(List<Holiday> holidaysInMonth, LocalDate currentDate);

    /**
     * Returns the user a message if the report was sent or not
     */
    public abstract String sendFeedback();

    protected String generateShortReport() {
        List<String> uniqueDays = new ArrayList<>();
        String days = "";

        if (holidaysInMonth != null && !holidaysInMonth.isEmpty()) {
            for (Holiday holiday : holidaysInMonth) {
                if (dayIsUnique(uniqueDays, holiday.getDate_day())) {
                    uniqueDays.add(holiday.getDate_day());
                    days += holiday.getDate_day() + ",";

                }

            }
            days = days.substring(0, days.length() - 1);

        }

        return "All days in " + yearMonth.getMonth() + " of " + yearMonth.getYear() + " with holidays: " + days;

    }

    protected boolean dayIsUnique(List<String> uniqueDays, String day) {
        for (String holiday : uniqueDays) {
            if (holiday.equals(day)) {
                return false;

            }

        }
        return true;

    }

}
