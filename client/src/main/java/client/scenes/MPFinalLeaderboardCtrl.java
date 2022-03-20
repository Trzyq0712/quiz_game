package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MPFinalLeaderboardCtrl extends ReusedButtonCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ApplicationUtils utils;



    @FXML
    ImageView music;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    @Inject
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    public void playAgain() {
        mainCtrl.enterWaitingRoom();
    }

    public void toggleSound(){
        utils.toggleSound();
    }

    public void emote(Event e){
        mainCtrl.emote(e);
    }

}
