package server.api;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.ActivityService;
import server.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static commons.Config.*;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/play")
public class PreGameController extends BaseController {


    //private Long gameID = 0L;
    private List<Player> waitingPlayers;
    private ExecutorService pollThreads = Executors.newFixedThreadPool(4);
    private HashMap<Long, Game> ongoingGames;

    @Autowired
    public PreGameController(ActivityService activityService) {
        super(activityService);
        waitingPlayers = new ArrayList<>();
        ongoingGames = new HashMap<>();
    }

    public List<Player> getWaitingPlayers() {
        return waitingPlayers;
    }

    public ExecutorService getPollThreads() {
        return pollThreads;
    }

    public HashMap<Long, Game> getOngoingGames() {
        return ongoingGames;
    }

    /**
     * @param player the name with which the player wants to enter the singleplayer game
     * @return random confirmation for now
     */
    @PostMapping(path = "/single")
    public ResponseEntity<Boolean> playSingle(@RequestBody Player player) {
        return ResponseEntity.ok(true);
    }

    /**
     * @param player the name with which the player wants to join the waiting room
     * @return false if name is taken
     *         true if name is not taken
     */
    @PostMapping(path = "/join")
    public ResponseEntity<Boolean> playMulti(@RequestBody Player player) {
        //Player player = new Player(name, 0);
        for (Player p : waitingPlayers) {
            if (p.getPlayerName().equals(player.getPlayerName())) {
                return ResponseEntity.ok(false);
            }
        }
        waitingPlayers.add(player);
        System.out.println("added " + player.getPlayerName() + "to the waiting room");
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "/getGameID")
    public ResponseEntity<Long> supplyGameID() {
        return ResponseEntity.ok(Game.gameCounter);
    }

    @GetMapping(path = "/start")
    public Long startMultiplayerGame() {
        Game game = new Game();
        for (Player p : waitingPlayers) {
            game.addAPlayer(p);
        }
        waitingPlayers.clear();
        for (int i = 0; i < totalQuestions; i++) {
            game.getQuestionTypes().put(i, (int) (Math.random() * 4));
            game.getActivities().put(i, activityService.get4Activities());
        }
        ongoingGames.put(game.getGameId(), game);
        return game.getGameId();
    }

    @PostMapping(path = "/start/single") //this should ONLY be called by singleplayer!
    public ResponseEntity<Long> startSinglePLayerGame(@RequestBody Player player) {
        Game game = new Game();
        game.addAPlayer(player);
        for (int i = 0; i < totalQuestions; i++) {
            game.getQuestionTypes().put(i, (int) (Math.random() * 4));
            game.getActivities().put(i, activityService.get4Activities());
        }
        ongoingGames.put(game.getGameId(), game);
        return ResponseEntity.ok(game.getGameId());
    }

    @PostMapping(path = "/getQuestionType")
    public ResponseEntity<Integer> getQuestionType(@RequestBody ClientInfo clientInfo) {
        int currentQuestion = clientInfo.getCurrentQuestion();
        Long gameID = clientInfo.getGameID();
        int questionType = ongoingGames.get(gameID).getQuestionTypes().get(currentQuestion);
        return ResponseEntity.ok(questionType);
    }

    @PostMapping(path = "/get4Activities")
    public ResponseEntity<ActivityList> get4Activities(@RequestBody ClientInfo clientInfo) {
        int currentQuestion = clientInfo.getCurrentQuestion();
        Long gameID = clientInfo.getGameID();
        List<Activity> activities = ongoingGames.get(gameID).getActivities().get(currentQuestion);
        ActivityList al = new ActivityList(activities);
        return ResponseEntity.ok(al);
    }

    @PostMapping(path = "/getSingleActivity")
    public ResponseEntity<Activity> getSingleActivity(@RequestBody ClientInfo clientInfo) {
        int currentQuestion = clientInfo.getCurrentQuestion();
        Long gameID = clientInfo.getGameID();
        Activity activity = ongoingGames.get(gameID).getActivities().get(currentQuestion).get(0);
        return ResponseEntity.ok(activity);
    }

    @PostMapping(path = "/getPlayers")
    public ResponseEntity<PlayerList> getPlayers(@RequestBody ClientInfo clientInfo) {
        Long gameID = clientInfo.getGameID();
        Game currentGame = ongoingGames.get(gameID);
        List<Player> listOfPlayers = currentGame.getPlayers();
        PlayerList playerList = new PlayerList(listOfPlayers);
        return ResponseEntity.ok(playerList);
    }

    @PostMapping(path = "/updateScore")
    public ResponseEntity<Boolean> updateScore(@RequestBody ClientInfo clientInfo) {
        Player player = clientInfo.getPlayer();
        Long gameID = clientInfo.getGameID();
        Game currentGame = ongoingGames.get(gameID);
        currentGame.updateScore(player);
        return ResponseEntity.ok(true);
    }

    /**
     * @return players that are currently in the waiting room
     */
    @GetMapping(path = "/waitingroom")
    public ResponseEntity<List<Player>> getWaitingroom() {
        return ResponseEntity.ok(waitingPlayers);
    }

    /**
     * Removes players from the waiting list
     * @param player player to be removed
     * @return players that are currently in the waiting room
     */
    @PostMapping(path = "/waitingroom/leave")
    public ResponseEntity<Boolean> leaveWaitingroom(@RequestBody Player player) {
        return ResponseEntity.ok(waitingPlayers.remove(player));
    }

    /**
     * @param clientPlayers The players that the client sees
     * @return The players that the server has
     * This method checks if the client and server waiting room players are the same. If
     * not then it sends over the server version for the client. Tried to do it with long
     * polling. The threads are there to hopefully make it non-blocking. For small scale it
     * seems that it works. Haven't tested it with more than 4 clients at the same time yet though.
     * Read online that DeferredResult is better for handling poll requests.
     */
    @PostMapping(path = "/waitingroom/poll")
    public DeferredResult<List<Player>> updates(@RequestBody List<Player> clientPlayers) {
        DeferredResult<List<Player>> output = new DeferredResult();
        System.out.println(waitingPlayers.equals(clientPlayers));
        pollThreads.execute(() -> {
            while(waitingPlayers.equals(clientPlayers)){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            output.setResult(waitingPlayers);
        });
        return output;
    }
}
