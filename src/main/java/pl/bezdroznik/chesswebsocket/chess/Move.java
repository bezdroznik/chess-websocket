package pl.bezdroznik.chesswebsocket.chess;

import java.util.ArrayList;
import java.util.List;

public class Move {

    Chessboard chessboard;
    Tile currentTile;

    public Move(Chessboard chessboard, Tile currentTile) {
        this.chessboard = chessboard;
        this.currentTile = currentTile;
    }

    List<Tile> possibleMoves = new ArrayList<>();

    public List<Tile> showPossibleMoves (){
        Tile [][]tiles = chessboard.getTiles();
        for (Tile[] row : tiles) {
            for (Tile testingTile : row) {
                if (canPieceMove(testingTile)) {
                    possibleMoves.add(testingTile);
                }
            }
        }
        return possibleMoves;
    }

    private boolean canPieceMove(Tile selectedTile) {
        if (currentTile.getPiece() != null){
            boolean movesCondition = currentTile.getPiece().specificPiecesMovements(currentTile, selectedTile, chessboard);
            return pieceColorCondition(selectedTile) && movesCondition && !Check.isCheck(chessboard, currentTile, selectedTile);
        }
        return false;
    }

    private boolean pieceColorCondition(Tile selectedTile) {
        if (selectedTile.getPiece() == null) {
            return true;
        }
        return currentTile.getPiece().getColor() != selectedTile.getPiece().getColor();
    }

// chuj wie gdzie to dac
    public static boolean isWayFreeOfPieces(Tile currentPieceTile, Tile selectedTile, Chessboard board) {
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
                if (rows[pieceRow][pieceColumn].getPiece() != null) {
                    return false;
                }
        }
        return true;
    }

    public void fillTileWithSelectedPiece(List<Tile> possibleMoves, Tile selectedTile ){
        if (possibleMoves.contains(selectedTile)) {
            selectedTile.setPiece(currentTile.getPiece());
            currentTile.setPiece(null);
        }
    }
}
