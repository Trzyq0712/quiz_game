package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;

import java.util.Arrays;
import java.util.List;

import static com.google.inject.Guice.createInjector;

public class SinglePlayerCtrl extends ReusedButtonCtrl {
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

    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;



    @Inject
    public SinglePlayerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void showHome() {
        mainCtrl.showHome();
        restore();
    }

    public void restore() {
        firstButton.setVisible(true);
        secondButton.setVisible(true);
        thirdButton.setVisible(true);
        hintJoker.setVisible(true);
        timeJoker.setVisible(true);
        pointsJoker.setVisible(true);
    }

    public void answerClick(Event event) {
        mainCtrl.buttonSound();
        List<Button> listOfButtons = Arrays.asList(firstButton, secondButton, thirdButton);
        Button activated = (Button) event.getSource();
        for (Button b : listOfButtons) {
            if (b.getId() != activated.getId()) {
                b.setVisible(false);
            }
        }
    }

    public void toggleSound() {
    }

    public void hintClick() {
        mainCtrl.buttonSound();
        hintJoker.setVisible(false);
    }

    public void pointsClick() {
        mainCtrl.buttonSound();
        pointsJoker.setVisible(false);
    }

    public void timeClick() {
        mainCtrl.buttonSound();
        timeJoker.setVisible(false);

    }
}
