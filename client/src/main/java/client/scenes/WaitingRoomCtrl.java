package client.scenes;

import client.utils.ApplicationUtils;
import client.utils.GameUtils;
import client.utils.ServerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import commons.Emote;
import commons.Player;
import jakarta.ws.rs.ServiceUnavailableException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.List;

public class WaitingRoomCtrl extends BaseCtrl {
    static Boolean threadRun;
    public StompSession.Subscription waitingroom;
    Thread pollingThread;
    @FXML
    private GridPane playerGrid;

    private List<Player> playerList;

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl, ApplicationUtils utils, GameUtils gameUtils) {
        super(mainCtrl, utils, server, gameUtils);
    }

    @FXML
    private void startMultiplayer() {
        server.send("/app/waitingroom/start", true);
    }

    /**
     * clears the chat of all emoji's, this should be called after the user leaves a game
     */

    public void restoreChat() {
        for (int i = 0; i < mainCtrl.listOfChatBoxes.size(); i++) {
            VBox k = mainCtrl.listOfChatBoxes.get(i);
            k.getChildren().clear();
        }
    }

    /**
     * The polling Thread is responsible for polling the server for the user
     * to visibly see when players leave and join. threadRun is the flag
     * for when the thread should be running. Object mapper converts the received
     * HashTableObject list to Player list. There is also a second threadRun check because when the player
     * leaves the server still sends this as a change in the waiting room, and it
     * wants to load the playerGrid again. Platform.runLater is needed for scene
     * objects to be manipulated not from the main thread. I don't know how to make
     * a request not time-out.
     */

    public void setUp() {
        playerList = server.getWaitingPlayers();
        loadPlayerGrid(playerList);
        pollingThread = new Thread(() -> {
            threadRun = true;
            ObjectMapper mapper = new ObjectMapper();
            while (threadRun) {
                try {
                    playerList = mapper.convertValue(server.pollWaitingroom(playerList),
                            new TypeReference<List<Player>>() {
                            });
                    if (threadRun) {
                        Platform.runLater(() -> loadPlayerGrid(playerList));
                    }
                } catch (ServiceUnavailableException ex) {
                    System.out.println("This is to catch if the poll request times out");
                }
            }
        });
        pollingThread.start();

        waitingroom = server.registerForMessages("/topic/waitingroom/start", Integer.class, l -> {
            threadRun = false;
            gameUtils.setGameID((long)l);
            server.registerForMessages("/topic/emote/" + gameUtils.getGameID(), Emote.class, e -> {
                mainCtrl.emote(e.getPath(), e.getName());
            });
            leaveWaitingRoom(gameUtils.getPlayer());
            Platform.runLater(() -> {
                mainCtrl.showQuestion();
                utils.playButtonSound();
            });
            restoreChat();
            server.unsubscribe(waitingroom);
        });

    }

    /**
     * @param loadPlayers players to be put into the waiting room grid
     *                    <p>
     *                    Constraints are basically grid formatting.
     */
    public void loadPlayerGrid(List<Player> loadPlayers) {
        playerGrid.getChildren().clear();
        playerGrid.getRowConstraints().clear();
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(149);
        for (int i = 0; i < loadPlayers.size(); i++) {
            if (i % 4 == 0) playerGrid.getRowConstraints().add(con);
            playerGrid.add(new Label(loadPlayers.get(i).getPlayerName()), i % 4, i / 4);
        }
    }

    @Override
    public void showHome() {
        threadRun = false;
        leaveWaitingRoom(gameUtils.getPlayer());
        super.showHome();
    }


    public void leaveWaitingRoom(Player player) {
        server.leaveWaitingroom(player);
    }
}
