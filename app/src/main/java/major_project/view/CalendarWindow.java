package major_project.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import major_project.ViewManager;
import major_project.model.calendar.input.InputCalendar;
import major_project.model.calendar.output.OutputCalendar;
import major_project.view.menu.WordMatcherDialog;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CalendarWindow implements Window {
    private final ViewManager manager;
    private final InputCalendar inputModel;
    private final OutputCalendar outputModel;
    private final MediaPlayer mediaPlayer;
    private Scene scene;

    // menu elements
    private MenuBar menuBar;
    private Menu optionsMenu;
    private MenuItem wordMatcherMenuItem;

    // left elements
    private Label calendarLabel;
    private Label countryLabel;
    private List<MonthPane> monthPanes;
    private MonthPane currentMonthPane;
    private VBox calendarVBox;

    private Label wordToMatchLabel;
    private VBox wordToMatchVBox;

    private VBox leftVBox;

    // right elements
    private Label controlsLabel;
    private ListView<Month> monthListView;
    private ListView<Integer> yearListView;
    private HBox choiceHBox;
    private Button chooseButton;
    private Button clearCacheButton;
    private Button reportButton;
    private Button musicButton;
    private VBox rightVBox;

    // layout elements
    private SplitPane splitPane;
    private VBox windowVBox;

    private final Insets vBoxPadding = new Insets(30, 30, 30, 30);
    private final Insets hBoxPadding = new Insets(30, 30, 30, 30);
    private final int vBoxSpacing = 10;

    public CalendarWindow(ViewManager manager, InputCalendar model, OutputCalendar outputModel, MediaPlayer mediaPlayer) {
        this.manager = manager;
        this.inputModel = model;
        this.outputModel = outputModel;
        this.mediaPlayer = mediaPlayer;
        drawScene();

    }

    private void drawScene() {
        // menu elements
        wordMatcherMenuItem = new MenuItem("Word matcher");
        wordMatcherMenuItem.setOnAction(event -> {
            new WordMatcherDialog(manager, this);

        });

        optionsMenu = new Menu("Options");
        optionsMenu.getItems().add(wordMatcherMenuItem);
        menuBar = new MenuBar();
        menuBar.getMenus().add(optionsMenu);

        // left elements
        countryLabel = new Label(inputModel.getCountryAbv());
        countryLabel.setFont(new Font(manager.getDefaultFontSize()));

        calendarLabel = new Label("Calendar");
        calendarLabel.setFont(new Font(manager.getDefaultFontSize()));

        buildTimeSystems();
        buildMonth();
        buildCalendar();

        calendarVBox.setAlignment(Pos.CENTER);
        calendarVBox.setPadding(vBoxPadding);
        calendarVBox.setSpacing(vBoxSpacing);

        wordToMatchLabel = new Label("Word to match: " + inputModel.getWordToMatch());
        wordToMatchLabel.setFont(new Font(manager.getDefaultFontSize()));

        wordToMatchVBox = new VBox(wordToMatchLabel);
        wordToMatchVBox.setSpacing(vBoxSpacing);
        wordToMatchVBox.setAlignment(Pos.CENTER);

        leftVBox = new VBox(calendarVBox, wordToMatchVBox);
        leftVBox.setSpacing(vBoxSpacing);
        leftVBox.setAlignment(Pos.CENTER);

        // right elements
        controlsLabel = new Label("Controls");
        controlsLabel.setFont(new Font(manager.getDefaultFontSize()));

        choiceHBox = new HBox(monthListView, yearListView);
        choiceHBox.setPadding(hBoxPadding);

        chooseButton = new Button("Choose!");
        chooseButton.setOnAction(action -> {
            try {
                int year = yearListView.getSelectionModel().getSelectedItem();
                int month = monthListView.getSelectionModel().getSelectedItem().getValue();

                if (!inputModel.getCurrentDate().isEqual(LocalDate.of(year, month, inputModel.getCurrentDate().getDayOfMonth()))) {
                    inputModel.setCurrentDate(LocalDate.of(year, month, 1));
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
                    inputModel.resetHolidays();

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
                    outputModel.sendShortReport(currentMonthPane.getHolidays(), inputModel.getCurrentDate());
                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setHeaderText(outputModel.sendFeedback());
                    information.showAndWait();

                }

            });

        });

        reportButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Sends a short report to the phone number found in the TWILIO_API_TO env variable, of all the days with holidays in the current month and year.");

            }

        });

        musicButton = new Button("Toggle music");
        musicButton.setOnAction(action -> {
            boolean playing = mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
            if (playing) {
                mediaPlayer.pause();

            } else {
                mediaPlayer.play();

            }

        });

        musicButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Pauses/plays the current music playing.");

            }

        });

        rightVBox = new VBox(controlsLabel, choiceHBox, chooseButton, clearCacheButton, reportButton, musicButton);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPadding(vBoxPadding);
        rightVBox.setSpacing(vBoxSpacing);

        // window layout
        splitPane = new SplitPane();
        splitPane.getItems().addAll(leftVBox, rightVBox);

        windowVBox = new VBox(menuBar, splitPane);

        scene = new Scene(windowVBox, manager.getViewWidth(), manager.getViewHeight());

    }

    private void buildTimeSystems() {
        monthListView = new ListView<>(FXCollections.observableArrayList(Month.values()));
        monthListView.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Choose a month and year, then click choose!");

            }

        });

        List<Integer> years = new ArrayList<>();
        LocalDate datesToExtract = inputModel.getMinDate();

        while (datesToExtract.isBefore(inputModel.getMaxDate())) {
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
        LocalDate chosenDate = inputModel.getCurrentDate();
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

        currentMonthPane = new MonthPane(inputModel);
        monthPanes.add(currentMonthPane);
        buildCalendar();

    }

    private void createInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMaxWidth(500);
        alert.setHeaderText(message);
        alert.showAndWait();

    }

    public void setWordToMatchLabel() {
        wordToMatchLabel.setText("Word to match: " + inputModel.getWordToMatch());

    }

    @Override
    public Scene getScene() {
        return scene;

    }

}
