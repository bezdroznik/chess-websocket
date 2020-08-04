package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Color;

@Getter
public class Bishop extends Piece {

    private final String symbol = "B";

    public Bishop(Color color) {
        super(color);
    }
}