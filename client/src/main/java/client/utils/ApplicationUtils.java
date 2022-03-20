package client.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static client.Config.backgroundMusic;

public class ApplicationUtils {

    private final MediaPlayer musicPlayer = new MediaPlayer(new Media(backgroundMusic.toURI().toString()));
    private final Image musicOn = new Image(getClass().getClassLoader().getResourceAsStream("images/music.png"));
    private final Image musicOff = new Image(getClass().getClassLoader().getResourceAsStream("images/musicOff.png"));
    private final List<ImageView> musicToggles = new ArrayList<>();


    public ApplicationUtils() {
        musicPlayer.play();
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
    }

    /**
     * Toggles the background music of the application when called.
     */
    public void toggleSound() {
        musicPlayer.setMute(!musicPlayer.isMute());
        musicToggles.forEach(iv -> {
            if (musicPlayer.isMute()) iv.setImage(musicOff);
            else iv.setImage(musicOn);
        });
    }

    public void registerMusicToggle(ImageView iv) {
        musicToggles.add(iv);
    }
}
