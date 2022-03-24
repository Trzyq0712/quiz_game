package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebsocketsController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebsocketsController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/waitingroom/start") // app/waitingroom/start
    @SendTo("/topic/waitingroom/start")
    public Boolean startGame(Boolean b){
        return b;
    }

    @MessageMapping("/emote/{id}") // app/emote/start
    public void emote(Object o, @DestinationVariable("id") String id){
        this.simpMessagingTemplate.convertAndSend("/topic/emote/{id}",o);
    }
}
