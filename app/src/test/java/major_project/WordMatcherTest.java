package major_project;

import major_project.model.WordMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordMatcherTest {
    WordMatcher wordMatcher;

    @BeforeEach
    void setupModels() {
        wordMatcher = new WordMatcher();

    }

    @Test
    void chooseWord_CheckString_ValidInput_ReturnedTrue() {
        String good1 = "day";
        String good2 = "hap34234";
        String good3 = "a day";
        String good4 = "*hello!";

        assertTrue(wordMatcher.checkString(good1));
        assertTrue(wordMatcher.checkString(good2));
        assertTrue(wordMatcher.checkString(good3));
        assertTrue(wordMatcher.checkString(good4));

    }

    @Test
    void chooseWord_CheckString_WrongInput_ReturnedFalse() {
        String empty = "";
        String spaces1 = "  ";

        assertFalse(wordMatcher.checkString(empty));
        assertFalse(wordMatcher.checkString(spaces1));

    }

}
