package major_project.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import major_project.ViewManager;
import major_project.model.chooseForfeit.ChooseForfeit;

public class ChooseForfeitWindow implements Window {
    private final ViewManager manager;
    private final ChooseForfeit model;
    private final MediaPlayer mediaPlayer;
    private Scene scene;

    private Label helpLabel;
    private TextField stringChosenField;
    private Button chooseButton;
    private Button musicButton;
    private Label feedback;
    private VBox vBox;

    private final double maxTextFieldLength = 200;
    private final double feedbackFontSize = 16;
    private final int vBoxSpacing = 10;

    public ChooseForfeitWindow(ViewManager manager, ChooseForfeit model, MediaPlayer mediaPlayer) {
        this.manager = manager;
        this.model = model;
        this.mediaPlayer = mediaPlayer;
        drawScene();

    }

    private void drawScene() {
        helpLabel = new Label("Welcome to Holidays API. Please choose a forfeit word! (Ctrl + click for help!)");
        helpLabel.setFont(new Font(manager.getDefaultFontSize()));

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
                model.setForfeitString(manager.getInputModel(), text);
                manager.setScene(new WorldMapWindow(manager, mediaPlayer));

            } else {
                feedback.setVisible(true);

            }

        });

        chooseButton.setOnMouseClicked(event -> {
            if (event.isControlDown()) {
                createInformationAlert("Sets a forfeit word if valid, then changes view to the world map.");

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

        feedback = new Label("String is invalid. Please make sure it is not empty, and is not just spaces.");
        feedback.setFont(new Font(feedbackFontSize));
        feedback.setVisible(false);

        vBox = new VBox(helpLabel, stringChosenField, chooseButton, musicButton, feedback);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(vBoxSpacing);

        scene = new Scene(vBox, manager.getViewWidth(), manager.getViewHeight());

    }

    @Override
    public Scene getScene() {
        return scene;

    }

    private void createInformationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMaxWidth(500);
        alert.setHeaderText(message);
        alert.showAndWait();

    }

}
