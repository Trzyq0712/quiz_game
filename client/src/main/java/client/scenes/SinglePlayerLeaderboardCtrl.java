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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @FXML
    private TableView<PlayerScore> tableView;
    @FXML
    private TableColumn<PlayerScore, String> rank;
    @FXML
    private TableColumn<PlayerScore, String> player;
    @FXML
    private TableColumn<PlayerScore, String> score;
    @FXML
    private TableColumn<PlayerScore, String> scoredTime;

    private List<PlayerScore> allPlayerScores = new ArrayList<>();

    private ObservableList<PlayerScore> data = FXCollections.observableList(getAllPlayerScores());



    @Inject
    public SinglePlayerLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
        allPlayerScores = new ArrayList<>();
        allPlayerScores.add(new PlayerScore(1,"kh",5555));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        rank.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getRank())));
        player.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPlayerName()));
        score.setCellValueFactory(p -> new SimpleStringProperty(String.valueOf(p.getValue().getScore())));
        scoredTime.setCellValueFactory(new PropertyValueFactory<PlayerScore, String>("scoredTime"));
    }

    public List<PlayerScore> getAllPlayerScores() {
        if(allPlayerScores.size()==0){
            allPlayerScores.add(new PlayerScore(0,"0",0));
        }
        allPlayerScores.add(new PlayerScore(1,"0",0));
        allPlayerScores.add(new PlayerScore(2,"0",0));
        allPlayerScores.add(new PlayerScore(3,"0",0));
        return allPlayerScores;
    }


    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    public void playAgain() {
        mainCtrl.restore();
        mainCtrl.showQuestion();
    }

}
