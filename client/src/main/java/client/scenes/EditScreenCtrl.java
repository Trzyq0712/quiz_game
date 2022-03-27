package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;


public class EditScreenCtrl extends BaseCtrl {

    @Inject
    public EditScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils, server);
    }

    @FXML
    private void addActivity() {
        utils.playButtonSound();
        mainCtrl.editActivity(true);
    }
}
