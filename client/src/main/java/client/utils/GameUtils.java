package client.utils;

import static commons.Config.*;
import commons.Player;
import javafx.scene.control.Label;

public class GameUtils {
    private Player player;
    private int currentQuestion;
    private GameType gameType;
    private Long currentTimeMillis;

    public void resetGame() {
        player = null;
        currentQuestion = 0;
        gameType = null;
    }

    public void startTimer() {
        currentTimeMillis = System.currentTimeMillis();
    }

    public long stopTimer() {
        return System.currentTimeMillis() - currentTimeMillis;
    }

    /**
     * Updates the passed in labels to show the current question and score out of the total.
     *
     * @param question Label which contains the current question in format "Question X/Y".
     * @param score    Label which contains the current score in format "Score X/Y".
     * @param update   Indicates if question counter should be incremented, otherwise, if false it will just update
     *                 the given label acoording to currentQuestion variable.
     */
    public void updateTracker(Label question, Label score, boolean update) {
        if (update) {
            currentQuestion++;
        }
        question.setText("Question " + currentQuestion + "/" + totalQuestions);
        score.setText("Score " + player.getScore() + "/" + currentQuestion * 200);
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public enum GameType {
        SinglePlayer,
        MultiPlayer
    }
}
