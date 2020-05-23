package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public class Rook extends Piece {

    String symbol = "R";

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean specificPiecesMovements(Tile currentRookTile, Tile selectedTile, Chessboard board) {
        int vectorH = selectedTile.getRow() - currentRookTile.getRow();
        int vectorV = selectedTile.getColumn() - currentRookTile.getColumn();
        boolean vectorCondition = (vectorH == 0 || vectorV == 0);
        boolean isWayFreeOfPiecesCondition = isWayFreeOfPieces(currentRookTile, selectedTile, board);

        return (vectorCondition && isWayFreeOfPiecesCondition);
    }

    @Override
    public String toString() {
        return "Rook";
    }
}