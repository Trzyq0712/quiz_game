package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;


public class HomescreenCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final ApplicationUtils utils;


    @FXML
    private ImageView leaderboard;
    @FXML
    public MediaView mvv;
    @FXML
    public ImageView music;

    //Image myImage = new Image(getClass().getClassLoader().getResourceAsStream("images/leaderboard.png"));

    @Inject
    public HomescreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.utils = utils;
        //leaderboard.setImage(myImage);
    }

    public void showSPLeaderboard(MouseEvent event) {
        mainCtrl.showSPLeaderboard();
    }

    public void showExitScreen(MouseEvent event) {
        mainCtrl.showExitScreen();
    }

    public void showPrompt(Event e) {
        mainCtrl.showNewPrompt(e);
    }

    public void toggleSound(){
        utils.toggleSound();
    }

    public void showEditScreen(ActionEvent event) {
        mainCtrl.showEditScreen();
    }

    public void showInfo() {
        mainCtrl.showInfo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utils.registerMusicToggle(music);
    }
}
