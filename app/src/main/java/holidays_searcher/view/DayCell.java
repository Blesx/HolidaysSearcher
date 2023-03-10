package holidays_searcher.view;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import holidays_searcher.model.Holiday;
import holidays_searcher.model.calendar.CalendarModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DayCell extends VBox {
    private final LocalDate date;
    private final CalendarModel calendarModel;

    private List<Holiday> holidays;

    private boolean isHoliday = false;
    private boolean unknown = true;

    public DayCell(LocalDate date, CalendarModel calendarModel) {
        this.date = date;
        this.calendarModel = calendarModel;
        holidays = new ArrayList<>();
        buildCell();

    }

    private void buildCell() {
        setAlignment(Pos.CENTER);
        getChildren().add(new Label(Integer.toString(getDay())));
        setColor();

        setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Date with information about holiday(s), if any, from the holidays api. To view pre-existing data, select 'cache hit'. For a new api call, select 'fresh api call'.");

            } else {
                checkHolidayEvent();

            }

        });

        addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> setStyle("-fx-background-color:#cfe2f3; -fx-border-color:#000000"));
        addEventHandler(MouseEvent.MOUSE_EXITED, event -> setColor());

    }

    private void checkHolidayEvent() {
        // checks date is in cache
        List<String> array = new ArrayList<>();
        array.add("cache data");
        array.add("fresh api call");

        if (calendarModel.dateIsChecked(date)) {
            ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(array.get(0), array);
            choiceDialog.setHeaderText("Cache data detected. Choose which data you would like to load.");
            Optional<String> result = choiceDialog.showAndWait();

            if (result.isPresent() && result.get().equals("cache data")) {
                // get cache data
                unknown = false;
                getChildren().clear();
                getChildren().add(new Label(Integer.toString(getDay())));

                if (calendarModel.dateHasHoliday(date)) {
                    isHoliday = true;
                    holidays = calendarModel.getHolidaysOnDay(date);
                    for (Holiday holiday : holidays) {
                        getChildren().add(new Label(holiday.getName()));

                    }
                    createDescriptionAlert();
                    checkHolidaysMatchWord(holidays);
                    setColor();
                    return;

                }
                getChildren().add(new Label("No holidays"));
                setColor();
                return;

            } else if (result.isPresent() && result.get().equals("fresh api call")) {
                calendarModel.deleteRecord(date);

            }

        }

        unknown = false;
        getChildren().clear();
        getChildren().add(new Label(Integer.toString(getDay())));

        // sends request and checks cell for holiday
        if (calendarModel.checkDateForHoliday(date)) {
            isHoliday = true;
            setColor();
            holidays = calendarModel.getHolidaysOnDay(date);

            for (Holiday holiday : holidays) {
                getChildren().add(new Label(holiday.getName()));

            }
            createDescriptionAlert();
            checkHolidaysMatchWord(holidays);

        } else {
            isHoliday = false;
            setColor();
            getChildren().add(new Label("No holidays"));

        }

    }

    private void createInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMaxWidth(500);
        alert.setHeaderText(message);
        alert.showAndWait();

    }

    private void createDescriptionAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String holDescriptions = "";

        for (Holiday holiday : holidays) {
            holDescriptions += "    - " + holiday.getName() + ": " + holiday.getType() + " holiday at location(s) : " + holiday.getLocation() + "\r\n";

        }

        alert.setHeaderText(date.toString());
        alert.setContentText(holDescriptions);
        alert.showAndWait();

    }

    private void checkHolidaysMatchWord(List<Holiday> holidays) {
        if (calendarModel.getWordMatcher().isMatchWithHolidays(holidays)) {
            createMatchFoundAlert();

        }

    }

    private void createMatchFoundAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMaxWidth(500);
        alert.setTitle("Match found!");
        alert.setHeaderText("This holiday contains the word '" + calendarModel.getWordMatcher().getWord() + "'!");
        alert.showAndWait();

    }

    private void setColor() {
        if (unknown) {
            setStyle("-fx-background-color:#ffffff; -fx-border-color:#000000");
            return;

        } else if (isHoliday) {
            setStyle("-fx-background-color:#d9ead3; -fx-border-color:#000000");
            return;

        }
        setStyle("-fx-background-color:#ffcfd7; -fx-border-color:#000000");

    }

    public List<Holiday> getHolidays() {
        return holidays;

    }

    public int getDay() {
        return date.getDayOfMonth();

    }

    public int getMonth() {
        return date.getMonthValue();

    }

    public int getYear() {
        return date.getYear();

    }

}
