package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MCQuestionCtrl extends BaseQuestionCtrl {

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
    Label questionTracker;
    @FXML
    Label scoreLabel;
    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;
    private Activity activity;

    @Inject
    public MCQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(server, mainCtrl, utils, gameUtils);
    }


    public void generateActivity() {
        activity = server.getSingleActivity(gameUtils.getCurrentQuestion(), gameUtils.getGameID());
        long answer = activity.getEnergyConsumption();
        long option1;
        long option2;
        int random = (int)(Math.random()*10);
        switch (random%4){
            case 0:
                option1= (long) (answer * 1.5);
                option2= (long) (answer * 0.5);
                break;
            case 1:
                option1= (long) (answer * 1.5);
                option2= (long) (answer * 2.0);
                break;
            default:
                option1= (long) (answer * 0.75);
                option2= (long) (answer * 0.5);
                break;
        }
        Activity a = new Activity(activity.getDescription(), option1, activity.getPicturePath());
        Activity b = new Activity(activity.getDescription(), option2, activity.getPicturePath());
        List<Activity> activities = Arrays.asList(activity, a, b);
        Collections.shuffle(activities);
        answerButtonId = activities.indexOf(activity) + 1;
        displayActivity(activities);
        setHasPlayerAnswered(false);
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
     *
     * @param event button that was clicked, so either A, B or C
     */
   /* public void answerClick(Event event) {
        utils.playButtonSound();
        long timeToAnswer = gameUtils.stopTimer();
        setHasPlayerAnswered(true);
        List<Button> listOfButtons = Arrays.asList(firstButton, secondButton, thirdButton);
        Button activated = (Button) event.getSource();
        long i = 0;
        long buttonNb = 0;
        for (Button b : listOfButtons) {
            i++;
            if (!b.getId().equals(activated.getId())) {
                b.setVisible(false);
            } else {
                buttonNb = i;
            }
        }
        grantPoints(new Answer(buttonNb, timeToAnswer));
    }*/



}
