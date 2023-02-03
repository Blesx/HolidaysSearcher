package holidays_searcher.model;

import java.util.List;

public class WordMatcher {
    private String word = null;

    public boolean isMatchWithHolidays(List<Holiday> holidays) {
        if (!isValidWord(word)) {
            return false;

        }

        for (Holiday holiday : holidays) {
            if (holiday.getName().toLowerCase().contains(word)) {
                return true;

            }
        }

        return false;

    }

    public boolean isValidWord(String word) {
        if (word == null || word.isEmpty() || word.isBlank()) {
            return false;

        }

        return true;

    }

    public String getWord() {
        return word;

    }

    public void setWord(String word) {
        this.word = word.toLowerCase();

    }

}
