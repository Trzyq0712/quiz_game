package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

import static client.Config.maxChatMessages;
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
    ProgressBar pgBar;

    @FXML
    Label questionTracker;
    @FXML
    ImageView music;

    @FXML
    VBox chatbox;
    @FXML
    StackPane emotePane;
    @FXML
    Label chatboxTitle;
    @FXML
    Label emoteTitle;

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

    public void answerClick(Event event) {
        mainCtrl.buttonSound();
        List<Button> listOfButtons = Arrays.asList(firstButton, secondButton, thirdButton);
        Button activated = (Button) event.getSource();
        for (Button b : listOfButtons) {
            if (b.getId() != activated.getId()) {
                b.setVisible(false);
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

    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBar, timePerQuestion, 0);
    }

    public void updateQuestionTracker() {
        mainCtrl.updateQuestionTracker(questionTracker, true);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

    public void activateSingleplayer() {
        chatboxTitle.setVisible(false);
        chatbox.setVisible(false);
        emotePane.setVisible(false);
        emoteTitle.setVisible(false);
        timeJoker.setVisible(false);
    }

    public void activateMultiplayer() {
        chatboxTitle.setVisible(true);
        chatbox.setVisible(true);
        emotePane.setVisible(true);
        emoteTitle.setVisible(true);
        timeJoker.setVisible(true);
    }




}
