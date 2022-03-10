package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

import static com.google.inject.Guice.createInjector;

public class WaitingRoomCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private GridPane playerGrid;
    private List<Player> players;

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
    public void showHome() {
        mainCtrl.showHome();
    }

    public void startGame() {
        mainCtrl.startGame();
    }

    /**
     * Loads the waiting list players on to the waiting room grid
     */
    public void loadPlayerGrid() {
        playerGrid.getRowConstraints().clear();
        players = server.getWaitingPlayers();
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(149);
        for(int i=0; i < players.size(); i++){
            if(i%4==0) playerGrid.getRowConstraints().add(con);
            playerGrid.add(new Label(players.get(i).name), i%4, i/4);
        }
    }
}
