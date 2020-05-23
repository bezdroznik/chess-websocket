package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public class Pawn extends Piece {

    String symbol = "P";
    private boolean didPawnMove = false;

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean specificPiecesMovements(Tile currentPawnTile, Tile selectedTile, Chessboard chessboard) {
        int moveDirection = pawnMoveDirection(currentPawnTile);
        boolean canPawnMove = canPawnMove(moveDirection, currentPawnTile, selectedTile);
        boolean canPawnAttack = canPawnAttack(moveDirection, currentPawnTile, selectedTile);

        return canPawnMove || canPawnAttack;
    }

    private boolean canPawnAttack(int moveDirection, Tile currentPawnTile, Tile selectedTile){
        boolean directionCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection;
        boolean vectorCondition = Math.abs(selectedTile.getColumn() - currentPawnTile.getColumn()) == 1;
        boolean colorCondition = color != selectedTile.getPiece().getColor() && selectedTile.getPiece().getColor() != null;
        if (directionCondition && vectorCondition && colorCondition){
            didPawnMove = true;
            return true;
        }
        return false;
    }

    private boolean canPawnMove(int moveDirection, Tile currentPawnTile, Tile selectedTile){
        boolean directionCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection;
        boolean firstPawnMoveCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection * 2;
        boolean vectorCondition = selectedTile.getColumn() - currentPawnTile.getColumn() == 0;
        boolean colorCondition = color != selectedTile.getPiece().getColor() && selectedTile.getPiece().getColor() != null;
        if (!didPawnMove && firstPawnMoveCondition && vectorCondition && colorCondition){
            didPawnMove = true;
            return true;
        } else if (directionCondition && vectorCondition && colorCondition){
            didPawnMove = true;
            return true;
        }
        return false;
    }

    private int pawnMoveDirection(Tile currentTile){
        if (currentTile.getPiece().getColor() == Color.WHITE){
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Pawn";
    }

}