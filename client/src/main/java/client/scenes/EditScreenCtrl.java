package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
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

import static com.google.inject.Guice.createInjector;

public class EditScreenCtrl extends ReusedButtonCtrl{
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

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
    public EditScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    public void addActivity() {
        mainCtrl.editActivity(true, null);
    }

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

    private void setUpRow(int i) {
        Image editImage = new Image("images\\gear.png");
        Image deleteImage = new Image("images\\delete.png");
        setUpImage(i);
        setUpEdit(editImage, i);
        setUpDelete(deleteImage, i);
        setUpLabels(activityList.get(i), i);
    }

    private void setUpLabels(Activity activity, int index) {
        Label descriptionLabel = new Label(activity.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setTextAlignment(TextAlignment.CENTER);

        activityGrid.add(descriptionLabel, 0, index % 10);
        activityGrid.add(new Label(activity.getEnergyConsumption().toString()), 1, index % 10);
    }

    public void setUpImage(int index){
        ImageView imageView = new ImageView();
        Image image = new Image(new ByteArrayInputStream(server.getImageBuffer(activityList.get(index).getId())));
        setProperties(imageView, image, index);
        activityGrid.add(imageView, 2, index % 10);
    }

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
        activityGrid.add(editActivity, 3, index % 10);
    }

    public void updateEdit(Activity newActivity) {
        for(Activity activity: activityList){
            if(activity.getId() == newActivity.getId()){
                activity = newActivity;
                break;
            }
        }
        loadGrid();
    }

    public void updateAdd(Activity newActivity) {
        activityList.add(newActivity);
        pageCount = activityList.size() / activitiesPerPage;
        if(activityList.size() % activitiesPerPage != 0)
            pageCount++;
        spinnerValues.setMax(pageCount);
        loadPage();
    }

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
        activityGrid.add(deleteActivity, 4, index % 10);
    }

    public void setProperties(ImageView imageView, Image image, int index){
        imageView.setImage(image);
        imageView.getStyleClass().add("clickable");
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setId(Integer.toString(index));
    }

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
