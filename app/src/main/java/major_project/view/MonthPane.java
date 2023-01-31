package major_project.view;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import major_project.model.Holiday;
import major_project.model.calendar.CalendarModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MonthPane {
    private final CalendarModel calendarModel;
    private final LocalDate assignedDate;

    private GridPane monthPane;
    private List<DayCell> cells;

    private final int rowCount = 5;
    private final int colCount = 7;

    public MonthPane(CalendarModel calendarModel) {
        this.calendarModel = calendarModel;
        this.assignedDate = calendarModel.getCurrentDate();
        cells = new ArrayList<>();
        buildPane();
        buildContent();

    }

    private void buildPane() {
        monthPane = new GridPane();
        monthPane.setGridLinesVisible(true);

        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100d / rowCount);

        for (int i = 0; i < rowCount; i++) {
            monthPane.getRowConstraints().add(rc);

        }

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100d / colCount);

        for (int i = 0; i < colCount; i++) {
            monthPane.getColumnConstraints().add(cc);

        }

    }

    private void buildContent() {
        LocalDate date = assignedDate.withDayOfMonth(1);
        LocalDate monthEndDate = assignedDate.plusMonths(1).withDayOfMonth(1);

        int xCord = 0;
        int yCord = 0;

        while (xCord != rowCount && date.isBefore(monthEndDate)) {
            while (yCord != colCount && date.isBefore(monthEndDate)) {
                DayCell cell = new DayCell(date, calendarModel);
                cells.add(cell);
                monthPane.add(cell, yCord, xCord);
                date = date.plusDays(1);
                yCord++;

            }
            xCord++;
            yCord = 0;

        }

    }

    public LocalDate getAssignedDate() {
        return assignedDate;

    }

    public GridPane getMonthPane() {
        return monthPane;

    }

    public List<Holiday> getHolidays() {
        List<Holiday> holidays = new ArrayList<>();

        for (DayCell cell : cells) {
            holidays.addAll(cell.getHolidays());

        }

        return holidays;

    }

}
