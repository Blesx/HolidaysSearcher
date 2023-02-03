package holidays_searcher;

import holidays_searcher.model.Holiday;
import holidays_searcher.model.WordMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordMatcherTest extends BaseTest {
    WordMatcher wordMatcher;

    @BeforeEach
    void setupModels() {
        wordMatcher = new WordMatcher();

    }

    @Test
    void whenHolidaysHasNoMatches_isMatchWithHolidays_ReturnsFalse() {
        String toMatch = "apy";
        wordMatcher.setWord(toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(happyDay);
        holidays.add(jubilantDay);

        assertFalse(wordMatcher.isMatchWithHolidays(holidays));

    }

    @Test
    void whenHolidaysHasMatch_isMatchWithHolidays_ReturnsTrue() {
        String toMatch = "# 9";
        wordMatcher.setWord(toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(crazyDay);

        assertTrue(wordMatcher.isMatchWithHolidays(holidays));

        String toMatch2 = "&&#$";
        wordMatcher.setWord(toMatch2);
        assertTrue(wordMatcher.isMatchWithHolidays(holidays));

    }

    @Test
    void whenHolidaysHasMatch_isMatchWithHolidays_ReturnsTrue_2() {
        String toMatch = "jubilant";
        wordMatcher.setWord(toMatch);

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(happyDay);
        holidays.add(jubilantDay);

        assertTrue(wordMatcher.isMatchWithHolidays(holidays));

    }

    @Test
    void whenValidWordChosen_isValidWord_ReturnsTrue() {
        String good1 = "day";
        String good2 = "hap34234";
        String good3 = "a day";
        String good4 = "*hello!";

        assertTrue(wordMatcher.isValidWord(good1));
        assertTrue(wordMatcher.isValidWord(good2));
        assertTrue(wordMatcher.isValidWord(good3));
        assertTrue(wordMatcher.isValidWord(good4));

    }

    @Test
    void whenInvalidWordChosen_isValidWord_ReturnsFalse() {
        String empty = "";
        String spaces1 = "  ";

        assertFalse(wordMatcher.isValidWord(empty));
        assertFalse(wordMatcher.isValidWord(spaces1));

    }

}
