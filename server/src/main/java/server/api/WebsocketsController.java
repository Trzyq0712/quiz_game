package server.api;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketsController {

    @MessageMapping("/waitingroom/start") // app/waitingroom/start
    @SendTo("/topic/waitingroom/start")
    public Boolean startGame(Boolean b){
        return b;
    }

    @MessageMapping("/emote/1") // app/emote/start
    @SendTo("/topic/emote/1")
    public String emote(String s){
        return s;
    }
}
