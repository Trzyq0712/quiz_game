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

    private final Game game;

    public CurrentPlayerScoreController(Game game) {
        this.game = game;
    }

    /**
     * Returns all player scores currently stored in the database
     * @return - list of all scores
     */
    @GetMapping(path = "")
    public ResponseEntity<List<PlayerScore>> getAll() {
        return ResponseEntity.ok(game.getPlayers());
    }

    /**
     * Get a player current score from the database by id
     * @param id - id of the requested player score
     * @return - the requested player
     */
    @GetMapping(path = "{id}")
    public ResponseEntity<PlayerScore> getByPlayer(@PathVariable("id") long id) {
        if (id < 0 || game.getById(id)==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(game.getById(id));
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
        List<PlayerScore> topScores = game.getPlayers().stream()
                .sorted(PlayerScore::getScore);
        topScores = topScores.stream()
                .limit(amount)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topScores);
    }



    /**
     * Add a player score to the database
     * @param playerScore - result to be added to the database
     * @return - the PlayerScore that was added
     */
    @PostMapping(path = "")
    public ResponseEntity<PlayerScore> add(@RequestBody PlayerScore playerScore) {
        return ResponseEntity.ok(game.addAPlayer(playerScore));
    }

    /**
     * Deletes every temporary PlayerScores in Game
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Boolean> deleteAll() {
        return ResponseEntity.ok(game.removeAll());
    }

    /**
     * If a player quits the game in the middle of it,
     * then they can be deleted from Game
     * @param id is the id of the player
     */
    @PostMapping(path = { "", "/{id}" })
    public ResponseEntity<Boolean> deleteByID(@PathVariable("id") long id) {
        if (id < 0 || game.getById(id)==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(game.removeAPlayerWithId(id));
    }

}

