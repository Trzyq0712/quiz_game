package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.google.inject.Guice.createInjector;

public class MPNamePromptCtrl extends NamePromptCtrl{

    //I think we can remove these 2 lines since they are in NamePromptCtrl but not sure tho
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private TextField nameField;

    @Inject
    public MPNamePromptCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void enterWaitingRoom(){
        if(checkName(nameField) && server.enterWaitingRoom(nameField.getText())){
            mainCtrl.enterWaitingRoom();
            nameField.setPromptText("Enter your name...");
        } else
            nameField.setPromptText("Name is taken!");
        nameField.clear();
    }
}
