package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import commons.Answer;
import commons.Emote;
import commons.Player;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static commons.Config.*;


public abstract class BaseQuestionCtrl extends BaseCtrl {

    protected boolean doublePoints;
    protected int answerButtonId;
    protected long answer;
    protected boolean hasPlayerAnswered;
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
    ProgressBar pgBar;
    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;

    protected boolean doublePointsActive;
    protected int lastScoredPoints; //this will be doubled if the player activates 2x points.



    public BaseQuestionCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    public boolean getHasPlayerAnswered() {
        return hasPlayerAnswered;
    }

    public void setHasPlayerAnswered(boolean bool) {
        hasPlayerAnswered = bool;
    }

    /**
     * Keeps track of number of questions played
     * and how many points we scored
     */
    public void updateTracker() {
        gameUtils.updateTracker(questionTracker, scoreLabel, true);
    }


    /**
     * triggers the progressbar of this scene when called, 0 indicates what to do when the bar depletes
     * see activateGenericProgressBar in mainCtrl for more info
     */
    public void activateProgressBar() {
        utils.runProgressBar(pgBar, timePerQuestion, mainCtrl::showAnswerReveal);
    }

    public void restoreDoublePoints() {
        hasPlayerAnswered = false;
        doublePointsActive = false;
    }

    /**
     * If in a multiple choice type of question,
     * then all the buttons are visible again for the new question
     */
    public void restoreAnswers() {
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        thirdButton.setVisible(true);
    }

    /**
     * After a game is completed, or stopped
     * the jokers are accessible again in the next new game
     */
    public void restoreJokers() {
        mainCtrl.visibilityTimeJoker(true);
        mainCtrl.visibilityHintJoker(true);
        mainCtrl.visibilityPointsJoker(true);
    }

    /**
     * @param answer - answer the player submitted,
     *               button the player clicked on
     */
    public void grantPoints(Answer answer) {
        int earnedPoints = 0;
        if (answer.getAnswer() == answerButtonId) earnedPoints = answer.getPoints();
        if (!hasPlayerAnswered && doublePointsActive) {
            earnedPoints *= 2;
            doublePointsActive = false;
        }
        lastScoredPoints = earnedPoints;
        mainCtrl.setAnswersForAnswerReveal(earnedPoints, false);
        setHasPlayerAnswered(true);
        gameUtils.getPlayer().addPoints(earnedPoints);
        Long gameID = gameUtils.getGameID();
        Player player = gameUtils.getPlayer();
        server.updateScore(gameID, player);
    }

    /**
     * Disables the player to click on the time joker
     */
    @FXML
    private void timeClick() {
        utils.playButtonSound();
        mainCtrl.visibilityTimeJoker(false);
    }

    /**
     * Adds the emote to the chatbox
     *
     * @param e - emote
     */
    public void emote(Event e) {
        String path = ((ImageView) e.getSource()).getImage().getUrl();
        Emote emote = new Emote(path, gameUtils.getPlayer().getPlayerName());
        server.send("/app/emote/" + gameUtils.getGameID(), emote);
    }

    /**
     * Disables the player to click on double points joker again
     * notifies the player that they activated the joker
     */
    @FXML
    protected void pointsClick() {
        utils.playButtonSound();
        mainCtrl.visibilityPointsJoker(false);
        if (hasPlayerAnswered) {
            gameUtils.getPlayer().addPoints(lastScoredPoints);
            mainCtrl.setAnswersForAnswerReveal(lastScoredPoints*2, false);
        } else doublePointsActive = true;
        utils.addNotification("2x points activated", "green");

    }

    /**
     * Disables the player to click on hint joker again
     * and hides one of the wrong answers
     */

    @FXML
    protected void hintClick () {
        utils.playButtonSound();
        utils.addNotification("hint activated", "green");
        mainCtrl.visibilityHintJoker(false);
        List<Button> listOfButtons = Arrays.asList(firstButton, secondButton, thirdButton);
        List<Button> wrongButtons = new ArrayList<>();
        for (Button b : listOfButtons) {
            if (listOfButtons.indexOf(b) + 1 != answerButtonId) {
                wrongButtons.add(b);
            }
        }
        int indexToBeRemoved = (int) (Math.random() * 2);
        wrongButtons.get(indexToBeRemoved).setVisible(false);
    }

    /**
     * Gives a hint for the estimate question
     * and disables the player to click on hint joker again
     */
    @FXML
    protected void estimateHintClick () {
        utils.playButtonSound();
        mainCtrl.visibilityHintJoker(false);
        int nbOfDigits = (answer+"").length(); //transform the long into a String
        utils.addNotification("There are "+nbOfDigits+" digits in the answer","green");
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
        hasPlayerAnswered = true;
    }

}
