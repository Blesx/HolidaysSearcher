package major_project.model;

import major_project.model.calendar.input.InputCalendar;

public class WordMatcher {
    public boolean checkString(String word) {
        if (word.isEmpty() || word.isBlank()) {
            return false;

        }

        return true;

    }

    public void setWord(InputCalendar calendar, String word) {
        calendar.setWordToMatch(word);

    }

}
