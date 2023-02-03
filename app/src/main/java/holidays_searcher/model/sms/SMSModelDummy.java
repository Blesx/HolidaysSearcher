package holidays_searcher.model.sms;

import holidays_searcher.model.Holiday;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class SMSModelDummy extends SMSModel {
    @Override
    public void sendShortReport(List<Holiday> holidaysInMonth, LocalDate currentDate) {
        this.holidaysInMonth = holidaysInMonth;
        yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        report = generateShortReport();

    }

    @Override
    public String sendFeedback() {
        return report;
    }

}
