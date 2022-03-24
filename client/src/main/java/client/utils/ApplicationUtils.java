package client.utils;

import client.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


/**
 * A class with controls for the application, such as sound control. The class is a singleton.
 */
public class ApplicationUtils {

    private final MediaPlayer musicPlayer = new MediaPlayer(new Media(Config.backgroundMusic));
    private final Image musicOn = new Image(Config.loader.getResourceAsStream("images/music.png"));
    private final Image musicOff = new Image(Config.loader.getResourceAsStream("images/musicOff.png"));
    private final List<ImageView> musicToggles = new ArrayList<>();
    private final AudioClip buttonClickSound = new AudioClip(Config.buttonClickSound);

    public ApplicationUtils() {
        musicPlayer.play();
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
    }

    /**
     * Toggles the mute state background music of the application when called.
     */
    public void toggleMusic() {
        musicPlayer.setMute(!musicPlayer.isMute());
        musicToggles.forEach(iv -> {
            if (musicPlayer.isMute()) iv.setImage(musicOff);
            else iv.setImage(musicOn);
        });
    }

    /**
     * Register a music indicators for changes when sound is being toggled.
     * @param iv The ImageView which should be registered for changes. The image will change between an on and off.
     */
    public void registerMusicToggle(ImageView iv) {
        musicToggles.add(iv);
    }

    public void playButtonSound() {
        buttonClickSound.play();
    }
}
