package client.scenes;

import client.utils.ApplicationUtils;
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
    public EstimateQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(server, mainCtrl, utils);
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
     * Max amount of points can be configured in the config file.
     */
    public void submitGuess(){
        mainCtrl.buttonSound();
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
                points = (int) (fraction * maxPointsPerQuestion);
                mainCtrl.getPlayerScore().addPoints(points);
            }
        } catch (Exception e) {
            errorLabel.setText("Please type a number");
            errorLabel.setVisible(true);
        }
        mainCtrl.setAnswersforAnswerReveal(points,true);
    }


    public void restoreSubmit() {
        submitButton.setVisible(true);
        textField.setText("");
        errorLabel.setVisible(false);
    }
}
