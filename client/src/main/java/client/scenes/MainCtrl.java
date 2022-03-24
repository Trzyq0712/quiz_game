/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;


import commons.PlayerScore;
import client.Config;
import commons.Activity;
import javafx.application.Platform;
import commons.Player;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;


public class MainCtrl  {

    private Stage primaryStage;
    private Stage secondaryStage;

    private Player player;

    private PlayerScore playerScore;

    private HomeScreenCtrl homeCtrl;
    private Scene homeScene;

    private SinglePlayerLeaderboardCtrl splCtrl;
    private Scene splScene;

    private ExitScreenCtrl exitCtrl;
    private Scene exitScene;

    private WaitingRoomCtrl waitingCtrl;
    private Scene waitingScene;

    private IntermediateLeaderboardCtrl intermediateCtrl;
    private Scene intermediateScene;

    private AnswerRevealCtrl answerRevealCtrl;
    private Scene answerRevealScene;

    private MPFinalLeaderboardCtrl MPFinal;
    private Scene MPFinalScene;

    private InfoCtrl infoCtrl;
    private Scene infoScene;

    private EditScreenCtrl editCtrl;
    private Scene editScene;

    private PromptCtrl promptCtrl;
    private Scene promptScene;

    private QuestionCtrl questionCtrl;
    private Scene questionScene;

    private EstimateQuestionCtrl estimateQuestionCtrl;
    private Scene estimateQuestionScene;

    private MCQuestionCtrl MCQuestionCtrl;
    private Scene MCQuestionScene;

    private EditActivityCtrl editActivityCtrl;
    private Scene editActivityScene;

    Long startTime;
    int currentQuestion = 0;

    boolean active = true; /* if true progressbar will load the next scene on depletion, if false, it means the user has
    clicked the homebutton. So he exited the game
    at which point the next scene shouldnt be loaded anymore */
    /**
     * If true, game knows the player is in singleplayer, if false, the game knows
     * that the player is in multiplayer.
     */
    boolean singlePlayerModeActive;
    /**
     * Amount of messages currently displaying in the chat.
     */
    int amountOfMessages = 0;
    List<VBox> listOfChatBoxes;
    List<StackPane> listOfHolders;

    public void initialize(Stage primaryStage,
                           Pair<HomeScreenCtrl, Parent> home,
                           Pair<SinglePlayerLeaderboardCtrl, Parent> sp,
                           Pair<ExitScreenCtrl, Parent> exit,
                           Pair<WaitingRoomCtrl, Parent> waiting,
                           Pair<EditScreenCtrl, Parent> edit,
                           Pair<IntermediateLeaderboardCtrl, Parent> intermediate,
                           Pair<AnswerRevealCtrl, Parent> answerReveal,
                           Pair<MPFinalLeaderboardCtrl, Parent> MPFinalLeaderboard,
                           Pair<InfoCtrl, Parent> info,
                           Pair<PromptCtrl, Parent> prompt,
                           Pair<QuestionCtrl, Parent> question,
                           Pair<EditActivityCtrl, Parent> editActivity,
                           Pair<EstimateQuestionCtrl, Parent> estimateQuestion,
                           Pair<MCQuestionCtrl, Parent> MCQuestion) {
        this.primaryStage = primaryStage;
        /*this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());*/


        this.homeCtrl = home.getKey();
        this.homeScene = new Scene(home.getValue());

        this.splCtrl = sp.getKey();
        this.splScene = new Scene(sp.getValue());

        this.exitCtrl = exit.getKey();
        this.exitScene = new Scene(exit.getValue());

        this.waitingCtrl = waiting.getKey();
        this.waitingScene = new Scene(waiting.getValue());

        this.intermediateCtrl = intermediate.getKey();
        this.intermediateScene = new Scene(intermediate.getValue());

        this.answerRevealCtrl = answerReveal.getKey();
        this.answerRevealScene = new Scene(answerReveal.getValue());

        this.MPFinal = MPFinalLeaderboard.getKey();
        this.MPFinalScene = new Scene(MPFinalLeaderboard.getValue());

        this.infoCtrl = info.getKey();
        this.infoScene = new Scene(info.getValue());

        this.editCtrl = edit.getKey();
        this.editScene = new Scene(edit.getValue());

        this.promptCtrl = prompt.getKey();
        this.promptScene = new Scene(prompt.getValue());

        this.questionCtrl = question.getKey();
        this.questionScene = new Scene(question.getValue());

        this.estimateQuestionCtrl = estimateQuestion.getKey();
        this.estimateQuestionScene = new Scene(estimateQuestion.getValue());

        this.MCQuestionCtrl = MCQuestion.getKey();
        this.MCQuestionScene = new Scene(MCQuestion.getValue());

        this.editActivityCtrl = editActivity.getKey();
        this.editActivityScene = new Scene(editActivity.getValue());

        secondaryStage = new Stage();

        //showOverview();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            if(player != null)
                waitingCtrl.leaveWaitingroom(player);
            primaryStage.close();
        });
        showHome();
        initializeChatBoxes();
        initializeHolders();
        primaryStage.show();
    }

    public PlayerScore getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(PlayerScore playerScore) {
        this.playerScore = playerScore;
    }

    /**
     * Initializes an array of all the chatboxes in the application, this way they can be easily accessed and all kept
     * in sync.
     */

    public void initializeChatBoxes() {
        listOfChatBoxes = Arrays.asList(questionCtrl.chatbox, intermediateCtrl.chatbox, answerRevealCtrl.chatbox,
                MPFinal.chatbox);
    }

    /**
     * Initializes an array of all the stackpanes which are holding the chatbox and emoji's. this way they can be
     * easily accessed and their visibility property can be easily toggled when the player is playing singleplayer or
     * multiplayer.
     */

    public void initializeHolders() {
        listOfHolders = Arrays.asList(questionCtrl.chatAndEmoteHolder, answerRevealCtrl.chatAndEmoteHolder,
                intermediateCtrl.chatAndEmoteHolder, MPFinal.chatAndEmoteHolder,
                 estimateQuestionCtrl.chatAndEmoteHolder, MCQuestionCtrl.chatAndEmoteHolder);
    }

    /**
     * This method modifies the question screen so that it's suited for singleplayer, in this case, it's hiding the
     * time joker since that isn't applicable to singleplayer.
     */

    public void activateSingleplayer() {
        questionCtrl.timeJoker.setVisible(false);
        estimateQuestionCtrl.timeJoker.setVisible(false);
        MCQuestionCtrl.timeJoker.setVisible(false);
        for (StackPane s : listOfHolders) {
            s.setVisible(false);
        }
    }

    /**
     * This method modifies the question screen so that it's suited for multiplayer, in this case, it's showing the
     * time joker since that is applicable to multiplayer.
     */

    public void activateMultiplayer() {
        questionCtrl.timeJoker.setVisible(true);
        estimateQuestionCtrl.timeJoker.setVisible(true);
        for (StackPane s : listOfHolders) {
            s.setVisible(true);
        }
    }

    /**
     * Produces the sound of a button when invoked, this function should be called when a button is clicked.
     */

    public void buttonSound() {
        Media media = new Media(Config.buttonClickSound.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }

    /**
     * Shows the home screen.
     */

    public void showHome() {
        player = null;
        primaryStage.setTitle(Config.title);
        homeScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(homeScene);
        active = false;
        restore();
        buttonSound();
    }

    /**
     * Based on which button the player clicked, the player will get the nameprompt for single- or multiplayer.
     * @param e The button on which the player has clicked to reach the nameprompt.
     */
    public void showNewPrompt(Event e) {
        String mode = ((Button) e.getSource()).getText();
        if (mode.equals("Singleplayer")) {
            singlePlayerModeActive = true;
            promptCtrl.startButton.setPrefWidth(200);
            promptCtrl.startButton.setText("Enter game");
        } else {
            singlePlayerModeActive = false;
            promptCtrl.startButton.setPrefWidth(500);
            promptCtrl.startButton.setText("Enter waiting room");
        }
        promptScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        promptCtrl.setUp();
        primaryStage.setScene(promptScene);
        buttonSound();
    }

    /**
     * Shows the singleplayer leaderboard.
     */

    public void showSPLeaderboard() {
        splScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(splScene);
        buttonSound();
        splCtrl.showPLayAgain();
    }

    /**
     * Shows the singleplayer leaderboard, without the play again button.
     */

    public void showSPLeaderboardFromHome() {
        splScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(splScene);
        buttonSound();
        splCtrl.hidePlayAgain();
    }

    /**
     * Shows the exitscreen when the user wants to quit the application.
     */

    public void showExitScreen() {
        exitScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        secondaryStage.setScene(exitScene);
        secondaryStage.setTitle(Config.quit);
        secondaryStage.centerOnScreen();
        secondaryStage.sizeToScene();
        /*quitStage.setMinHeight(quitStage.getMinHeight());
        quitStage.setMinWidth(quitStage.getMinWidth());*/
        secondaryStage.show();
        //primaryStage.setScene(exitScene);
        buttonSound();
    }

    /**
     * Shows the question screen, sets
     * active = true
     * so that the application is aware that a game is active.
     * Function triggers the progressbar to start decreasing.
     */


    public void showQuestion() {
        active = true;
         //APPLY CSS SHEET
        if (singlePlayerModeActive)
            activateSingleplayer();
        else
            activateMultiplayer();
        int value = (int)(Math.random()*3);
        switch (value%3){
            case 0: {
                questionScene.getStylesheets().add(Config.styleSheet);
                questionCtrl.updateTracker();
                questionCtrl.generateActivity();
                primaryStage.setScene(questionScene);
                new Thread(() -> questionCtrl.activateProgressBar()).start();
                break;
            }
            case 1: {
                estimateQuestionScene.getStylesheets().add(Config.styleSheet);
                estimateQuestionCtrl.updateTracker();
                estimateQuestionCtrl.generateActivity();
                primaryStage.setScene(estimateQuestionScene);
                new Thread(() -> estimateQuestionCtrl.activateProgressBar()).start();
                break;
            }

            case 2: {
                MCQuestionScene.getStylesheets().add(Config.styleSheet);
                MCQuestionCtrl.updateTracker();
                MCQuestionCtrl.generateActivity();
                primaryStage.setScene(MCQuestionScene);
                new Thread(() -> MCQuestionCtrl.activateProgressBar()).start();
                break;
            }
        }

    }

    /**
     * Shows the waiting room and adds the player to the waiting room so other clients can be informed about this.
     * @param player The player which is added to the waiting room.
     */
    public void enterWaitingRoom(Player player) {
        this.player = player;
        primaryStage.setTitle(Config.titleWaitingRoom);
        waitingScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        waitingCtrl.setUp(player);
        primaryStage.setScene(waitingScene);
        buttonSound();
    }

    /**
     * This is here for reentering a multi game to work without changing a lot of stuff.
     * When we add a multiplayer controller going to remake it.
     * Don't want to do it now not to mess up controllers for the future.
     */
    public void enterWaitingRoom() {
        enterWaitingRoom(player);
    }


    /**
     * Triggers the progressbar to start going down, calls the appropriate function on depletion.
     * @param pgBar The progressbar being modified.
     * @param totalTime The total time the progress bar should last.
     * @param call Indicates what function should be called next.
     */
    public void activateGenericProgressBar(ProgressBar pgBar, double totalTime, int call) {
        if (!active) {
            startTime = null;
            return;
        }
        if (startTime == null) startTime = System.currentTimeMillis();
        double delta = getDelta();
        double progress = (totalTime - delta) / totalTime;
        if (progress >= 0 && progress <= 1) pgBar.setProgress(progress);
        if (progress > 0.7) pgBar.setStyle("-fx-accent: green");
        else if (progress > 0.4) pgBar.setStyle("-fx-accent: orange");
        else pgBar.setStyle("-fx-accent: red");
        if (delta < totalTime) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            // your code here
                            activateGenericProgressBar(pgBar, totalTime, call);
                        }
                    },
                    5
            );
        } else {
            startTime = null;
            if (active) {
                if (call == 0) {
                    Platform.runLater(() -> showAnswerReveal());
                } else if (call == 1 && currentQuestion < Config.totalQuestions) {
                    estimateQuestionCtrl.restoreSubmit();
                    questionCtrl.restoreAnswers();
                    MCQuestionCtrl.restoreAnswers();
                    if (singlePlayerModeActive) Platform.runLater(() -> showQuestion());
                    else Platform.runLater(() -> showIntermediateLeaderboard());
                } else if (call == 1 && currentQuestion >= Config.totalQuestions) {
                    restore();
                    if (singlePlayerModeActive) {
                        splCtrl.addPlayer(getPlayerScore());
                        getPlayerScore().setScore(0);
                        Platform.runLater(() -> showSPLeaderboard());
                    } else Platform.runLater(() -> showMPFinalLeaderboard());
                } else if (call == 2) {
                    Platform.runLater(() -> showQuestion());
                }
            }
        }
    }

    public void refresh(){
        splCtrl.refresh();
    }

    /**
     * Resets the question to 0 and makes jokers and answers visible again.
     * Should be called after a game is done.
     */

    public void restore() {
        currentQuestion = 0;
        questionCtrl.restoreJokers();
        estimateQuestionCtrl.restoreJokers();
        MCQuestionCtrl.restoreJokers();
        questionCtrl.restoreAnswers();
        estimateQuestionCtrl.restoreSubmit();
    }

    /**
     * Updates all the chatboxes to display the emoji that has been clicked.
     * @param e The emote that has been clicked.
     */

    public void emote(Event e) {
        for (VBox c : listOfChatBoxes) {
            HBox hbox = new HBox();
            Image arg = ((ImageView) e.getSource()).getImage();
            Label user = new Label(" user01:  ");
            ImageView emote = new ImageView(arg);
            emote.setFitHeight(50);
            emote.setFitWidth(50);
            hbox.getChildren().addAll(user, emote);
            hbox.setAlignment(Pos.CENTER_LEFT);
            if (amountOfMessages >= Config.maxChatMessages) {
                c.getChildren().remove(0);
            }
            c.getChildren().add(hbox);
            c.setSpacing(10);
        }
        amountOfMessages++;
        buttonSound();
    }

    /**
     * Can be used to keep track of time that has passed since a certain point.
     * @return Time passed since startTime variable in milliseconds.
     */

    public long getDelta() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Shows the screen where answers are revealed.
     */

    public void showAnswerReveal() {
        answerRevealCtrl.updateTracker();
        answerRevealScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(answerRevealScene);
        new Thread(() -> answerRevealCtrl.activateProgressBar()).start();
    }

    public void showMPFinalLeaderboard() {
        MPFinalScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(MPFinalScene);
    }

    public void showIntermediateLeaderboard() {
        intermediateCtrl.updateQuestionTracker();
        intermediateScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(intermediateScene);
        new Thread(() -> intermediateCtrl.activateProgressBar()).start();
    }

    /**
     * Updates the passed in labels to show the current question and score out of the total.
     * @param question Label which contains the current question in format "Question X/Y".
     * @param score Label which contains the current score in format "Score X/Y".
     * @param update Indicates if question counter should be incremented, otherwise, if false it will just update
     * the given label acoording to currentQuestion variable.
     */

    public void updateTracker(Label question, Label score, boolean update) {
        if (update) {
            currentQuestion++;
        }
        question.setText("Question " + currentQuestion + "/" + Config.totalQuestions);
        score.setText("Score " + playerScore.getScore() + "/" + currentQuestion * 200);
    }

    public void showInfo() {
        infoCtrl.setHintExplainer();
        infoCtrl.setTimeExplainer();
        infoCtrl.setDoublePointsExplainerExplainer();
        infoScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(infoScene);
    }

    public void showEditScreen() {
        primaryStage.setTitle(Config.edit);
        editCtrl.setUp();
        editScene.getStylesheets().add(Config.styleSheet);//APPLY CSS SHEET
        primaryStage.setScene(editScene);
    }

    public void editActivity(boolean add, Activity activity) {
        editActivityCtrl.setUp(add, activity);
        editActivityScene.getStylesheets().add(Config.styleSheet); //APPLY CSS SHEET
        secondaryStage.setScene(editActivityScene);
        secondaryStage.centerOnScreen();
        secondaryStage.sizeToScene();
        secondaryStage.show();
    }

    public void updateEdit(Activity newActivity) {
        editCtrl.updateEdit(newActivity);

    }

    public void updateAdd(Activity newActivity) {
        editCtrl.updateAdd(newActivity);
    }

    public Stage getSecondaryStage(){
        return secondaryStage;
    }

    /*public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }*/
    /**
     * Used to prepare the answer reveal screen for a multiple choice question with 3 activities as answers
     * @param activities - a list of 3 activities
     * @param answerButtonId - the id of the correct answer button
     */
    public void setAnswersforAnswerReveal(List<Activity> activities,int answerButtonId) {
        answerRevealCtrl.setAnswers(activities,answerButtonId);
    }

    /**
     * Used to prepare the answer reveal screen for an estimate question
     * @param activity - the generated activity
     */
    public void setAnswersforAnswerReveal(Activity activity) {
        answerRevealCtrl.setAnswer(activity);
    }

    /**
     * Used to send the answer reveal screen the obtained points
     * @param points - the points that the player obtained
     * @param bool - indicates which label to set visible
     *             -> true for estimate related label
     *             -> false for MC with 3 activities related label
     */
    public void setAnswersforAnswerReveal(int points, boolean bool) {
        answerRevealCtrl.setAnswer(points,bool);
    }

}
