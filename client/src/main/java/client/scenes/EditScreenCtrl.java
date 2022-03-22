package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.io.ByteArrayInputStream;
import java.util.List;


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
        activityList = server.getActivities();
        start = 0;
        if(activityList.size() - activitiesPerPage < 0) end = activityList.size();
        else end = start + activitiesPerPage;
        pageCount = activityList.size() / activitiesPerPage;
        if(activityList.size() % activitiesPerPage != 0)
            pageCount++;
        spinnerValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, pageCount, 1);
        pageSpinner.setEditable(true);
        pageSpinner.setValueFactory(spinnerValues);

        loadGrid();
    }

    /**
     * Loads the activity grid from start index to end index of activityList
     * Platform run later is used to prevent spam clicking
     */
    public void loadGrid(){
        activityGrid.getChildren().clear();
        for(int i=start; i < end; i++) setUpRow(i);

        pageLabel.setText("\\ " + pageCount);
        pageSpinner.getValueFactory().setValue(start / activitiesPerPage + 1);
        if(start != 0) previousButton.setVisible(true);
        else previousButton.setVisible(false);
        if(end != activityList.size()) nextButton.setVisible(true);
        else nextButton.setVisible(false);

        Platform.runLater(() -> {
            nextButton.setDisable(false);
            previousButton.setDisable(false);
            showButton.setDisable(false);
        });
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
        start -= activitiesPerPage;
        if(start - activitiesPerPage < 0) start = 0;
        end = start + activitiesPerPage;
        loadGrid();
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
        start += activitiesPerPage;
        end = start + activitiesPerPage;
        if(end > activityList.size()) end = activityList.size();
        loadGrid();
    }

    /**
     * loads the activity into the row and creates other objects
     *
     * @param i is the index of the row
     */
    private void setUpRow(int i) {
        Image editImage = new Image(getClass().getClassLoader().getResourceAsStream("images/gear.png"));
        Image deleteImage = new Image(getClass().getClassLoader().getResourceAsStream("images/delete.png"));
        setUpImage(i);
        setUpEdit(editImage, i);
        setUpDelete(deleteImage, i);
        setUpLabels(activityList.get(i), i);
    }

    /**
     * Sets up the labels of the row
     *
     * @param activity is the activity from which the labels get data
     * @param index is the index in which to put the row
     */
    private void setUpLabels(Activity activity, int index) {
        Label descriptionLabel = new Label(activity.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        activityGrid.add(descriptionLabel, 0, index % activitiesPerPage);
        activityGrid.add(new Label(activity.getEnergyConsumption().toString()), 1, index % activitiesPerPage);
    }

    /**
     * sets up the image of the activity
     *
     * @param index is the index in which to put the row
     */
    public void setUpImage(int index){
        ImageView imageView = new ImageView();
        Image image = new Image(server.SERVER + activityList.get(index).getPicturePath());
        setProperties(imageView, image, index);
        activityGrid.add(imageView, 2, index % activitiesPerPage);
    }

    /**
     * sets up the edit ImageView
     *
     * @param image is the icon of the gear
     * @param index is the index in which to put the row
     */
    public void setUpEdit(Image image, int index){
        ImageView editActivity = new ImageView();
        editActivity.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int index = Integer.parseInt(((ImageView) event.getSource()).getId());
                mainCtrl.editActivity(false, activityList.get(index));
                event.consume();
            }
        });
        setProperties(editActivity, image, index);
        activityGrid.add(editActivity, 3, index % activitiesPerPage);
    }

    /**
     * Updates the old activity with the new activity fields
     *
     * @param newActivity is the activity from which to get new data
     */
    public void updateEdit(Activity newActivity) {
        for(Activity activity: activityList){
            if(activity.getId() == newActivity.getId()){
                activity = newActivity;
                break;
            }
        }
        loadGrid();
    }

    /**
     * Adds the new activity to the activity list and sets a new page count
     * spinnerValues.setMax sets the maximum possible value of the spinner to the new page count
     *
     * @param newActivity is the new activity to be added to the list
     */
    public void updateAdd(Activity newActivity) {
        activityList.add(newActivity);
        pageCount = activityList.size() / activitiesPerPage;
        if(activityList.size() % activitiesPerPage != 0)
            pageCount++;
        spinnerValues.setMax(pageCount);
        loadPage();
    }

    /**
     * sets up the delete ImageView
     *
     * @param image is the icon of the X
     * @param index is the index in which to put the row
     */
    public void setUpDelete(Image image, int index){
        ImageView deleteActivity = new ImageView();
        setProperties(deleteActivity, image, index);
        deleteActivity.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                int index = Integer.parseInt(((ImageView) event.getSource()).getId());
                if(server.deletePostActivity(activityList.get(index).getId())){
                    activityList.remove(index);
                    pageCount = activityList.size() / activitiesPerPage;
                    if(activityList.size() % activitiesPerPage != 0)
                        pageCount++;
                    spinnerValues.setMax(pageCount);
                    loadPage();
                }

                event.consume();
            }
        });
        activityGrid.add(deleteActivity, 4, index % activitiesPerPage);
    }

    /**
     * Adds basic properties to the imageView
     *
     * @param imageView is the imageView of which the properties will be set
     * @param image is the image to be applied
     * @param index is the index in which to put the row
     */
    public void setProperties(ImageView imageView, Image image, int index) {
        imageView.setImage(image);
        imageView.getStyleClass().add("clickable");
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setId(Integer.toString(index));
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

        int page = (Integer) pageSpinner.getValueFactory().getValue();
        start = (page - 1) * activitiesPerPage;
        end = start + activitiesPerPage;
        if(end > activityList.size()) end = activityList.size();

        System.out.println(start + " " + end);

        loadGrid();
    }
}
