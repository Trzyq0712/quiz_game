package client.scenes;


import client.utils.ApplicationUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseCtrl implements Initializable {

    protected final MainCtrl mainCtrl;
    protected final ApplicationUtils utils;

    @FXML
    protected ImageView music;

    public BaseCtrl(MainCtrl mainCtrl, ApplicationUtils utils) {
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    public void showHome() {
        mainCtrl.showHome();
    }

    @FXML
    protected void toggleSound() {
        utils.toggleSound();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utils.registerMusicToggle(music);
    }
}
