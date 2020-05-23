package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public class Bishop extends Piece {

    String symbol = "B";

    public Bishop(Piece.Color color) {
        super(color);
    }

    @Override
    public boolean specificPiecesMovements(Tile currentBishopTile, Tile selectedTile, Chessboard board) {
        int vectorH = selectedTile.getRow() - currentBishopTile.getRow();
        int vectorV = selectedTile.getColumn() - currentBishopTile.getColumn();
        int resultant = Math.abs(vectorV) - Math.abs(vectorH);
        boolean isWayFreeOfPieces = isWayFreeOfPieces(currentBishopTile, selectedTile, board);

        return (resultant == 0 && isWayFreeOfPieces);
    }


            @Override
            public String toString () {
                return "Bishop";
            }
        }