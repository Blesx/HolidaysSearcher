package major_project;

import major_project.model.Holiday;
import major_project.model.TwilioAPIManager;
import major_project.model.calendar.input.InputCalendarOnline;
import major_project.model.calendar.output.OutputCalendarOnline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OnlineOutputTest extends BaseTest {
    InputCalendarOnline inputOnlineMock;
    OutputCalendarOnline outputOnline;
    TwilioAPIManager twilioAPIManager;

    @BeforeEach
    void setupModels() {
        inputOnlineMock = mock(InputCalendarOnline.class);
        twilioAPIManager = mock(TwilioAPIManager.class);
        outputOnline = new OutputCalendarOnline(twilioAPIManager);

    }

    @Test
    void onlineOutput_SendShortReport_HolidaysInMonthSent_ReportGenerated1() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(sadDay);
        holidayList.add(jubilantDay);

        when(inputOnlineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(inputOnlineMock.getCurrentDate()).thenReturn(happyDate);
        when(twilioAPIManager.sendAPIRequest("All days in JANUARY of 2023 with holidays: 20,24")).thenReturn(true);

        outputOnline.sendShortReport(inputOnlineMock.getHolidaysInMonth(), inputOnlineMock.getCurrentDate());
        String expected = "Report sent!";

        assertEquals(expected, outputOnline.sendFeedback());
        verify(inputOnlineMock).getHolidaysInMonth();
        verify(inputOnlineMock).getCurrentDate();
        verify(twilioAPIManager).sendAPIRequest("All days in JANUARY of 2023 with holidays: 20,24");

    }

    @Test
    void onlineOutput_SendShortReport_HolidaysSent_ReportGenerated2() {
        List<Holiday> holidayList = new ArrayList<>();
        LocalDate maxDate = LocalDate.of(2037, 12, 1);

        when(inputOnlineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(inputOnlineMock.getMaxDate()).thenReturn(maxDate);
        when(twilioAPIManager.sendAPIRequest("All days in DECEMBER of 2037 with holidays: ")).thenReturn(false);

        outputOnline.sendShortReport(inputOnlineMock.getHolidaysInMonth(), inputOnlineMock.getMaxDate());
        String expected = "There was a problem sending the report.";

        assertEquals(expected, outputOnline.sendFeedback());
        verify(inputOnlineMock).getHolidaysInMonth();
        verify(inputOnlineMock).getMaxDate();
        verify(twilioAPIManager).sendAPIRequest("All days in DECEMBER of 2037 with holidays: ");

    }

}
