package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
public class Knight extends Piece {

    private final String symbol = "N";

    public Knight(Color color) {
        super(color);
    }

    @Override
    public boolean canPieceDoSpecificMove(Tile currentKnightTile, Tile selectedTile, Chessboard chessboard) {
        int rowShift = selectedTile.getRow() - currentKnightTile.getRow();
        int columnShift = selectedTile.getColumn() - currentKnightTile.getColumn();

        if (Math.abs(columnShift) == 1 && Math.abs(rowShift) == 2){
            return true;
        }
        return Math.abs(columnShift) == 2 && Math.abs(rowShift) == 1;
    }

    @Override
    public String toString() {
        return "Knight";
    }
}