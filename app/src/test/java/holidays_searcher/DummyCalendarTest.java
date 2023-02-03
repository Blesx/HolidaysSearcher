package holidays_searcher;

import holidays_searcher.model.Holiday;
import holidays_searcher.model.calendar.CalendarModelDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DummyCalendarTest extends BaseTest {
    CalendarModelDummy calendarModelDummy;

    @BeforeEach
    void setupModels() {
        calendarModelDummy = new CalendarModelDummy();
        calendarModelDummy.setCountryAbv("RU");
        calendarModelDummy.setCurrentDate(fakeCurrentDate);

    }

    @Test
    void whenRussiaChosen_CheckDateForHoliday_ReturnsTrueForRussiaHolidays() {
        assertTrue(calendarModelDummy.checkDateForHoliday(happyDate));
        assertFalse(calendarModelDummy.checkDateForHoliday(chinaDate));

    }

    @Test
    void whenChinaChosen_CheckDateForHoliday_ReturnsTrueForChinaHolidays() {
        calendarModelDummy.setCountryAbv("CN");

        assertTrue(calendarModelDummy.checkDateForHoliday(happyChinaDate));
        assertFalse(calendarModelDummy.checkDateForHoliday(sadDate));

    }

    @Test
    void whenDateHasNoHoliday_CheckDateForHoliday_ReturnsFalse() {
        assertFalse(calendarModelDummy.checkDateForHoliday(noHolidaysDate1));
        assertFalse(calendarModelDummy.checkDateForHoliday(noHolidaysDate2));

    }

    @Test
    void whenDayWithHolidaysSelected_GetHolidaysOnDay_ReturnsHolidays() {
        List<Holiday> expected = new ArrayList<>();
        expected.add(happyDay);
        expected.add(jubilantDay);

        calendarModelDummy.checkDateForHoliday(happyDate);
        List<Holiday> observed = calendarModelDummy.getHolidaysOnDay(happyDate);

        assertEquals(expected.size(), observed.size());

    }

    @Test
    void whenDayWithNoHolidaysSelected_GetHolidaysOnDay_ReturnsEmptyList() {
        calendarModelDummy.resetHolidays();

        List<Holiday> observed = calendarModelDummy.getHolidaysOnDay(happyDate);
        List<Holiday> observed2 = calendarModelDummy.getHolidaysOnDay(noHolidaysDate1);

        assertEquals(observed.size(), 0);
        assertEquals(observed2.size(), 0);

    }

    @Test
    void whenDayWithHolidaySelected_GetHolidaysInMonth_ReturnsHolidays() {
        calendarModelDummy.checkDateForHoliday(sadDate);
        List<Holiday> observed = calendarModelDummy.getHolidaysInMonth();

        assertEquals(1, observed.size());

    }

    @Test
    void whenDayWithMultipleHolidaysSelected_GetHolidaysInMonth_ReturnsHolidays() {
        calendarModelDummy.checkDateForHoliday(sadDate);
        calendarModelDummy.checkDateForHoliday(happyDate);
        List<Holiday> observed = calendarModelDummy.getHolidaysInMonth();

        assertEquals(3, observed.size());

    }

    @Test
    void whenNoHolidaysSelected_GetHolidaysInMonth_ReturnsEmptyList() {
        List<Holiday> observed = calendarModelDummy.getHolidaysInMonth();

        assertEquals(0, observed.size());

    }

    @Test
    void whenTrackedDates_ResetHolidays_ResetsListsToEmpty() {
        calendarModelDummy.checkDateForHoliday(sadDate);
        calendarModelDummy.checkDateForHoliday(noHolidaysDate1);

        calendarModelDummy.resetHolidays();
        int holidaysFoundListSize = calendarModelDummy.getHolidaysFoundList().size();
        int datesWithNoHolidaysListSize = calendarModelDummy.getDatesWithNoHolidaysList().size();

        assertEquals(0, holidaysFoundListSize);
        assertEquals(0, datesWithNoHolidaysListSize);

    }

    @Test
    void whenNoTrackedDates_ResetHolidays_KeepsEmptyLists() {
        calendarModelDummy.resetHolidays();
        int holidaysFoundListSize = calendarModelDummy.getHolidaysFoundList().size();
        int datesWithNoHolidaysListSize = calendarModelDummy.getDatesWithNoHolidaysList().size();

        assertEquals(0, holidaysFoundListSize);
        assertEquals(0, datesWithNoHolidaysListSize);

    }

    @Test
    void whenHolidaysHaveNotBeenSelected_DateIsChecked_ReturnsFalse() {
        assertFalse(calendarModelDummy.dateIsChecked(happyDate));
        assertFalse(calendarModelDummy.dateIsChecked(noHolidaysDate1));

    }

    @Test
    void whenHolidaysHaveBeenSelected_DateIsChecked_ReturnsTrue() {
        calendarModelDummy.checkDateForHoliday(happyDate);
        calendarModelDummy.checkDateForHoliday(noHolidaysDate1);

        assertTrue(calendarModelDummy.dateIsChecked(happyDate));
        assertTrue(calendarModelDummy.dateIsChecked(noHolidaysDate1));

    }

    @Test
    void whenDateHasHolidayAndFound_DateHasHoliday_ReturnsTrue() {
        calendarModelDummy.checkDateForHoliday(sadDate);

        assertTrue(calendarModelDummy.checkDateForHoliday(sadDate));

    }

    @Test
    void whenDateHasHolidayButNotFound_DateHasHoliday_ReturnsFalse() {
        assertFalse(calendarModelDummy.dateHasHoliday(sadDate));

    }

    @Test
    void whenDateHasNoHoliday_DateHasHoliday_ReturnsFalse() {
        assertFalse(calendarModelDummy.dateHasHoliday(noHolidaysDate1));

    }

}
