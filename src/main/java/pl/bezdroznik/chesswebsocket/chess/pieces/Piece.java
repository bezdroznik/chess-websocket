package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public abstract class Piece {

    Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public abstract boolean specificPiecesMovements(Tile currentTile, Tile selectedTile, Chessboard chessboard);

    boolean isWayFreeOfPieces(Tile currentPieceTile, Tile selectedTile, Chessboard board) { // chuj wie gdzie to dac, oddzielna klasa?
        int pieceRow = currentPieceTile.getRow();
        int pieceColumn = currentPieceTile.getColumn();
        int vectorH = selectedTile.getRow() - pieceRow;
        int vectorV = selectedTile.getColumn() - pieceColumn;
        int vectorHToIncrement;
        int vectorVToIncrement;

        if (vectorH == 0){
            vectorHToIncrement = 0;
        } else {
            vectorHToIncrement = Math.abs(vectorH)/vectorH;
        }
        if (vectorV == 0){
            vectorVToIncrement = 0;
        } else {
            vectorVToIncrement = Math.abs(vectorV)/vectorV;
        }

        Tile[][] rows = board.getTiles();
        while (pieceRow != selectedTile.getRow() - vectorHToIncrement || pieceColumn != selectedTile.getColumn() - vectorVToIncrement){
            pieceRow += vectorHToIncrement;
            pieceColumn += vectorVToIncrement;
            if (rows[pieceRow][pieceColumn].getPiece() != null){
                return false;
            }
        }
        return true;
    }

    public enum Color {
        WHITE, BLACK
    }
}