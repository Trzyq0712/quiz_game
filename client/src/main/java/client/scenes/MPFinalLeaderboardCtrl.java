package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

import static com.google.inject.Guice.createInjector;

public class MPFinalLeaderboardCtrl extends ReusedButtonCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);


    @FXML
    ImageView music;

    @Inject
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void playAgain() {
        mainCtrl.enterWaitingRoom();
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

}
