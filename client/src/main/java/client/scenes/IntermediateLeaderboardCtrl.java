package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static client.Config.*;

public class IntermediateLeaderboardCtrl extends BaseCtrl {

    private final ServerUtils server;


    @FXML
    ProgressBar pgBarIntermediate;

    @FXML
    Label questionTracker;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    @Inject
    public IntermediateLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBarIntermediate, timeForIntermediate, 2);
    }

    public void updateQuestionTracker() {
        mainCtrl.updateQuestionTracker(questionTracker, false);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

}
