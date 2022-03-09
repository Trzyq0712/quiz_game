package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static com.google.inject.Guice.createInjector;

public class SPNamePromptCtrl extends NamePromptCtrl{

    //I think we can remove these 2 lines since they are in NamePromptCtrl but not sure tho
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private TextField nameField;
    @FXML
    private Label errorLabel;

    @Inject
    public SPNamePromptCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * When the start button is clicked this fires off.
     * It checks whether the provided name abides by certain rules and whether it has permission
     * to start a singleplayer game
     */
    public void startGame() {
        if(checkName(nameField, errorLabel) && server.startSingle(nameField.getText())){
            mainCtrl.startGame();
        }
    }

    /**
     * mane the nameField prompt display this
     */
    public void setUp(){
        nameField.clear();
        nameField.setPromptText("Enter your name...");
        errorLabel.setVisible(false);
    }
}
