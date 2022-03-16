package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import static client.Config.*;

import static com.google.inject.Guice.createInjector;

public class PromptCtrl extends ReusedButtonCtrl{

    private final ServerUtils server;

    //I think we can remove these 2 lines since they are in NamePromptCtrl but not sure tho
    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private TextField nameField;
    @FXML
    private Label errorLabel;
    @FXML
    ImageView music;
    @FXML
    public Button startButton;

    @Inject
    public PromptCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
    }

    /**
     * When the start button is clicked this fires off.
     * It checks whether the provided name abides by certain rules and whether it has permission
     * to start a singleplayer game
     */
    public void startGame() {
        if(checkName(nameField, errorLabel) && server.startSingle(nameField.getText())){
            mainCtrl.showQuestion();
            mainCtrl.buttonSound();
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

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    /**
     * @param nameField The text field in the scene
     * @return true if the text in nameField adheres to certain rules
     * (rules for now are now whitespaces and that something must be entered)
     * (we can ignore no whitespaces if we convert the text to base64 because then the url request doesn't mess up)
     */
    public boolean checkName(TextField nameField, Label errorLabel){
        String name = nameField.getText();
        if (name.length() > maxCharsUsername) {
            errorLabel.setText("Name needs to be 20 characters or less!");
            errorLabel.setVisible(true);
            return false;
        } else if(name.contains(" ")){
            errorLabel.setText("No whitespaces allowed!");
            errorLabel.setVisible(true);
            return false;
        } else if(name.equals("")){
            errorLabel.setText("You have to enter something!");
            errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    /**
     * When the start button is clicked this fires off.
     * It checks whether the provided name abides by certain rules and whether it has permission
     * to enter the waiting room. If it doesn't then for now I've just written it off that the
     * name is taken but that may not be the case if the connection doesn't go through and so on.
     */
    public void enterWaitingRoom(){
        if(checkName(nameField, errorLabel)){
            Player player = new Player(nameField.getText());
            if(server.enterWaitingRoom(nameField.getText()))
                mainCtrl.enterWaitingRoom(new Player(nameField.getText()));
            else{
                errorLabel.setText("Name is taken!");
                errorLabel.setVisible(true);
            }
        }
    }

    public void confirm() {
        if (mainCtrl.singlePlayerModeActive) startGame();
        else enterWaitingRoom();
    }
}
