package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import server.database.PlayerRepository;
import commons.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/playerscore")
public class PlayerScoreController {

    private final PlayerRepository repo;

    @Autowired
    public PlayerScoreController(PlayerRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns all player scores stored in the database.
     *
     * @return list of all scores.
     */
    @GetMapping(path = "")
    public ResponseEntity<List<Player>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    /**
     * Get a player score from the database by id.
     *
     * @param id id of the requested player score.
     * @return the requested player.
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<Player> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

    /**
     * Get the top amount of player results with the highest scores.
     *
     * @param amount the requested number of best results, cannot be negative.
     * @return the list of results, list may be shorter if not enough results.
     */
    @GetMapping(path = "top/{amount}")
    public ResponseEntity<List<Player>> getTop(@PathVariable("amount") int amount) {
        if (amount < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Player> topScores = repo.findAll(
                Sort.by(Sort.Direction.DESC, "score"));
        topScores = topScores.stream()
                .limit(amount)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topScores);
    }

    /**
     * Add a player score to the database.
     *
     * @param Player result to be added to the database.
     * @return the score that was created.
     */
    @PostMapping(path = "")
    public ResponseEntity<Player> add(@RequestBody Player Player) {
        if(Player.getPlayerName()==null){
            return ResponseEntity.badRequest().build();
        }
        Player p = repo.save(Player);
        return ResponseEntity.ok(p);
    }

}
