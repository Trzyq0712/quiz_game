package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for jokers.
 */
@RestController
@RequestMapping("/api/joker")
public class JokerController {

    /**
     * Currently, the hint joker  can't be fully implemented because the questions aren't implemented yet.
     * @return The string which indicates to the client which answer should be removed.
     */

    @GetMapping(path = "hint")
    public ResponseEntity<String> getHint() {
        List<String> answers = Arrays.asList("a", "b", "c");
        int index = (int) Math.floor(Math.random() * answers.size());
        return ResponseEntity.ok(answers.get(index));
    }


}
