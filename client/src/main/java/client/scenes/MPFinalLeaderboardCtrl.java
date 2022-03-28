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

    @FXML
    public VBox chatbox;
    @FXML
    public StackPane chatAndEmoteHolder;

    NamePromptCtrl promptCtrl;

    @Inject
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils,
                                  NamePromptCtrl promptCtrl, GameUtils gameUtils){
        super(mainCtrl, utils, server, gameUtils);
        this.promptCtrl = promptCtrl;
    }

    @FXML
    private void playAgain() {
        utils.playButtonSound();
        gameUtils.resetGame();
        //promptCtrl.enterWaitingRoom();
        mainCtrl.showNamePromtScene();
    }

    @FXML
    private void emote(Event e){
        utils.playButtonSound();
        String path = ((ImageView)e.getSource()).getImage().getUrl();
        Emote emote = new Emote(path, gameUtils.getPlayer().getPlayerName());
        server.send("/app/emote/1", emote);
    }

}
