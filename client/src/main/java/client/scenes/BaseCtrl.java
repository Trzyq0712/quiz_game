package client.scenes;


import client.utils.ApplicationUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A base class for controllers which have various common controls, such as sound toggles.
 */
public abstract class BaseCtrl implements Initializable {

    protected final MainCtrl mainCtrl;
    protected final ApplicationUtils utils;

    @FXML
    protected ImageView music;

    public BaseCtrl(MainCtrl mainCtrl, ApplicationUtils utils) {
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    @FXML
    public void showHome() {
        utils.playButtonSound();
        utils.cancelProgressBar();
        mainCtrl.showHome();
    }

    /**
     * Toggles music in the application.
     */
    @FXML
    protected void toggleMusic() {
        utils.playButtonSound();
        utils.toggleMusic();
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
    }
}
