package server.api;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.PlayerScore;
import server.Game;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for accessing scores of players
 */
@RestController
@RequestMapping("/api/currentplayerscore")
public class CurrentPlayerScoreController {

    private final Game repo;

    public CurrentPlayerScoreController(Game repo) {
        this.repo = repo;
    }

//    @Autowired
//    public CurrentPlayerScoreController(PlayerTempScoreRepository repo) {
//        this.repo = repo;
//    }

    /**
     * Returns all player scores currently stored in the database
     * @return - list of all scores
     */
    @GetMapping(path = "")
    public List<PlayerScore> getAll() {
        return repo.getPlayers();
    }

    /**
     * Get a player current score from the database by id
     * @param id - id of the requested player score
     * @return - the requested player
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<PlayerScore> getByPlayer(@PathVariable("id") long id) {
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

    /**
     * Deletes every temporary scores in the repository
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<List<PlayerScore>> deleteAll() {
        repo.deleteAll();
        return ResponseEntity.ok(repo.findAll());
    }

    /**
     * If a player quits the game in the middle of it,
     * then they can be deleted from the repository
     * @param id is the id of the player
     */
    @PostMapping(path = { "", "/{id}" })
    public ResponseEntity<PlayerScore> deleteByID(@PathVariable("id") long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(repo.getById(id));
    }

}

