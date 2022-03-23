package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import commons.Activity;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.inject.Inject;

import static commons.Config.*;

public class EstimateQuestionCtrl extends BaseCtrl{

    protected final ServerUtils server;

    private Activity activity;

    @FXML
    ImageView pointsJoker;
    @FXML
    ImageView timeJoker;
    @FXML
    Label ActivityDescription;
    @FXML
    ImageView questionImage;
    @FXML
    ProgressBar pgBar;
    @FXML
    Label questionTracker;
    @FXML
    Label scoreLabel;
    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;
    @FXML
    TextField textField;
    @FXML
    Button submitButton;

    @Inject
    public EstimateQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    public void pointsClick() {
        mainCtrl.buttonSound();
        pointsJoker.setVisible(false);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

    public void timeClick() {
        mainCtrl.buttonSound();
        timeJoker.setVisible(false);
    }

    public void updateTracker() {
        mainCtrl.updateTracker(questionTracker, scoreLabel, true);
    }

    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBar, timePerQuestion, 0);
    }

    public void generateActivity() {
        activity = server.getActivity();
        displayActivity();
    }

    private void displayActivity() {
        ActivityDescription.setText(activity.getDescription());
        questionImage.setImage(new Image(ServerUtils.SERVER + activity.getPicturePath()));
        mainCtrl.setAnswersforAnswerReveal(activity);
    }

    /**
     * Score is calculated in the range from 0 to 2 * the correct answer.
     * Points granted decline in a linear fashion from the correct answer.
     */
    public void submitGuess(){
        mainCtrl.buttonSound();
        submitButton.setVisible(false);
        double start = 0;
        double center = activity.getEnergyConsumption();
        double end = 2 * center;
        double guess = Double.parseDouble(textField.getText());
        int points = 0;
        if (guess > start && guess < end) {
            double fraction;
            if (guess <= center) {
                fraction =  guess / center;
            } else {
                fraction = (center - (guess - center)) / center;
            }
            points = (int) (fraction * 200);
            mainCtrl.getPlayerScore().addPoints(points);
        }
        mainCtrl.setAnswersforAnswerReveal(points,true);
    }

    public void restoreJokers() {
        timeJoker.setVisible(true);
        pointsJoker.setVisible(true);
    }

    public void restoreSubmitButton() {
        submitButton.setVisible(true);
    }
}
