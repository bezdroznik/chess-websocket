package pl.bezdroznik.chesswebsocket.chess.pieces;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;

@Getter
public class Pawn extends Piece {

    private final String symbol = "P";

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public boolean canPieceDoSpecificMove(Tile currentPawnTile, Tile selectedTile, Chessboard chessboard) {
        int moveDirection = pawnMoveDirection(currentPawnTile);
        boolean canPawnMove = canPawnMove(moveDirection, currentPawnTile, selectedTile, chessboard);
        boolean canPawnAttack = canPawnAttack(moveDirection, currentPawnTile, selectedTile);

        return canPawnMove || canPawnAttack;
    }

    private boolean canPawnAttack(int moveDirection, Tile currentPawnTile, Tile selectedTile){
        boolean directionCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection;
        boolean vectorCondition = Math.abs(selectedTile.getColumn() - currentPawnTile.getColumn()) == 1;
        return directionCondition && vectorCondition && attackColorCondition(currentPawnTile, selectedTile);
    }

    private boolean canPawnMove(int moveDirection, Tile currentPawnTile, Tile selectedTile, Chessboard chessboard){
        boolean directionCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection;
        boolean firstPawnMoveCondition = selectedTile.getRow() - currentPawnTile.getRow() == moveDirection * 2;
        boolean isTileInFrontOfPawnOccupied = !isTileInFrontOfPawnOccupied(moveDirection, currentPawnTile, chessboard);
        boolean vectorCondition = selectedTile.getColumn() - currentPawnTile.getColumn() == 0;
        if (!didMove && firstPawnMoveCondition && vectorCondition && moveColorCondition(selectedTile) && isTileInFrontOfPawnOccupied){
            return true;
        } else return directionCondition && vectorCondition && moveColorCondition(selectedTile);
    }

    private boolean isTileInFrontOfPawnOccupied(int moveDirection, Tile currentPawnTile, Chessboard chessboard) {
        Tile[][]tiles = chessboard.getTiles();
        return tiles[currentPawnTile.getRow() + moveDirection][currentPawnTile.getColumn()].getPiece() != null;
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