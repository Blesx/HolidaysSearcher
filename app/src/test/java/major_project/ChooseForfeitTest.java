package major_project;

import major_project.model.chooseForfeit.ChooseForfeit;
import major_project.model.chooseForfeit.ChooseForfeitDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChooseForfeitTest {
    ChooseForfeit chooseForfeit;

    @BeforeEach
    void setupModels() {
        chooseForfeit = new ChooseForfeitDefault();

    }

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
