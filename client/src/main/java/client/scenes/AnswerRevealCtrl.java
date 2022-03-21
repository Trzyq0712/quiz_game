package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

import static client.Config.timeAnswerReveal;

public class AnswerRevealCtrl extends BaseCtrl {

    private final ServerUtils server;

    @FXML
    ProgressBar pgBarReveal;
    @FXML
    Label questionTracker;
    @FXML
    Label scoreLabel;
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;

    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;

    @FXML
    Label estimateAnswer;
    @FXML
    Label pointsGranted;

    @FXML
    ImageView checkmark1;
    @FXML
    ImageView checkmark2;
    @FXML
    ImageView checkmark3;

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

    public void updateTracker() {
        mainCtrl.updateTracker(questionTracker, scoreLabel, false);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

    /**
     * takes in a list of 3 activities and sets the values next to the answer
     * @param activities - list of 3 activities passed from the QuestionCtrl
     * @param answerButtonId - numeric id of the correct answer
     */
    public void setAnswers(List<Activity> activities, int answerButtonId) {
        setEstimateQuestionFormat(false);
        checkmark1.setImage(null);
        checkmark2.setImage(null);
        checkmark3.setImage(null);
        Image checkmark = new Image("/images/checkmark.png");
        if (answerButtonId == 1) {
            checkmark1.setImage(checkmark);
        } else if (answerButtonId == 2) {
            checkmark2.setImage(checkmark);
        } else if (answerButtonId == 3) {
            checkmark3.setImage(checkmark);
        }
        label1.setText(activities.get(0).getEnergyConsumption().toString());
        label2.setText(activities.get(1).getEnergyConsumption().toString());
        label3.setText(activities.get(2).getEnergyConsumption().toString());
    }

    /**
     * Takes in a boolean, if true, sets the estimate question format, if false, sets the MC question format
     * @param bool
     */
    private void setEstimateQuestionFormat(Boolean bool) {
        estimateAnswer.setVisible(bool);
        pointsGranted.setVisible(bool);
        checkmark1.setVisible(!bool);
        checkmark2.setVisible(!bool);
        checkmark3.setVisible(!bool);
        label1.setVisible(!bool);
        label2.setVisible(!bool);
        label3.setVisible(!bool);
        firstButton.setVisible(!bool);
        secondButton.setVisible(!bool);
        thirdButton.setVisible(!bool);
    }

    public void setAnswer(Activity activity) {
        setEstimateQuestionFormat(true);
        estimateAnswer.setText(activity.getEnergyConsumption().toString());
    }

    public void setAnswer(int points) {
        pointsGranted.setText("You got " + points + " points!");
    }
}
