package major_project;

import javafx.embed.swing.JFXPanel;
import major_project.model.Holiday;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDate;

class BaseTest {
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

    @BeforeAll
    static void setupHolidaysAndDates() {
        final JFXPanel fxPanel = new JFXPanel();

        happyDay = new Holiday("Happy Day", "", "", "A day for being really happy and stuff...", "RU", "Moscow", "Regional", "01/20/2023", "2023", "1", "20", "Thursday");
        jubilantDay = new Holiday("Jubilant Day", "", "", "Wow! This is what happens when happy is on crack!", "RU", "", "Regional", "01/20/2023", "2023", "1", "20", "Thursday");
        sadDay = new Holiday("Sad Day", "", "", "Sometimes being sad needs to be recognized, well who knows.", "RU", "", "National", "01/24/2023", "2023", "1", "24", "Monday");
        chinaDay = new Holiday("China Day", "", "", "Why is this a day? Every day is China day.", "CN", "", "National", "04/01/2022", "2022", "4", "1", "Sunday");
        happyChinaDay = new Holiday("Happy China Day", "", "", "The same day as Russia.", "CN", "", "National", "05/20/2022", "2022", "5", "20", "Thursday");
        crazyDay = new Holiday("&&#$^^# 999", "", "", "Why is this a day? Every day is China day.", "CN", "", "National", "04/01/2022", "2022", "4", "1", "Sunday");

        happyDate = LocalDate.of(2023, 1, 20);
        jubilantDate = LocalDate.of(2023, 1, 20);
        sadDate = LocalDate.of(2023, 1, 24);
        chinaDate = LocalDate.of(2022, 4, 1);
        happyChinaDate = LocalDate.of(2022, 5, 20);

    }

}