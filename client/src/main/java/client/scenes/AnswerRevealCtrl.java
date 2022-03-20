package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Activity;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

import static client.Config.timeAnswerReveal;
import static com.google.inject.Guice.createInjector;

public class AnswerRevealCtrl extends ReusedButtonCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    ProgressBar pgBarReveal;

    @FXML
    Label questionTracker;

    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;

    @FXML
    ImageView music;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    @Inject
    public AnswerRevealCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * starts the countdown of the progressbar for the answer reveal
     */
    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBarReveal, timeAnswerReveal, 1);
    }

    /**
     * updates the label of this page which displays the current question.
     * it's called with false since the question doesn't update when answers are revealed, only when the next question
     * shows.
     */

    public void updateQuestionTracker() {
        mainCtrl.updateQuestionTracker(questionTracker, false);
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

    /**
     * takes in a list of 3 activities and sets the values next to the answer
     * @param activities - list of 3 activities passed from the QuestionCtrl
     */
    public void setAnswers(List<Activity> activities) {
        label1.setText(activities.get(0).getEnergyConsumption().toString());
        label2.setText(activities.get(1).getEnergyConsumption().toString());
        label3.setText(activities.get(2).getEnergyConsumption().toString());
    }
}
