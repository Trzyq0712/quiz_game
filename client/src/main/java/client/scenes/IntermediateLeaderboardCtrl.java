package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Emote;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static commons.Config.*;


public class IntermediateLeaderboardCtrl extends BaseCtrl {

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
    ProgressBar pgBarIntermediate;

    @FXML
    Label questionTracker;
    @FXML
    Label scoreLabel;

    @FXML
    VBox chatbox;
    @FXML
    StackPane chatAndEmoteHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
    }

    @Inject
    public IntermediateLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl,
                                       ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    public void activateProgressBar() {
        utils.runProgressBar(pgBarIntermediate, timeForIntermediate, mainCtrl::showQuestion, null);
    }

    public void updateQuestionTracker() {
        gameUtils.updateTracker(questionTracker, scoreLabel, false);
    }

    @FXML
    private void emote(Event e) {
        utils.playButtonSound();
        String path = ((ImageView)e.getSource()).getImage().getUrl();
        Emote emote = new Emote(path, gameUtils.getPlayer().getPlayerName());
        server.send("/app/emote/" + gameUtils.getGameID(), emote);
    }

    public void refresh(){
        var players = server.getPlayers(gameUtils.getGameID()).getListOfPlayers();
        data = FXCollections.observableList(players);
        int currentRank = 1;
        var listOfPlayers = data.stream().sorted().collect(Collectors.toList());
        for (Player p : listOfPlayers) {
            p.setRank(currentRank++);
        }
        var result = listOfPlayers.stream()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        table.setItems(result);;
    }

}
