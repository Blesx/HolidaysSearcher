package major_project.model.chooseForfeit;

import major_project.model.calendar.input.InputCalendar;

public interface ChooseForfeit {
    /**
     * Checks if the string inputted is a valid forfeit word for future matching
     */
    boolean checkString(String word);

    /**
     * Sets the forfeit string in the calendar model
     */
    void setForfeitString(InputCalendar calendar, String forfeit);

}
