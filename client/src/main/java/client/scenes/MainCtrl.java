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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static client.Config.*;



public class MainCtrl  {

    private Stage primaryStage;
    private Stage quitStage;

    private Player player;

    /*private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;*/

    private HomescreenCtrl homeCtrl;
    private Scene homeScene;

    private SPNamePromptCtrl nameCtrl;
    private Scene namePromptScene;

    private MPNamePromptCtrl mpnameCtrl;
    private Scene mpnamePromptScene;

    private SinglePlayerLeaderboardCtrl splCtrl;
    private Scene splScene;

    private ExitScreenCtrl exitCtrl;
    private Scene exitScene;

    private SinglePlayerCtrl singleCtrl;
    private Scene singleScene;

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

    Long startTime;
    int currentQuestion = 0;
    List<ImageView> listOfMusicIcons;
    Image musicOn = new Image(getClass().getClassLoader().getResourceAsStream("images/music.png"));
    Image musicOff = new Image(getClass().getClassLoader().getResourceAsStream("images/musicOff.png"));

    private EditScreenCtrl editCtrl;
    private Scene editScene;

    public void initialize(Stage primaryStage,
                           Pair<HomescreenCtrl, Parent> home,
                           Pair<SPNamePromptCtrl, Parent> name,
                           Pair<MPNamePromptCtrl, Parent> mpname,
                           Pair<SinglePlayerLeaderboardCtrl, Parent> sp,
                           Pair<SinglePlayerCtrl, Parent> single,
                           Pair<ExitScreenCtrl, Parent> exit,
                           Pair<WaitingRoomCtrl, Parent> waiting,
                           Pair<EditScreenCtrl, Parent> edit,
                           Pair<IntermediateLeaderboardCtrl, Parent> intermediate,
                           Pair<AnswerRevealCtrl, Parent> answerReveal,
                           Pair<MPFinalLeaderboardCtrl, Parent> MPFinalLeaderboard,
                           Pair<InfoCtrl, Parent> info) {
        this.primaryStage = primaryStage;
        /*this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());*/


        this.homeCtrl = home.getKey();
        this.homeScene = new Scene(home.getValue());

        this.nameCtrl = name.getKey();
        this.namePromptScene = new Scene(name.getValue());

        this.mpnameCtrl = mpname.getKey();
        this.mpnamePromptScene = new Scene(mpname.getValue());

        this.splCtrl = sp.getKey();
        this.splScene = new Scene(sp.getValue());

        this.singleCtrl = single.getKey();
        this.singleScene = new Scene(single.getValue());

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

        //showOverview();
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            if(player != null)
                waitingCtrl.leaveWaitingroom(player);
            primaryStage.close();
        });
        showHome();
        initializeMusicIcons();
        primaryStage.show();
    }

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

    public void initializeMusicIcons() {
        listOfMusicIcons = Arrays.asList(homeCtrl.music, infoCtrl.music, answerRevealCtrl.music, editCtrl.music,
                intermediateCtrl.music, MPFinal.music, mpnameCtrl.music, singleCtrl.music, splCtrl.music,
                nameCtrl.music, waitingCtrl.music); //if new scenes are added, make sure to add their music icons here!
    }

    public void buttonSound() {
        Media media = new Media(buttonClickSound.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }


    public void showHome() {
        player = null;
        primaryStage.setTitle(title);
        homeScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(homeScene);
        buttonSound();
    }

    public void showNamePrompt() {
        namePromptScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        nameCtrl.setUp();
        primaryStage.setScene(namePromptScene);
        buttonSound();
    }

    public void showMPNamePrompt() {
        mpnamePromptScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        mpnameCtrl.setUp();
        primaryStage.setScene(mpnamePromptScene);
    }

    public void showSPLeaderboard() {
        splScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(splScene);
        buttonSound();
    }

    public void showExitScreen() {
        exitScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        quitStage = new Stage();
        quitStage.setScene(exitScene);
        quitStage.setTitle(quit);
        quitStage.centerOnScreen();
        quitStage.sizeToScene();
        /*quitStage.setMinHeight(quitStage.getMinHeight());
        quitStage.setMinWidth(quitStage.getMinWidth());*/
        quitStage.show();
        //primaryStage.setScene(exitScene);
        buttonSound();
    }

    public void startGame() {
        singleScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        if(true) {
            singleCtrl.updateQuestionTracker();
            primaryStage.setScene(singleScene);
            //show singleplayer
        } else{
            singleCtrl.updateQuestionTracker();
            primaryStage.setScene(singleScene);
            //show multiplayer not yet implemented
        }
        new Thread(() -> singleCtrl.activateProgressBar()).start();
        buttonSound();
    }

    public void enterWaitingRoom(Player player) {
        this.player = player;
        primaryStage.setTitle(titleWaitingRoom);
        waitingScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        waitingCtrl.setUp(player);
        primaryStage.setScene(waitingScene);
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
     *
     * @param pgBar the progressbar being modified
     * @param totalTime the total time the progress bar should last
     * @param call indicates what function should be called next
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
            if (call == 0) Platform.runLater(() -> showAnswerReveal());
            else if (call == 1 && currentQuestion < totalQuestions) {
                singleCtrl.restoreAnswers();
                Platform.runLater(() -> showIntermediateLeaderboard());
            } else if (call == 1 && currentQuestion >= totalQuestions) {
                Platform.runLater(() -> showMPFinalLeaderboard());
                currentQuestion = 0;
                singleCtrl.restoreJokers();
                singleCtrl.restoreAnswers();
            } else if (call == 2 ) Platform.runLater(() -> startGame());
        }
    }

    public long getDelta() {
        return System.currentTimeMillis() - startTime;
    }

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