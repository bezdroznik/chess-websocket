package pl.bezdroznik.chesswebsocket.chess;

import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

public class CurrentGameState {

    private CurrentGameState(Chessboard chessboard, Tile currentTile, Tile selectedTile){
        this.chessboard = chessboard;

        this.currentTile = currentTile;
        this.currentTileRow = currentTile.getRow();
        this.currentTileColumn = currentTile.getColumn();
        this.currentPiece = currentTile.getPiece();

        this.selectedTile = selectedTile;
        this.selectedTileRow = selectedTile.getRow();
        this.selectedTileColumn = selectedTile.getColumn();
        this.selectedPiece = selectedTile.getPiece();
    }

    public static CurrentGameState currentGameState(Chessboard chessboard, Tile currentTile, Tile selectedTile) {
        return new CurrentGameState(chessboard, currentTile, selectedTile);
    }


    public final Chessboard chessboard;

    public final Tile currentTile;
    public final int currentTileRow;
    public final int currentTileColumn;
    public final Piece currentPiece;

    public final Tile selectedTile;
    public final int selectedTileRow;
    public final int selectedTileColumn;
    public final Piece selectedPiece;

}
