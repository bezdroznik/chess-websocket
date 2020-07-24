package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Color;

@Getter
public class King extends Piece {

    private final String symbol = "K";

    public King(Color color) {
        super(color);
    }
}