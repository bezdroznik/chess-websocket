package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Color;

@Getter
public class Queen extends Piece {

    private final String symbol = "Q";

    public Queen(Color color) {
        super(color);
    }

}