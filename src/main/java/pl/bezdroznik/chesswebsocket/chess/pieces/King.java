package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class King extends Piece {

    String symbol = "K";

    public King(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "King";
    }
}
