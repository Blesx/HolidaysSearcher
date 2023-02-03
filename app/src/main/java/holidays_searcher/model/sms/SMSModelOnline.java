package holidays_searcher.model.sms;

import holidays_searcher.model.Holiday;
import holidays_searcher.model.apis.TwilioAPIManager;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class SMSModelOnline extends SMSModel {
    private final TwilioAPIManager twilioAPIManager;
    private boolean reportSent;

    public SMSModelOnline(TwilioAPIManager twilioAPIManager) {
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

}
