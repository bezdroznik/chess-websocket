package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.King;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Check {

    public static boolean isCheck(Chessboard chessboard, Tile currentTile, Tile selectedTile){
        if (currentTile.getPiece() == null){
            return false;
        }
        return checkSimulation(chessboard, currentTile, selectedTile);
    }

    private static boolean checkSimulation(Chessboard chessboard, Tile currentTile, Tile selectedTile){
        Piece king = findKingTileFromChessboard(chessboard, currentTile.getPiece()).getPiece();
        saveTilesForRollback(currentTile, selectedTile);
        fillTilesWithPiecesForCheckTest(currentTile, selectedTile);
        List <Tile> enemyList = findEnemiesFromChessboard(chessboard, king.getColor());
      for (Tile testingEnemyPieceTile : enemyList){
            if (canSelectedPieceAttackTheKing(testingEnemyPieceTile, king, chessboard)){
                rollbackTilesAfterCheckTest(currentTile, selectedTile);
                return true;
            }
            rollbackTilesAfterCheckTest(currentTile, selectedTile);
        }
        return false;
    }

    private static void saveTilesForRollback(Tile currentTile, Tile selectedTile) {
        tilesRollback.setCurrentPiece(currentTile.getPiece());
        tilesRollback.setSelectedPiece(selectedTile.getPiece());
    }

    private static void fillTilesWithPiecesForCheckTest(Tile currentTile, Tile selectedTile){
        selectedTile.setPiece(currentTile.getPiece());
        currentTile.setPiece(null);
    }

    private static void rollbackTilesAfterCheckTest(Tile currentTile, Tile selectedTile){
        selectedTile.setPiece(tilesRollback.getSelectedPiece());
        currentTile.setPiece(tilesRollback.getCurrentPiece());
    }

    private static Boolean canSelectedPieceAttackTheKing (Tile testingEnemyPieceTile, Piece king, Chessboard chessboard){
        Tile currentKingTile = findKingTileFromChessboard(chessboard, king);
        boolean pieceColorCondition = testingEnemyPieceTile.getPiece().getColor() != king.getColor();
        boolean movesCondition = testingEnemyPieceTile.getPiece().specificPiecesMovements(testingEnemyPieceTile, currentKingTile, chessboard);

        return pieceColorCondition && movesCondition;
    }

    private static List<Tile> findEnemiesFromChessboard(Chessboard chessboard, Piece.Color kingColor){
        ArrayList <Tile> enemyList = new ArrayList<>();
        Tile[][]tiles = chessboard.getTiles();

        for (Tile[] row : tiles) {
            for (Tile column : row) {
                if(column.getPiece() != null) {
                    Piece piece = column.getPiece();
                    if (piece.getColor() != kingColor) {
                        enemyList.add(column);
                    }
                }
            }
        }
        return enemyList;
    }

    private static Tile findKingTileFromChessboard(Chessboard chessboard, Piece allyPiece) {
        Piece.Color kingColor = allyPiece.getColor();
        Tile[][]tiles = chessboard.getTiles();
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                Piece piece = tile.getPiece();
                if (piece instanceof King && piece.getColor().equals(kingColor)) {
                    return tile;
                }
            }
        }
        return null;
    }

    @Getter
    @Setter
    private static class TilesRollback {

        Piece currentPiece;
        Piece selectedPiece;
    }

    static TilesRollback tilesRollback = new TilesRollback();
}