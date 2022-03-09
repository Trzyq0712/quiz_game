package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import static com.google.inject.Guice.createInjector;

public abstract class NamePromptCtrl extends ReusedButttonCtrl{
    protected final ServerUtils server;
    protected final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @Inject
    public NamePromptCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * @param nameField The text field in the scene
     * @return true if the text in nameField adheres to certain rules
     * (rules for now are now whitespaces and that something must be entered)
     * (we can ignore no whitespaces if we convert the text to base64 because then the url request doesn't mess up)
     */
    public boolean checkName(TextField nameField){
        String name = nameField.getText();
        if(name.contains(" ")){
            nameField.setPromptText("No whitespaces allowed!");
            return false;
        } else if(name.equals("")){
            nameField.setPromptText("You have to enter something!");
            return false;
        }
        return true;
    }
}
