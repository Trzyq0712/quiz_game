package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Injector;
import commons.Player;
import jakarta.ws.rs.ServiceUnavailableException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.List;

import static com.google.inject.Guice.createInjector;

public class WaitingRoomCtrl extends ReusedButtonCtrl{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    @FXML
    private GridPane playerGrid;
    @FXML
    ImageView music;

    private Player player;
    private List<Player> playerList;
    Thread pollingThread;
    static Boolean threadRun;

    @Inject
    public WaitingRoomCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(mainCtrl);
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void startGame() {
        threadRun = false;
        leaveWaitingroom(player);
        mainCtrl.showQuestion();
        mainCtrl.buttonSound();
        restoreChat1();
    }

    public void restoreChat() {
        for (VBox i : mainCtrl.listOfChatBoxes) {
            int z = i.getChildren().size();
            for (var k : i.getChildren()) {
                i.getChildren().remove(k);
            }
        }
    }

    public void restoreChat1() {
        for (int i = 0; i < mainCtrl.listOfChatBoxes.size(); i++) {
            VBox k = mainCtrl.listOfChatBoxes.get(i);
            k.getChildren().clear();
        }
        mainCtrl.amountOfMessages = 0;
    }

    public void toggleSound(){
        mainCtrl.toggleSound();
    }

    /**
     * @param player is the player that is waiting in the waiting room.
     *
     * The polling Thread is responsible for polling the server for the user
     * to visibly see when players leave and join. threadRun is the flag
     * for when the thread should be running. Object mapper converts the received
     * HashTableObject list to Player list. There is also a second threadRun check because when the player
     * leaves the server still sends this as a change in the waiting room, and it
     * wants to load the playerGrid again. Platform.runLater is needed for scene
     * objects to be manipulated not from the main thread. I don't know how to make
     * a request not time-out.
     */
    public void setUp(Player player){
        this.player = player;
        playerList = server.getWaitingPlayers();
        loadPlayerGrid(playerList);
        pollingThread = new Thread(() -> {
            threadRun = true;
            ObjectMapper mapper = new ObjectMapper();
            while(threadRun){
                try{
                    playerList = mapper.convertValue(server.pollWaitingroom(playerList),
                            new TypeReference<List<Player>>() { });
                    if(threadRun){
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                loadPlayerGrid(playerList);
                            }
                        });
                    }
                } catch (ServiceUnavailableException ex){
                    System.out.println("This is to catch if the poll request times out");
                }
            }
        });
        pollingThread.start();
    }

    /**
     * @param loadPlayers players to be put into the waiting room grid
     *
     * Constraints are basically grid formatting.
     */
    public void loadPlayerGrid(List<Player> loadPlayers) {
        playerGrid.getChildren().clear();
        playerGrid.getRowConstraints().clear();
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(149);
        for(int i=0; i < loadPlayers.size(); i++){
            if(i%4==0) playerGrid.getRowConstraints().add(con);
            playerGrid.add(new Label(loadPlayers.get(i).name), i%4, i/4);
        }
    }

    @Override
    public void showHome(){
        threadRun = false;
        leaveWaitingroom(player);
        mainCtrl.showHome();
    }

    public void leaveWaitingroom(Player player){
        server.leaveWaitingroom(player);
    }
}
