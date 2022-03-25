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


import client.Config;
import commons.Activity;
import javafx.event.Event;
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

import java.util.Arrays;
import java.util.List;


public class MainCtrl {

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
    private MCQuestionCtrl MCQuestionCtrl;
    private Scene MCQuestionScene;
    private EditActivityCtrl editActivityCtrl;
    private Scene editActivityScene;

    // --------------------- to move START

    /**
     * Amount of messages currently displaying in the chat.
     */
    int amountOfMessages = 0;
    List<VBox> listOfChatBoxes;
    List<StackPane> listOfHolders;

    // -------------------- to move END

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
                           Pair<MCQuestionCtrl, Parent> MCQuestion) {

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

        this.editActivityCtrl = editActivity.getKey();
        this.editActivityScene = new Scene(editActivity.getValue());

        secondaryStage = new Stage();

        // TODO Consider refactoring
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
//            if (player != null)
//                waitingRoomCtrl.leaveWaitingRoom(player);
            primaryStage.close();
        });

        showHome();

        // ----- to move START
        initializeChatBoxes();
        initializeHolders();
        // ----- to move END

        primaryStage.show();
    }

    // --- to move START

    /**
     * Initializes an array of all the chatboxes in the application, this way they can be easily accessed and all kept
     * in sync.
     */
    public void initializeChatBoxes() {
        listOfChatBoxes = Arrays.asList(comparisonQuestionCtrl.chatbox, intermediateLeaderboardCtrl.chatbox,
                answerRevealCtrl.chatbox, MPFinalLeaderboardCtrl.chatbox);
    }

    /**
     * Initializes an array of all the stackpanes which are holding the chatbox and emoji's. this way they can be
     * easily accessed and their visibility property can be easily toggled when the player is playing singleplayer or
     * multiplayer.
     */
    public void initializeHolders() {
        listOfHolders = Arrays.asList(comparisonQuestionCtrl.chatAndEmoteHolder, answerRevealCtrl.chatAndEmoteHolder,
                intermediateLeaderboardCtrl.chatAndEmoteHolder, MPFinalLeaderboardCtrl.chatAndEmoteHolder,
                estimateQuestionCtrl.chatAndEmoteHolder, MCQuestionCtrl.chatAndEmoteHolder);
    }

    /**
     * This method modifies the question screen so that it's suited for singleplayer, in this case, it's hiding the
     * time joker since that isn't applicable to singleplayer.
     */
    public void activateSingleplayer() {
        comparisonQuestionCtrl.timeJoker.setVisible(false);
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
        comparisonQuestionCtrl.timeJoker.setVisible(true);
        estimateQuestionCtrl.timeJoker.setVisible(true);
        MCQuestionCtrl.timeJoker.setVisible(true);
        for (StackPane s : listOfHolders) {
            s.setVisible(true);
        }
    }


    // --- to move END

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


    // TODO consider refactoring START

    /**
     * Shows the singleplayer leaderboard.
     */
    public void showSPLeaderboard() {
        singlePlayerLeaderboardScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(singlePlayerLeaderboardScene);
        singlePlayerLeaderboardCtrl.showPLayAgain();
    }

    /**
     * Shows the singleplayer leaderboard, without the play again button.
     */
    public void showSPLeaderboardFromHome() {
        singlePlayerLeaderboardScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(singlePlayerLeaderboardScene);
        singlePlayerLeaderboardCtrl.hidePlayAgain();
    }
    // TODO consider refactoring END

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


    // --- to move START
    // TODO consider refactoring

    /**
     * Shows the question screen, sets
     * active = true
     * so that the application is aware that a game is active.
     * Function triggers the progressbar to start decreasing.
     */
    public void showQuestion() {
//        if (singlePlayerModeActive)
//            activateSingleplayer();
//        else
//            activateMultiplayer();
        int value = (int) (Math.random() * 3);
        switch (value % 3) {
            case 0: {
                questionScreenScene.getStylesheets().add(Config.styleSheet);
                comparisonQuestionCtrl.updateTracker();
                comparisonQuestionCtrl.generateActivity();
                primaryStage.setScene(questionScreenScene);
                new Thread(() -> comparisonQuestionCtrl.activateProgressBar()).start();
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
    // --- to move END

    public void showWaitingRoom() {
        primaryStage.setTitle(Config.titleWaitingRoom);
        waitingRoomCtrl.setUp();
        waitingRoomScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(waitingRoomScene);
    }


    // --- to move START
    // TODO consider refactoring



    public void refresh() {
        singlePlayerLeaderboardCtrl.refresh();
    }

    /**
     * Resets the question to 0 and makes jokers and answers visible again.
     * Should be called after a game is done.
     */
    public void restore() {
        comparisonQuestionCtrl.restoreJokers();
        estimateQuestionCtrl.restoreJokers();
        MCQuestionCtrl.restoreJokers();
        comparisonQuestionCtrl.restoreAnswers();
        estimateQuestionCtrl.restoreSubmit();
        MCQuestionCtrl.restoreAnswers();
    }

    public void restoreQuestions() {
        comparisonQuestionCtrl.restoreAnswers();
        estimateQuestionCtrl.restoreSubmit();
        MCQuestionCtrl.restoreAnswers();
    }

    /**
     * Updates all the chatboxes to display the emoji that has been clicked.
     *
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
    }


    // --- to move END


    /**
     * Shows the screen where answers are revealed.
     */
    public void showAnswerReveal() {
        answerRevealCtrl.updateTracker();
        answerRevealScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(answerRevealScene);
        new Thread(() -> answerRevealCtrl.activateProgressBar()).start();
    }

    public void showMPFinalLeaderboard() {
        MPFinalLeaderboardScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(MPFinalLeaderboardScene);
    }

    public void showIntermediateLeaderboard() {
        intermediateLeaderboardCtrl.updateQuestionTracker();
        intermediateLeaderboardScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(intermediateLeaderboardScene);
        new Thread(() -> intermediateLeaderboardCtrl.activateProgressBar()).start();
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
        editScreenScene.getStylesheets().add(Config.styleSheet);
        primaryStage.setScene(editScreenScene);
    }

    public void editActivity(boolean add) {
        if (add) {
            editActivityCtrl.setUp();
            editActivityScene.getStylesheets().add(Config.styleSheet);
            secondaryStage.setScene(editActivityScene);
            secondaryStage.centerOnScreen();
            secondaryStage.sizeToScene();
            secondaryStage.show();
        }
    }

    // --- to move START

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

    // --- to move END

}
