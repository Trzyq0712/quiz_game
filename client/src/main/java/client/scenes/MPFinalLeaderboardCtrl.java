package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MPFinalLeaderboardCtrl extends BaseCtrl {

    private final ServerUtils server;



    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    @Inject
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    @FXML
    private void playAgain() {
        utils.playButtonSound();
        mainCtrl.enterWaitingRoom();
    }

    @FXML
    private void emote(Event e){
        utils.playButtonSound();
        mainCtrl.emote(e);
    }

}
