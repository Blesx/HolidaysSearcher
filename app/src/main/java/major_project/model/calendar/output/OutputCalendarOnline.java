package major_project.model.calendar.output;

import major_project.model.Holiday;
import major_project.model.TwilioAPIManager;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class OutputCalendarOnline implements OutputCalendar {
    private final TwilioAPIManager twilioAPIManager;
    private List<Holiday> holidaysInMonth;
    private YearMonth yearMonth;
    private String report;
    private boolean reportSent;

    public OutputCalendarOnline(TwilioAPIManager twilioAPIManager) {
        this.twilioAPIManager = twilioAPIManager;

    }

    @Override
    public void sendShortReport(List<Holiday> holidaysInMonth, LocalDate currentDate) {
        // generate report
        this.holidaysInMonth = holidaysInMonth;
        yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        report = generateShortReport();

        reportSent = twilioAPIManager.sendAPIRequest(report);

    }

    @Override
    public String sendFeedback() {
        if (reportSent) {
            return "Report sent!";

        }
        return "There was a problem sending the report.";

    }

    private String generateShortReport() {
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

    private boolean dayIsUnique(List<String> uniqueDays, String day) {
        for (String holiday : uniqueDays) {
            if (holiday.equals(day)) {
                return false;

            }

        }
        return true;

    }

}
