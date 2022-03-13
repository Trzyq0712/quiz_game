package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import static client.Config.*;

import static com.google.inject.Guice.createInjector;

public class IntermediateLeaderboardCtrl extends ReusedButtonCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    ProgressBar pgBarIntermediate;

    @FXML
    Label questionTracker;

    @FXML
    ImageView music;

    @FXML
    VBox chatbox;
    @FXML
    StackPane emotePane;
    @FXML
    Label chatboxTitle;
    @FXML
    Label emoteTitle;

    @Inject
    public IntermediateLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    public void activateProgressBar() {
        mainCtrl.activateGenericProgressBar(pgBarIntermediate, timeForIntermediate, 2);
    }

    public void updateQuestionTracker() {
        mainCtrl.updateQuestionTracker(questionTracker, false);
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

}
