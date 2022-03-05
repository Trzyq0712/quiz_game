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


import static com.google.inject.Guice.createInjector;

public class HomescreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private ImageView leaderboard;

    //Image myImage = new Image(getClass().getClassLoader().getResourceAsStream("images/leaderboard.png"));

    @Inject
    public HomescreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        //leaderboard.setImage(myImage);
    }



    public void showNamePrompt(ActionEvent event) {
        mainCtrl.showNamePrompt();
    }

    public void showSPLeaderboard(MouseEvent event) {
        mainCtrl.showSPLeaderboard();
    }

    public void showExitScreen(MouseEvent event) {
        mainCtrl.showExitScreen();
    }

    public void toggleSound(ActionEvent event){

    }
}
