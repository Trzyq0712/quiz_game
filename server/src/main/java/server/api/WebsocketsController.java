package server.api;

import commons.Emote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;


import java.net.http.HttpClient;



@RestController
public class WebsocketsController {

    @Autowired
    PreGameController preGameController;

    @MessageMapping("/waitingroom/start") // /app/waitingroom/start
    @SendTo("/topic/waitingroom/start")
    public Boolean startGame(Boolean b){
        //make game
        preGameController.startGame();
        return b;
    }


    @MessageMapping("/emote/{id}") // /app/emote/id
    @SendTo("/topic/emote/{id}")
    public Emote emote(Emote e){
        return e;
    }
}
