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

import static client.Config.*;



public class MainCtrl  {

    private Stage primaryStage;
    private Stage secondaryStage;

    private Player player;

    /*private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;*/

    private HomescreenCtrl homeCtrl;
    private Scene homeScene;

    private SinglePlayerLeaderboardCtrl splCtrl;
    private Scene splScene;

    private ExitScreenCtrl exitCtrl;
    private Scene exitScene;

    /*private AudioClip clip = new AudioClip(f.toURI().toString());*/
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

    private EditActivityCtrl editActivityCtrl;
    private Scene editActivityScene;

    Long startTime;
    int currentQuestion = 0;
    List<ImageView> listOfMusicIcons;
    Image musicOn = new Image(getClass().getClassLoader().getResourceAsStream("images/music.png"));
    Image musicOff = new Image(getClass().getClassLoader().getResourceAsStream("images/musicOff.png"));
    boolean active = true; /*if true progressbar will load the next scene on depletion, if false, it means the user has
    clicked the homebutton. So he exited the game
    at which point the next scene shouldnt be loaded anymore*/
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
                           Pair<HomescreenCtrl, Parent> home,
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
                           Pair<EditActivityCtrl, Parent> editActivity) {
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

        //homeScene.setOnMouseClicked(e -> clip.play());

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
        initializeMusicIcons();
        initializeChatBoxes();
        initializeHolders();
        primaryStage.show();
    }

    /**
     * Toggles the backgroundmusic of the application when called.
     */
    public void toggleSound() {
        if (homeCtrl.mvv.getMediaPlayer().isMute()) {
            homeCtrl.mvv.getMediaPlayer().setMute(false);
            for (ImageView i : listOfMusicIcons) {
                i.setImage(musicOn);
            }
        } else {
            homeCtrl.mvv.getMediaPlayer().setMute(true);
            for (ImageView i : listOfMusicIcons) {
                i.setImage(musicOff);
            }
        }
        buttonSound();
    }

    /**
     * Initializes an array of the musicicons of the whole application, this is needed so we can access all of them
     * to keep them in sync if we want to for example change the music icon between on/off.
     */

    public void initializeMusicIcons() {
        listOfMusicIcons = Arrays.asList(homeCtrl.music, infoCtrl.music, answerRevealCtrl.music, editCtrl.music,
                intermediateCtrl.music, MPFinal.music, promptCtrl.music, questionCtrl.music, splCtrl.music,
                waitingCtrl.music); //if new scenes are added, make sure to add their music icons here!
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
                intermediateCtrl.chatAndEmoteHolder, MPFinal.chatAndEmoteHolder);
    }

    /**
     * This method modifies the question screen so that it's suited for singleplayer, in this case, it's hiding the
     * time joker since that isn't applicable to singleplayer.
     */

    public void activateSingleplayer() {
        questionCtrl.timeJoker.setVisible(false);
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
        for (StackPane s : listOfHolders) {
            s.setVisible(true);
        }
    }

    /**
     * Produces the sound of a button when invoked, this function should be called when a button is clicked.
     */

    public void buttonSound() {
        Media media = new Media(buttonClickSound.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }

    /**
     * Shows the home screen.
     */

    public void showHome() {
        player = null;
        primaryStage.setTitle(title);
        homeScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
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
        promptScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        promptCtrl.setUp();
        primaryStage.setScene(promptScene);
        buttonSound();
    }

    /**
     * Shows the singleplayer leaderboard.
     */

    public void showSPLeaderboard() {
        splScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(splScene);
        buttonSound();
    }

    /**
     * Shows the exitscreen when the user wants to quit the application.
     */

    public void showExitScreen() {
        exitScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        secondaryStage.setScene(exitScene);
        secondaryStage.setTitle(quit);
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
        questionScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        if(singlePlayerModeActive) {
            activateSingleplayer();
            questionCtrl.updateQuestionTracker();
            primaryStage.setScene(questionScene);
            //show singleplayer
        } else {
            activateMultiplayer();
            questionCtrl.updateQuestionTracker();
            primaryStage.setScene(questionScene);
            //show multiplayer, partly implemented
        }
        new Thread(() -> questionCtrl.activateProgressBar()).start();
    }

    /**
     * Shows the waiting room and adds the player to the waiting room so other clients can be informed about this.
     * @param player The player which is added to the waiting room.
     */
    public void enterWaitingRoom(Player player) {
        this.player = player;
        primaryStage.setTitle(titleWaitingRoom);
        waitingScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        waitingCtrl.setUp(player);
        primaryStage.setScene(waitingScene);
        buttonSound();
    }

    /**
     * This is here for reentering a multi game to work without changing a lot of stuff.
     * When we add a multiplayer controller going to remake it.
     * Don't want to do it know not to mess up controllers for the future.
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
                if (call == 0) Platform.runLater(() -> showAnswerReveal());
                else if (call == 1 && currentQuestion < totalQuestions) {
                    questionCtrl.restoreAnswers();
                    if (singlePlayerModeActive) Platform.runLater(() -> showQuestion());
                    else Platform.runLater(() -> showIntermediateLeaderboard());
                } else if (call == 1 && currentQuestion >= totalQuestions) {
                    restore();
                    if (singlePlayerModeActive) Platform.runLater(() -> showSPLeaderboard());
                    else Platform.runLater(() -> showMPFinalLeaderboard());
                } else if (call == 2) Platform.runLater(() -> showQuestion());
            }
        }
    }

    /**
     * Resets the question to 0 and makes jokers and answers visible again.
     * Should be called after a game is done.
     */

    public void restore() {
        currentQuestion = 0;
        questionCtrl.restoreJokers();
        questionCtrl.restoreAnswers();
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
            if (amountOfMessages >= maxChatMessages) {
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
        answerRevealCtrl.updateQuestionTracker();
        answerRevealScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(answerRevealScene);
        new Thread(() -> answerRevealCtrl.activateProgressBar()).start();
    }

    public void showMPFinalLeaderboard() {
        MPFinalScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(MPFinalScene);
    }

    public void showIntermediateLeaderboard() {
        intermediateCtrl.updateQuestionTracker();
        intermediateScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(intermediateScene);
        new Thread(() -> intermediateCtrl.activateProgressBar()).start();
    }

    /**
     * Updates the passed in label to show the current question out of the total.
     * @param label Label which contains the current question in format "Question X/Y".
     * @param update Indicates if question counter should be incremented, otherwise, if false it will just update
     * the given label acoording to currentQuestion variable.
     */

    public void updateQuestionTracker(Label label, boolean update) {
        if (update) currentQuestion++;
        label.setText("Question " + currentQuestion + "/" + totalQuestions);
    }

    public void showInfo() {
        infoCtrl.setHintExplainer();
        infoCtrl.setTimeExplainer();
        infoCtrl.setDoublePointsExplainerExplainer();
        infoScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(infoScene);
    }

    public void showEditScreen() {
        primaryStage.setTitle(edit);
        editScene.getStylesheets().add(styleSheet);//APPLY CSS SHEET
        primaryStage.setScene(editScene);
    }

    public void editActivity(boolean add) {
        if (add) {
            editActivityCtrl.setUp();
            editActivityScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
            secondaryStage.setScene(editActivityScene);
            secondaryStage.centerOnScreen();
            secondaryStage.sizeToScene();
            secondaryStage.show();
        }
    }

    /*public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }*/

    /*public void showAdd() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }*/
}