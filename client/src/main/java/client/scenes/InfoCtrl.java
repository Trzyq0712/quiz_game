package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import static client.Config.*;

public class InfoCtrl extends ReusedButtonCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ApplicationUtils utils;

    @FXML
    Label hintExplainer;
    @FXML
    Label timeExplainer;
    @FXML
    Label doublePointsExplainer;
    @FXML
    ImageView music;


    @Inject
    public InfoCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    public void setHintExplainer() {
        hintExplainer.setText("Eliminates an incorrect\nanswer from multiple\nchoice questions");
    }

    public void setTimeExplainer() {
        timeExplainer.setText(String.format("Reduces opponents\ntime by %d%%", timeReductionPercentage));
    }

    public void setDoublePointsExplainerExplainer() {
        doublePointsExplainer.setText("Doubles your points\nfor the current question");
    }


    public void toggleSound(){
        utils.toggleSound();
    }
}
