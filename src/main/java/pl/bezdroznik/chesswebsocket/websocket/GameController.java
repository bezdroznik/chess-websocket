package pl.bezdroznik.chesswebsocket.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.bezdroznik.chesswebsocket.chess.GameState;
import pl.bezdroznik.chesswebsocket.chess.SelectedTile;


@Controller
public class GameController {

    @MessageMapping("/newgame")
    @SendTo("/topic/board")
    public GameState newGame(StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("new " + headerAccessor);
        System.out.println("new user " + headerAccessor.getUser());

        GameState gameState = new GameState();
        headerAccessor.getSessionAttributes().put("gamestate", gameState);
        JoinGame.headerAccessor= headerAccessor;
        System.out.println("new game created");
        return gameState;
    }

    @MessageMapping("/joingame")
    @SendTo("/topic/board")
    public GameState joinGame(StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("join " + headerAccessor);
        System.out.println("join user " + headerAccessor.getUser().getName());
        GameState gameState = (GameState) JoinGame.headerAccessor.getSessionAttributes().get("gamestate");
        headerAccessor.getSessionAttributes().put("gamestate", gameState);
        return gameState;
    }

    @MessageMapping("/selectPiece")
    @SendTo("/topic/board")
    public GameState select(SelectedTile selectedTile, StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("sel " + headerAccessor);
        if (headerAccessor.getSessionAttributes().get("gamestate") != null) {
            GameState gameState = (GameState) headerAccessor.getSessionAttributes().get("gamestate");
            gameState.analyze(selectedTile, headerAccessor.getUser().getName());
            return gameState;
        }
        return null;
    }

    @MessageMapping("/promotion")
    @SendTo("/topic/board")
    public GameState promotion(SelectedTile selectedTile, StompHeaderAccessor headerAccessor) throws Exception {
        GameState gameState = (GameState) headerAccessor.getSessionAttributes().get("gamestate");
        gameState.promotion(selectedTile);
        return gameState;
    }

}