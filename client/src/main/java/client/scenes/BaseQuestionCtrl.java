package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import commons.Answer;
import commons.Emote;
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



    public BaseQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils, server);
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
        jokerConfirmation.setVisible(false);
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
        server.disconnect();
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
     * @param e - emote
     */
    public void emote(Event e) {
        String path = ((ImageView)e.getSource()).getImage().getUrl();
        Emote emote = new Emote(path,mainCtrl.getPlayerScore().getPlayerName());
        server.send("/app/emote/1", emote);
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
        jokerConfirmation.setText("Your scored points will be doubled!");
        jokerConfirmation.setVisible(true);
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
