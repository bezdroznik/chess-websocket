package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Check;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public class Knight extends Piece {

    String symbol = "N";

    public Knight(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "Knight";
    }

    @Override
    public boolean canMove(Tile currentKnightTile, Tile selectedTile, Chessboard board) {
        boolean pieceColorCondition = color != selectedTile.getPiece().getColor();
        boolean movesCondition = movesCondition(currentKnightTile, selectedTile);
        boolean checkCondition = Check.isCheck(board, selectedTile, selectedTile.getPiece());
        if (pieceColorCondition && movesCondition && checkCondition) {
            return true;
        }
        return false;
    }

    private boolean movesCondition(Tile currentKnightTile, Tile selectedTile) {
        int selectedTileRow = selectedTile.getRow();
        int selectedTileColumn = selectedTile.getColumn();
        int knightRow = currentKnightTile.getRow();
        int knightColumn = currentKnightTile.getColumn();
        if (Math.abs(selectedTileColumn - knightColumn) == 1 && Math.abs(selectedTileRow - knightRow) == 2){
            return true;
        } else if (Math.abs(selectedTileColumn - knightColumn) == 2 && Math.abs(selectedTileRow - knightRow) == 1){
            return true;
        }
        return false;
    }
}
