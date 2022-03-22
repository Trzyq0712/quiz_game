package server.api;

import commons.Answer;
import commons.PlayerScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Game;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for accessing scores of players during a game
 */
@RestController
@RequestMapping("/api/currentplayerscore")
public class CurrentPlayerScoreController {

    private Game game;

    @Autowired
    public CurrentPlayerScoreController(Game game) {
        this.game = game;
    }

    /**
     * Returns all player scores currently stored in the database
     *
     * @return - list of all scores
     */
    @GetMapping(path = "")
    public ResponseEntity<List<PlayerScore>> getAll() {
        return ResponseEntity.ok(game.getPlayers());
    }

    /**
     * Get a player current score from the database by id
     *
     * @param name - id of the requested player score
     * @return - the requested player
     */
    @GetMapping(path = "{name}")
    public ResponseEntity<PlayerScore> getByPlayer(@PathVariable("name") String name) {
        if (game.getByName(name) == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(game.getByName(name));
    }

    /**
     * Get the top amount of player results with the highest scores
     *
     * @param amount - the requested number of best results, cannot be negative
     * @return - the list of results, list may be shorter if not enough results
     */
    @GetMapping(path = "top/{amount}")
    public ResponseEntity<List<PlayerScore>> getTop(@PathVariable("amount") int amount) {
        if (amount < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<PlayerScore> topScores = game.getPlayers()
                .stream()
                .sorted(Comparator.comparingInt(ps -> -ps.getScore()))
                .limit(amount)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topScores);
    }


    /**
     * Add a player score to the database
     *
     * @param playerScore - result to be added to the database
     * @return - the PlayerScore that was added
     */
    @PostMapping(path = "")
    public ResponseEntity<PlayerScore> add(@RequestBody PlayerScore playerScore) {
        return ResponseEntity.ok(game.addAPlayer(playerScore));
    }

    /**
     * Awarding points to a player
     *
     * @param name   - name of the player
     * @param amount - amount of points awarded
     * @return the PlayerScore for confirmation
     */
    @PostMapping(path = {"", "/{name}/{amount}"})
    public ResponseEntity<PlayerScore> addPointsToAPlayer(@PathVariable String name, @PathVariable int amount) {
        if (game.getByName(name) == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(game.addPointsToAPlayer(name, amount));
    }

    /**
     * Deletes every temporary PlayerScores in Game
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<Boolean> deleteAll() {
        return ResponseEntity.ok(game.removeAll());
    }

    /**
     * If a player quits the game in the middle of it,
     * then they can be deleted from Game
     *
     * @param name is the id of the player
     */
    @PostMapping(path = {"", "/{name}"})
    public ResponseEntity<Boolean> deleteByName(@PathVariable("name") String name) {
        if (game.getByName(name) == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(game.removeAPlayerWithName(name));
    }

    /**
     * @param answer - the answer coming from the player
     * @return the points granted
     */
    @PostMapping(path = {"", "/grantpoints"})
    public ResponseEntity<Integer> grantPoints(@RequestBody Answer answer) {
        int points = 0;
        if(checkAnswer(answer)) {
            long percentageOfTimeTaken = answer.getTimeToAnswer() / 3000;
            points = 100 +(int) percentageOfTimeTaken * 100;
            //gives 100 points if you answer correctly, and extra points according to how fast it was answered
        }
        return ResponseEntity.ok(points);
    }

    private boolean checkAnswer(Answer answer) {
        return true;
    }

}

