package major_project.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import major_project.ViewManager;
import major_project.model.MusicPlayer;

public class MusicPlayerDialog extends Dialog {
    private final ViewManager manager;

    private Label helpLabel;
    private Button musicButton;
    private VBox vBox;

    private final double dialogFontSize = 14;
    private final int vBoxSpacing = 10;

    public MusicPlayerDialog(ViewManager manager) {
        this.manager = manager;
        drawScene();

    }

    private void drawScene() {
        helpLabel = new Label("Press to toggle on or off the music!");
        helpLabel.setFont(new Font(dialogFontSize));

        musicButton = new Button("Toggle music");
        musicButton.setOnAction(action -> {
            MusicPlayer musicPlayer = manager.getInputModel().getMusicPlayer();
            if (musicPlayer.isPlayingMusic()) {
                musicPlayer.pauseMusic();

            } else {
                musicPlayer.playMusic();

            }

        });

        vBox = new VBox(helpLabel, musicButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(vBoxSpacing);

        getDialogPane().setContent(vBox);
        getDialogPane().getButtonTypes().addAll(
                ButtonType.CLOSE

        );

        setTitle("Music player");

        showAndWait();

    }

}
