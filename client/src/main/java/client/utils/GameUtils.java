package client.utils;

import client.Config;
import commons.Player;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Pair;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameUtils {
    private Player player;
    private int currentQuestion;
    private Timer timer;
    private GameType gameType;
    private Long currentTimeMillis;

    public void resetGame() {
        player = null;
        currentQuestion = 0;
        cancelProgressBar();
        gameType = null;
    }

    public void runProgressBarWithCallback(ProgressBar progressBar, long runTime, Runnable callback) {
        timer = new Timer();
        int steps = 200;
        TimerTask updateProgressBar = new TimerTask() {
            @Override
            public void run() {
                double progress = progressBar.getProgress();
                System.out.println(progress);
                Platform.runLater(() -> progressBar.setProgress(progress - 1.0 / (double)steps));
            }
        };
        List<Pair<Double, String>> updates = List.of(
                new Pair<>(0.0, "green"), new Pair<>(0.5, "orange"), new Pair<>(0.75, "red"));
        for (var update : updates) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    progressBar.setStyle("-fx-accent: " + update.getValue());
                }
            }, (long)(update.getKey() * runTime));
        }
        timer.schedule(updateProgressBar, 0, runTime / steps);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(callback::run);
                timer.cancel();
            }
        }, runTime);
    }

    public void cancelProgressBar() {
        if (timer != null) timer.cancel();
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
        question.setText("Question " + currentQuestion + "/" + Config.totalQuestions);
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
