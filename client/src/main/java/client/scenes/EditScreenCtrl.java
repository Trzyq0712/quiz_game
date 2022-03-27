package client.scenes;

import client.utils.ActivityBoardUtils;
import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;


public class EditScreenCtrl extends BaseCtrl {

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

    ActivityBoardUtils actUtils;


    @Inject
    public EditScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils, server);
    }

    /**
     * Opens the secondary edit screen from mainCtrl
     */
    public void addActivity() {
        mainCtrl.editActivity(true, null);
    }

    /**
     * Fetches activities
     * Sets up the activity browser
     * Loads the activity browser on page 1
     */
    public void setUp() {
        actUtils = new ActivityBoardUtils(mainCtrl, 5, activityGrid, previousButton,
                nextButton, showButton, pageSpinner, pageLabel, server);
        pageSpinner.setEditable(true);
        pageSpinner.setValueFactory(actUtils.getSpinnerValues());
        actUtils.loadGrid();
    }

    /**
     * Goes to previous page of activities
     * Platform run later is used to prevent spam clicking
     */
    public void loadPrevious(){
        Platform.runLater(() -> {
            nextButton.setDisable(true);
            previousButton.setDisable(true);
        });
        actUtils.loadPrevious();
    }


    /**
     * Goes to next page of activities
     * Platform run later is used to prevent spam clicking
     */
    public void loadNext(){
        Platform.runLater(() -> {
            nextButton.setDisable(true);
            previousButton.setDisable(true);
        });
        actUtils.loadNext();
    }

    /**
     * Loads the page selected in the pageSpinner
     *
     * Platform run later is used to prevent spam clicking
     */
    public void loadPage() {
        Platform.runLater(() -> {
            showButton.setDisable(true);
        });

        actUtils.loadPage();
    }

    public ActivityBoardUtils getActUtils() {
        return actUtils;
    }
}
