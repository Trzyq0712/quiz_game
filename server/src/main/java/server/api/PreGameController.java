package server.api;

import commons.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/play")
public class PreGameController {

    private List<Player> waitingPlayers;
    private ExecutorService pollThreads = Executors.newFixedThreadPool(4);

    public PreGameController() {
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
        Player player = new Player(name);
        if(waitingPlayers.contains(player))
            return ResponseEntity.ok(false);

        waitingPlayers.add(0, player);
        System.out.println("added " + name);
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
    public DeferredResult<List<Player>> updates(@RequestBody List<Player> clientPlayers) {
        DeferredResult<List<Player>> output = new DeferredResult();
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
