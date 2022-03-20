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

import static client.Config.timeAnswerReveal;

public class AnswerRevealCtrl extends BaseCtrl {

    private final ServerUtils server;

    @FXML
    ProgressBar pgBarReveal;
    @FXML
    Label questionTracker;
    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    @Inject
    public AnswerRevealCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    /**
     * starts the countdown of the progressbar for the answer reveal
     */
    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBarReveal, timeAnswerReveal, 1);
    }

    /**
     * updates the label of this page which displays the current question.
     * it's called with false since the question doesn't update when answers are revealed, only when the next question
     * shows.
     */

    public void updateQuestionTracker() {
        mainCtrl.updateQuestionTracker(questionTracker, false);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

}
