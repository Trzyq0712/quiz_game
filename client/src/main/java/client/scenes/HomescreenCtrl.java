package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;


import static client.Config.backgroundMusic;
import static com.google.inject.Guice.createInjector;

public class HomescreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private ImageView leaderboard;
    @FXML
    public MediaView mvv;
    @FXML
    public ImageView music;

    //Image myImage = new Image(getClass().getClassLoader().getResourceAsStream("images/leaderboard.png"));

    @Inject
    public HomescreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        //leaderboard.setImage(myImage);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        repeatSound();
                    }
                },
                500
        );
    }



    public void showNamePrompt(ActionEvent event) {
        mainCtrl.showNamePrompt();
    }

    public void showMPNamePrompt(ActionEvent event) {
        mainCtrl.showMPNamePrompt();
    }

    public void showSPLeaderboard(MouseEvent event) {
        mainCtrl.showSPLeaderboard();
    }

    public void showExitScreen(MouseEvent event) {
        mainCtrl.showExitScreen();
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }


    public void repeatSound(){
        Media media = new Media(backgroundMusic.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        mvv.setMediaPlayer(player);
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        player.play();
    }


    public void showEditScreen(ActionEvent event) {
        mainCtrl.showEditScreen();
    }


    public void showInfo() {
        mainCtrl.showInfo();
    }
}
