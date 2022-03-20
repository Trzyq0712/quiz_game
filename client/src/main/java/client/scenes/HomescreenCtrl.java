package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.scene.input.MouseEvent;


public class HomescreenCtrl extends BaseCtrl {

    private final ServerUtils server;

    @Inject
    public HomescreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    public void showSPLeaderboard(MouseEvent event) {
        mainCtrl.showSPLeaderboard();
    }

    public void showExitScreen(MouseEvent event) {
        mainCtrl.showExitScreen();
    }

    public void showPrompt(Event e) {
        mainCtrl.showNewPrompt(e);
    }

    public void showEditScreen() {
        mainCtrl.showEditScreen();
    }

    public void showInfo() {
        mainCtrl.showInfo();
    }

}
