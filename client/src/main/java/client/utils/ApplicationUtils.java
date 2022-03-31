package client.utils;


import static commons.Config.*;
import static client.utils.Config.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;
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

    private final MediaPlayer musicPlayer = new MediaPlayer(new Media(backgroundMusic));
    private final Image musicOn = new Image(loader.getResourceAsStream("images/music.png"));
    private final Image musicOff = new Image(loader.getResourceAsStream("images/musicOff.png"));
    private final List<Pair<Double, String>> colorUpdates = List.of(
            new Pair<>(.99999, "green"), new Pair<>(0.5, "orange"), new Pair<>(0.25, "red"));
    private final List<ImageView> musicToggles = new ArrayList<>();
    private final List<VBox> notificationsBoxes = new ArrayList<>();
    private final AudioClip buttonClickSound = new AudioClip(Config.buttonClickSound);
    private long startTime;
    private long runTime;
    private long reducedTime;
    private Timer timer;
    private SimpleDoubleProperty progress;


    public ApplicationUtils() {
        musicPlayer.play();
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
    }

    /**
     * A method for starting a progress bar with a countdown.
     * After the time runs out the callback function is executed.
     *
     * @param progressBar The progress bar that is to be modified.
     * @param runTime Time given in milliseconds. Defines how long the progress bar should run for.
     * @param callback The function to be executed after the progress bar runs out i.e. the runTime time passes.
     */
    public void runProgressBar(ProgressBar progressBar, long runTime, Runnable callback) {
        this.startTime = System.currentTimeMillis();
        this.runTime = runTime;
        this.reducedTime = 0;
        progress = new SimpleDoubleProperty(1.0);
        progress.lessThanOrEqualTo(0.0).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("Calling the callback");
                Platform.runLater(callback::run);
                cancelProgressBar();
            }
        });
        for (var p : colorUpdates) {
            progress.lessThanOrEqualTo(p.getKey()).addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    Platform.runLater(() -> progressBar.setStyle("-fx-accent: " + p.getValue()));
                }
            });
        }
        progressBar.progressProperty().bind(progress);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long timePassed = System.currentTimeMillis() - startTime + reducedTime;
                Platform.runLater(() -> progress.set(1.0 - (double)timePassed / (double)runTime));
                System.out.println(progress.getValue());
            }
        }, 10, 50);
    }

    /**
     * Cancel the currently executing progress bar by interrupting the thread responsible for setting the progress.
     */
    public void cancelProgressBar() {
        if (timer != null) timer.cancel();
        reducedTime = 0;
    }

    /**
     * Reduce the progress bar runtime by adding to the total reduced time.
     * @param reductionFactor The reduction factor i.e. 0.2 would mean the remaining time is reduced by 20%.
     */
    public void reduceTime(double reductionFactor) {
        long remainingTime = runTime - (System.currentTimeMillis() - startTime);
        reducedTime += (long)((double)remainingTime * reductionFactor);
    }

    /**
     * Toggles the mute state background music of the application when called.
     */
    public void toggleMusic() {
        musicPlayer.setMute(!musicPlayer.isMute());
        musicToggles.forEach(iv -> {
            if (iv != null) {
                if (musicPlayer.isMute()) iv.setImage(musicOff);
                else iv.setImage(musicOn);
            }
        });
    }

    /**
     * Register a music indicators for changes when sound is being toggled.
     * @param iv The ImageView which should be registered for changes. The image will change between an on and off.
     */
    public void registerMusicToggle(ImageView iv) {
        musicToggles.add(iv);
    }

    /**
     * Adds the notification box of the inheriting controller to the list of all notification boxes.
     * @param notificationBox Notification box to be registered, so notifications can be added later.
     */

    public void registerNotificationBox(VBox notificationBox) {
        notificationsBoxes.add(notificationBox);
    }

    public void addNotification(String message, String color) {
        for (VBox notificationBox : notificationsBoxes) {
            if (notificationBox != null) {
                Platform.runLater(() -> {
                    HBox hbox = new HBox();
                    Label notification = new Label("  " + message);
                    notification.setStyle(notificationStyle + color);
                    hbox.getChildren().addAll(notification);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    if (notificationBox.getChildren().size() >= maxAmountOfNotifications) {
                        notificationBox.getChildren().remove(0);
                    }
                    notificationBox.getChildren().add(hbox);
                    notificationBox.setSpacing(10);
                });
            }
        }
    }

    public void clearNotificationBox() {
        for (VBox notificationBox : notificationsBoxes) {
            if (notificationBox != null) {
                notificationBox.getChildren().clear();
            }
        }
    }


    public void playButtonSound() {
        buttonClickSound.play();
    }

}
