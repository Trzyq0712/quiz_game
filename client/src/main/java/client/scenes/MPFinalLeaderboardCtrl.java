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

    public void playAgain() {
        mainCtrl.enterWaitingRoom();
    }

    public void emote(Event e){
        server.send("/app/emote/{gameId}", mainCtrl.getPlayerScore().getPlayerName());
    }

}
