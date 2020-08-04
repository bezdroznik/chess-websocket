package pl.bezdroznik.chesswebsocket.chess;

import java.util.List;

public class GameState {

    public final Player WHITE_PLAYER;
    public final Player BLACK_PLAYER;
    public final Chessboard chessboard;
    public final Tile[][] tiles;
    public Color turn;
    public boolean isTileSelected;
    public List<Tile> possibleMoves;

    public Tile currentTile;
    public Tile selectedTile;

    public Tile tileForEnPassanMoveAgainstWhitePlayer;
    public Tile tileForEnPassanMoveAgainstBlackPlayer;

    public boolean canPromote;

    public Tile promotionPawnTile;

    private GameState(String whitePlayerName, String blackPlayerName){
        this.chessboard = Chessboard.getStandardChessboard();
        this.tiles = chessboard.getTiles();
        this.turn = Color.WHITE;
        this.isTileSelected = false;
        this.canPromote = false;
        this.WHITE_PLAYER = new Player(whitePlayerName, Color.WHITE);
        this.BLACK_PLAYER = new Player(blackPlayerName, Color.BLACK);
    }

    public static GameState createGameState(String whitePlayer, String blackPlayer) {
        return new GameState(whitePlayer, blackPlayer);
    }
}
