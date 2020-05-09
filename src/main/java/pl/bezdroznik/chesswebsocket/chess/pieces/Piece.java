package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Piece {

    public Piece(Color color) {
        this.color = color;
    }

    public enum Color {
        WHITE, BLACK
    }

    Color color;
}
