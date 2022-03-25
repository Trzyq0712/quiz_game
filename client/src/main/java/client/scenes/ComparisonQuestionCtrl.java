package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
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


public class ComparisonQuestionCtrl extends BaseQuestionCtrl {


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
    public ComparisonQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(server, mainCtrl, utils, gameUtils);

    }

    public List<Activity> getActivities() {
        return activities;
    }


    /**
     * hides all buttons except for the one that was clicked
     * @param event button that was clicked, so either A, B or C
     */
    public void answerClick(Event event) {
        utils.playButtonSound();
        long timeToAnswer = gameUtils.stopTimer();
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
        Platform.runLater(() -> {
            ActivityDescription1.setText(activities.get(0).getDescription());
            ActivityDescription2.setText(activities.get(1).getDescription());
            ActivityDescription3.setText(activities.get(2).getDescription());
            questionImage1.setImage(new Image(ServerUtils.SERVER + activities.get(0).getPicturePath()));
            questionImage2.setImage(new Image(ServerUtils.SERVER + activities.get(1).getPicturePath()));
            questionImage3.setImage(new Image(ServerUtils.SERVER + activities.get(2).getPicturePath()));
        });
        mainCtrl.setAnswersForAnswerReveal(activities,answerButtonId);
    }

}
