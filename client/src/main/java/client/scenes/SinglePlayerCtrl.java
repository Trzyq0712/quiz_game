package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;

import static com.google.inject.Guice.createInjector;

public class SinglePlayerCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    ImageView hintJoker;
    @FXML
    ImageView pointsJoker;
    @FXML
    ImageView timeJoker;

    @Inject
    public SinglePlayerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void showHome() {
        mainCtrl.showHome();
    }

    public void answerClick() {

    }

    public void toggleSound() {

    }

    public void hintClick() {
        hintJoker.setVisible(false);
    }

    public void pointsClick() {
        pointsJoker.setVisible(false);
    }

    public void timeClick() {
        timeJoker.setVisible(false);

    }
}
