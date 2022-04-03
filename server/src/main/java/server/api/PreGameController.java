package server.api;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.ActivityService;
import server.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static commons.Config.*;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/play")
public class PreGameController extends BaseController {

    //private Long gameID = 0L;
    private List<Player> waitingPlayers;
    private HashMap<Long, Game> ongoingGames = new HashMap<>();//maps gameID to actual Game instance

    @Autowired
    public PreGameController(ActivityService activityService) {
        super(activityService);
        waitingPlayers = new ArrayList<>();
    }

    /**
     * @param name the name with which the player wants to enter the singleplayer game
     * @return random confirmation for now
     */
    @PostMapping(path = "/single")
    public ResponseEntity<Boolean> playSingle(@RequestBody String name) {
        return ResponseEntity.ok(true);
    }

    /**
     * @param name the name with which the player wants to join the waiting room
     * @return false if name is taken
     *         true if name is not taken
     */
    @PostMapping(path = "/join")
    public synchronized ResponseEntity<Boolean> playMulti(@RequestBody String name) {
        Player player = new Player(name, 0);
        if(waitingPlayers.contains(player)) return ResponseEntity.ok(false);
        waitingPlayers.add(player);
        System.out.println("added " + name + "to the waiting room");
        notifyAll();
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "/getGameID")
    public ResponseEntity<Long> supplyGameID() {
        return ResponseEntity.ok(Game.gameCounter);
    }

    @GetMapping(path = "/start") //this should ONLY be called by singleplayer!
    public ResponseEntity<Boolean> startGame() {
        Game game = new Game();
        game.getPlayers().addAll(waitingPlayers);
        waitingPlayers.clear();
        for (int i = 0; i < totalQuestions; i++) {
            game.getQuestionTypes().put(i, (int) (Math.random() * 3));
            game.getActivities().put(i, activityService.get3Activities());
        }
        ongoingGames.put(game.getGameId(), game);
        return ResponseEntity.ok(true);
    }

    @PostMapping(path = "/getQuestionType")
    public ResponseEntity<Integer> getQuestionType(@RequestBody ClientInfo clientInfo) {
        int currentQuestion = clientInfo.getCurrentQuestion();
        Long gameID = clientInfo.getGameID();
        int questionType = ongoingGames.get(gameID).getQuestionTypes().get(currentQuestion);
        return ResponseEntity.ok(questionType);
    }

    @PostMapping(path = "/get3Activities")
    public ResponseEntity<ActivityList> get3Activities(@RequestBody ClientInfo clientInfo) {
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
    public synchronized ResponseEntity<Boolean> leaveWaitingroom(@RequestBody Player player) {
        notifyAll();
        return ResponseEntity.ok(waitingPlayers.remove(player));
    }

    /**
     * makes the response thread sleep until it is notified by either a new player joining or leaving
     *      * the waiting room. Once it is notified the return fires off.
     * @return The players that the server has
     */
    @PostMapping(path = "/waitingroom/poll")
    @GetMapping(path = "/waitingroom/poll")
    public synchronized ResponseEntity<List<Player>> updates() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(waitingPlayers);
    }
}
