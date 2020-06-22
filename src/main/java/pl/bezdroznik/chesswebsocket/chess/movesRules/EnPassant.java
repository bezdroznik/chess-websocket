package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.Pawn;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

public class EnPassant {
    private static Tile tileForEnPassanMoveAgainstWhitePlayer;
    private static Tile tileForEnPassanMoveAgainstBlackPlayer;

    private static void updateEnPassantState(Chessboard chessboard, Tile currentTile, Tile selectedTile){
        if (currentTile.getPiece() instanceof Pawn && !currentTile.getPiece().didMove){
            Tile[][] tiles = chessboard.getTiles();
            if (currentTile.getRow() == 1 && selectedTile.getRow() == 3){
                tileForEnPassanMoveAgainstWhitePlayer = tiles[2][currentTile.getColumn()];
            } else if(currentTile.getRow() == 6 && selectedTile.getRow() == 4) {
                tileForEnPassanMoveAgainstBlackPlayer = tiles[5][currentTile.getColumn()];
            }
        } else if (currentTile.getPiece().getColor() == Piece.Color.WHITE){
            tileForEnPassanMoveAgainstWhitePlayer = null;
        } else{
            tileForEnPassanMoveAgainstBlackPlayer = null;
        }
    }

    public static void enPassanMove(Chessboard chessboard, Tile currentTile, Tile selectedTile) {
        if (canEnPassan(chessboard, currentTile, selectedTile)){
            Tile[][] tiles = chessboard.getTiles();
            tiles[currentTile.getRow()][selectedTile.getColumn()].setPiece(null);
        }
        updateEnPassantState(chessboard, currentTile, selectedTile);
    }

    public static boolean canEnPassan(Chessboard chessboard, Tile currentTile, Tile selectedTile){
        boolean instanceCondition = currentTile.getPiece() instanceof Pawn;
        boolean columnShiftCondition = Math.abs(Math.abs(currentTile.getColumn()) - Math.abs(selectedTile.getColumn())) == 1;
        return instanceCondition && columnShiftCondition && colorCondition(currentTile, selectedTile)
                && Check.willBeNoCheckAfterMove(chessboard, currentTile, selectedTile);
    }

    private static boolean colorCondition(Tile currentTile, Tile selectedTile){
        if (currentTile.getPiece().getColor() == Piece.Color.WHITE){
            boolean rowCondition = currentTile.getRow() == 4 && selectedTile.getRow() == 5;
            return rowCondition && tileForEnPassanMoveAgainstBlackPlayer == selectedTile;
        } else {
            boolean rowCondition = currentTile.getRow() == 3 && selectedTile.getRow() == 2;
            return rowCondition && tileForEnPassanMoveAgainstWhitePlayer == selectedTile;
        }
    }

}