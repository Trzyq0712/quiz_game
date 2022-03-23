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
        mainCtrl.showSPLeaderboard();
    }

    @FXML
    private void showExitScreen() {
        mainCtrl.showExitScreen();
    }

    @FXML
    private void showPrompt(Event e) {
        mainCtrl.showNewPrompt(e);
    }

    @FXML
    private void showEditScreen() {
        mainCtrl.showEditScreen();
    }

    @FXML
    private void showInfo() {
        mainCtrl.showInfo();
    }

}
