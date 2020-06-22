package pl.bezdroznik.chesswebsocket.chess.movesRules;

import pl.bezdroznik.chesswebsocket.chess.Chessboard;
import pl.bezdroznik.chesswebsocket.chess.Tile;
import pl.bezdroznik.chesswebsocket.chess.pieces.King;
import pl.bezdroznik.chesswebsocket.chess.pieces.Rook;

public class Castling {

    private final Chessboard chessboard;
    private final Tile currentTile;
    private final Tile selectedTile;
    private final Tile[][] tiles;

    private Tile kingTile;
    private Tile rookTile;
    private boolean piecesInstanceCondition;

    public Castling(Chessboard chessboard, Tile currentTile, Tile selectedTile) {
        this.chessboard = chessboard;
        this.currentTile = currentTile;
        this.selectedTile = selectedTile;
        this.tiles = chessboard.getTiles();
        findKingAndRookTiles();
    }

    private void findKingAndRookTiles() {
        if (currentTile.getPiece() instanceof King && selectedTile.getPiece() instanceof Rook) {
            kingTile = currentTile;
            rookTile = selectedTile;
            piecesInstanceCondition = true;
        } else if (currentTile.getPiece() instanceof Rook && selectedTile.getPiece() instanceof King) {
            kingTile = selectedTile;
            rookTile = currentTile;
            piecesInstanceCondition = true;
        } else {
            piecesInstanceCondition = false;
        }
    }

    public boolean canCastling() {
        return (piecesInstanceCondition && didMoveCondition() && areNoPiecesBetweenRookAndKing() && checkCondition());
    }

    private boolean didMoveCondition() {
        return !kingTile.getPiece().didMove && !rookTile.getPiece().didMove && rowCondition();
    }

    private boolean rowCondition() {
        if (kingTile.getRow() == 0 && rookTile.getRow() == 0) {
            return true;
        }
        return kingTile.getRow() == 7 && rookTile.getRow() == 7;
    }

    private boolean areNoPiecesBetweenRookAndKing() {
        int castlingRow = kingTile.getRow();
        if (rookTile.getColumn() == 0) {
            return tiles[castlingRow][1].getPiece() == null && tiles[castlingRow][2].getPiece() == null && tiles[castlingRow][3].getPiece() == null;
        } return tiles[castlingRow][5].getPiece() == null && tiles[castlingRow][6].getPiece() == null;
    }

    private boolean checkCondition() {
        int castlingRow = kingTile.getRow();

        if (rookTile.getColumn() == 0 &&
                Check.willBeNoCheckAfterMove(chessboard, kingTile, tiles[castlingRow][kingTile.getColumn() - 2]) &&
                Check.willBeNoCheckAfterMove(chessboard, kingTile, tiles[castlingRow][kingTile.getColumn() - 1]) &&
                Check.willBeNoCheckAfterMove(chessboard, kingTile, kingTile)) {
            return true;
        } return (rookTile.getColumn() == 7 &&
                Check.willBeNoCheckAfterMove(chessboard, kingTile, kingTile) &&
                Check.willBeNoCheckAfterMove(chessboard, kingTile, tiles[castlingRow][kingTile.getColumn() + 1]) &&
                Check.willBeNoCheckAfterMove(chessboard, kingTile, tiles[castlingRow][kingTile.getColumn() + 2]));
    }

    public void castlingMove() {
        if (rookTile.getColumn() == 0) {
            updateKingAndRookPosition(2, 3);
        } else if (rookTile.getColumn() == 7) {
            updateKingAndRookPosition(6, 5);
        }
    }

    private void updateKingAndRookPosition(int newKingColumn, int newRookColumn) {
        int castlingRow = kingTile.getRow();

        rookTile.getPiece().didMove = true;
        kingTile.getPiece().didMove = true;
        tiles[castlingRow][newRookColumn].setPiece(rookTile.getPiece());
        tiles[castlingRow][newKingColumn].setPiece(kingTile.getPiece());
        tiles[castlingRow][rookTile.getColumn()].setPiece(null);
        tiles[castlingRow][kingTile.getColumn()].setPiece(null);
    }

}