package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Position;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.King;
import pl.bezdroznik.chesswebsocket.chess.pieces.Rook;

public class Castling {

    public static boolean canCastling(Position p) {
        if (p.currentTile.getPiece() == null || p.selectedTile.getPiece() == null){
            return false;
        }
        boolean piecesInstanceCondition = p.currentTile.getPiece() instanceof King && p.selectedTile.getPiece() instanceof Rook;
        boolean colorCondition = p.currentTile.getPiece().getColor() == p.selectedTile.getPiece().getColor();
        boolean didMoveCondition = !p.currentTile.getPiece().didMove && !p.selectedTile.getPiece().didMove;
        return (piecesInstanceCondition && didMoveCondition && colorCondition
                && PieceMoveValidator.isWayFreeOfPieces(p) && checkCondition(p));
    }

    private static boolean checkCondition(Position p) {
        return (p.selectedTile.getColumn() == 0 &&
                singleTileCheckCondition(p,-2) && singleTileCheckCondition(p,-1) && singleTileCheckCondition(p,0))
                || (p.selectedTile.getColumn() == 7 &&
                singleTileCheckCondition(p,0) && singleTileCheckCondition(p,1) && singleTileCheckCondition(p,2));
    }

    private static boolean singleTileCheckCondition(Position p, int number) {
        Tile currentTile = p.currentTile;
        Tile selectedTile = p.selectedTile;
        boolean condition = Check.willBeNoCheckAfterMove(p, p.tiles[p.currentTile.getRow()][p.currentTile.getColumn() + number]);
        p.currentTile = currentTile;
        p.selectedTile = selectedTile;
        return condition;
    }

    public static void castlingMove(Position p) {
        if (p.selectedTile.getColumn() == 0) {
            updateKingAndRookPosition(p, 2, 3);
        } else if (p.selectedTile.getColumn() == 7) {
            updateKingAndRookPosition(p, 6, 5);
        }
    }

    private static void updateKingAndRookPosition(Position p, int newKingColumn, int newRookColumn) {
        p.selectedTile.getPiece().didMove = true;
        p.currentTile.getPiece().didMove = true;
        p.tiles[p.currentTile.getRow()][newRookColumn].setPiece(p.selectedTile.getPiece());
        p.tiles[p.currentTile.getRow()][newKingColumn].setPiece(p.currentTile.getPiece());
        p.tiles[p.currentTile.getRow()][p.selectedTile.getColumn()].setPiece(null);
        p.tiles[p.currentTile.getRow()][p.currentTile.getColumn()].setPiece(null);
    }
}