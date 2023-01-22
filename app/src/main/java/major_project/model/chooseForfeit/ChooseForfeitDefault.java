package major_project.model.chooseForfeit;

import major_project.model.calendar.input.InputCalendar;

public class ChooseForfeitDefault implements ChooseForfeit {
    @Override
    public boolean checkString(String word) {
        if (word.isEmpty() || word.isBlank()) {
            return false;

        }

        return true;

    }

    @Override
    public void setForfeitString(InputCalendar calendar, String forfeit) {
        calendar.setForfeitWord(forfeit);

    }

}
