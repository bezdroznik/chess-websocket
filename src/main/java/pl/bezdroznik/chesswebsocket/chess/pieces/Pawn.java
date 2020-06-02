package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
@Setter
public class Pawn extends Piece {

    String symbol = "P";
    public boolean didPawnMove = false;

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
        return directionCondition && vectorCondition && attackColorCondition(currentPawnTile, selectedTile);
    }

    private boolean canPawnMove(int moveDirection, Tile currentPawnTile, Tile selectedTile){
        boolean directionCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection;
        boolean firstPawnMoveCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection * 2;
        boolean vectorCondition = selectedTile.getColumn() - currentPawnTile.getColumn() == 0;
        if (!didPawnMove && firstPawnMoveCondition && vectorCondition && moveColorCondition(selectedTile)){
            return true;
        } else return directionCondition && vectorCondition && moveColorCondition(selectedTile);
    }

    private boolean moveColorCondition(Tile selectedTile) {
        return selectedTile.getPiece() == null;
    }

    private boolean attackColorCondition(Tile currentPawnTile ,Tile selectedTile) {
        if(selectedTile.getPiece() == null){
            return false;
        }
        return currentPawnTile.getPiece().getColor() != selectedTile.getPiece().getColor();
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