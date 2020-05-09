package pl.bezdroznik.chesswebsocket.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.bezdroznik.chesswebsocket.chess.GameState;

import javax.websocket.OnOpen;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class GameController {

    @MessageMapping("/newgame")
    @SendTo("/topic/board")
    public GameState newGame(NewGameRequest newGameRequest) throws Exception {
        return new GameState();
    }
}