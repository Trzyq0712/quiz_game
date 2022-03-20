package client.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

import static client.Config.backgroundMusic;

public class ApplicationUtils {

    private MediaPlayer mp = new MediaPlayer(new Media(backgroundMusic.toURI().toString()));
    private Image musicOn = new Image(getClass().getClassLoader().getResourceAsStream("images/music.png"));
    private Image musicOff = new Image(getClass().getClassLoader().getResourceAsStream("images/musicOff.png"));
    private List<ImageView> musicToggles = new ArrayList<>();


    public ApplicationUtils() {
        mp.play();
        mp.setAutoPlay(true);
    }

    /**
     * Toggles the background music of the application when called.
     */
    public void toggleSound() {
        mp.setMute(!mp.isMute());
        musicToggles.forEach(iv -> {
            if (mp.isMute()) iv.setImage(musicOff);
            else iv.setImage(musicOn);
        });
    }

    public void registerMusicToggle(ImageView iv) {
        musicToggles.add(iv);
    }
}
