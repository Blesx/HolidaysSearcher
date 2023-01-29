package major_project.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import major_project.ViewManager;
import major_project.model.WordMatcher;
import major_project.view.CalendarWindow;

public class WordMatcherDialog extends Dialog {
    private final ViewManager manager;
    private final CalendarWindow calendarWindow;
    private final WordMatcher model;

    private Label helpLabel;
    private TextField stringChosenField;
    private Button chooseButton;
    private Label feedback;
    private VBox vBox;

    private final double maxTextFieldLength = 200;
    private final double dialogFontSize = 14;
    private final int vBoxSpacing = 10;

    public WordMatcherDialog(ViewManager manager, CalendarWindow calendarWindow) {
        this.manager = manager;
        this.calendarWindow = calendarWindow;
        model = new WordMatcher();
        drawScene();

    }

    private void drawScene() {
        helpLabel = new Label("Please choose a word to match! (Ctrl + click for help!)");
        helpLabel.setFont(new Font(dialogFontSize));

        stringChosenField = new TextField();
        stringChosenField.setMaxWidth(maxTextFieldLength);
        stringChosenField.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Pick a string. Can have symbols, but must not be empty, and must not have just spaces.");

            }

        });

        chooseButton = new Button("Choose word!");
        chooseButton.setOnAction(action -> {
            String text = stringChosenField.getText();

            if (model.checkString(text)) {
                model.setWord(manager.getInputModel(), text);
                calendarWindow.setWordToMatchLabel();
                feedback.setText("Word to match set!");


            } else {
                feedback.setText("String is invalid. Please make sure it is not empty, and is not just spaces.");

            }
            feedback.setVisible(true);

        });

        chooseButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Sets a word to match if valid.");

            }

        });

        feedback = new Label("String is invalid. Please make sure it is not empty, and is not just spaces.");
        feedback.setFont(new Font(dialogFontSize));
        feedback.setVisible(false);

        vBox = new VBox(helpLabel, stringChosenField, chooseButton, feedback);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(vBoxSpacing);

        getDialogPane().setContent(vBox);
        getDialogPane().getButtonTypes().addAll(
                ButtonType.CLOSE
        );

        setTitle("Word matcher");

        showAndWait();

    }

    private void createInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMaxWidth(500);
        alert.setHeaderText(message);
        alert.showAndWait();

    }

}
