package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.inject.Inject;

import static commons.Config.*;

public class EstimateQuestionCtrl extends BaseQuestionCtrl {

    private Activity activity;

    @FXML
    Label ActivityDescription;
    @FXML
    ImageView questionImage;
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
    @FXML
    private Label errorLabel;


    @Inject
    public EstimateQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(server, mainCtrl, utils, gameUtils);
    }


    public void generateActivity() {
        activity = server.getSingleActivity(mainCtrl.currentQuestion, mainCtrl.gameID);
        displayActivity();
        setHasPlayerAnswered(false);
    }

    private void displayActivity() {
        ActivityDescription.setText(activity.getDescription());
        questionImage.setImage(new Image(ServerUtils.SERVER + activity.getPicturePath()));
        mainCtrl.setAnswersForAnswerReveal(activity);
    }


    /**
     * Score is calculated in the range from 0 to 2 * the correct answer.
     * Points granted decline in a linear fashion from the correct answer.
     * Max amount of points can be configured in the config file.
     */
    public void submitGuess(){
        utils.playButtonSound();
        int points = 0;
        try {
            double start = 0;
            double center = activity.getEnergyConsumption();
            double end = 2 * center;
            double guess = Double.parseDouble(textField.getText());
            submitButton.setVisible(false);
            if (guess > start && guess < end) {
                double deviation = Math.abs(guess - center);
                double fraction = (center - deviation) / center;
                fraction = easeOutSinusoidal(fraction);
                points = (int) (fraction * maxPointsPerQuestion);
                if (doublePointsActive) {
                    points *= 2;
                    doublePointsActive = false;
                }
                gameUtils.getPlayer().addPoints(points);
            }
        } catch (Exception e) {
            errorLabel.setText("Please type a number");
            errorLabel.setVisible(true);
        }
        lastScoredPoints = points;
        mainCtrl.setAnswersForAnswerReveal(points,true);
    }

    /**
     * Ease out function to award players who guess close to the correct answer more.
     * Can be found here: https://foon.uk/easing/
     * @param fraction The x-value for the function.
     * @return The y-value.
     */
    public double easeOutSinusoidal(double fraction) {
        return Math.sin(0.5 * fraction * Math.PI);
    }


    public void restoreSubmit() {
        submitButton.setVisible(true);
        textField.setText("");
        errorLabel.setVisible(false);
    }
}
