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
        super(mainCtrl, utils);
        this.server = server;
    }

    public void pointsClick() {
        utils.playButtonSound();
        pointsJoker.setVisible(false);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

    public void timeClick() {
        utils.playButtonSound();
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
        mainCtrl.setAnswersForAnswerReveal(activity);
    }

    public void submitGuess(){
        int guess = Integer.parseInt(textField.getText());
        int points = (int)(guess/(double)activity.getEnergyConsumption() * 200);
        mainCtrl.getPlayer().addPoints(points);
        mainCtrl.setAnswersForAnswerReveal(points,true);
    }


    public void restoreSubmit() {
        submit.setDisable(false);
        textField.setText("");
        errorLabel.setVisible(false);
    }
}
