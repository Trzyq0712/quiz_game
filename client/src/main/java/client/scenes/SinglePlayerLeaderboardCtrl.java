package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class SinglePlayerLeaderboardCtrl extends BaseCtrl {

    private final ServerUtils server;

    @FXML
    ImageView imageView;

    @Inject
    public SinglePlayerLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    public void playAgain() {
        mainCtrl.restore();
        mainCtrl.showQuestion();
    }

}
