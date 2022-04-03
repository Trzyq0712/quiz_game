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


import client.utils.GameUtils;
import client.utils.ServerUtils;
import commons.Config;
import commons.Activity;
import commons.NotificationMessage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


public class MainCtrl {

    /**
     * Amount of messages currently displaying in the chat.
     */
    int amountOfMessages = 0;
    List<VBox> listOfChatBoxes;
    List<StackPane> listOfHolders;
    private Stage primaryStage;
    private Stage secondaryStage;
    private HomeScreenCtrl homeScreenCtrl;
    private Scene homeScreenScene;
    private SinglePlayerLeaderboardCtrl singlePlayerLeaderboardCtrl;
    private Scene singlePlayerLeaderboardScene;
    private ExitScreenCtrl exitScreenCtrl;
    private Scene exitScreenScene;
    private WaitingRoomCtrl waitingRoomCtrl;
    private Scene waitingRoomScene;
    private IntermediateLeaderboardCtrl intermediateLeaderboardCtrl;
    private Scene intermediateLeaderboardScene;
    private AnswerRevealCtrl answerRevealCtrl;
    private Scene answerRevealScene;
    private MPFinalLeaderboardCtrl MPFinalLeaderboardCtrl;
    private Scene MPFinalLeaderboardScene;
    private InfoScreenCtrl infoScreenCtrl;
    private Scene infoScreenScene;
    private EditScreenCtrl editScreenCtrl;
    private Scene editScreenScene;
    private NamePromptCtrl namePromptCtrl;
    private Scene namePromptScene;
    private ComparisonQuestionCtrl comparisonQuestionCtrl;
    private Scene questionScreenScene;
    private EstimateQuestionCtrl estimateQuestionCtrl;
    private Scene estimateQuestionScene;
    private SimilarQuestionCtrl similarQuestionCtrl;
    private Scene similarQuestionScene;
    private MCQuestionCtrl MCQuestionCtrl;
    private Scene MCQuestionScene;
    private EditActivityCtrl editActivityCtrl;
    private Scene editActivityScene;

    Long startTime;

    boolean active = true; /* if true progressbar will load the next scene on depletion, if false, it means the user has
    clicked the homebutton. So he exited the game
    at which point the next scene shouldnt be loaded anymore */
    /**
     * If true, game knows the player is in singleplayer, if false, the game knows
     * that the player is in multiplayer.
     */
    boolean singlePlayerModeActive;
    ServerUtils server;
    GameUtils gameUtils;


    public void initialize(Stage primaryStage,
                           Pair<HomeScreenCtrl, Parent> homeScreen,
                           Pair<SinglePlayerLeaderboardCtrl, Parent> singlePlayerLeaderboard,
                           Pair<ExitScreenCtrl, Parent> exitScreen,
                           Pair<WaitingRoomCtrl, Parent> waitingRoom,
                           Pair<EditScreenCtrl, Parent> editScreen,
                           Pair<IntermediateLeaderboardCtrl, Parent> intermediateLeaderboard,
                           Pair<AnswerRevealCtrl, Parent> answerReveal,
                           Pair<MPFinalLeaderboardCtrl, Parent> MPFinalLeaderboard,
                           Pair<InfoScreenCtrl, Parent> infoScreen,
                           Pair<NamePromptCtrl, Parent> namePrompt,
                           Pair<ComparisonQuestionCtrl, Parent> comparisonQuestion,
                           Pair<EditActivityCtrl, Parent> editActivity,
                           Pair<EstimateQuestionCtrl, Parent> estimateQuestion,
                           Pair<MCQuestionCtrl, Parent> MCQuestion,
                           Pair<SimilarQuestionCtrl, Parent> similarQuestion,
                           ServerUtils s,
                           GameUtils g) {
        this.server = s;
        this.gameUtils = g;

        this.primaryStage = primaryStage;

        this.homeScreenCtrl = homeScreen.getKey();
        this.homeScreenScene = new Scene(homeScreen.getValue());

        this.singlePlayerLeaderboardCtrl = singlePlayerLeaderboard.getKey();
        this.singlePlayerLeaderboardScene = new Scene(singlePlayerLeaderboard.getValue());

        this.exitScreenCtrl = exitScreen.getKey();
        this.exitScreenScene = new Scene(exitScreen.getValue());

        this.waitingRoomCtrl = waitingRoom.getKey();
        this.waitingRoomScene = new Scene(waitingRoom.getValue());

        this.intermediateLeaderboardCtrl = intermediateLeaderboard.getKey();
        this.intermediateLeaderboardScene = new Scene(intermediateLeaderboard.getValue());

        this.answerRevealCtrl = answerReveal.getKey();
        this.answerRevealScene = new Scene(answerReveal.getValue());

        this.MPFinalLeaderboardCtrl = MPFinalLeaderboard.getKey();
        this.MPFinalLeaderboardScene = new Scene(MPFinalLeaderboard.getValue());

        this.infoScreenCtrl = infoScreen.getKey();
        this.infoScreenScene = new Scene(infoScreen.getValue());

        this.editScreenCtrl = editScreen.getKey();
        this.editScreenScene = new Scene(editScreen.getValue());

        this.namePromptCtrl = namePrompt.getKey();
        this.namePromptScene = new Scene(namePrompt.getValue());
        this.namePromptScene.getStylesheets().add(Config.styleSheet);

        this.comparisonQuestionCtrl = comparisonQuestion.getKey();
        this.questionScreenScene = new Scene(comparisonQuestion.getValue());

        this.estimateQuestionCtrl = estimateQuestion.getKey();
        this.estimateQuestionScene = new Scene(estimateQuestion.getValue());

        this.MCQuestionCtrl = MCQuestion.getKey();
        this.MCQuestionScene = new Scene(MCQuestion.getValue());

        this.similarQuestionCtrl = similarQuestion.getKey();
        this.similarQuestionScene = new Scene(similarQuestion.getValue());

        this.editActivityCtrl = editActivity.getKey();
        this.editActivityScene = new Scene(editActivity.getValue());

        secondaryStage = new Stage();

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            beforeExit();
            Platform.exit();
        });
        showHome();
        initializeChatBoxes();
        initializeHolders();
        primaryStage.show();
    }

    /**
     * Does these things before exiting:
     * If it called from the waiting room, it warns the server that it is leaving and to be removed from
     * the waiting room
     * If it is called from the game, it sends the notification that this player is leaving
     * Finally disconnects from the server
     */
    public void beforeExit() {
        if (server.isConnected()) {
            if (gameUtils.getPlayer() != null) {
                if(client.utils.Config.isWaiting){
                    waitingRoomCtrl.threadRun = false;
                    server.leaveWaitingroom(gameUtils.getPlayer());
                } else server.send("/app/leave/" + gameUtils.getGameID(),
                            new NotificationMessage(gameUtils.getPlayer().getPlayerName() + " left"));
            }
            server.disconnect();
        }
        if(client.utils.Config.playerName != null) saveNameToFile();
    }

    /**
     * Saves the player name to file
     */
    private void saveNameToFile() {
        try {
            PrintWriter pw = new PrintWriter(client.utils.Config.nameFile);
            pw.print(client.utils.Config.playerName);
            pw.flush();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    /**
     * Initializes an array of all the chatboxes in the application, this way they can be easily accessed and all kept
     * in sync.
     */
    public void initializeChatBoxes() {
        listOfChatBoxes = Arrays.asList(comparisonQuestionCtrl.chatbox, intermediateLeaderboardCtrl.chatbox,
                answerRevealCtrl.chatbox, MPFinalLeaderboardCtrl.chatbox,
                estimateQuestionCtrl.chatbox, MCQuestionCtrl.chatbox, similarQuestionCtrl.chatbox);
    }

    /**
     * Initializes an array of all the stackpanes which are holding the chatbox and emoji's. this way they can be
     * easily accessed and their visibility property can be easily toggled when the player is playing singleplayer or
     * multiplayer.
     */
    public void initializeHolders() {
        listOfHolders = Arrays.asList(comparisonQuestionCtrl.chatAndEmoteHolder, answerRevealCtrl.chatAndEmoteHolder,
                intermediateLeaderboardCtrl.chatAndEmoteHolder, MPFinalLeaderboardCtrl.chatAndEmoteHolder,
                estimateQuestionCtrl.chatAndEmoteHolder, MCQuestionCtrl.chatAndEmoteHolder,
                similarQuestionCtrl.chatAndEmoteHolder);
    }

    /**
     * This method modifies the question screen so that it's suited for singleplayer, in this case, it's hiding the
     * time joker since that isn't applicable to singleplayer.
     */
    public void activateSingleplayer() {
        comparisonQuestionCtrl.timeJoker.setVisible(false);
        estimateQuestionCtrl.timeJoker.setVisible(false);
        MCQuestionCtrl.timeJoker.setVisible(false);
        similarQuestionCtrl.timeJoker.setVisible(false);
        for (StackPane s : listOfHolders) {
            s.setVisible(false);
        }
    }

    /**
     * This method modifies the question screen so that it's suited for multiplayer, in this case, it's showing the
     * time joker since that is applicable to multiplayer.
     */
    public void activateMultiplayer() {
        comparisonQuestionCtrl.timeJoker.setVisible(true);
        estimateQuestionCtrl.timeJoker.setVisible(true);
        MCQuestionCtrl.timeJoker.setVisible(true);
        similarQuestionCtrl.timeJoker.setVisible(true);
        for (StackPane s : listOfHolders) {
            s.setVisible(true);
        }
    }


    /**
     * Hides or shows the points joker in all the question types
     *
     * @param bool - true if we want to make them visible, false otherwise
     */
    public void visibilityPointsJoker(Boolean bool) {
        estimateQuestionCtrl.pointsJoker.setVisible(bool);
        comparisonQuestionCtrl.pointsJoker.setVisible(bool);
        MCQuestionCtrl.pointsJoker.setVisible(bool);
        similarQuestionCtrl.pointsJoker.setVisible(bool);
    }

    /**
     * Hides or shows the points joker in all the question types
     *
     * @param bool - true if we want to make them visible, false otherwise
     */
    public void visibilityHintJoker(Boolean bool) {
        estimateQuestionCtrl.hintJoker.setVisible(bool);
        comparisonQuestionCtrl.hintJoker.setVisible(bool);
        MCQuestionCtrl.hintJoker.setVisible(bool);
        similarQuestionCtrl.hintJoker.setVisible(bool);
    }

    /**
     * Hides or shows the points joker in all the question types
     *
     * @param bool - true if we want to make them visible, false otherwise
     */
    public void visibilityTimeJoker(Boolean bool) {
        estimateQuestionCtrl.timeJoker.setVisible(bool);
        comparisonQuestionCtrl.timeJoker.setVisible(bool);
        MCQuestionCtrl.timeJoker.setVisible(bool);
        similarQuestionCtrl.timeJoker.setVisible(bool);
    }

    /**
     * Shows the home screen.
     */
    public void showHome() {
        primaryStage.setTitle(Config.title);
        homeScreenScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(homeScreenScene);
        restore();
    }

    public void showNamePromtScene() {
        primaryStage.setScene(namePromptScene);
    }

    /**
     * Shows the singleplayer leaderboard.
     */
    public void showSPLeaderboard() {
        singlePlayerLeaderboardScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(singlePlayerLeaderboardScene);
        singlePlayerLeaderboardCtrl.showPLayAgain();
        singlePlayerLeaderboardCtrl.refresh();
        singlePlayerLeaderboardCtrl.indicatePlayerRanking();
    }

    /**
     * Shows the singleplayer leaderboard, without the play again button.
     */
    public void showSPLeaderboardFromHome() {
        singlePlayerLeaderboardScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(singlePlayerLeaderboardScene);
        singlePlayerLeaderboardCtrl.hidePlayAgain();
        singlePlayerLeaderboardCtrl.refresh();
        singlePlayerLeaderboardCtrl.hideRankingInfo();
    }

    /**
     * Shows the exitscreen when the user wants to quit the application.
     */
    public void showExitScreen() {
        exitScreenScene.getStylesheets().add(Config.styleSheet);
        secondaryStage.setScene(exitScreenScene);
        secondaryStage.setTitle(Config.quit);
        secondaryStage.centerOnScreen();
        secondaryStage.sizeToScene();
        secondaryStage.show();
    }

    /**
     * Shows the question screen, sets
     * active = true
     * so that the application is aware that a game is active.
     * Function triggers the progressbar to start decreasing.
     */
    public void showQuestion() {
        active = true;
        int value = server.getQuestionType(gameUtils.getCurrentQuestion(), gameUtils.getGameID());
        switch (value) {
            case 0: {
                questionScreenScene.getStylesheets().add(Config.styleSheet);
                comparisonQuestionCtrl.generateActivity();
                comparisonQuestionCtrl.updateTracker();
                gameUtils.startTimer();
                primaryStage.setScene(questionScreenScene);
                comparisonQuestionCtrl.activateProgressBar();
                break;
            }
            case 1: {
                estimateQuestionScene.getStylesheets().add(Config.styleSheet);
                estimateQuestionCtrl.generateActivity();
                estimateQuestionCtrl.updateTracker();
                gameUtils.startTimer();
                primaryStage.setScene(estimateQuestionScene);
                estimateQuestionCtrl.activateProgressBar();
                break;
            }
            case 2: {
                MCQuestionScene.getStylesheets().add(Config.styleSheet);
                MCQuestionCtrl.generateActivity();
                MCQuestionCtrl.updateTracker();
                gameUtils.startTimer();
                primaryStage.setScene(MCQuestionScene);
                MCQuestionCtrl.activateProgressBar();
                break;
            }
            case 3:{
                similarQuestionScene.getStylesheets().add(Config.styleSheet);
                similarQuestionCtrl.generateActivity();
                similarQuestionCtrl.updateTracker();
                gameUtils.startTimer();
                primaryStage.setScene(similarQuestionScene);
                similarQuestionCtrl.activateProgressBar();
                break;
            }
        }

    }


    public void showWaitingRoom() {
        primaryStage.setTitle(Config.titleWaitingRoom);
        waitingRoomCtrl.setUp();
        waitingRoomScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(waitingRoomScene);
    }


    /**
     * Resets the question to 0 and makes jokers and answers visible again.
     * Should be called after a game is done.
     */
    public void restore() {
        comparisonQuestionCtrl.restoreJokers();
        estimateQuestionCtrl.restoreJokers();
        MCQuestionCtrl.restoreJokers();
        similarQuestionCtrl.restoreJokers();
        similarQuestionCtrl.restoreAnswers();
        comparisonQuestionCtrl.restoreAnswers();
        estimateQuestionCtrl.restoreSubmit();
        MCQuestionCtrl.restoreAnswers();
    }

    public void restoreQuestions() {
        comparisonQuestionCtrl.restoreAnswers();
        estimateQuestionCtrl.restoreSubmit();
        MCQuestionCtrl.restoreAnswers();
        similarQuestionCtrl.restoreAnswers();
    }

    /**
     * Updates all the chatboxes to display the emoji that has been clicked.
     *
     * @param path - The path of the clicked emoji image
     * @param name - The name of the player
     */

    public void emote(String path, String name) {
        for (VBox c : listOfChatBoxes) {
            Platform.runLater(() -> {
                HBox hbox = new HBox();
                Image arg = new Image(ServerUtils.SERVER + "emotes" + path);
                Label user = new Label(name + ":  ");
                ImageView emote = new ImageView(arg);
                emote.setFitHeight(50);
                emote.setFitWidth(50);
                hbox.getChildren().addAll(user, emote);
                hbox.setAlignment(Pos.CENTER_LEFT);
                if (c.getChildren().size() >= Config.maxChatMessages) {
                    c.getChildren().remove(0);
                }
                c.getChildren().add(hbox);
                c.setSpacing(10);
            });
        }
    }

    /**
     * Shows the screen where answers are revealed.
     */
    public void showAnswerReveal() {
        answerRevealCtrl.updateTracker();
        answerRevealScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(answerRevealScene);
        answerRevealCtrl.activateProgressBar();
    }

    public void showMPFinalLeaderboard() {
        MPFinalLeaderboardScene.getStylesheets().add(Config.styleSheet);
        MPFinalLeaderboardCtrl.refresh();
        primaryStage.setScene(MPFinalLeaderboardScene);
    }

    public void showIntermediateLeaderboard() {
        intermediateLeaderboardCtrl.updateQuestionTracker();
        intermediateLeaderboardScene.getStylesheets().add(Config.styleSheet);
        intermediateLeaderboardCtrl.refresh();
        primaryStage.setScene(intermediateLeaderboardScene);
        intermediateLeaderboardCtrl.activateProgressBar();
    }


    public void showInfo() {
        infoScreenCtrl.setHintExplainer();
        infoScreenCtrl.setTimeExplainer();
        infoScreenCtrl.setDoublePointsExplainerExplainer();
        infoScreenScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(infoScreenScene);
    }

    public void showEditScreen() {
        primaryStage.setTitle(Config.edit);
        editScreenCtrl.setUp();
        editScreenScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(editScreenScene);
    }

    public void editActivity(boolean add, Activity activity) {
        editActivityCtrl.setUp(add, activity, editScreenCtrl.getActUtils());
        editActivityScene.getStylesheets().add(Config.styleSheet);
        secondaryStage.setScene(editActivityScene);
        secondaryStage.centerOnScreen();
        secondaryStage.sizeToScene();
        secondaryStage.show();
    }

    public Stage getSecondaryStage(){
        return secondaryStage;
    }

    /**
     * Used to prepare the answer reveal screen for a multiple choice question with 3 activities as answers
     *
     * @param activities     - a list of 3 activities
     * @param answerButtonId - the id of the correct answer button
     */
    public void setAnswersForAnswerReveal(List<Activity> activities, int answerButtonId) {
        answerRevealCtrl.setAnswers(activities, answerButtonId);
    }

    /**
     * Used to prepare the answer reveal screen for an estimate question
     *
     * @param activity - the generated activity
     */
    public void setAnswersForAnswerReveal(Activity activity) {
        answerRevealCtrl.setAnswer(activity);
    }

    /**
     * Used to send the answer reveal screen the obtained points
     *
     * @param points - the points that the player obtained
     * @param bool   - indicates which label to set visible
     *               -> true for estimate related label
     *               -> false for MC with 3 activities related label
     */
    public void setAnswersForAnswerReveal(int points, boolean bool) {
        answerRevealCtrl.setAnswer(points, bool);
    }

    public void refreshLabels() {
        editScreenCtrl.updateLabels();
    }

}
