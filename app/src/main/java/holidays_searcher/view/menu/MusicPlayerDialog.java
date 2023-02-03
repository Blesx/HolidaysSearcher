package holidays_searcher.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import holidays_searcher.ViewManager;
import holidays_searcher.model.MusicPlayer;

public class MusicPlayerDialog extends Dialog {
    private final ViewManager viewManager;

    private Label helpLabel;
    private Button musicButton;
    private VBox vBox;

    private final double dialogFontSize = 14;
    private final int vBoxSpacing = 10;

    public MusicPlayerDialog(ViewManager viewManager) {
        this.viewManager = viewManager;
        drawScene();

    }

    private void drawScene() {
        helpLabel = new Label("Press to toggle on or off the music!");
        helpLabel.setFont(new Font(dialogFontSize));

        musicButton = new Button("Toggle music");
        musicButton.setOnAction(action -> {
            MusicPlayer musicPlayer = viewManager.getCalendarModel().getMusicPlayer();
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
