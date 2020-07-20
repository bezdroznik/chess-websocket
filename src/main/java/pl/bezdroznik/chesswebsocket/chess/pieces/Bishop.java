package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.movesRules.Move;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
public class Bishop extends Piece {

    private final String symbol = "B";

    public Bishop(Piece.Color color) {
        super(color);
    }

    @Override
    public boolean canPieceDoSpecificMove(Tile currentBishopTile, Tile selectedTile, Chessboard board) {
        int vectorH = selectedTile.getRow() - currentBishopTile.getRow();
        int vectorV = selectedTile.getColumn() - currentBishopTile.getColumn();
        int resultant = Math.abs(vectorV) - Math.abs(vectorH);

        return (resultant == 0 && Move.isWayFreeOfPieces(currentBishopTile, selectedTile, board));
    }
}