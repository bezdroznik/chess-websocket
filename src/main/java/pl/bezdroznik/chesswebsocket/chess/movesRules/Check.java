package pl.bezdroznik.chesswebsocket.chess.movesRules;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.*;
import pl.bezdroznik.chesswebsocket.chess.pieces.King;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

import java.util.List;

class Check {

    public static boolean willBeNoCheckAfterMove(Position p, Tile selectedTile){
        if (p.currentTile != selectedTile){
            saveTilesForRollback(p.currentTile, selectedTile);
            fillTilesWithPiecesForCheckTest(p.currentTile, selectedTile);
        }
        Tile kingTile = findKingTileFromChessboard(p.tiles, selectedTile.getPiece().getColor());
        Color opponentPiecesColor = findOpponentPiecesColor(kingTile.getPiece().getColor());
        List <Tile> allTilesOccupiedByOpponentPieces = Analyze.findAllTilesOccupiedByPiecesOfTheSameColor(p.tiles, opponentPiecesColor);
        for (Tile testingEnemyPieceTile : allTilesOccupiedByOpponentPieces){
            if (canSelectedPieceAttackTheKing(p, testingEnemyPieceTile, kingTile)){
                rollbackTilesAfterCheckTest(p.currentTile, selectedTile);
                return false;
            }
        }
        rollbackTilesAfterCheckTest(p.currentTile, selectedTile);
        return true;
    }

    private static void saveTilesForRollback(Tile currentTile, Tile selectedTile) {

        tilesRollback.setCurrentTile(currentTile);
        tilesRollback.setSelectedTile(selectedTile);
        tilesRollback.setCurrentPiece(currentTile.getPiece());
        tilesRollback.setSelectedPiece(selectedTile.getPiece());
    }

    private static void fillTilesWithPiecesForCheckTest(Tile currentTile, Tile selectedTile){
        selectedTile.setPiece(currentTile.getPiece());
        currentTile.setPiece(null);
    }

    private static void rollbackTilesAfterCheckTest(Tile currentTile, Tile selectedTile) {
        if (currentTile != selectedTile) {
            selectedTile = tilesRollback.getSelectedTile();
            currentTile = tilesRollback.getCurrentTile();
            selectedTile.setPiece(tilesRollback.getSelectedPiece());
            currentTile.setPiece(tilesRollback.getCurrentPiece());
        }
    }

    private static Boolean canSelectedPieceAttackTheKing (Position p, Tile testingEnemyPieceTile, Tile kingTile){
        p.currentTile = testingEnemyPieceTile;
        p.selectedTile = kingTile;
        return PieceMoveValidator.canMove(p);
    }

    private static Tile findKingTileFromChessboard(Tile[][] tiles, Color kingColor) {
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

    private static Color findOpponentPiecesColor(Color currentPlayerColor) {
        if (currentPlayerColor == Color.WHITE){
            return Color.BLACK;
        } return  Color.WHITE;
    }
    @Getter
    @Setter
    private static class TilesRollback {

        Tile currentTile;
        Tile selectedTile;
        Piece currentPiece;
        Piece selectedPiece;
    }

    static TilesRollback tilesRollback = new TilesRollback();

}