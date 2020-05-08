package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

@Getter
@Setter
public class Tile {

    private Color color;
    private Piece piece;

    private Tile(Color color) {
        this.color = color;
    }

    private enum Color {
        WHITE, BLACK
    }

    public static Tile whiteTile() {
        return new Tile(Color.WHITE);
    }

    public static Tile blackTile() {
        return new Tile(Color.BLACK);
    }

    @Override
    public String toString() {
        if (piece != null) {
            return piece.toString();
        }
        return color.toString();
    }
}

