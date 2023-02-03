package holidays_searcher;

import holidays_searcher.model.Holiday;
import holidays_searcher.model.calendar.CalendarModelDummy;
import holidays_searcher.model.sms.SMSModelDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DummySMSTest extends BaseTest {
    CalendarModelDummy calendarModelDummyMock;
    SMSModelDummy smsModelDummy;

    @BeforeEach
    void setupModels() {
        calendarModelDummyMock = mock(CalendarModelDummy.class);
        smsModelDummy = new SMSModelDummy();

    }

    @Test
    void whenHolidaysSelected_SendShortReport_ReturnsReportWithHolidays() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(sadDay);
        holidayList.add(jubilantDay);

        when(calendarModelDummyMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(calendarModelDummyMock.getCurrentDate()).thenReturn(happyDate);

        smsModelDummy.sendShortReport(calendarModelDummyMock.getHolidaysInMonth(), calendarModelDummyMock.getCurrentDate());
        String expected = "All days in JANUARY of 2023 with holidays: 20,24";

        assertEquals(expected, smsModelDummy.sendFeedback());
        verify(calendarModelDummyMock).getHolidaysInMonth();
        verify(calendarModelDummyMock).getCurrentDate();

    }

    @Test
    void whenNoHolidaysSelected_SendShortReport_ReturnsReportWithNoHolidays() {
        List<Holiday> holidayList = new ArrayList<>();
        LocalDate maxDate = LocalDate.of(2037, 12, 1);

        when(calendarModelDummyMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(calendarModelDummyMock.getMaxDate()).thenReturn(maxDate);

        smsModelDummy.sendShortReport(calendarModelDummyMock.getHolidaysInMonth(), calendarModelDummyMock.getMaxDate());
        String expected = "All days in DECEMBER of 2037 with holidays: ";

        assertEquals(expected, smsModelDummy.sendFeedback());
        verify(calendarModelDummyMock).getHolidaysInMonth();
        verify(calendarModelDummyMock).getMaxDate();

    }

}
