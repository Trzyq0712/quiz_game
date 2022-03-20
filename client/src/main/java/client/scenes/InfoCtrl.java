package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static client.Config.*;

public class InfoCtrl extends BaseCtrl {

    private final ServerUtils server;

    @FXML
    Label hintExplainer;
    @FXML
    Label timeExplainer;
    @FXML
    Label doublePointsExplainer;


    @Inject
    public InfoCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
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

}
