package holidays_searcher;

import holidays_searcher.model.MusicPlayer;
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
    void whenMusicPlayed_isPlayingMusic_ReturnsTrue() {
        musicPlayer.playMusic();
        assertTrue(musicPlayer.isPlayingMusic());

    }

    @Test
    void whenMusicPaused_isPlayingMusic_ReturnsFalse() {
        assertFalse(musicPlayer.isPlayingMusic());

    }

}
