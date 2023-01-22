package major_project.model.calendar.output;

import major_project.model.Holiday;

import java.time.LocalDate;
import java.util.List;

public interface OutputCalendar {
    /**
     * Sends a short report to the phone number via Twilio specified by the user's env variable - TWILIO_API_TO
     */
    void sendShortReport(List<Holiday> holidaysInMonth, LocalDate currentDate);

    /**
     * Returns the user a message if the report was sent or not
     */
    String sendFeedback();

}
