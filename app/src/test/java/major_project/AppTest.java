package major_project;

import major_project.model.Holiday;
import major_project.model.HolidaysAPIManager;
import major_project.model.SQLManager;
import major_project.model.TwilioAPIManager;
import major_project.model.calendar.input.InputCalendarOffline;
import major_project.model.calendar.input.InputCalendarOnline;
import major_project.model.calendar.output.OutputCalendarOffline;
import major_project.model.calendar.output.OutputCalendarOnline;
import major_project.model.chooseForfeit.ChooseForfeit;
import major_project.model.chooseForfeit.ChooseForfeitDefault;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    InputCalendarOffline inputOffline;
    InputCalendarOffline inputOfflineMock;
    OutputCalendarOffline outputOffline;
    InputCalendarOnline inputOnline;
    InputCalendarOnline inputOnlineMock;
    HolidaysAPIManager holidaysAPIManager;
    SQLManager sqlManager;
    OutputCalendarOnline outputOnline;
    TwilioAPIManager twilioAPIManager;
    ChooseForfeit chooseForfeit;

    static Holiday happyDay;
    static Holiday jubilantDay;
    static Holiday sadDay;
    static Holiday chinaDay;
    static Holiday happyChinaDay;
    static Holiday crazyDay;

    static LocalDate happyDate;
    static LocalDate jubilantDate;
    static LocalDate sadDate;
    static LocalDate chinaDate;
    static LocalDate happyChinaDate;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @BeforeAll
    static void setupHolidaysAndDates() {
        happyDay = new Holiday("Happy Day", "", "", "A day for being really happy and stuff...", "RU", "Moscow", "Regional", "5/20/2022", "2022", "5", "20", "Thursday");
        jubilantDay = new Holiday("Jubilant Day", "", "", "Wow! This is what happens when happy is on crack!", "RU", "", "Regional", "5/20/2022", "2022", "5", "20", "Thursday");
        sadDay = new Holiday("Sad Day", "", "", "Sometimes being sad needs to be recognized, well who knows.", "RU", "", "National", "5/24/2022", "2022", "5", "24", "Monday");
        chinaDay = new Holiday("China Day", "", "", "Why is this a day? Every day is China day.", "CN", "", "National", "4/1/2022", "2022", "4", "1", "Sunday");
        happyChinaDay = new Holiday("Happy China Day", "", "", "The same day as Russia.", "CN", "", "National", "5/20/2022", "2022", "5", "20", "Thursday");
        crazyDay = new Holiday("&&#$^^# 999", "", "", "Why is this a day? Every day is China day.", "CN", "", "National", "4/1/2022", "2022", "4", "1", "Sunday");

        happyDate = LocalDate.of(2022, 5, 20);
        jubilantDate = LocalDate.of(2022, 5, 20);
        sadDate = LocalDate.of(2022, 5, 24);
        chinaDate = LocalDate.of(2022, 4, 1);
        happyChinaDate = LocalDate.of(2022, 5, 20);

    }

    @BeforeEach
    void setupModels() {
        inputOffline = new InputCalendarOffline();
        inputOfflineMock = mock(InputCalendarOffline.class);
        outputOffline = new OutputCalendarOffline();
        holidaysAPIManager = mock(HolidaysAPIManager.class);
        sqlManager = mock(SQLManager.class);
        inputOnline = new InputCalendarOnline(holidaysAPIManager, sqlManager);
        inputOnlineMock = mock(InputCalendarOnline.class);
        twilioAPIManager = mock(TwilioAPIManager.class);
        outputOnline = new OutputCalendarOnline(twilioAPIManager);
        chooseForfeit = new ChooseForfeitDefault();

    }

    /**
     * DummyInput tests
     */

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

    /**
     * DummyOutput tests
     */

    @Test
    void dummyOutput_SendShortReport_HolidaysInMonthSent_ReportGenerated1() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(sadDay);
        holidayList.add(jubilantDay);

        when(inputOfflineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(inputOfflineMock.getCurrentDate()).thenReturn(happyDate);

        outputOffline.sendShortReport(inputOfflineMock.getHolidaysInMonth(), inputOfflineMock.getCurrentDate());
        String expected = "All days in MAY of 2022 with holidays: 20,24";

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

    /**
     * OnlineInput tests
     */

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

    @Test
    void onlineInput_checkHolidaysMatchForfeit_NoMatches_ReturnedFalse() {
        String toMatch = "apy";
        chooseForfeit.setForfeitString(inputOnline, toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(happyDay);
        holidays.add(jubilantDay);

        assertFalse(inputOnline.checkHolidaysMatchForfeit(holidays));

    }

    @Test
    void onlineInput_checkHolidaysMatchForfeit_MatchFound_ReturnedTrue() {
        String toMatch = "# 9";
        chooseForfeit.setForfeitString(inputOnline, toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(crazyDay);

        assertTrue(inputOnline.checkHolidaysMatchForfeit(holidays));

        String toMatch2 = "&&#$";
        chooseForfeit.setForfeitString(inputOnline, toMatch2);
        assertTrue(inputOnline.checkHolidaysMatchForfeit(holidays));

    }

    @Test
    void onlineInput_checkHolidaysMatchForfeit2_MatchFound_ReturnedTrue() {
        String toMatch = "jubilant";
        chooseForfeit.setForfeitString(inputOnline, toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(happyDay);
        holidays.add(jubilantDay);

        assertTrue(inputOnline.checkHolidaysMatchForfeit(holidays));

    }

    /**
     * OnlineOutput tests
     */

    @Test
    void onlineOutput_SendShortReport_HolidaysInMonthSent_ReportGenerated1() {
        List<Holiday> holidayList = new ArrayList<>();
        holidayList.add(happyDay);
        holidayList.add(sadDay);
        holidayList.add(jubilantDay);

        when(inputOnlineMock.getHolidaysInMonth()).thenReturn(holidayList);
        when(inputOnlineMock.getCurrentDate()).thenReturn(happyDate);
        when(twilioAPIManager.sendAPIRequest("All days in MAY of 2022 with holidays: 20,24")).thenReturn(true);

        outputOnline.sendShortReport(inputOnlineMock.getHolidaysInMonth(), inputOnlineMock.getCurrentDate());
        String expected = "Report sent!";

        assertEquals(expected, outputOnline.sendFeedback());
        verify(inputOnlineMock).getHolidaysInMonth();
        verify(inputOnlineMock).getCurrentDate();
        verify(twilioAPIManager).sendAPIRequest("All days in MAY of 2022 with holidays: 20,24");

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

    /**
     * ChooseForfeit model tests
     */

    @Test
    void chooseForfeit_CheckString_ValidInput_ReturnedTrue() {
        String good1 = "day";
        String good2 = "hap34234";
        String good3 = "a day";
        String good4 = "*hello!";

        assertTrue(chooseForfeit.checkString(good1));
        assertTrue(chooseForfeit.checkString(good2));
        assertTrue(chooseForfeit.checkString(good3));
        assertTrue(chooseForfeit.checkString(good4));

    }

    @Test
    void chooseForfeit_CheckString_WrongInput_ReturnedFalse() {
        String empty = "";
        String spaces1 = "  ";

        assertFalse(chooseForfeit.checkString(empty));
        assertFalse(chooseForfeit.checkString(spaces1));

    }

}