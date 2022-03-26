package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Emote;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MPFinalLeaderboardCtrl extends BaseCtrl {

<<<<<<< HEAD
    private final ServerUtils server;
    private final GameUtils gameUtils;

=======
>>>>>>> dev
    @FXML
    public VBox chatbox;
    @FXML
    public StackPane chatAndEmoteHolder;

    @Inject
<<<<<<< HEAD
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils);
        this.server = server;
        this.gameUtils = gameUtils;
    }

    @FXML
    private void playAgain() {
        utils.playButtonSound();
        mainCtrl.showNamePromtScene();
    }

    @FXML
    private void emote(Event e){
        utils.playButtonSound();
        mainCtrl.emote(e);
=======
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils, server);
    }

    public void playAgain() {
        server.disconnect();
        mainCtrl.enterWaitingRoom();
    }

    public void emote(Event e){
        String path = ((ImageView)e.getSource()).getImage().getUrl();
        Emote emote = new Emote(path,mainCtrl.getPlayerScore().getPlayerName());
        server.send("/app/emote/1", emote);
>>>>>>> dev
    }

}
