package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public abstract class Piece {

    Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract boolean canMove(Tile currentTile, Tile tile, Chessboard chessboard);

    public enum Color {
        WHITE, BLACK
    }
}
