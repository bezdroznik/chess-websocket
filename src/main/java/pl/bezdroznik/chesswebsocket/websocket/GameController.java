package pl.bezdroznik.chesswebsocket.websocket;

import com.google.gson.Gson;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.bezdroznik.chesswebsocket.chess.Analyze;
import pl.bezdroznik.chesswebsocket.chess.GameState;
import pl.bezdroznik.chesswebsocket.chess.Player;
import pl.bezdroznik.chesswebsocket.chess.SelectedTile;

import java.util.ArrayList;
import java.util.List;


@Controller
public class GameController {

    @MessageMapping("/newGame")
    @SendTo("/topic/board")
    public String newGame(StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("NEW GAME");
        if (!AvailableGames.availableGames.contains(headerAccessor.getUser().getName())){
            AvailableGames.availableGames.add(headerAccessor.getUser().getName());
        }
        return "waiting for the opponent";
    }

    @MessageMapping("/getGames")
    @SendTo("/topic/games")
    public String getGames(StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("GET GAMES");
        Gson gson = new Gson();
        List<String> list = new ArrayList<>();
            for(int i = 0; i < AvailableGames.availableGames.size(); i++) {
                if (AvailableGames.availableGames.get(i) != headerAccessor.getUser().getName()){
                    list.add(AvailableGames.availableGames.get(i));
                }
            }
        headerAccessor.getSessionAttributes().put("joingame", AvailableGames.availableGames);
        return gson.toJson(list);
    }

    @MessageMapping("/selectPiece")
    @SendTo("/topic/board")
    public GameState select(SelectedTile selectedTile, StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("SELECT PIECE");
        if (headerAccessor.getSessionAttributes().get("gamestate") == null) {
            GameState gameState = AvailableGames.gameConnections.get(headerAccessor.getUser().getName());
            headerAccessor.getSessionAttributes().put("gamestate", gameState);
        }
            GameState gameState = (GameState) headerAccessor.getSessionAttributes().get("gamestate");
            Analyze.analyze(gameState, selectedTile, headerAccessor.getUser().getName());
            return gameState;
    }

    @MessageMapping("/promotion")
    @SendTo("/topic/board")
    public GameState promotion(SelectedTile selectedTile, StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("PROMOTION");
        GameState gameState = (GameState) headerAccessor.getSessionAttributes().get("gamestate");
        Analyze.promotion(gameState, selectedTile);
        return gameState;
    }

    @MessageMapping("/getCurrentPlayer")
    @SendTo("/topic/player")
    public String getPlayer(StompHeaderAccessor headerAccessor) throws Exception {
        System.out.println("GET CURRENT PLAYER");
        if(headerAccessor.getUser() != null){
            return headerAccessor.getUser().getName();
        }
        return null;
    }

    @MessageMapping("/joinGame")
    @SendTo("/topic/board")
    public GameState joinGame(Player opponent, StompHeaderAccessor headerAccessor) throws Exception {
//        String opp = AvailableGames.availableGames.stream().filter(e -> e.equals(opponent.name)).findFirst().toString();

        for (int i = 0; i< AvailableGames.availableGames.size(); i++) {
            if (AvailableGames.availableGames.get(i).equals(opponent.name)){
                String opponentName = AvailableGames.availableGames.get(i);

                GameState gameState = GameState.createGameState(headerAccessor.getUser().getName(), opponentName);

                SelectedTile selectedTile = new SelectedTile();
                selectedTile.setName("B4");
                Analyze.analyze(gameState, selectedTile, headerAccessor.getUser().getName());

                AvailableGames.availableGames.remove(i);
                AvailableGames.gameConnections.put(headerAccessor.getUser().getName(), gameState);
                AvailableGames.gameConnections.put(opponentName, gameState);
                System.out.println("JOIN GAME");
                return gameState;
            }
        }
        System.out.println("JOIN GAME - NULL");
        return null;
    }
}