package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

import static com.google.inject.Guice.createInjector;

public class HomescreenCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    private WaitingRoomCtrl waitCtrl;
    private Scene waitScene;

    @Inject
    public HomescreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void log(ActionEvent event) throws IOException {
        //System.out.println(event.getSource());
        Parent root = FXMLLoader.load(getClass().getResource("WaitingRoom"));
        /*var home = FXML.load(WaitingRoomCtrl.class, "client", "scenes", "WaitingRoom.fxml");
        this.waitCtrl = home.getKey();
        this.waitScene = new Scene(home.getValue());
        primaryStage.setScene(waitScene);*/
        System.out.println("PRESS$$");
    }


}
