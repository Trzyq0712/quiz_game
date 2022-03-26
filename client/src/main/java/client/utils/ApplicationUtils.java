package client.utils;

import commons.Config;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static commons.Config.*;

/**
 * A class with controls for the application, such as sound control. The class is a singleton.
 */
public class ApplicationUtils {

    private final MediaPlayer musicPlayer = new MediaPlayer(new Media(backgroundMusic.toURI().toString()));
    private final Image musicOn = new Image(getClass().getClassLoader().getResourceAsStream("images/music.png"));
    private final Image musicOff = new Image(getClass().getClassLoader().getResourceAsStream("images/musicOff.png"));
    private final List<ImageView> musicToggles = new ArrayList<>();
    private final List<VBox> notificationsBoxes = new ArrayList<>();


    public ApplicationUtils() {
        musicPlayer.play();
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
    }

    /**
     * Toggles the mute state background music of the application when called.
     */
    public void toggleSound() {
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
                    notification.setId(color);
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


}
