package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.PlayerScore;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.google.inject.Guice.createInjector;

public class SinglePlayerLeaderboardCtrl extends ReusedButtonCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    ImageView imageView;

    @FXML
    ImageView music;

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
    public SinglePlayerLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
        scoredTime.setCellValueFactory(new PropertyValueFactory<PlayerScore, String>("scoredTime"));
    }

    public void addPlayer(PlayerScore p){
        server.addPlayerToSPLeaderboard(p);
        refresh();
    }

    public void refresh(){
        var players = server.getPlayersInSPL();
        data = FXCollections.observableList(players);
        table.setItems(data);
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    public void playAgain() {
        mainCtrl.restore();
        mainCtrl.showQuestion();
    }

}
