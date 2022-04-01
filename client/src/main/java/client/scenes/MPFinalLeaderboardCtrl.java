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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MPFinalLeaderboardCtrl extends BaseCtrl {

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
    public VBox chatbox;
    @FXML
    public StackPane chatAndEmoteHolder;

    @FXML
    private Label rankInfo;

    NamePromptCtrl promptCtrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
    }

    @Inject
    public MPFinalLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils,
                                  NamePromptCtrl promptCtrl, GameUtils gameUtils){
        super(mainCtrl, utils, server, gameUtils);
        this.promptCtrl = promptCtrl;
    }

    @FXML
    private void playAgain() {
        utils.playButtonSound();
        gameUtils.resetGame();
        mainCtrl.showNamePromtScene();
    }

    @FXML
    private void emote(Event e){
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
