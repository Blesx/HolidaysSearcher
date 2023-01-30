package major_project;

import major_project.model.MusicPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MusicPlayerTest extends BaseTest {
    MusicPlayer musicPlayer;

    @BeforeEach
    void setupModels() {
        musicPlayer = new MusicPlayer();

    }

    @Test
    void whenMusicPlayed_checkMediaPlayer_ReturnsTrue() {
        musicPlayer.playMusic();
        assertTrue(musicPlayer.isPlayingMusic());

    }

    @Test
    void whenMusicPaused_checkMediaPlayer_ReturnsFalse() {
        assertFalse(musicPlayer.isPlayingMusic());

    }

}
