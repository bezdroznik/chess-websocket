package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Position;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import java.util.ArrayList;
import java.util.List;

public class MoveValidator {

    public static List<Tile> findPossibleMoves(Position p) {
        Tile currentTile = p.currentTile;
        List<Tile> possibleMoves = new ArrayList<>();
        for (Tile[] row : p.tiles) {
            for (Tile testingTile : row) {
                p.selectedTile = testingTile;
                p.currentTile = currentTile;
                if (canPieceMove(p) || Castling.canCastling(p) || EnPassant.canEnPassan(p)) {
                    possibleMoves.add(testingTile);
                }
            }
        }
        return possibleMoves;
    }

    private static boolean canPieceMove(Position p) {
        return pieceColorValidator(p) && PieceMoveValidator.canMove(p) && Check.willBeNoCheckAfterMove(p, p.selectedTile);
    }

    private static boolean pieceColorValidator(Position p) {
        if (p.selectedTile.getPiece() == null) {
            return true;
        }
        return p.currentTile.getPiece().getColor() != p.selectedTile.getPiece().getColor();
    }
}