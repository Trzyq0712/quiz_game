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


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import static client.Config.*;



public class MainCtrl  {

    private Stage primaryStage;
    private Stage quitStage;



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

    private answerRevealCtrl answerRevealCtrl;
    private Scene answerRevealScene;

    Long startTime;

    public void initialize(Stage primaryStage,
                           Pair<HomescreenCtrl, Parent> home,
                           Pair<SPNamePromptCtrl, Parent> name,
                           Pair<MPNamePromptCtrl, Parent> mpname,
                           Pair<SinglePlayerLeaderboardCtrl, Parent> sp,
                           Pair<SinglePlayerCtrl, Parent> single,
                           Pair<ExitScreenCtrl, Parent> exit,
                           Pair<WaitingRoomCtrl, Parent> waiting,
                           Pair<IntermediateLeaderboardCtrl, Parent> intermediate,
                           Pair<answerRevealCtrl, Parent> answerReveal) {
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

        //showOverview();
        showHome();
        primaryStage.show();
    }

    public void buttonSound() {
        //clip.play();
        Media media = new Media(buttonClickSound.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }


    public void showHome() {
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
            primaryStage.setScene(singleScene);
            //show singleplayer
        } else{
            primaryStage.setScene(singleScene);
            //show multiplayer not yet implemented
        }
        new Thread(() -> singleCtrl.activateProgressBar()).start();
        buttonSound();
    }

    public void enterWaitingRoom() {
        primaryStage.setTitle(titleWaitingRoom);
        waitingScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(waitingScene);
    }

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
        }
        else {
            startTime = null;
            if (call == 0) this.showAnswerReveal();
            else if (call == 1) this.showIntermediateLeaderboard();
            else if (call == 2) this.startGame();
        }
    }

    public long getDelta() {
        return System.currentTimeMillis() - startTime;
    }

    public void showAnswerReveal() {
        answerRevealScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(answerRevealScene);
        new Thread(() -> answerRevealCtrl.activateProgressBar()).start();
    }

    public void showIntermediateLeaderboard() {
        intermediateScene.getStylesheets().add(styleSheet); //APPLY CSS SHEET
        primaryStage.setScene(intermediateScene);
        new Thread(() -> intermediateCtrl.activateProgressBar()).start();
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