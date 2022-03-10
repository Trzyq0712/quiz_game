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
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import com.google.inject.Injector;

//import client.scenes.AddQuoteCtrl;
import client.scenes.MainCtrl;
//import client.scenes.QuoteOverviewCtrl;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        //var overview = FXML.load(QuoteOverviewCtrl.class, "client", "scenes", "QuoteOverview.fxml");
        //var add = FXML.load(AddQuoteCtrl.class, "client", "scenes", "AddQuote.fxml");


        var home =
                FXML.load(HomescreenCtrl.class, "client", "scenes", "Homescreen.fxml");
        var name =
                FXML.load(SPNamePromptCtrl.class, "client", "scenes", "NamePrompt.fxml");
        var mpname =
                FXML.load(MPNamePromptCtrl.class, "client", "scenes", "MPNamePromptCtrl.fxml");
        var sp =
                FXML.load(SinglePlayerLeaderboardCtrl.class, "client", "scenes", "SinglePlayerLeaderboard.fxml");
        var exit =
                FXML.load(ExitScreenCtrl.class, "client", "scenes", "ExitScreen.fxml");
        var single =
                FXML.load(SinglePlayerCtrl.class, "client", "scenes", "Singleplayer.fxml");
        var waiting =
                FXML.load(WaitingRoomCtrl.class, "client", "scenes", "WaitingRoom.fxml");
        var edit = 
                FXML.load(EditScreenCtrl.class, "client", "scenes", "EditScreen.fxml");
        var intermediate =
                FXML.load(IntermediateLeaderboardCtrl.class, "client", "scenes", "intermediateLeaderboard.fxml");
        var answerReveal =
                FXML.load(answerRevealCtrl.class, "client", "scenes", "answerReveal.fxml");
        var MPFinal =
                FXML.load(MPFinalLeaderboardCtrl.class, "client", "scenes", "MPFinalLeaderboard.fxml");
        var info =
                FXML.load(infoCtrl.class, "client", "scenes", "info.fxml");


        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        mainCtrl.initialize(primaryStage,
                home,
                name,
                mpname,
                sp,
                single,
                exit,
                waiting,
                edit,
                intermediate,
                answerReveal,
                MPFinal,
                info);
    }
}