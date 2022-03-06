package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Injector;

import static com.google.inject.Guice.createInjector;

public class SPNamePromptCtrl extends NamePromptCtrl{

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);


    public SPNamePromptCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void startGame() {
        mainCtrl.startGame();
    }
}
