package client.scenes;

import client.Config;
import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Emote;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

<<<<<<< HEAD
public class IntermediateLeaderboardCtrl extends BaseCtrl {

    private final ServerUtils server;
    private final GameUtils gameUtils;


=======

import static client.Config.*;

public class IntermediateLeaderboardCtrl extends BaseCtrl {

>>>>>>> dev
    @FXML
    ProgressBar pgBarIntermediate;

    @FXML
    Label questionTracker;
    @FXML
    Label scoreLabel;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    @Inject
<<<<<<< HEAD
    public IntermediateLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl,
                                       ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils);
        this.server = server;
        this.gameUtils = gameUtils;
=======
    public IntermediateLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils, server);
>>>>>>> dev
    }

    public void activateProgressBar() {
        utils.runProgressBar(pgBarIntermediate, Config.timeForIntermediate, mainCtrl::showQuestion);
    }

    public void updateQuestionTracker() {
        gameUtils.updateTracker(questionTracker, scoreLabel, false);
    }

<<<<<<< HEAD
    @FXML
    private void emote(Event e) {
        utils.playButtonSound();
        mainCtrl.emote(e);
=======
    public void emote(Event e){
        String path = ((ImageView)e.getSource()).getImage().getUrl();
        Emote emote = new Emote(path,mainCtrl.getPlayerScore().getPlayerName());
        server.send("/app/emote/1", emote);
>>>>>>> dev
    }

}
