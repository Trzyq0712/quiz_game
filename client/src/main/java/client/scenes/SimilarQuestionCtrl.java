package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;


public class SimilarQuestionCtrl extends BaseQuestionCtrl {


    @FXML
    Label ActivityDescription;
    @FXML
    ImageView questionImage;
    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    private Activity activity;

    private List<Activity> activities;

    @Inject
    public SimilarQuestionCtrl(ServerUtils server, MainCtrl mainCtrl,
                                  ApplicationUtils utils, GameUtils gameUtils) {
        super(server, mainCtrl, utils, gameUtils);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * gets 3 activities from the server, calculates the correct answer and displays the activities
     */
    public void generateActivity() {
        activities = server.get3Activities(gameUtils.getCurrentQuestion(), gameUtils.getGameID()).getListOfActivities();
        long answer = activities.stream().map(Activity::getEnergyConsumption)
                .sorted().collect(Collectors.toList()).get(2);
        answerButtonId = activities.stream().map(Activity::getEnergyConsumption)
                .collect(Collectors.toList()).indexOf(answer) + 1;
        displayActivities();
        setHasPlayerAnswered(false);
    }

    private void displayActivities() {

        mainCtrl.setAnswersForAnswerReveal(activities, answerButtonId);
    }

}
