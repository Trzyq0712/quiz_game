package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import commons.PlayerScore;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static client.Config.maxCharsUsername;


public class NamePromptCtrl extends BaseCtrl implements Initializable {

    private final ServerUtils server;

    @FXML
    private TextField nameField;
    @FXML
    private Label errorLabel;
    @FXML
    public Button startButton;

    @Inject
    public NamePromptCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    /**
     * When the start button is clicked this fires off.
     * It checks whether the provided name abides by certain rules and whether it has permission
     * to start a singleplayer game
     * A new player is created, the name entered will be used for identification later on
     */
    public void startGame() {
        if(checkName(nameField, errorLabel) && server.startSingle(nameField.getText())){
            PlayerScore player = new PlayerScore(0, nameField.getText(),0);
            mainCtrl.setPlayerScore(player);
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

    /**
     * when the player clicks the button, we have to check if the player is in single- or multiplayer
     * depending on this we call the appropriate function
     */
    public void confirm() {
        if (mainCtrl.singlePlayerModeActive) startGame();
        else enterWaitingRoom();
    }

}
