package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameState {

    public GameState() {
        this.chessboard = Chessboard.fillChessboardWithTiles();
        this.chessboard = Chessboard.fillWithPieces(chessboard);
        this.turn = Turn.WHITE;
    }

    Chessboard chessboard;
    Turn turn;
}
