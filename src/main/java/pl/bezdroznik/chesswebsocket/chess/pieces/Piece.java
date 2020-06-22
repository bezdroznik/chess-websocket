package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
public abstract class Piece {

    private final Color color;
    public boolean didMove;

    public Piece(Color color) {
        this.color = color;
        this.didMove = false;
    }

    public abstract boolean canPieceDoSpecificMove(Tile currentTile, Tile selectedTile, Chessboard chessboard);

    public enum Color {
        WHITE, BLACK
    }
}