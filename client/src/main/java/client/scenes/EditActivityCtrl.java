package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.PostActivity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import static client.Config.serverImagePath;


public class EditActivityCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ApplicationUtils utils;

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

    @Inject
    public EditActivityCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.utils = utils;
    }

    /**
     * resets the imageView to the placeholder image
     * makes the error label invisible
     */
    public void setUp() {
        imagePath = "images/placeholder.png";
        errorLabel.setVisible(false);
        imageView.setImage(new Image(imagePath));
    }

    /**
     * Opens the file dialog for the user to select the image to be added
     */
    @FXML
    private void addImage() {
        utils.playButtonSound();
        String path = imagePathField.getText();
        if(path.equals("")){
            errorLabel.setText("Path can't be empty");
            errorLabel.setVisible(true);
        }
        try {
            imagePath = path.replace('\\', File.separatorChar);
            imageView.setImage(new Image(imagePath));
        } catch (Exception ex){
            errorLabel.setText("Can't find image");
            errorLabel.setVisible(true);
        }
    }


    /**
     * try to add the activity to the database
     */
    @FXML
    private void tryAdd() {
        utils.playButtonSound();
        if (validActivity()) {
            Activity activity = new Activity(questionField.getText(),
                    Long.parseLong(consumptionField.getText()), imagePath);
            PostActivity postActivity = new PostActivity(activity,
                    serverImagePath);

            if (server.addPostActivity(postActivity) != null) errorLabel.setText("Successfully added!");
            else errorLabel.setText("Server did not allow the activity to be added");

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
