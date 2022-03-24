package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import commons.Answer;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import static client.Config.timePerQuestion;

public abstract class BaseQuestionCtrl extends BaseCtrl {

    @FXML
    ImageView hintJoker;
    @FXML
    ImageView pointsJoker;
    @FXML
    ImageView timeJoker;

    @FXML
    Label questionTracker;
    @FXML
    Label scoreLabel;
    @FXML
    Label jokerConfirmation;

    @FXML
    ProgressBar pgBar;

    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;

    protected boolean doublePoints;
    protected int answerButtonId;

    protected final ServerUtils server;


    public BaseQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    /**
     * Keeps track of number of questions played
     * and how many points we scored
     */
    public void updateTracker() {
        mainCtrl.updateTracker(questionTracker, scoreLabel, true);
    }

    /**
     * @param b - true if we are playing for double points
     *          - false otherwise
     */
    public void setDoublePoints(Boolean b) {
        doublePoints = b;
        if (b) {
            jokerConfirmation.setText("Your want to double your points!");
            jokerConfirmation.setVisible(true);
        } else {
            jokerConfirmation.setVisible(false);
        }
    }

    /**
     * triggers the progressbar of this scene when called, 0 indicates what to do when the bar depletes
     * see activateGenericProgressBar in mainCtrl for more info
     */
    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBar, timePerQuestion, 0);
    }

    /**
     * If in a multiple choice type of question,
     * then all the buttons are visible again for the new question
     */
    public void restoreAnswers() {
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        thirdButton.setVisible(true);
        setDoublePoints(false);
    }

    /**
     * After a game is completed, or stopped
     * the jokers are accessible again in the next new game
     */
    public void restoreJokers() {
        hintJoker.setVisible(true);
        timeJoker.setVisible(true);
        pointsJoker.setVisible(true);
        setDoublePoints(false);
    }

    /**
     * Goes to the home screen
     */
    public void showHome() {
        mainCtrl.showHome();
        restoreAnswers();
        restoreJokers();
    }

    /**
     * @param answer - answer the player submitted,
     *               button the player clicked on
     */
    public void grantPoints(Answer answer) {
        int earnedPoints = 0;
        if (answer.getAnswer() == answerButtonId)
            earnedPoints = answer.getPoints();
        if (doublePoints)
            earnedPoints *= 2;
        mainCtrl.getPlayerScore().addPoints(earnedPoints);
        mainCtrl.setAnswersforAnswerReveal(earnedPoints, false);
    }

    /**
     * Disables the player to click on the time joker
     */
    @FXML
    private void timeClick() {
        mainCtrl.buttonSound();
        timeJoker.setVisible(false);
    }

    /**
     * Adds the emote to the chatbox
     *
     * @param e - emote
     */
    public void emote(Event e) {
        mainCtrl.emote(e);
    }

    /**
     * Disables the player to click on double points joker again
     * notifies the player that they activated the joker
     */
    @FXML
    private void pointsClick() {
        mainCtrl.buttonSound();
        pointsJoker.setVisible(false);
        setDoublePoints(true);
    }

    /**
     * Disables the player to click on hint joker again
     * and hides one of the wrong answers
     */
    public void hintClick() {
        mainCtrl.buttonSound();
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
