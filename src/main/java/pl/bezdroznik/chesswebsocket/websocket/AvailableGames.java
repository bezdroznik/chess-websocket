package pl.bezdroznik.chesswebsocket.websocket;

import pl.bezdroznik.chesswebsocket.chess.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailableGames {

    static List<String> availableGames = new ArrayList<>();

    static Map<String, GameState> gameConnections = new HashMap<>();

}
