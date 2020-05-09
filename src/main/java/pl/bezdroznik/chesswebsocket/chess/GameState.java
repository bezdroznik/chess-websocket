package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GameState {

    public GameState() {
        this.chessboard = Chessboard.getStandardChessboard();
        this.turn = Turn.WHITE;
    }

    Chessboard chessboard;
    Turn turn;

    public void analyze(SelectedTile selectedTile) {
        // LECISZ PIŹDZIAK
    }

}
