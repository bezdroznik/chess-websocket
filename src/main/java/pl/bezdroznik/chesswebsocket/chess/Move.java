package pl.bezdroznik.chesswebsocket.chess;

import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Move {

    List<Tile> possibleMoves = new ArrayList<>();

    public List<Tile> showPossibleMoves (Tile tile, Chessboard chessboard){
        Piece piece = tile.getPiece();
        Tile [][]rows = chessboard.getRows();
        for (Tile[] tiles : rows) {
            for (Tile testingTile : tiles) {
                if (piece.canMove(tile, testingTile, chessboard)) {
                    possibleMoves.add(testingTile);
                }
            }
        }
        return possibleMoves;
    }






}
