package pl.bezdroznik.chesswebsocket.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/chatStomp")
    public ChatMessage get(ChatMessage chatMessage, StompHeaderAccessor headerAccessor) {
        System.out.println("chat " + headerAccessor);
        return chatMessage;
    }
}
