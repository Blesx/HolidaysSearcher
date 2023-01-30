package major_project;

import major_project.model.Holiday;
import major_project.model.HolidaysAPIManager;
import major_project.model.SQLManager;
import major_project.model.WordMatcher;
import major_project.model.calendar.input.InputCalendarOnline;
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

public class OnlineInputTest extends BaseTest {
    InputCalendarOnline inputOnline;
    HolidaysAPIManager holidaysAPIManager;
    SQLManager sqlManager;
    WordMatcher wordMatcher;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @BeforeEach
    void setupModels() {
        holidaysAPIManager = mock(HolidaysAPIManager.class);
        sqlManager = mock(SQLManager.class);
        inputOnline = new InputCalendarOnline(holidaysAPIManager, sqlManager);
        wordMatcher = new WordMatcher();

    }

    @Test
    void onlineInput_CheckDateForHolidayOnline_ChooseDate_ReturnTrue() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(jubilantDay);
        inputOnline.setCountryAbv("RU");

        when(holidaysAPIManager.sendAPIRequest(happyDate, "RU")).thenReturn(holidayList);
        when(sqlManager.dateInDatabase(happyDate.format(formatter), "RU")).thenReturn(false);

        assertTrue(inputOnline.checkDateForHoliday(happyDate));
        verify(holidaysAPIManager).sendAPIRequest(happyDate, "RU");
        verify(sqlManager).dateInDatabase(happyDate.format(formatter), "RU");

    }

    @Test
    void onlineInput_DateIsChecked_DatesChosen_ClickedDatesCorrect() {
        inputOnline.setCountryAbv("RU");
        LocalDate noHolidayDate = LocalDate.of(2022, 12, 12);

        when(sqlManager.dateInDatabase(happyDate.format(formatter), "RU")).thenReturn(false);
        when(sqlManager.dateInDatabase(noHolidayDate.format(formatter), "RU")).thenReturn(false);

        assertFalse(inputOnline.dateIsChecked(happyDate));
        assertFalse(inputOnline.dateIsChecked(noHolidayDate));

        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(jubilantDay);

        when(holidaysAPIManager.sendAPIRequest(happyDate, "RU")).thenReturn(holidayList);
        inputOnline.checkDateForHoliday(happyDate);

        when(holidaysAPIManager.sendAPIRequest(noHolidayDate, "RU")).thenReturn(null);
        inputOnline.checkDateForHoliday(noHolidayDate);

        when(sqlManager.dateInDatabase(happyDate.format(formatter), "RU")).thenReturn(true);
        when(sqlManager.dateInDatabase(noHolidayDate.format(formatter), "RU")).thenReturn(true);

        assertTrue(inputOnline.dateIsChecked(happyDate));
        assertTrue(inputOnline.dateIsChecked(noHolidayDate));
        verify(holidaysAPIManager).sendAPIRequest(happyDate, "RU");
        verify(sqlManager, times(3)).dateInDatabase(happyDate.format(formatter), "RU");
        verify(sqlManager, times(3)).dateInDatabase(noHolidayDate.format(formatter), "RU");

    }

    @Test
    void onlineInput_GetHolidaysOnDay_DayChosen_ReturnedHolidaysList() {
        inputOnline.setCountryAbv("RU");
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(jubilantDay);

        when(holidaysAPIManager.sendAPIRequest(happyDate, "RU")).thenReturn(holidayList);
        inputOnline.checkDateForHoliday(happyDate);
        when(sqlManager.getHolidaysOnDay(happyDate.format(formatter), "RU")).thenReturn(holidayList);
        List<Holiday> observed = inputOnline.getHolidaysOnDay(happyDate);

        assertEquals(holidayList.size(), observed.size());
        verify(holidaysAPIManager).sendAPIRequest(happyDate, "RU");
        verify(sqlManager).getHolidaysOnDay(happyDate.format(formatter), "RU");

    }

    @Test
    void onlineInput_GetHolidaysOnDay_DayChosen_ReturnedEmptyList() {
        inputOnline.setCountryAbv("RU");
        LocalDate randomDate = LocalDate.of(2022, 12, 12);

        List<Holiday> observed = inputOnline.getHolidaysOnDay(happyDate);
        List<Holiday> observed2 = inputOnline.getHolidaysOnDay(randomDate);

        assertEquals(observed.size(), 0);
        assertEquals(observed2.size(), 0);

    }

}
