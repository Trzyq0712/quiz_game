package client.utils;

import client.Config;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A class with controls for the application, such as sound control. The class is a singleton.
 */
public class ApplicationUtils {

    private final MediaPlayer musicPlayer = new MediaPlayer(new Media(Config.backgroundMusic));
    private final Image musicOn = new Image(Config.loader.getResourceAsStream("images/music.png"));
    private final Image musicOff = new Image(Config.loader.getResourceAsStream("images/musicOff.png"));
    private final List<ImageView> musicToggles = new ArrayList<>();
    private final AudioClip buttonClickSound = new AudioClip(Config.buttonClickSound);

    private Timer timer;


    public ApplicationUtils() {
        musicPlayer.play();
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
    }

    public void runProgressBar(ProgressBar progressBar, long runTime, Runnable callback) {
        List<Pair<Double, String>> updates = List.of(
                new Pair<>(0.0, "green"), new Pair<>(0.5, "orange"), new Pair<>(0.75, "red"));

        progressBar.setProgress(1.0);
        timer = new Timer();
        int steps = 200;

        for (var update : updates) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> progressBar.setStyle("-fx-accent: " + update.getValue()));
                }
            }, (long)(update.getKey() * runTime));
        }

        TimerTask updateProgressBar = new TimerTask() {
            @Override
            public void run() {
                double progress = progressBar.getProgress();
                Platform.runLater(() -> progressBar.setProgress(progress - 1.0 / (double)steps));
            }
        };

        timer.schedule(updateProgressBar, 20, runTime / steps);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                Platform.runLater(callback::run);
            }
        }, runTime);
    }

    public void cancelProgressBar() {
        if (timer != null) timer.cancel();
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
