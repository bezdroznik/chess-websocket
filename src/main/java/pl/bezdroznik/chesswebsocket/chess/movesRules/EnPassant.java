package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Color;
import pl.bezdroznik.chesswebsocket.chess.GameState;
import pl.bezdroznik.chesswebsocket.chess.Position;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.Pawn;

public class EnPassant {

    public static boolean canEnPassan(Position p){
        boolean instanceCondition = p.currentTile.getPiece() instanceof Pawn;
        boolean columnShiftCondition = Math.abs(Math.abs(p.currentTile.getColumn()) - Math.abs(p.selectedTile.getColumn())) == 1;
        return instanceCondition && columnShiftCondition && colorCondition(p)
                && Check.willBeNoCheckAfterMove(p, p.selectedTile);
    }

    private static void updateEnPassantState(GameState gs, Tile selectedTile){
        if (gs.currentTile.getPiece() instanceof Pawn && !gs.currentTile.getPiece().didMove){
            if (gs.currentTile.getRow() == 1 && selectedTile.getRow() == 3){
                gs.tileForEnPassanMoveAgainstWhitePlayer = gs.tiles[2][gs.currentTile.getColumn()];
            } else if(gs.currentTile.getRow() == 6 && selectedTile.getRow() == 4) {
                gs.tileForEnPassanMoveAgainstBlackPlayer = gs.tiles[5][selectedTile.getColumn()];
            }
        } else if (gs.currentTile.getPiece().getColor() == Color.WHITE){
            gs.tileForEnPassanMoveAgainstWhitePlayer = null;
        } else{
            gs.tileForEnPassanMoveAgainstBlackPlayer = null;
        }
    }

    public static void enPassanMove(GameState gs, Tile selectedTile) {
        Position position = new Position(gs, gs.currentTile, selectedTile);
        if (canEnPassan(position)){
            gs.tiles[gs.currentTile.getRow()][selectedTile.getColumn()].setPiece(null);
        }
        updateEnPassantState(gs, selectedTile);
    }

    private static boolean colorCondition(Position p){
        if (p.currentTile.getPiece().getColor() == Color.WHITE){
            boolean rowCondition = p.currentTile.getRow() == 4 && p.selectedTile.getRow() == 5;
            return rowCondition && p.tileForEnPassanMoveAgainstBlackPlayer == p.selectedTile;
        } else {
            boolean rowCondition = p.currentTile.getRow() == 3 && p.selectedTile.getRow() == 2;
            return rowCondition && p.tileForEnPassanMoveAgainstWhitePlayer == p.selectedTile;
        }
    }

}