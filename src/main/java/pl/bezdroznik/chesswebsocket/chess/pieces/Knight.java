package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Knight extends Piece {

    String symbol = "N";

    public Knight(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "Knight";
    }
}
