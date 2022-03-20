package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class SinglePlayerLeaderboardCtrl extends ReusedButtonCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ApplicationUtils utils;

    @FXML
    ImageView imageView;

    @FXML
    ImageView music;



    @Inject
    public SinglePlayerLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    public void toggleSound(){
        utils.toggleSound();
    }

    public void playAgain() {
        mainCtrl.restore();
        mainCtrl.showQuestion();
    }

}
