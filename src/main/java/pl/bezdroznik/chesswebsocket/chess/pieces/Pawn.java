package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pawn extends Piece {

    String symbol = "P";

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
