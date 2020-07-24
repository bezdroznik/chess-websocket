package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Color;
import pl.bezdroznik.chesswebsocket.chess.CurrentGameState;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

public class PieceMoveValidator {

    public static boolean canMove(CurrentGameState cgs){
        switch (cgs.currentPiece.getClass().getName()){
            case "Rook":
                boolean rookMoveVectorValidator = (getMoveVectorV(cgs) == 0 || getMoveVectorH(cgs) == 0);
                return (rookMoveVectorValidator && Move.isWayFreeOfPieces(cgs));

            case "Knight":
                return (Math.abs(getMoveColumnShift(cgs)) == 1 && Math.abs(getMoveRowShift(cgs)) == 2)
                        || (Math.abs(getMoveColumnShift(cgs)) == 2 && Math.abs(getMoveRowShift(cgs)) == 1);

            case "Bishop":
                return (getMoveResultant(cgs) == 0 && Move.isWayFreeOfPieces(cgs));

            case "Queen":
                boolean queenMoveVectorValidator = (getMoveResultant(cgs) == 0 || getMoveVectorV(cgs) == 0 || getMoveVectorH(cgs) == 0);
                return (queenMoveVectorValidator && Move.isWayFreeOfPieces(cgs));

            case "King":
                return Math.abs(getMoveColumnShift(cgs)) <= 1 && Math.abs(getMoveRowShift(cgs)) <= 1;

            case "Pawn":
                return canPawnMove(cgs) || canPawnAttack(cgs);

            default:
                return false;
        }
    }

    private static int getMoveVectorV(CurrentGameState cgs){
        return cgs.selectedTileRow - cgs.currentTileRow;
    }

    private static int getMoveVectorH(CurrentGameState cgs){
        return cgs.selectedTileColumn - cgs.currentTileColumn;
    }

    private static int getMoveResultant(CurrentGameState cgs){
        return Math.abs(getMoveVectorH(cgs)) - Math.abs(getMoveVectorV(cgs));
    }

    private static int getMoveRowShift(CurrentGameState cgs) {
        return cgs.selectedTileRow - cgs.currentTileRow;
    }

    private static int getMoveColumnShift(CurrentGameState cgs) {
        return cgs.selectedTileColumn - cgs.currentTileColumn;
    }


    private static boolean canPawnAttack(CurrentGameState cgs){
        return pawnMoveDirectionValidator(cgs) && getMoveResultant(cgs) == 1 && isPieceAttackedByAnOpponent(cgs);
    }

    private static boolean canPawnMove(CurrentGameState cgs){
        boolean firstPawnMoveDirectionValidator = getMoveVectorV(cgs) == pawnMoveDirection(cgs.currentPiece) * 2;
        boolean isTileInFrontOfPawnEmpty = isTileInFrontOfPawnEmpty(cgs);
        boolean firstPawnMoveValidator = !cgs.currentPiece.didMove && firstPawnMoveDirectionValidator && isTileInFrontOfPawnEmpty;

        return  (firstPawnMoveValidator || pawnMoveDirectionValidator(cgs))
                && getMoveVectorH(cgs) == 0 && isSelectedTileEmpty(cgs.selectedTile);
    }

    private static boolean isTileInFrontOfPawnEmpty(CurrentGameState cgs) {
        Tile[][]tiles = cgs.chessboard.getTiles();
        return tiles[cgs.currentTileRow + pawnMoveDirection(cgs.currentPiece)][cgs.currentTileColumn].getPiece() == null;
    }

    private static boolean isSelectedTileEmpty(Tile selectedTile) {
        return selectedTile.getPiece() == null;
    }

    private static boolean isPieceAttackedByAnOpponent(CurrentGameState cgs) {
        if(cgs.selectedPiece == null){
            return false;
        }
        return cgs.currentPiece.getColor() != cgs.selectedPiece.getColor();
    }

    private static int pawnMoveDirection(Piece piece){
        if (piece.getColor() == Color.WHITE){
            return 1;
        }
        return -1;
    }

    private static boolean pawnMoveDirectionValidator(CurrentGameState cgs){
        return getMoveVectorV(cgs) == pawnMoveDirection(cgs.currentPiece);
    }
}