package client.scenes;


import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A base class for controllers which have various common controls, such as sound toggles.
 */
public abstract class BaseCtrl implements Initializable {

    protected final MainCtrl mainCtrl;
    protected final ApplicationUtils utils;
    protected final ServerUtils server;

    @FXML
    protected ImageView music;
    @FXML
    protected VBox notificationBox;

    @Inject
    public BaseCtrl(MainCtrl mainCtrl, ApplicationUtils utils, ServerUtils server) {
        this.mainCtrl = mainCtrl;
        this.utils = utils;
        this.server = server;
    }

    public void showHome() {
        try {
            server.disconnect();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        mainCtrl.showHome();
    }

    /**
     * Toggles music in the application.
     */
    @FXML
    protected void toggleSound() {
        utils.toggleSound();
    }


    /**
     * Initialize is required to register ImageViews as toggles.
     * This is because of injection that happens after class is initialized.
     *
     * @param location Not relevant.
     * @param resources Not relevant.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utils.registerMusicToggle(music);
        utils.registerNotificationBox(notificationBox);
    }
}
