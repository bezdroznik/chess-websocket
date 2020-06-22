package pl.bezdroznik.chesswebsocket.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.bezdroznik.chesswebsocket.chess.GameState;
import pl.bezdroznik.chesswebsocket.chess.SelectedTile;


@Controller
public class GameController {

    // Po co ta klasa NewGameRequest? Spring tego nie wymaga a jak dla mnie to nic to nie robi? Jakaś przyszła funkcjonalność?
    // co robi ten headerAccessor, to jest do tego całego STOMP?
    // to jest tylko do generowania id sesji i jej atrybutów, czy ma jakieś połączenie z frontem?
    // no bo zasięg obiektu headerAccessor jest tylko w metodzie newGame, to gdzie jest on przechowywany, żeby go potem użyć w move?
    @MessageMapping("/newgame")
    @SendTo("/topic/board")
    public GameState newGame(NewGameRequest newGameRequest, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        GameState gameState = new GameState();
        headerAccessor.getSessionAttributes().put("gamestate", gameState);
        System.out.println("new game created");
        return gameState;
    }

    @MessageMapping("/selectPiece")
    @SendTo("/topic/board")
    public GameState select(SelectedTile selectedTile, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        if (headerAccessor.getSessionAttributes().get("gamestate") != null){
            GameState gameState = (GameState) headerAccessor.getSessionAttributes().get("gamestate");
            gameState.analyze(selectedTile);
            return gameState;
        }
        return null;
    }
}