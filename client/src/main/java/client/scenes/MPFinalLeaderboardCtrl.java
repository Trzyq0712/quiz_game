package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Emote;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MPFinalLeaderboardCtrl extends BaseCtrl {

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    PromptCtrl promptCtrl;

    @Inject
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, PromptCtrl promptCtrl){
        super(mainCtrl, utils, server);
        this.promptCtrl = promptCtrl;
    }

    public void playAgain() {
        server.disconnect();
        promptCtrl.enterWaitingRoom();
        mainCtrl.restore();
        utils.clearNotificationBox();
    }

    public void emote(Event e){
        String path = ((ImageView)e.getSource()).getImage().getUrl();
        Emote emote = new Emote(path,mainCtrl.getPlayerScore().getPlayerName());
        server.send("/app/emote/1", emote);
    }

}
