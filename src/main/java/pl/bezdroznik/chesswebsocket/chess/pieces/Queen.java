package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public class Queen extends Piece {

    String symbol = "Q";

    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean specificPiecesMovements(Tile currentQueenTile, Tile selectedTile, Chessboard board) {
        int vectorH = selectedTile.getRow() - currentQueenTile.getRow();
        int vectorV = selectedTile.getColumn() - currentQueenTile.getColumn();
        int resultant = Math.abs(vectorV) - Math.abs(vectorH);
        boolean vectorCondition = (resultant == 0 || vectorH == 0 || vectorV == 0);
        boolean isWayFreeOfPiecesCondition = isWayFreeOfPieces(currentQueenTile, selectedTile, board);

        return (vectorCondition && isWayFreeOfPiecesCondition);
    }

    @Override
    public String toString() {
        return "Queen";
    }
}