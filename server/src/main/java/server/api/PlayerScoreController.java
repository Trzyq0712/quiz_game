package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import server.database.PlayerScoreRepository;
import commons.PlayerScore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/playerscore")
public class PlayerScoreController {

    private final PlayerScoreRepository repo;

    @Autowired
    public PlayerScoreController(PlayerScoreRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns all player scores stored in the database
     * @return - list of all scores
     */
    @GetMapping(path = "")
    public ResponseEntity<List<PlayerScore>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    /**
     * Get a player score from the database by id
     * @param id - id of the requested player score
     * @return - the requested player
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<PlayerScore> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

    /**
     * Get the top amount of player results with the highest scores
     * @param amount - the requested number of best results, cannot be negative
     * @return - the list of results, list may be shorter if not enough results
     */
    @GetMapping(path = "top/{amount}")
    public ResponseEntity<List<PlayerScore>> getTop(@PathVariable("amount") int amount) {
        if (amount < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<PlayerScore> topScores = repo.findAll(
                Sort.by(Sort.Direction.DESC, "score"));
        topScores = topScores.stream()
                .limit(amount)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topScores);
    }

    /**
     * Add a player score to the database
     * @param playerScore - result to be added to the database
     * @return - the score that was created
     */
    @PostMapping(path = "")
    public ResponseEntity<PlayerScore> add(@RequestBody PlayerScore playerScore) {
        repo.save(playerScore);
        return ResponseEntity.ok(playerScore);
    }

}
