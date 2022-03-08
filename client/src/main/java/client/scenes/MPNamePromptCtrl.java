package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.inject.Guice.createInjector;

public class MPNamePromptCtrl extends NamePromptCtrl{

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private TextField nameField;

    @Inject
    public MPNamePromptCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void enterWaitingRoom(){
        try {
            if(nameField.getText().contains(" ")){
                nameField.setText("No whitespaces allowed!");
            } else if(nameField.getText().equals("")){
                nameField.setText("You have to enter something!");
            } else {
                URL url = new URL("http://localhost:8080/api/play/multi?fname="
                        + nameField.getText() + "&lname=" + nameField.getText());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                if(responseCode == 200)
                    mainCtrl.enterWaitingRoom();
                else
                    nameField.setText("Name is taken!");
            }
        } catch (Exception exception){
            System.out.println(exception);
        }
    }
}
