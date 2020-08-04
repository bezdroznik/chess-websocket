package pl.bezdroznik.chesswebsocket.chess;

public class Position {
    public Tile[][] tiles;
    public Tile currentTile;
    public Tile selectedTile;
    public Tile tileForEnPassanMoveAgainstWhitePlayer;
    public Tile tileForEnPassanMoveAgainstBlackPlayer;

    public Position(GameState gameState, Tile currentTile, Tile selectedTile) {
        this.tiles = gameState.tiles;
        this.currentTile = currentTile;
        this.selectedTile = selectedTile;
        this.tileForEnPassanMoveAgainstWhitePlayer = gameState.tileForEnPassanMoveAgainstWhitePlayer;
        this.tileForEnPassanMoveAgainstBlackPlayer = gameState.tileForEnPassanMoveAgainstBlackPlayer;
    }
}
