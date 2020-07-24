package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Color;

@Getter
public class Rook extends Piece {

    private final String symbol = "R";

    public Rook(Color color) {
        super(color);
    }

}