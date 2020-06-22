package pl.bezdroznik.chesswebsocket.chess.movesRules;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.GameState;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.King;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

import java.util.List;

class Check {

    public static boolean willBeNoCheckAfterMove(Chessboard chessboard, Tile currentTile, Tile selectedTile){
        if (currentTile != selectedTile){
            saveTilesForRollback(currentTile, selectedTile);
            fillTilesWithPiecesForCheckTest(currentTile, selectedTile);
        }
        Tile kingTile = findKingTileFromChessboard(chessboard, selectedTile.getPiece());
        Piece.Color opponentPiecesColor = findOpponentPiecesColor(kingTile);
        List <Tile> allTilesOccupiedByOpponentPieces = GameState.findAllTilesOccupiedByPiecesOfTheSameColor(chessboard, opponentPiecesColor);
        for (Tile testingEnemyPieceTile : allTilesOccupiedByOpponentPieces){
            if (canSelectedPieceAttackTheKing(testingEnemyPieceTile, kingTile, chessboard)){
                rollbackTilesAfterCheckTest(currentTile, selectedTile);
                return false;
            }
        }
        rollbackTilesAfterCheckTest(currentTile, selectedTile);
        return true;
    }

    private static Piece.Color findOpponentPiecesColor(Tile tileOccupiedByCurrentPlayerPiece) {
        if (tileOccupiedByCurrentPlayerPiece.getPiece().getColor() == Piece.Color.WHITE){
            return Piece.Color.BLACK;
        } return  Piece.Color.WHITE;
    }

    private static void saveTilesForRollback(Tile currentTile, Tile selectedTile) {
        tilesRollback.setCurrentPiece(currentTile.getPiece());
        tilesRollback.setSelectedPiece(selectedTile.getPiece());
    }

    private static void fillTilesWithPiecesForCheckTest(Tile currentTile, Tile selectedTile){
        selectedTile.setPiece(currentTile.getPiece());
        currentTile.setPiece(null);
    }

    private static void rollbackTilesAfterCheckTest(Tile currentTile, Tile selectedTile) {
        if (currentTile != selectedTile) {
            selectedTile.setPiece(tilesRollback.getSelectedPiece());
            currentTile.setPiece(tilesRollback.getCurrentPiece());
        }
    }

    private static Boolean canSelectedPieceAttackTheKing (Tile testingEnemyPieceTile, Tile kingTile, Chessboard chessboard){
        return testingEnemyPieceTile.getPiece().canPieceDoSpecificMove(testingEnemyPieceTile, kingTile, chessboard);
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