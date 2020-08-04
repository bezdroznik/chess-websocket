package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Color;
import pl.bezdroznik.chesswebsocket.chess.Position;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.*;

public class PieceMoveValidator {

    public static boolean canMove(Position p) {
        if (p.currentTile.getPiece() instanceof Rook) {
            boolean rookMoveVectorCondition = (getMoveVectorV(p) == 0 || getMoveVectorH(p) == 0);
            return (rookMoveVectorCondition && isWayFreeOfPieces(p));
        } else if (p.currentTile.getPiece() instanceof Knight) {
            return (Math.abs(getMoveVectorH(p)) == 1 && Math.abs(getMoveVectorV(p)) == 2)
                    || (Math.abs(getMoveVectorH(p)) == 2 && Math.abs(getMoveVectorV(p)) == 1);
        } else if (p.currentTile.getPiece() instanceof Bishop) {
            return (getMoveResultant(p) == 0 && isWayFreeOfPieces(p));
        } else if (p.currentTile.getPiece() instanceof Queen) {
            boolean queenMoveVectorCondition = (getMoveResultant(p) == 0 || getMoveVectorV(p) == 0 || getMoveVectorH(p) == 0);
            return (queenMoveVectorCondition && isWayFreeOfPieces(p));
        } else if (p.currentTile.getPiece() instanceof King) {
            return Math.abs(getMoveVectorH(p)) <= 1 && Math.abs(getMoveVectorV(p)) <= 1;
        } else if (p.currentTile.getPiece() instanceof Pawn) {
            return canPawnMove(p) || canPawnAttack(p);
        }
        return false;
    }

    private static int getMoveVectorV(Position p) {
        return p.selectedTile.getRow() - p.currentTile.getRow();
    }

    private static int getMoveVectorH(Position p) {
        return p.selectedTile.getColumn() - p.currentTile.getColumn();
    }

    private static int getMoveResultant(Position p) {
        return Math.abs(getMoveVectorH(p)) - Math.abs(getMoveVectorV(p));
    }


    public static boolean isWayFreeOfPieces(Position p) {
        int pieceRow = p.currentTile.getRow();
        int pieceColumn = p.currentTile.getColumn();
        int vectorVToIncrement = setVectorToIncrement(getMoveVectorV(p));
        int vectorHToIncrement = setVectorToIncrement(getMoveVectorH(p));

        while (pieceRow != p.selectedTile.getRow() - vectorVToIncrement
                || pieceColumn != p.selectedTile.getColumn() - vectorHToIncrement) {
            pieceRow += vectorVToIncrement;
            pieceColumn += vectorHToIncrement;
            if (p.tiles[pieceRow][pieceColumn].getPiece() != null) {
                return false;
            }
        }
        return true;
    }

    private static int setVectorToIncrement(int vector) {
        int vectorToIncrement;
        if (vector == 0) {
            vectorToIncrement = 0;
        } else {
            vectorToIncrement = Math.abs(vector) / vector;
        }
        return vectorToIncrement;
    }


    private static boolean canPawnAttack(Position p) {
        boolean vectorCondition = Math.abs(p.selectedTile.getColumn() - p.currentTile.getColumn()) == 1;
        return pawnMoveDirectionCondition(p) && vectorCondition && isTheTileOccupiedByAnOpponent(p);
    }

    private static boolean canPawnMove(Position p) {
        boolean firstPawnMoveDirectionCondition = getMoveVectorV(p) == pawnMoveDirection(p.currentTile.getPiece()) * 2;
        boolean isTileInFrontOfPawnEmpty = isTileInFrontOfPawnEmpty(p);
        boolean firstPawnMoveCondition = !p.currentTile.getPiece().didMove && firstPawnMoveDirectionCondition && isTileInFrontOfPawnEmpty;

        return (firstPawnMoveCondition || pawnMoveDirectionCondition(p))
                && getMoveVectorH(p) == 0 && isSelectedTileEmpty(p.selectedTile);
    }

    private static boolean isTileInFrontOfPawnEmpty(Position p) {
        return p.tiles[p.currentTile.getRow() + pawnMoveDirection(p.currentTile.getPiece())][p.currentTile.getColumn()].getPiece() == null;
    }

    private static boolean isSelectedTileEmpty(Tile selectedTile) {
        return selectedTile.getPiece() == null;
    }

    private static boolean isTheTileOccupiedByAnOpponent(Position p) {
        if (p.selectedTile.getPiece() == null) {
            return false;
        }
        return p.currentTile.getPiece().getColor() != p.selectedTile.getPiece().getColor();
    }

    private static int pawnMoveDirection(Piece piece) {
        if (piece.getColor() == Color.WHITE) {
            return 1;
        }
        return -1;
    }

    private static boolean pawnMoveDirectionCondition(Position p) {
        return getMoveVectorV(p) == pawnMoveDirection(p.currentTile.getPiece());
    }
}