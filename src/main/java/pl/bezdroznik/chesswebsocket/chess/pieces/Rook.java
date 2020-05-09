package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rook extends Piece {

    String symbol = "R";

    public Rook(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "Rook";
    }
}
