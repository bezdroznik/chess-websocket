package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

import java.util.ArrayList;
import java.util.List;

public class Move {

    private final Chessboard chessboard;
    private final Tile currentTile;
    private Tile selectedTile;
    private final List<Tile> possibleMoves = new ArrayList<>();


    public Move(Chessboard chessboard, Tile currentTile) {
        this.chessboard = chessboard;
        this.currentTile = currentTile;
    }

    public List<Tile> showPossibleMoves (){
        Tile [][]tiles = chessboard.getTiles();
        for (Tile[] row : tiles) {
            for (Tile testingTile : row) {
                selectedTile = testingTile;
                Castling castling = new Castling(chessboard, currentTile, selectedTile);
                if (canPieceMove() || castling.canCastling() || EnPassant.canEnPassan(chessboard, currentTile, selectedTile)) {
                    possibleMoves.add(testingTile);
                }
            }
        }
        return possibleMoves;
    }

    private boolean canPieceMove() {
            boolean movesCondition = currentTile.getPiece().canPieceDoSpecificMove(currentTile, selectedTile, chessboard);
            return pieceColorCondition() && movesCondition && Check.willBeNoCheckAfterMove(chessboard, currentTile, selectedTile);
    }

    private boolean pieceColorCondition() {
        if (selectedTile.getPiece() == null) {
            return true;
        }
        return currentTile.getPiece().getColor() != selectedTile.getPiece().getColor();
    }

    public static boolean isWayFreeOfPieces(Tile currentPieceTile, Tile selectedTile, Chessboard board) { // gdzie to dac, oddzielna klasa?
        int pieceRow = currentPieceTile.getRow();
        int pieceColumn = currentPieceTile.getColumn();
        int vectorH = selectedTile.getRow() - pieceRow;
        int vectorV = selectedTile.getColumn() - pieceColumn;
        int vectorHToIncrement = setVectorToIncrement(vectorH);
        int vectorVToIncrement = setVectorToIncrement(vectorV);

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

    private static int setVectorToIncrement(int vector){
        int vectorToIncrement;
        if (vector == 0){
            vectorToIncrement = 0;
        } else {
            vectorToIncrement = Math.abs(vector)/vector;
        }
        return vectorToIncrement;
    }

}