package server.api;

import commons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.ActivityService;
import server.Game;
import server.api.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static commons.Config.*;

/**
 * Controller for testing network connections with clients.
 */
@RestController
@RequestMapping("/api/connection")
public class ConnectionController extends BaseController {


    @Autowired
    public ConnectionController(ActivityService activityService) {
        super(activityService);
    }


    /*@PostMapping(path = "/join")
    public ResponseEntity<Boolean> playMulti(@RequestBody Player player) {
        return ResponseEntity.ok(true);
    }*/

    @GetMapping(path = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

}