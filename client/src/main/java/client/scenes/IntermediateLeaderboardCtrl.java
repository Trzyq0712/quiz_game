package client.scenes;

import client.Config;
import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class IntermediateLeaderboardCtrl extends BaseCtrl {

    private final ServerUtils server;
    private final GameUtils gameUtils;


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
    public IntermediateLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl,
                                       ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils);
        this.server = server;
        this.gameUtils = gameUtils;
    }

    public void activateProgressBar() {
        utils.runProgressBar(pgBarIntermediate, Config.timeForIntermediate,
                () -> Platform.runLater(mainCtrl::showQuestion));
    }

    public void updateQuestionTracker() {
        gameUtils.updateTracker(questionTracker, scoreLabel, false);
    }

    @FXML
    private void emote(Event e) {
        utils.playButtonSound();
        mainCtrl.emote(e);
    }

}
