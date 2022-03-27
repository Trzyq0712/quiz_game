package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Config;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import static commons.Config.*;


public class NamePromptCtrl extends BaseCtrl {

    private final GameUtils gameUtils;
    @FXML
    public Button startButton;
    @FXML
    private TextField nameField;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField serverField;

    @Inject
    public NamePromptCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils, server);
        this.gameUtils = gameUtils;
    }

    /**
     * When the start button is clicked this fires off.
     * It checks whether the provided name abides by certain rules and whether it has permission
     * to start a singleplayer game
     * A new player is created, the name entered will be used for identification later on
     */
    public void startGame() {
        utils.playButtonSound();
        if (checkName(nameField, errorLabel) && server.startSingle(nameField.getText())) {
            Player player = new Player(nameField.getText());
            gameUtils.setPlayer(player);
            gameUtils.requestGameID();
            server.start();
            //gameUtils.startTimer();
            mainCtrl.showQuestion();
        }
    }

    /**
     * mane the nameField prompt display this
     */
    public void setUp() {
        nameField.clear();
        nameField.setPromptText("Enter your name...");
        serverField.setText(Config.server);
        errorLabel.setVisible(false);
    }

    /**
     * @param nameField The text field in the scene
     * @return true if the text in nameField adheres to certain rules
     * (rules for now are now whitespaces and that something must be entered)
     * (we can ignore no whitespaces if we convert the text to base64 because then the url request doesn't mess up)
     */
    public boolean checkName(TextField nameField, Label errorLabel) {
        String name = nameField.getText();
        if (name.length() > maxCharsUsername) {
            errorLabel.setText("Name needs to be 20 characters or less!");
            errorLabel.setVisible(true);
            return false;
        } else if (name.contains(" ")) {
            errorLabel.setText("No whitespaces allowed!");
            errorLabel.setVisible(true);
            return false;
        } else if (name.equals("")) {
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

    public void enterWaitingRoom() {
        if (checkName(nameField, errorLabel)) {
            Player player = new Player(nameField.getText());
            if (server.enterWaitingRoom(nameField.getText())) {
                gameUtils.setPlayer(player);
                mainCtrl.showWaitingRoom();
                gameUtils.requestGameID();
            }
        } else {
            errorLabel.setText("Name is taken!");
            errorLabel.setVisible(true);
        }
    }


    /**
     * when the player clicks the button, we have to check if the player is in single- or multiplayer
     * depending on this we call the appropriate function
     */
    @FXML
    private void confirm() {
        utils.playButtonSound();
        ServerUtils.SERVER = serverField.getText(); //sets the server to the user input
        if (gameUtils.getGameType().equals(GameUtils.GameType.SinglePlayer)) {
            mainCtrl.activateSingleplayer();
            startGame();
        } else {
            mainCtrl.activateMultiplayer();
            enterWaitingRoom();
        }
    }

}
