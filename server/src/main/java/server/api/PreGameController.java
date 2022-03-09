package server.api;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.database.PlayerScoreRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/play")
public class PreGameController {
    List<Player> waitingPlayers;

    @Autowired
    public PreGameController(PlayerScoreRepository repo) {
        waitingPlayers = new ArrayList<>();
    }

    /**
     * @param name the name with which the player wants to enter the singleplayer game
     * @return random confirmation for now
     */
    @GetMapping(path = "/single")
    public ResponseEntity<String> playSingle(@RequestParam("name") String name) {
        return ResponseEntity.ok("Hello "  + name);
    }

    /**
     * @param name the name with which the player wants to join the waiting room
     * @return Bad response if name is already taken
     *         Ok response if name is not taken
     */
    @GetMapping(path = "/join")
    public ResponseEntity<String> playMulti(@RequestParam("name") String name) {
        Player player = new Player(name);
        if(waitingPlayers.contains(player))
            return ResponseEntity.badRequest().body("Name is taken!");

        waitingPlayers.add(player);
        return ResponseEntity.ok("Hello " + name);
    }

    /**
     * @return players that are currently in the waiting room
     */
    @GetMapping(path = "/waitingroom")
    public ResponseEntity<List<Player>> playSingle() {
        return ResponseEntity.ok(waitingPlayers);
    }
}
