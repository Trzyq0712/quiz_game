package server.api;

import commons.Person;
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
public class PlayerController {
    List<Person> waitingPlayers;


    @Autowired
    public PlayerController(PlayerScoreRepository repo) {
        waitingPlayers = new ArrayList<>();
    }

    @GetMapping(path = "/single")
    public ResponseEntity<String> playSingle(@RequestParam("fname") String fName, @RequestParam("lname") String lName) {
        return ResponseEntity.ok("Hello " + fName + " " + lName);
    }

    @GetMapping(path = "/multi")
    public ResponseEntity<String> playMulti(@RequestParam("fname") String fName, @RequestParam("lname") String lName) {
        Person player = new Person(fName, lName);
        if(waitingPlayers.contains(player))
            return ResponseEntity.badRequest().body("Name is taken");

        waitingPlayers.add(player);
        return ResponseEntity.ok("Hello " + fName + " " + lName);
    }

    @GetMapping(path = "/waitingroom")
    public ResponseEntity<List<Person>> playSingle() {
        return ResponseEntity.ok(waitingPlayers);
    }
}
