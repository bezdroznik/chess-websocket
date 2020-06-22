package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.movesRules.Move;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
public class Queen extends Piece {

    private final String symbol = "Q";

    public Queen(Color color) {
        super(color);
    }

    @Override
    public boolean canPieceDoSpecificMove(Tile currentQueenTile, Tile selectedTile, Chessboard board) {
        int vectorH = selectedTile.getRow() - currentQueenTile.getRow();
        int vectorV = selectedTile.getColumn() - currentQueenTile.getColumn();
        int resultant = Math.abs(vectorV) - Math.abs(vectorH);
        boolean vectorCondition = (resultant == 0 || vectorH == 0 || vectorV == 0);

        return (vectorCondition && Move.isWayFreeOfPieces(currentQueenTile, selectedTile, board));
    }

    @Override
    public String toString() {
        return "Queen";
    }
}