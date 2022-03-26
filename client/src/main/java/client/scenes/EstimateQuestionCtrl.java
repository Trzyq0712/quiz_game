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
    Button submit;
    @FXML
    private Label errorLabel;


    @Inject
    public EstimateQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(server, mainCtrl, utils);
    }


    public void generateActivity() {
        activity = server.getActivity();
        displayActivity();
        setHasPlayerAnswered(false);
    }

    private void displayActivity() {
        ActivityDescription.setText(activity.getDescription());
        questionImage.setImage(new Image(ServerUtils.SERVER + activity.getPicturePath()));
        mainCtrl.setAnswersforAnswerReveal(activity);
    }

    public void submitGuess() {
        int earnedPoints = 0;
        setHasPlayerAnswered(true);
        try {
            String number = textField.getText();
            if (number.contains(" ")) {
                errorLabel.setText("No whitespaces allowed!");
                errorLabel.setVisible(true);
            }
            long guess = Long.parseLong(number);
            long correctAnswer = activity.getEnergyConsumption();
            if (guess == correctAnswer) {
                earnedPoints = 200;
            } else if (correctAnswer - (0.1 * correctAnswer) <= guess
                    && guess <= correctAnswer + (0.1 * correctAnswer)) {
                earnedPoints = 180;
            } else if (correctAnswer - (0.2 * correctAnswer) <= guess
                    && guess <= correctAnswer + (0.2 * correctAnswer)) {
                earnedPoints = 160;
            } else if (correctAnswer - (0.3 * correctAnswer) <= guess
                    && guess <= correctAnswer + (0.3 * correctAnswer)) {
                earnedPoints = 140;
            } else if (correctAnswer - (0.4 * correctAnswer) <= guess
                    && guess <= correctAnswer + (0.4 * correctAnswer)) {
                earnedPoints = 120;
            } else if (correctAnswer - (0.5 * correctAnswer) <= guess
                    && guess <= correctAnswer + (0.5 * correctAnswer)) {
                earnedPoints = 100;
            } else if (correctAnswer - (0.7 * correctAnswer) <= guess
                    && guess <= correctAnswer + (0.7 * correctAnswer)) {
                earnedPoints = 50;
            }
            if (doublePoints)
                earnedPoints *= 2;
            submit.setDisable(true);
            mainCtrl.getPlayerScore().addPoints(earnedPoints);
            mainCtrl.setAnswersforAnswerReveal(earnedPoints, true);
        } catch (NumberFormatException e) {
            errorLabel.setText("Please type a number");
            errorLabel.setVisible(true);
            mainCtrl.setAnswersforAnswerReveal(earnedPoints, true);
        }
    }


    public void restoreSubmit() {
        submit.setDisable(false);
        setHasPlayerAnswered(false);
        textField.setText("");
        errorLabel.setVisible(false);
        setDoublePoints(false);
    }
}
