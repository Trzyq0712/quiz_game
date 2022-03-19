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
import org.checkerframework.checker.units.qual.A;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.io.File;

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

    JFileChooser fileChooser;
    String imagePath;

    @Inject
    public EditActivityCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void setUp(){
        imagePath = "images/placeholder.png";
        errorLabel.setVisible(false);
        imageView.setImage(new Image(imagePath));
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;

                String extension = f.getAbsolutePath().substring(f.getAbsolutePath().length()-3);
                if (extension.equals("jpg") || extension.equals("png")) return true;

                return false;
            }

            @Override
            public String getDescription() {
                return "Only jpg and png allowed";
            }
        });
        fileChooser.setAcceptAllFileFilterUsed(false);
    }

    public void browseFiles(){
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            imagePath = file.getAbsolutePath();
            imageView.setImage(new Image(imagePath));
        }
    }

    public void trySend(){
        if (validActivity()) {
            Activity activity = new Activity(questionField.getText(), Long.parseLong(consumptionField.getText()), imagePath);
            PostActivity postActivity = new PostActivity(activity);

            if (server.addActivity(postActivity) != null) {
                errorLabel.setText("Successfully added!");
                errorLabel.setVisible(true);
            } else {
                errorLabel.setText("Server did not allow the activity to be added");
                errorLabel.setVisible(true);
            }
        }
    }

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
