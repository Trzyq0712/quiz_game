package server.api;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/play")
public class PreGameController {
    private List<Player> waitingPlayers;

    @Autowired
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
     * @return Bad response if name is already taken
     *         Ok response if name is not taken
     */
    @PostMapping(path = "/join")
    public ResponseEntity<Boolean> playMulti(@RequestBody String name) {
        Player player = new Player(name);
        if(waitingPlayers.contains(player))
            return ResponseEntity.ok(false);

        waitingPlayers.add(player);
        return ResponseEntity.ok(true);
    }

    /**
     * @return players that are currently in the waiting room
     */
    @GetMapping(path = "/waitingroom")
    public ResponseEntity<List<Player>> getWaitingroom() {
        return ResponseEntity.ok(waitingPlayers);
    }
}
