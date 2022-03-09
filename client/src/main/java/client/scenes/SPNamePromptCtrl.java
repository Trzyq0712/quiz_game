package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.google.inject.Guice.createInjector;

public class SPNamePromptCtrl extends NamePromptCtrl{

    //I think we can remove these 2 lines since they are in NamePromptCtrl but not sure tho
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @javafx.fxml.FXML
    private TextField nameField;

    @Inject
    public SPNamePromptCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void startGame() {
        if(checkName(nameField) && server.startSingle(nameField.getText())){
            mainCtrl.startGame();
            nameField.setPromptText("Enter your name...");
        }
        nameField.clear();
    }
}
