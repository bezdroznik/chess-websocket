package pl.bezdroznik.chesswebsocket.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.bezdroznik.chesswebsocket.chess.GameState;
import pl.bezdroznik.chesswebsocket.chess.SelectedTile;

import javax.servlet.http.HttpSessionListener;
import javax.websocket.OnOpen;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class GameController {

    @MessageMapping("/newgame")
    @SendTo("/topic/board")
    public GameState newGame(NewGameRequest newGameRequest, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        GameState gameState = new GameState();
        headerAccessor.getSessionAttributes().put("gamestate", gameState);
        System.out.println("new game created");
        return gameState;
    }

    @MessageMapping("/move")
    @SendTo("/topic/board")
    public GameState move(SelectedTile selectedTile, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        GameState gameState = (GameState) headerAccessor.getSessionAttributes().get("gamestate");
        gameState.analyze(selectedTile);
        return gameState;
    }
}