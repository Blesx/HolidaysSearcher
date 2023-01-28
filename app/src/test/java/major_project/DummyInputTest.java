package major_project;

import major_project.model.Holiday;
import major_project.model.calendar.input.InputCalendarOffline;
import major_project.model.chooseForfeit.ChooseForfeit;
import major_project.model.chooseForfeit.ChooseForfeitDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DummyInputTest extends BaseTest {
    InputCalendarOffline inputOffline;
    ChooseForfeit chooseForfeit;

    @BeforeEach
    void setupModels() {
        inputOffline = new InputCalendarOffline();
        chooseForfeit = new ChooseForfeitDefault();

    }

    @Test
    void dummyInput_CheckDateForHoliday_RussiaChosen_CalendarCorrect() {
        inputOffline.setCountryAbv("RU");

        assertTrue(inputOffline.checkDateForHoliday(happyDate));
        assertFalse(inputOffline.checkDateForHoliday(chinaDate));

    }

    @Test
    void dummyInput_CheckDateForHoliday_ChinaChosen_CalendarCorrect() {
        inputOffline.setCountryAbv("CN");

        assertTrue(inputOffline.checkDateForHoliday(happyChinaDate));
        assertFalse(inputOffline.checkDateForHoliday(sadDate));

    }

    @Test
    void dummyInput_DateIsChecked_DatesChosen_ClickedDatesCorrect() {
        inputOffline.setCountryAbv("RU");
        LocalDate noHolidayDate = LocalDate.of(2022, 12, 12);

        assertFalse(inputOffline.dateIsChecked(happyDate));
        assertFalse(inputOffline.dateIsChecked(noHolidayDate));

        inputOffline.checkDateForHoliday(happyDate);
        inputOffline.checkDateForHoliday(noHolidayDate);

        assertTrue(inputOffline.dateIsChecked(happyDate));
        assertTrue(inputOffline.dateIsChecked(noHolidayDate));

    }

    @Test
    void dummyInput_GetHolidaysOnDay_DayChosen_ReturnedHolidaysList() {
        List<Holiday> expected = new ArrayList<>();
        expected.add(happyDay);
        expected.add(jubilantDay);

        inputOffline.setCountryAbv("RU");
        inputOffline.checkDateForHoliday(happyDate);
        List<Holiday> observed = inputOffline.getHolidaysOnDay(happyDate);

        assertEquals(expected.size(), observed.size());

    }

    @Test
    void dummyInput_GetHolidaysOnDay_DayChosen_ReturnedEmptyList() {
        inputOffline.setCountryAbv("RU");
        LocalDate randomRate = LocalDate.of(2022, 12, 12);

        inputOffline.resetHolidays();

        List<Holiday> observed = inputOffline.getHolidaysOnDay(happyDate);
        List<Holiday> observed2 = inputOffline.getHolidaysOnDay(randomRate);

        assertEquals(observed.size(), 0);
        assertEquals(observed2.size(), 0);

    }

    @Test
    void dummyInput_checkHolidaysMatchForfeit_NoMatches_ReturnedFalse() {
        String toMatch = "apy";
        chooseForfeit.setForfeitString(inputOffline, toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(happyDay);
        holidays.add(jubilantDay);

        assertFalse(inputOffline.checkHolidaysMatchForfeit(holidays));

    }

    @Test
    void dummyInput_checkHolidaysMatchForfeit_MatchFound_ReturnedTrue() {
        String toMatch = "# 9";
        chooseForfeit.setForfeitString(inputOffline, toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(crazyDay);

        assertTrue(inputOffline.checkHolidaysMatchForfeit(holidays));

        String toMatch2 = "&&#$";
        chooseForfeit.setForfeitString(inputOffline, toMatch2);
        assertTrue(inputOffline.checkHolidaysMatchForfeit(holidays));

    }

    @Test
    void dummyInput_checkHolidaysMatchForfeit2_MatchFound_ReturnedTrue() {
        String toMatch = "jubilant";
        chooseForfeit.setForfeitString(inputOffline, toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(happyDay);
        holidays.add(jubilantDay);

        assertTrue(inputOffline.checkHolidaysMatchForfeit(holidays));

    }

}
