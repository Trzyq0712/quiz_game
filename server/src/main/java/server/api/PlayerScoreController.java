package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import server.database.PlayerScoreRepository;
import commons.PlayerScore;

import java.util.List;

@RestController
@RequestMapping("/api/playerscore")
public class PlayerScoreController {

    private final PlayerScoreRepository repo;

    public PlayerScoreController(PlayerScoreRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public ResponseEntity<List<PlayerScore>> getAll() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerScore> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.getById(id));
    }

}
