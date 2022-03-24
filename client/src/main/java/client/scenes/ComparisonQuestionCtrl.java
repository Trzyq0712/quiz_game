package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.Answer;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ComparisonQuestionCtrl extends BaseCtrl {


    @FXML
    Label ActivityDescription1;
    @FXML
    Label ActivityDescription2;
    @FXML
    Label ActivityDescription3;

    @FXML
    ImageView questionImage1;
    @FXML
    ImageView questionImage2;
    @FXML
    ImageView questionImage3;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    //Long startTime;

    private  List<Activity> activities;

    @Inject
    public ComparisonQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;

    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void showHome() {
        mainCtrl.showHome();
        restoreAnswers();
        restoreJokers();
        utils.playButtonSound();
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

    public void pointsClick() {
        utils.playButtonSound();
        pointsJoker.setVisible(false);
    }

    public void timeClick() {
        utils.playButtonSound();
        timeJoker.setVisible(false);
    }

    /**
     * triggers the progressbar of this scene when called, 0 indicates what to do when the bar depletes
     * see activateGenericProgressBar in mainCtrl for more info
     */
    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBar, timePerQuestion, 0);
    }

    public void updateTracker() {
        mainCtrl.updateTracker(questionTracker, scoreLabel, true);
    }

    public void emote(Event e){
        utils.playButtonSound();
        mainCtrl.emote(e);
    }

    /**
     * gets 3 activities from the server, calculates the correct answer and displays the activities
     */
    public void generateActivity(){
        activities = server.get3Activities();
        long answer = activities.stream().map(Activity::getEnergyConsumption)
                .sorted().collect(Collectors.toList()).get(2);
        answerButtonId = activities.stream().map(Activity::getEnergyConsumption)
                .collect(Collectors.toList()).indexOf(answer) + 1;
        displayActivities();
    }

    private void displayActivities() {
        ActivityDescription1.setText(activities.get(0).getDescription());
        ActivityDescription2.setText(activities.get(1).getDescription());
        ActivityDescription3.setText(activities.get(2).getDescription());
        questionImage1.setImage(new Image(ServerUtils.SERVER + activities.get(0).getPicturePath()));
        questionImage2.setImage(new Image(ServerUtils.SERVER + activities.get(1).getPicturePath()));
        questionImage3.setImage(new Image(ServerUtils.SERVER + activities.get(2).getPicturePath()));
        mainCtrl.setAnswersForAnswerReveal(activities,answerButtonId);
    }

}
