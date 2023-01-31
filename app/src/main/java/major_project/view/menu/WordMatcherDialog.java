package major_project.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import major_project.ViewManager;
import major_project.model.WordMatcher;

public class WordMatcherDialog extends Dialog {
    private final ViewManager viewManager;

    private Label helpLabel;
    private TextField stringChosenField;
    private Button chooseButton;
    private Label feedback;
    private VBox vBox;

    private final double maxTextFieldLength = 200;
    private final double dialogFontSize = 14;
    private final int vBoxSpacing = 10;

    public WordMatcherDialog(ViewManager viewManager) {
        this.viewManager = viewManager;
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
            WordMatcher wordMatcher = viewManager.getCalendarModel().getWordMatcher();
            String text = stringChosenField.getText();

            if (wordMatcher.isValidWord(text)) {
                wordMatcher.setWord(text);
                feedback.setText("'" + text + "' is now set!");


            } else {
                feedback.setText("Word is invalid. Please ensure word is not blank.");

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
