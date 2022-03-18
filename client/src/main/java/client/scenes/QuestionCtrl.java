package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Activity;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static client.Config.timePerQuestion;
import static com.google.inject.Guice.createInjector;

public class QuestionCtrl extends ReusedButtonCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    ImageView hintJoker;
    @FXML
    ImageView pointsJoker;
    @FXML
    ImageView timeJoker;

    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;

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
    ProgressBar pgBar;

    @FXML
    Label questionTracker;
    @FXML
    ImageView music;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    //Long startTime;
    int amountOfMessages = 0;


    @Inject
    public QuestionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void showHome() {
        mainCtrl.showHome();
        restoreAnswers();
        restoreJokers();
    }

    public void restoreAnswers() {
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        thirdButton.setVisible(true);
    }

    public void restoreJokers() {
        hintJoker.setVisible(true);
        timeJoker.setVisible(true);
        pointsJoker.setVisible(true);
    }

    /**
     * hides all buttons except for the one that was clicked
     * @param event button that was clicked, so either A, B or C
     */
    public void answerClick(Event event) {
        mainCtrl.buttonSound();
        long timeToAnswer = mainCtrl.getDelta();
        List<Button> listOfButtons = Arrays.asList(firstButton, secondButton, thirdButton);
        Button activated = (Button) event.getSource();
        long i = 0;
        long buttonNb = 0;
        for (Button b : listOfButtons) {
            i++;
            if (b.getId() != activated.getId()) {
                b.setVisible(false);
            } else{
                buttonNb=i;
            }
        }
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    public void hintClick() {
        mainCtrl.buttonSound();
        hintJoker.setVisible(false);
    }

    public void pointsClick() {
        mainCtrl.buttonSound();
        pointsJoker.setVisible(false);
    }

    public void timeClick() {
        mainCtrl.buttonSound();
        timeJoker.setVisible(false);

    }

    /**
     * triggers the progressbar of this scene when called, 0 indicates what to do when the bar depletes
     * see activateGenericProgressBar in mainCtrl for more info
     */
    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBar, timePerQuestion, 0);
    }

    public void updateQuestionTracker() {
        mainCtrl.updateQuestionTracker(questionTracker, true);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

    public void generateActivity(){
        List<Activity> activities = server.get3Activities();
        ActivityDescription1.setText(activities.get(0).getDescription());
        ActivityDescription2.setText(activities.get(1).getDescription());
        ActivityDescription3.setText(activities.get(2).getDescription());
        questionImage1.setImage(new Image(ServerUtils.SERVER + activities.get(0).getPicturePath()));
        questionImage2.setImage(new Image(ServerUtils.SERVER + activities.get(1).getPicturePath()));
        questionImage3.setImage(new Image(ServerUtils.SERVER + activities.get(2).getPicturePath()));
        mainCtrl.setAnswersforAnswerReveal(activities);
    }

}
