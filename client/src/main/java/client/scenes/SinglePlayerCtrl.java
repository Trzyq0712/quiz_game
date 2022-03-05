package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @FXML
    Button firstButton;
    @FXML
    Button secondButton;
    @FXML
    Button thirdButton;



    @Inject
    public SinglePlayerCtrl(ServerUtils server, MainCtrl mainCtrl) {
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

    public void answerClick(ActionEvent event) {/*the fact that this function uses the ActionEvent parameter causes a lot of errors in the console,
    no clue why. functionality works as expected tho.... i tried 2 scenario's, A: have empty function with the ActionEvent parameter, B: have an empty function without the
    ActionEvent parameter. A gives errors, B doesn't*/
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
        hintJoker.setVisible(false);
    }

    public void pointsClick() {
        pointsJoker.setVisible(false);
    }

    public void timeClick() {
        timeJoker.setVisible(false);

    }
}
