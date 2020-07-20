package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.movesRules.Move;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
public class Rook extends Piece {

    private final String symbol = "R";

    public Rook(Color color) {
        super(color);
    }

    @Override
    public boolean canPieceDoSpecificMove(Tile currentRookTile, Tile selectedTile, Chessboard board) {
        int vectorH = selectedTile.getRow() - currentRookTile.getRow();
        int vectorV = selectedTile.getColumn() - currentRookTile.getColumn();
        boolean vectorCondition = (vectorH == 0 || vectorV == 0);

        return (vectorCondition && Move.isWayFreeOfPieces(currentRookTile, selectedTile, board));
    }
}