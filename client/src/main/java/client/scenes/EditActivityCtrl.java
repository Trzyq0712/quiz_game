package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Activity;
import commons.PostActivity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.google.inject.Guice.createInjector;

public class EditActivityCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    ImageView imageView;
    @FXML
    Label errorLabel;
    @FXML
    TextField questionField;
    @FXML
    TextField consumptionField;
    @FXML
    TextField imagePathField;

    String imagePath;
    boolean add;
    Activity activity;
    byte[] pictureBuffer;
    FileChooser fileChooser;

    @Inject
    public EditActivityCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * resets all the fields
     * resets the imageView to the placeholder image
     * makes the error label invisible
     */
    public void setUp(boolean add, Activity activity) {
        fileChooser = new FileChooser();
        imagePath = "images/placeholder.png";
        errorLabel.setVisible(false);
        imageView.setImage(new Image(imagePath));
        questionField.setText("");
        consumptionField.setText("");
        imagePathField.setText("");
        this.add = add;
        if(!add){
            this.activity = activity;
            questionField.setText(activity.getDescription());
            consumptionField.setText(activity.getEnergyConsumption().toString());
            pictureBuffer = server.getImageBuffer(activity.getId());
            imageView.setImage(new Image(new ByteArrayInputStream(pictureBuffer)));
            imagePath = activity.getPicturePath();
        } else this.activity = new Activity();
    }

    /**
     * Opens the file dialog for the user to select the image to be added
     */
    public void addImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedImage = fileChooser.showOpenDialog(mainCtrl.getSecondaryStage());
        imagePathField.setText(selectedImage.getAbsolutePath());
        imagePath = selectedImage.getAbsolutePath();
        try {
            pictureBuffer = Files.readAllBytes(Paths.get(imagePath));
            imageView.setImage(new Image(imagePath));
        } catch (Exception ex){
            errorLabel.setText("Can't find image");
            errorLabel.setVisible(true);
        }
    }


    /**
     * try to add the activity to the database
     */
    public void tryAdd() {
        if (validActivity()) {
            activity.setDescription(questionField.getText());
            activity.setEnergyConsumption(Long.parseLong(consumptionField.getText()));
            activity.setPicturePath(imagePath);
            PostActivity postActivity = new PostActivity(activity, pictureBuffer);
            if(add){
                Activity newActivity = server.addPostActivity(postActivity);
                if (newActivity != null){
                    mainCtrl.updateAdd(newActivity);
                    Stage stage = (Stage) imageView.getScene().getWindow();
                    stage.close();
                }  else errorLabel.setText("Server did not allow the activity to be added");
            } else {
                Activity newActivity = server.updatePostActivity(postActivity);
                if (newActivity != null){
                    mainCtrl.updateEdit(newActivity);
                    Stage stage = (Stage) imageView.getScene().getWindow();
                    stage.close();
                }  else errorLabel.setText("Server did not allow the activity to be added");
            }

            errorLabel.setVisible(true);
        }
    }

    /**
     * @return true if the activity fields pass the tests
     */
    private boolean validActivity() {
        String description = questionField.getText();
        Long consumption = (long) -1;

        if (description.equals("")) {
            errorLabel.setText("Question field can't be empty!");
            errorLabel.setVisible(true);
            return false;
        } else if (consumptionField.getText().equals("")) {
            errorLabel.setText("Energy consumption field can't bet empty!");
            errorLabel.setVisible(true);
            return false;
        } else if (imagePath.equals("images/placeholder.png")) {
            errorLabel.setText("Please provide an image!");
            errorLabel.setVisible(true);
            return false;
        }

        try {
            consumption = Long.parseLong(consumptionField.getText());
            if (consumption <= 0) {
                errorLabel.setText("Only positive numbers allowed!");
                errorLabel.setVisible(true);
                return false;
            }
        } catch (NumberFormatException ex) {
            errorLabel.setText("Only numbers allowed in the consumption field!");
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

}
