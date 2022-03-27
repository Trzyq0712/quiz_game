package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SinglePlayerLeaderboardCtrl extends BaseCtrl implements Initializable {

    private ObservableList<Player> data;

    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> rank;
    @FXML
    private TableColumn<Player, String> player;
    @FXML
    private TableColumn<Player, String> score;
    @FXML
    private TableColumn<Player, String> scoredTime;
    @FXML
    private Button playAgain;


    @Inject
    public SinglePlayerLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils, server);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
        scoredTime.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getTime())));
    }

    /**
     * Adds a player to the repo
     *
     * @param p - the player we want to add to the leaderboard
     */
    public void addPlayer(Player p) {
        server.addPlayerToSPLeaderboard(p);
        refresh();
    }

    @FXML
    private void refreshButton() {
        utils.playButtonSound();
        refresh();
    }

    /**
     * Update the leaderboard
     */
    public void refresh() {
        var players = server.getPlayersInSPL();
        //A sort should be done to display the Players in the correct order
        data = FXCollections.observableList(players);
        table.setItems(data);
    }

    public void playAgain() {
        utils.playButtonSound();
        mainCtrl.restore();
        mainCtrl.showQuestion();
    }

    /**
     * The button "Play a singleplayer again" is made visible
     */
    public void showPLayAgain() {
        playAgain.setVisible(true);
    }

    /**
     * The button "Play a singleplayer again" is hidden
     * Used when leaderboard is accessed from the homescreen
     */
    public void hidePlayAgain() {
        playAgain.setVisible(false);
    }

}
