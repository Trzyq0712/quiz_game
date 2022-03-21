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
import client.utils.ApplicationUtils;
import com.google.inject.Injector;

import client.scenes.MainCtrl;
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

        var home =
                FXML.load(HomeScreenCtrl.class, "client", "scenes", "Homescreen.fxml");
        var sp =
                FXML.load(SinglePlayerLeaderboardCtrl.class, "client", "scenes", "SinglePlayerLeaderboard.fxml");
        var exit =
                FXML.load(ExitScreenCtrl.class, "client", "scenes", "ExitScreen.fxml");
        var waiting =
                FXML.load(WaitingRoomCtrl.class, "client", "scenes", "WaitingRoom.fxml");
        var edit = 
                FXML.load(EditScreenCtrl.class, "client", "scenes", "EditScreen.fxml");
        var intermediate =
                FXML.load(IntermediateLeaderboardCtrl.class, "client", "scenes", "intermediateLeaderboard.fxml");
        var answerReveal =
                FXML.load(AnswerRevealCtrl.class, "client", "scenes", "AnswerReveal.fxml");
        var MPFinal =
                FXML.load(MPFinalLeaderboardCtrl.class, "client", "scenes", "MPFinalLeaderboard.fxml");
        var info =
                FXML.load(InfoCtrl.class, "client", "scenes", "Info.fxml");
        var prompt =
                FXML.load(PromptCtrl.class, "client", "scenes", "Prompt.fxml");
        var question =
                FXML.load(QuestionCtrl.class, "client", "scenes", "Question.fxml");


        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        INJECTOR.getInstance(ApplicationUtils.class);

        mainCtrl.initialize(primaryStage,
                home,
                sp,
                exit,
                waiting,
                edit,
                intermediate,
                answerReveal,
                MPFinal,
                info,
                prompt,
                question);
    }
}