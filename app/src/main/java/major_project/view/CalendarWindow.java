package major_project.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import major_project.ViewManager;
import major_project.model.calendar.CalendarModel;
import major_project.model.sms.SMSModel;
import major_project.view.menu.MusicPlayerDialog;
import major_project.view.menu.WordMatcherDialog;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CalendarWindow implements Window {
    private final ViewManager viewManager;
    private final CalendarModel calendarModel;
    private final SMSModel smsModel;
    private Scene scene;

    // menu elements
    private MenuBar menuBar;
    private Menu optionsMenu;
    private MenuItem wordMatcherMenuItem;
    private MenuItem musicPlayerMenuItem;

    // left elements
    private Label calendarLabel;
    private Label countryLabel;
    private List<MonthPane> monthPanes;
    private MonthPane currentMonthPane;
    private VBox calendarVBox;
    private VBox leftVBox;

    // right elements
    private Label controlsLabel;
    private ListView<Month> monthListView;
    private ListView<Integer> yearListView;
    private HBox choiceHBox;
    private Button chooseButton;
    private Button clearCacheButton;
    private Button reportButton;
    private VBox rightVBox;

    // layout elements
    private SplitPane splitPane;
    private VBox windowVBox;

    private final Insets vBoxPadding = new Insets(30, 30, 30, 30);
    private final Insets hBoxPadding = new Insets(30, 30, 30, 30);
    private final int vBoxSpacing = 10;

    public CalendarWindow(ViewManager viewManager, CalendarModel model, SMSModel smsModel) {
        this.viewManager = viewManager;
        this.calendarModel = model;
        this.smsModel = smsModel;
        drawScene();

    }

    private void drawScene() {
        // menu elements
        wordMatcherMenuItem = new MenuItem("Word matcher");
        wordMatcherMenuItem.setOnAction(event -> {
            new WordMatcherDialog(viewManager);

        });

        musicPlayerMenuItem = new MenuItem("Music player");
        musicPlayerMenuItem.setOnAction(event -> {
            new MusicPlayerDialog(viewManager);

        });

        optionsMenu = new Menu("Options");
        optionsMenu.getItems().addAll(wordMatcherMenuItem, musicPlayerMenuItem);
        menuBar = new MenuBar();
        menuBar.getMenus().add(optionsMenu);

        // left elements
        countryLabel = new Label(calendarModel.getCountryAbv());
        countryLabel.setFont(new Font(viewManager.getDefaultFontSize()));

        calendarLabel = new Label("Calendar");
        calendarLabel.setFont(new Font(viewManager.getDefaultFontSize()));

        buildTimeSystems();
        buildMonth();
        buildCalendar();

        calendarVBox.setAlignment(Pos.CENTER);
        calendarVBox.setPadding(vBoxPadding);
        calendarVBox.setSpacing(vBoxSpacing);

        leftVBox = new VBox(calendarVBox);
        leftVBox.setSpacing(vBoxSpacing);
        leftVBox.setAlignment(Pos.CENTER);

        // right elements
        controlsLabel = new Label("Controls");
        controlsLabel.setFont(new Font(viewManager.getDefaultFontSize()));

        choiceHBox = new HBox(monthListView, yearListView);
        choiceHBox.setPadding(hBoxPadding);

        chooseButton = new Button("Choose!");
        chooseButton.setOnAction(action -> {
            try {
                int year = yearListView.getSelectionModel().getSelectedItem();
                int month = monthListView.getSelectionModel().getSelectedItem().getValue();

                if (!calendarModel.getCurrentDate().isEqual(LocalDate.of(year, month, calendarModel.getCurrentDate().getDayOfMonth()))) {
                    calendarModel.setCurrentDate(LocalDate.of(year, month, 1));
                    buildMonth();

                }

            } catch (NullPointerException e) {
                System.out.println("Choose a month and a year!");

            }

        });

        chooseButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Chooses the selectable date, ranges from 1st Jan 1970 to 31st Dec 2037.");

            }

        });

        clearCacheButton = new Button("Clear cache");
        clearCacheButton.setOnAction(action -> {
            Alert confirmClear = new Alert(Alert.AlertType.CONFIRMATION);
            confirmClear.setHeaderText("Would you like to clear the cache?");
            confirmClear.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    calendarModel.resetHolidays();

                }

            });

        });

        clearCacheButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Clears the current cache, so every new entry is a fresh call to the holidays api.");

            }

        });

        reportButton = new Button("Send report");
        reportButton.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Would you like to send a short report to your phone number (allocated by env. variable TWILIO_API_TO)?");
            alert.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    smsModel.sendShortReport(currentMonthPane.getHolidays(), calendarModel.getCurrentDate());
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setHeaderText(smsModel.sendFeedback());
                    information.showAndWait();

                }

            });

        });

        reportButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Sends a short report to the phone number found in the TWILIO_API_TO env variable, of all the days with holidays in the current month and year.");

            }

        });

        rightVBox = new VBox(controlsLabel, choiceHBox, chooseButton, clearCacheButton, reportButton);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPadding(vBoxPadding);
        rightVBox.setSpacing(vBoxSpacing);

        // window layout
        splitPane = new SplitPane();
        splitPane.getItems().addAll(leftVBox, rightVBox);

        windowVBox = new VBox(menuBar, splitPane);

        scene = new Scene(windowVBox, viewManager.getViewWidth(), viewManager.getViewHeight());

    }

    private void buildTimeSystems() {
        monthListView = new ListView<>(FXCollections.observableArrayList(Month.values()));
        monthListView.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Choose a month and year, then click choose!");

            }

        });

        List<Integer> years = new ArrayList<>();
        LocalDate datesToExtract = calendarModel.getMinDate();

        while (datesToExtract.isBefore(calendarModel.getMaxDate())) {
            years.add((datesToExtract.getYear()));
            datesToExtract = datesToExtract.plusYears(1);

        }

        yearListView = new ListView<>(FXCollections.observableArrayList(years));
        yearListView.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Choose a month and year, then click choose!");

            }

        });

    }

    private void buildCalendar() {
        if (calendarVBox == null) {
            calendarVBox = new VBox();

        }

        calendarVBox.getChildren().clear();
        calendarVBox.getChildren().addAll(countryLabel, calendarLabel, currentMonthPane.getMonthPane());

    }

    private void buildMonth() {
        LocalDate chosenDate = calendarModel.getCurrentDate();
        calendarLabel.setText(chosenDate.getMonth() + " " + chosenDate.getYear());

        if (monthPanes == null) {
            monthPanes = new ArrayList<>();

        }

        chosenDate = chosenDate.withDayOfMonth(1);

        for (MonthPane monthPane : monthPanes) {
            if (monthPane.getAssignedDate().withDayOfMonth(1).equals(chosenDate)) {
                currentMonthPane = monthPane;
                buildCalendar();
                return;

            }

        }

        currentMonthPane = new MonthPane(calendarModel);
        monthPanes.add(currentMonthPane);
        buildCalendar();

    }

    private void createInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMaxWidth(500);
        alert.setHeaderText(message);
        alert.showAndWait();

    }

    @Override
    public Scene getScene() {
        return scene;

    }

}
