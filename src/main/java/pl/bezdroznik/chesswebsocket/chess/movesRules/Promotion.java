package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.*;

public class Promotion {


    public static Piece promotion(Tile promotedPawnTile, String promotionTypeName) {
            switch (promotionTypeName) {
                case "queen":
                    return new Queen(promotedPawnTile.getPiece().getColor());
                case "knight":
                    return new Knight(promotedPawnTile.getPiece().getColor());
                case "rook":
                    return new Rook(promotedPawnTile.getPiece().getColor());
                case "bishop":
                    return new Bishop(promotedPawnTile.getPiece().getColor());
            }
        return null;
    }

    public static boolean canPromotePawn(Tile promotedPawnTile) {
        if (promotedPawnTile.getPiece() instanceof Pawn) {
            return (promotedPawnTile.getPiece().getColor() == Piece.Color.WHITE && promotedPawnTile.getRow() == 7) ||
                    promotedPawnTile.getRow() == 0;
            }
        return false;
    }
}
