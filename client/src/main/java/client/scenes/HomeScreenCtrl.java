package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import com.google.inject.Inject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class HomeScreenCtrl extends BaseCtrl {

<<<<<<< HEAD
    private final GameUtils gameUtils;
    private final NamePromptCtrl namePromptCtrl;

    @Inject
    public HomeScreenCtrl(MainCtrl mainCtrl, ApplicationUtils utils,
                          GameUtils gameUtils, NamePromptCtrl namePromptCtrl) {
        super(mainCtrl, utils);
        this.gameUtils = gameUtils;
        this.namePromptCtrl = namePromptCtrl;
=======
    @Inject
    public HomeScreenCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils, server);
>>>>>>> dev
    }

    @FXML
    private void showSPLeaderboard() {
        utils.playButtonSound();
        mainCtrl.showSPLeaderboardFromHome();
    }

    @FXML
    private void showExitScreen() {
        utils.playButtonSound();
        mainCtrl.showExitScreen();
    }

    /**
     * Based on which button the player clicked, the player will get the nameprompt for single- or multiplayer.
     *
     * @param e The button on which the player has clicked to reach the nameprompt.
     */
    @FXML
    private void showPrompt(Event e) {
        utils.playButtonSound();
        gameUtils.resetGame();
        String mode = ((Button) e.getSource()).getText();
        if (mode.equals("Singleplayer")) {
            gameUtils.setGameType(GameUtils.GameType.SinglePlayer);
            namePromptCtrl.startButton.setPrefWidth(200);
            namePromptCtrl.startButton.setText("Enter game");
        } else {
            gameUtils.setGameType(GameUtils.GameType.MultiPlayer);
            namePromptCtrl.startButton.setPrefWidth(500);
            namePromptCtrl.startButton.setText("Enter waiting room");
        }
        namePromptCtrl.setUp();
        mainCtrl.showNamePromtScene();
    }

    @FXML
    private void showEditScreen() {
        utils.playButtonSound();
        mainCtrl.showEditScreen();
    }

    @FXML
    private void showInfo() {
        utils.playButtonSound();
        mainCtrl.showInfo();
    }

}
