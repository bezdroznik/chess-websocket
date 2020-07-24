package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Color;

@Getter
public class Piece {

    private final Color color;
    public boolean didMove;

    public Piece(Color color) {
        this.color = color;
        this.didMove = false;
    }
}