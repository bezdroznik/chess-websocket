package pl.bezdroznik.chesswebsocket.chess;

public class Player {
    public final String name;
    public final Color color;

    public Player(String playerName, Color color) {
        this.name = playerName;
        this.color = color;
    }
}
