package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Color;

@Getter
public class Knight extends Piece {

    private final String symbol = "N";

    public Knight(Color color) {
        super(color);
    }

}