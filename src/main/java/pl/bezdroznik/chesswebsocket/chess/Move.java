package pl.bezdroznik.chesswebsocket.chess;

import java.util.ArrayList;
import java.util.List;

public class Move {

    List<Tile> possibleMoves = new ArrayList<>();

    public List<Tile> showPossibleMoves (Tile tile, Chessboard chessboard){
        Tile [][]tiles = chessboard.getTiles();
        for (Tile[] row : tiles) {
            for (Tile testingTile : row) {
                if (canMove(tile, testingTile, chessboard)) {
                    possibleMoves.add(testingTile);
                }
            }
        }
        return possibleMoves;
    }

    private boolean canMove(Tile tile, Tile selectedTile, Chessboard board) {
        boolean pieceColorCondition = tile.getPiece().getColor() != selectedTile.getPiece().getColor();
        boolean checkCondition = !Check.isCheck(board, selectedTile, selectedTile.getPiece());
        boolean movesCondition = tile.getPiece().specificPiecesMovements(tile, selectedTile, board);

        return pieceColorCondition && checkCondition && movesCondition;
    }

}
