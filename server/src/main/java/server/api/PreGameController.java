package server.api;

import com.fasterxml.jackson.databind.ser.Serializers;
import commons.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.ActivityService;
import server.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static commons.Config.*;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/play")
public class PreGameController extends BaseController {

    private Long gameID = 0L;
    private List<PlayerScore> waitingPlayers;
    private ExecutorService pollThreads = Executors.newFixedThreadPool(4);
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
    public ResponseEntity<Boolean> playMulti(@RequestBody String name) {
        PlayerScore player = new PlayerScore(name, 0);
        if(waitingPlayers.contains(player))
            return ResponseEntity.ok(false);
        waitingPlayers.add(0, player);
        System.out.println("added " + name);
        return ResponseEntity.ok(true);
    }

    @GetMapping(path = "/getGameID")
    public ResponseEntity<Long> supplyGameID() {
        return ResponseEntity.ok(gameID);
    }

    //@GetMapping(path = "/startMultiplayer")
    public void startGame() {
        Game game = new Game();
        game.getPlayers().addAll(waitingPlayers);
        //waitingPlayers.clear();
        for (int i = 0; i < totalQuestions; i++) {
            game.getQuestionTypes().put(i, (int) (Math.random() * 3));
            game.getActivities().put(i, activityService.get3Activities());
        }
        ongoingGames.put(game.getGameId(), game);
        gameID++;
        return;
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
    public ResponseEntity<List<PlayerScore>> getWaitingroom() {
        return ResponseEntity.ok(waitingPlayers);
    }

    /**
     * Removes players from the waiting list
     * @param player player to be removed
     * @return players that are currently in the waiting room
     */
    @PostMapping(path = "/waitingroom/leave")
    public ResponseEntity<Boolean> leaveWaitingroom(@RequestBody Player player) {
        waitingPlayers.remove(player);
        return ResponseEntity.ok(true);
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
    public DeferredResult<List<PlayerScore>> updates(@RequestBody List<Player> clientPlayers) {
        DeferredResult<List<PlayerScore>> output = new DeferredResult();
        System.out.println(waitingPlayers.equals(clientPlayers));
        pollThreads.execute(() -> {
            while(waitingPlayers.equals(clientPlayers)){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            output.setResult(waitingPlayers);
        });
        return output;
    }
}
