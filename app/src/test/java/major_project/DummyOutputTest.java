package major_project;

import major_project.model.Holiday;
import major_project.model.calendar.input.InputCalendarOffline;
import major_project.model.calendar.output.OutputCalendarOffline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DummyOutputTest extends BaseTest {
    InputCalendarOffline inputOfflineMock;
    OutputCalendarOffline outputOffline;

    @BeforeEach
    void setupModels() {
        inputOfflineMock = mock(InputCalendarOffline.class);
        outputOffline = new OutputCalendarOffline();

    }

    @Test
    void dummyOutput_SendShortReport_HolidaysInMonthSent_ReportGenerated1() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(sadDay);
        holidayList.add(jubilantDay);

        when(inputOfflineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(inputOfflineMock.getCurrentDate()).thenReturn(happyDate);

        outputOffline.sendShortReport(inputOfflineMock.getHolidaysInMonth(), inputOfflineMock.getCurrentDate());
        String expected = "All days in JANUARY of 2023 with holidays: 20,24";

        assertEquals(expected, outputOffline.sendFeedback());
        verify(inputOfflineMock).getHolidaysInMonth();
        verify(inputOfflineMock).getCurrentDate();

    }

    @Test
    void dummyOutput_SendShortReport_HolidaysSent_ReportGenerated2() {
        List<Holiday> holidayList = new ArrayList<>();
        LocalDate maxDate = LocalDate.of(2037, 12, 1);

        when(inputOfflineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(inputOfflineMock.getMaxDate()).thenReturn(maxDate);

        outputOffline.sendShortReport(inputOfflineMock.getHolidaysInMonth(), inputOfflineMock.getMaxDate());
        String expected = "All days in DECEMBER of 2037 with holidays: ";

        assertEquals(expected, outputOffline.sendFeedback());
        verify(inputOfflineMock).getHolidaysInMonth();
        verify(inputOfflineMock).getMaxDate();

    }

}
