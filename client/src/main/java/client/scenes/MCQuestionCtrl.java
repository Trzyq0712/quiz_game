package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import commons.Activity;
import commons.Answer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static client.Config.timePerQuestion;

public class MCQuestionCtrl extends BaseCtrl {

    protected final ServerUtils server;

    private Activity activity;
    private int answerButtonId;

    @FXML
    ImageView hintJoker;
    @FXML
    ImageView pointsJoker;
    @FXML
    ImageView timeJoker;
    @FXML
    Label ActivityDescription;
    @FXML
    ImageView questionImage;
    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;
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

    @Inject
    public MCQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
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

    public void showHome() {
        mainCtrl.showHome();
        restoreAnswers();
        restoreJokers();
    }

    public void updateTracker() {
        mainCtrl.updateTracker(questionTracker, scoreLabel, true);
    }

    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBar, timePerQuestion, 0);
    }

    public void generateActivity() {
        activity = server.getActivity();
        long answer = activity.getEnergyConsumption();
        Activity a = new Activity(activity.getDescription(), (long)(answer*1.5),activity.getPicturePath());
        Activity b = new Activity(activity.getDescription(), (long)(answer*0.5),activity.getPicturePath());
        List<Activity> activities = Arrays.asList(activity,a,b);
        Collections.shuffle(activities);
        answerButtonId = activities.indexOf(activity)+1;
        displayActivity(activities);
    }

    private void displayActivity(List<Activity> activities) {
        ActivityDescription.setText(activity.getDescription());
        questionImage.setImage(new Image(ServerUtils.SERVER + activity.getPicturePath()));
        firstButton.setText(String.valueOf(activities.get(0).getEnergyConsumption()));
        secondButton.setText(String.valueOf(activities.get(1).getEnergyConsumption()));
        thirdButton.setText(String.valueOf(activities.get(2).getEnergyConsumption()));
        mainCtrl.setAnswersForAnswerReveal(activities, answerButtonId);
    }

    /**
     * hides all buttons except for the one that was clicked
     * @param event button that was clicked, so either A, B or C
     */
    public void answerClick(Event event) {
        utils.playButtonSound();
        long timeToAnswer = mainCtrl.getDelta();
        List<Button> listOfButtons = Arrays.asList(firstButton, secondButton, thirdButton);
        Button activated = (Button) event.getSource();
        long i = 0;
        long buttonNb = 0;
        for (Button b : listOfButtons) {
            i++;
            if (!b.getId().equals(activated.getId())) {
                b.setVisible(false);
            } else{
                buttonNb=i;
            }
        }
        grantPoints(new Answer(buttonNb, timeToAnswer));
    }

    /**
     * @param answer - answer the player submitted
     */
    public void grantPoints(Answer answer){
        int earnedPoints = 0;
        if(answer.getAnswer() == answerButtonId)
            earnedPoints = answer.getPoints();
        mainCtrl.getPlayer().addPoints(earnedPoints);
        mainCtrl.setAnswersForAnswerReveal(earnedPoints,false);
    }

    public void restoreJokers() {
        hintJoker.setVisible(true);
        timeJoker.setVisible(true);
        pointsJoker.setVisible(true);
    }

    public void restoreAnswers() {
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        thirdButton.setVisible(true);
    }

    public void hintClick() {
        utils.playButtonSound();
        hintJoker.setVisible(false);
        String falseAnswer = server.activateHint();
        switch (falseAnswer) {
            case "a":
                firstButton.setVisible(false);
                break;
            case "b":
                secondButton.setVisible(false);
                break;
            case "c":
                thirdButton.setVisible(false);
                break;
        }
    }
}
