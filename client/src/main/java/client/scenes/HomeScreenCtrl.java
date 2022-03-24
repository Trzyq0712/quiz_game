package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;


public class HomeScreenCtrl extends BaseCtrl {

    private final ServerUtils server;

    @Inject
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    @FXML
    private void showSPLeaderboard() {
        utils.playButtonSound();
        mainCtrl.showSPLeaderboardFromHome();
    }

    @FXML
    private void showExitScreen() {
        utils.playButtonSound();
        mainCtrl.showExitScreen();
    }

    @FXML
    private void showPrompt(Event e) {
        utils.playButtonSound();
        mainCtrl.showNewPrompt(e);
    }

    @FXML
    private void showEditScreen() {
        utils.playButtonSound();
        mainCtrl.showEditScreen();
    }

    @FXML
    private void showInfo() {
        utils.playButtonSound();
        mainCtrl.showInfo();
    }

}
