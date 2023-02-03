package holidays_searcher;

import holidays_searcher.model.Holiday;
import holidays_searcher.model.apis.HolidaysAPIManager;
import holidays_searcher.model.apis.SQLManager;
import holidays_searcher.model.calendar.CalendarModelOnline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class OnlineCalendarTest extends BaseTest {
    CalendarModelOnline calendarModelOnline;
    HolidaysAPIManager holidaysAPIManager;
    SQLManager sqlManager;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @BeforeEach
    void setupModels() {
        holidaysAPIManager = mock(HolidaysAPIManager.class);
        sqlManager = mock(SQLManager.class);
        calendarModelOnline = new CalendarModelOnline(holidaysAPIManager, sqlManager);
        calendarModelOnline.setCountryAbv("RU");
        calendarModelOnline.setCurrentDate(fakeCurrentDate);

    }

    @Test
    void whenDateHasHoliday_CheckDateForHoliday_ReturnsTrue() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(jubilantDay);

        when(sqlManager.dateInDatabase(happyDate.format(formatter), "RU")).thenReturn(false);
        when(holidaysAPIManager.sendAPIRequest(happyDate, "RU")).thenReturn(holidayList);

        assertTrue(calendarModelOnline.checkDateForHoliday(happyDate));
        verify(holidaysAPIManager).sendAPIRequest(happyDate, "RU");
        verify(sqlManager).dateInDatabase(happyDate.format(formatter), "RU");

    }

    @Test
    void whenDateHasNoHoliday_CheckDateForHoliday_ReturnsFalse() {
        List<Holiday> emptyList = new ArrayList<>();

        when(sqlManager.dateInDatabase(noHolidaysDate1.format(formatter), "RU")).thenReturn(false);
        when(holidaysAPIManager.sendAPIRequest(noHolidaysDate1, "RU")).thenReturn(emptyList);

        assertFalse(calendarModelOnline.checkDateForHoliday(noHolidaysDate1));
        verify(holidaysAPIManager).sendAPIRequest(noHolidaysDate1, "RU");
        verify(sqlManager).dateInDatabase(noHolidaysDate1.format(formatter), "RU");

    }

    @Test
    void whenDayWithHolidaysChosen_GetHolidaysOnDay_ReturnsHolidays() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(jubilantDay);

        when(holidaysAPIManager.sendAPIRequest(happyDate, "RU")).thenReturn(holidayList);
        calendarModelOnline.checkDateForHoliday(happyDate);
        when(sqlManager.getHolidaysOnDay(happyDate.format(formatter), "RU")).thenReturn(holidayList);
        List<Holiday> observed = calendarModelOnline.getHolidaysOnDay(happyDate);

        assertEquals(holidayList.size(), observed.size());
        verify(holidaysAPIManager).sendAPIRequest(happyDate, "RU");
        verify(sqlManager).getHolidaysOnDay(happyDate.format(formatter), "RU");

    }

    @Test
    void whenDayWithNoHolidaysChosen_GetHolidaysOnDay_ReturnsEmptyList() {
        LocalDate randomDate = LocalDate.of(2022, 12, 12);

        List<Holiday> observed = calendarModelOnline.getHolidaysOnDay(happyDate);
        List<Holiday> observed2 = calendarModelOnline.getHolidaysOnDay(randomDate);

        assertEquals(observed.size(), 0);
        assertEquals(observed2.size(), 0);

    }

    @Test
    void whenDayWithHolidaySelected_GetHolidaysInMonth_ReturnsHolidays() {
        List<Holiday> holidaysList = new ArrayList<>();
        holidaysList.add(happyDay);

        when(sqlManager.getHolidaysInMonth("2023", "01", "RU")).thenReturn(holidaysList);

        assertEquals(holidaysList, calendarModelOnline.getHolidaysInMonth());
        verify(sqlManager).getHolidaysInMonth("2023", "01", "RU");

    }

    @Test
    void whenNoHolidaysSelected_GetHolidaysInMonth_ReturnsEmptyList() {
        List<Holiday> emptyList = new ArrayList<>();

        when(sqlManager.getHolidaysInMonth("2023", "01", "RU")).thenReturn(emptyList);

        assertEquals(emptyList, calendarModelOnline.getHolidaysInMonth());

    }

    @Test
    void whenHolidaysHaveNotBeenSelected_DateIsChecked_ReturnsFalse() {
        when(sqlManager.dateInDatabase(happyDate.format(formatter), "RU")).thenReturn(false);
        when(sqlManager.dateInDatabase(noHolidaysDate1.format(formatter), "RU")).thenReturn(false);

        assertFalse(calendarModelOnline.dateIsChecked(happyDate));
        assertFalse(calendarModelOnline.dateIsChecked(noHolidaysDate1));

    }

    @Test
    void whenHolidaysHaveBeenSelected_DateIsChecked_ReturnsTrue() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(jubilantDay);

        when(holidaysAPIManager.sendAPIRequest(happyDate, "RU")).thenReturn(holidayList);
        calendarModelOnline.checkDateForHoliday(happyDate);

        when(holidaysAPIManager.sendAPIRequest(noHolidaysDate1, "RU")).thenReturn(null);
        calendarModelOnline.checkDateForHoliday(noHolidaysDate1);

        when(sqlManager.dateInDatabase(happyDate.format(formatter), "RU")).thenReturn(true);
        when(sqlManager.dateInDatabase(noHolidaysDate1.format(formatter), "RU")).thenReturn(true);

        assertTrue(calendarModelOnline.dateIsChecked(happyDate));
        assertTrue(calendarModelOnline.dateIsChecked(noHolidaysDate1));
        verify(holidaysAPIManager).sendAPIRequest(happyDate, "RU");
        verify(sqlManager, times(2)).dateInDatabase(happyDate.format(formatter), "RU");
        verify(sqlManager, times(2)).dateInDatabase(noHolidaysDate1.format(formatter), "RU");

    }

    @Test
    void whenDateHasHolidayAndFound_DateHasHoliday_ReturnsTrue() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(jubilantDay);

        when(sqlManager.dateInDatabase(happyDate.format(formatter), "RU")).thenReturn(false);
        when(holidaysAPIManager.sendAPIRequest(happyDate, "RU")).thenReturn(holidayList);
        assertTrue(calendarModelOnline.checkDateForHoliday(happyDate));

        when(sqlManager.dateHasHoliday(happyDate.format(formatter), "RU")).thenReturn(true);

        assertTrue(calendarModelOnline.dateHasHoliday(happyDate));
        verify(sqlManager).dateHasHoliday(happyDate.format(formatter), "RU");

    }

    @Test
    void whenDateHasHolidayButNotFound_DateHasHoliday_ReturnsFalse() {
        when(sqlManager.dateHasHoliday(happyDate.format(formatter), "RU")).thenReturn(false);

        assertFalse(calendarModelOnline.dateHasHoliday(happyDate));
        verify(sqlManager).dateHasHoliday(happyDate.format(formatter), "RU");
    }

    @Test
    void whenDateHasNoHoliday_DateHasHoliday_ReturnsFalse() {
        when(sqlManager.dateHasHoliday(noHolidaysDate1.format(formatter), "RU")).thenReturn(false);

        assertFalse(calendarModelOnline.dateHasHoliday(fakeCurrentDate));
        verify(sqlManager).dateHasHoliday(noHolidaysDate1.format(formatter), "RU");

    }

}
