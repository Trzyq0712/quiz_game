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
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;
    private Stage quitStage;

    /*private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;*/

    private HomescreenCtrl homeCtrl;
    private Scene homeScene;

    private NamePromptCtrl nameCtrl;
    private Scene namePromptScene;

    private NamePromptCtrl mpnameCtrl;
    private Scene mpnamePromptScene;

    private SinglePlayerLeaderboardCtrl splCtrl;
    private Scene splScene;

    private ExitScreenCtrl exitCtrl;
    private Scene exitScene;

    private SinglePlayerCtrl singleCtrl;
    private Scene singleScene;

    private WaitingRoomCtrl waitingCtrl;
    private Scene waitingScene;

    public void initialize(Stage primaryStage,
                           Pair<HomescreenCtrl, Parent> home,
                           Pair<SPNamePromptCtrl, Parent> name,
                           Pair<MPNamePromptCtrl, Parent> mpname,
                           Pair<SinglePlayerLeaderboardCtrl, Parent> sp,
                           Pair<SinglePlayerCtrl, Parent> single,
                           Pair<ExitScreenCtrl, Parent> exit,
                           Pair<WaitingRoomCtrl, Parent> waiting) {
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

        this.waitingCtrl = waiting.getKey();
        this.waitingScene = new Scene(waiting.getValue());

        //showOverview();
        showHome();
        primaryStage.show();
    }

    public void showHome() {
        primaryStage.setTitle("The Energy Quiz");
        homeScene.getStylesheets().add("style.css"); //APPLY CSS SHEET
        primaryStage.setScene(homeScene);
    }

    public void showNamePrompt() {
        namePromptScene.getStylesheets().add("style.css"); //APPLY CSS SHEET
        primaryStage.setScene(namePromptScene);
    }

    public void showMPNamePrompt() {
        mpnamePromptScene.getStylesheets().add("style.css"); //APPLY CSS SHEET
        primaryStage.setScene(mpnamePromptScene);
    }

    public void showSPLeaderboard() {
        splScene.getStylesheets().add("style.css"); //APPLY CSS SHEET
        primaryStage.setScene(splScene);
    }

    public void showExitScreen() {
        exitScene.getStylesheets().add("style.css"); //APPLY CSS SHEET
        quitStage = new Stage();
        quitStage.setScene(exitScene);
        quitStage.setTitle("Sure you want to quit?");
        quitStage.centerOnScreen();
        quitStage.sizeToScene();
        /*quitStage.setMinHeight(quitStage.getMinHeight());
        quitStage.setMinWidth(quitStage.getMinWidth());*/
        quitStage.show();
        //primaryStage.setScene(exitScene);
    }

    public void startGame() {
        singleScene.getStylesheets().add("style.css"); //APPLY CSS SHEET
        if(true) {
            primaryStage.setScene(singleScene);
            //show singleplayer
        } else{
            primaryStage.setScene(singleScene);
            //show multiplayer not yet implemented
        }
    }

    public void enterWaitingRoom() {
        primaryStage.setTitle("The Waiting Room");
        waitingScene.getStylesheets().add("style.css"); //APPLY CSS SHEET
        primaryStage.setScene(waitingScene);
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