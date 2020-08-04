package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Color;

@Getter
public class Pawn extends Piece {

    private final String symbol = "P";

    public Pawn(Color color) {
        super(color);
    }
}