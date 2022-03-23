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


import static client.Config.timePerQuestion;

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
    Button submit;
    @FXML
    private Label errorLabel;

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

    public void submitGuess() {
        try {
            long guess = Integer.parseInt(textField.getText());
            long correctAnswer = activity.getEnergyConsumption();
            int points = 0;
            if (guess == correctAnswer) {
                points = 200;
            } else if (correctAnswer - (0.1 * correctAnswer) <= guess
                        && guess <= correctAnswer + (0.1 * correctAnswer)) {
                points = 180;
            } else if (correctAnswer - (0.2 * correctAnswer) <= guess
                        && guess <= correctAnswer + (0.2 * correctAnswer)) {
                points = 160;
            } else if (correctAnswer - (0.3 * correctAnswer) <= guess
                        && guess <= correctAnswer + (0.3 * correctAnswer)) {
                points = 140;
            } else if (correctAnswer - (0.4 * correctAnswer) <= guess
                        && guess <= correctAnswer + (0.4 * correctAnswer)) {
                points = 120;
            } else if (correctAnswer - (0.5 * correctAnswer) <= guess
                        && guess <= correctAnswer + (0.5 * correctAnswer)) {
                points = 100;
            } else if (correctAnswer - (0.7 * correctAnswer) <= guess
                        && guess <= correctAnswer + (0.7 * correctAnswer)) {
                points = 50;
            }
            submit.setDisable(true);
            mainCtrl.getPlayerScore().addPoints(points);
            mainCtrl.setAnswersforAnswerReveal(points, true);
        } catch(Exception e){
            errorLabel.setText("Please type a number" );
            errorLabel.setVisible(true);
        }
    }

    public void restoreJokers() {
        timeJoker.setVisible(true);
        pointsJoker.setVisible(true);
    }

    public void restoreSubmit(){
        submit.setDisable(false);
        textField.setText("");
    }
}
