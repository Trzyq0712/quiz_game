package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;


public class EditScreenCtrl
        extends BaseCtrl {

    private final ServerUtils server;

    @FXML
    ImageView music;
    @FXML
    GridPane activityGrid;
    @FXML
    Button previousButton;
    @FXML
    Button nextButton;
    @FXML
    Button showButton;
    @FXML
    Spinner<Integer> pageSpinner;
    @FXML
    Label pageLabel;

    List<Activity> activityList;
    int start, end, activitiesPerPage = 5, pageCount;
    SpinnerValueFactory.IntegerSpinnerValueFactory spinnerValues;

    @Inject
    public EditScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    @FXML
    private void addActivity() {
        utils.playButtonSound();
        mainCtrl.editActivity(true);
    }
}
