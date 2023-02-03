package holidays_searcher.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class MusicPlayer {
    private Media media;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    public MusicPlayer() {
        setUpMusicPlayer();

    }

    private void setUpMusicPlayer() {
        try {
            media = new Media(getClass().getResource("/raindrops.wav").toURI().toString());
            mediaPlayer = new MediaPlayer(media);

        } catch (URISyntaxException | NullPointerException e) {
            System.out.println("Something wrong happened trying to load the music.");
            return;

        }

        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.setVolume(0.25);

    }

    public void playMusic() {
        mediaPlayer.play();
        isPlaying = true;

    }

    public void pauseMusic() {
        mediaPlayer.pause();
        isPlaying = false;

    }

    public boolean isPlayingMusic() {
        return isPlaying;

    }

}
