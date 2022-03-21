package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.PlayerScore;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SinglePlayerLeaderboardCtrl extends BaseCtrl implements Initializable {
    private final ServerUtils server;

    @FXML
    ImageView imageView;

    private ObservableList<PlayerScore> data;

    @FXML
    private TableView<PlayerScore> table;
    @FXML
    private TableColumn<PlayerScore, String> rank;
    @FXML
    private TableColumn<PlayerScore, String> player;
    @FXML
    private TableColumn<PlayerScore, String> score;
    @FXML
    private TableColumn<PlayerScore, String> scoredTime;


    @Inject
    public SinglePlayerLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils) {
        super(mainCtrl, utils);
        this.server = server;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
        scoredTime.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getTime())));
    }

    /**
     * Adds a playr to the repo
     * @param p - thre player we want to add to the leaderboard
     */
    public void addPlayer(PlayerScore p){
        server.addPlayerToSPLeaderboard(p);
        refresh();
    }

    /**
     * Update the leaderboard
     */
    public void refresh(){
        var players = server.getPlayersInSPL();
        //A sort should be done to display the PlayerScores in the correct order
        data = FXCollections.observableList(players);
        table.setItems(data);
    }

    public void playAgain() {
        mainCtrl.restore();
        mainCtrl.showQuestion();
    }

}
