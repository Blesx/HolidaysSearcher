package holidays_searcher;

import holidays_searcher.model.Holiday;
import holidays_searcher.model.apis.TwilioAPIManager;
import holidays_searcher.model.calendar.CalendarModelOnline;
import holidays_searcher.model.sms.SMSModelOnline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OnlineSMSTest extends BaseTest {
    CalendarModelOnline calendarModelOnlineMock;
    SMSModelOnline smsModelOnline;
    TwilioAPIManager twilioAPIManager;

    @BeforeEach
    void setupModels() {
        calendarModelOnlineMock = mock(CalendarModelOnline.class);
        twilioAPIManager = mock(TwilioAPIManager.class);
        smsModelOnline = new SMSModelOnline(twilioAPIManager);

    }

    @Test
    void whenHolidaysSelected_SendShortReport_ReturnsReportWithHolidays() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(sadDay);
        holidayList.add(jubilantDay);

        when(calendarModelOnlineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(calendarModelOnlineMock.getCurrentDate()).thenReturn(happyDate);
        when(twilioAPIManager.sendAPIRequest("All days in JANUARY of 2023 with holidays: 20,24")).thenReturn(true);

        smsModelOnline.sendShortReport(calendarModelOnlineMock.getHolidaysInMonth(), calendarModelOnlineMock.getCurrentDate());
        String expected = "Report sent!";

        assertEquals(expected, smsModelOnline.sendFeedback());
        verify(calendarModelOnlineMock).getHolidaysInMonth();
        verify(calendarModelOnlineMock).getCurrentDate();
        verify(twilioAPIManager).sendAPIRequest("All days in JANUARY of 2023 with holidays: 20,24");

    }

    @Test
    void whenNoHolidaysSelected_SendShortReport_ReturnsErrorReport() {
        List<Holiday> holidayList = new ArrayList<>();
        LocalDate maxDate = LocalDate.of(2037, 12, 1);

        when(calendarModelOnlineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(calendarModelOnlineMock.getMaxDate()).thenReturn(maxDate);
        when(twilioAPIManager.sendAPIRequest("All days in DECEMBER of 2037 with holidays: ")).thenReturn(false);

        smsModelOnline.sendShortReport(calendarModelOnlineMock.getHolidaysInMonth(), calendarModelOnlineMock.getMaxDate());
        String expected = "There was a problem sending the report.";

        assertEquals(expected, smsModelOnline.sendFeedback());
        verify(calendarModelOnlineMock).getHolidaysInMonth();
        verify(calendarModelOnlineMock).getMaxDate();
        verify(twilioAPIManager).sendAPIRequest("All days in DECEMBER of 2037 with holidays: ");

    }

}
