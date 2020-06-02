package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public class King extends Piece {

    String symbol = "K";

    public King(Color color) {
        super(color);
    }

    @Override
    public boolean specificPiecesMovements(Tile currentKingTile, Tile selectedTile, Chessboard chessboard) {
        int rowShift = selectedTile.getRow() - currentKingTile.getRow();
        int columnShift = selectedTile.getColumn() - currentKingTile.getColumn();

        return Math.abs(columnShift) <= 1 && Math.abs(rowShift) <= 1;
    }

    @Override
    public String toString() {
        return "King";
    }
}