package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.fxml.FXML;
import static client.Config.timePerQuestion;

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

    @FXML
    ProgressBar pgBar;

    Long startTime;


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
        /*pgBar.setStyle("-fx-accent: green");
        pgBar.setProgress(.5);*/

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


    public void activateProgressBar() {
        if (startTime == null) startTime = System.currentTimeMillis();
        double delta = getDelta();
        double progress = (timePerQuestion - delta) / timePerQuestion;
        if (progress >= 0 && progress <= 1) pgBar.setProgress(progress);
        if (progress > 0.7) pgBar.setStyle("-fx-accent: green");
        else if (progress > 0.4) pgBar.setStyle("-fx-accent: orange");
        else pgBar.setStyle("-fx-accent: red");
        if (delta < timePerQuestion) {
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            // your code here
                            activateProgressBar();
                        }
                    },
                    5
            );
        }
        else startTime = null;
    }

    public long getDelta() {
        return System.currentTimeMillis() - startTime;
    }
}
