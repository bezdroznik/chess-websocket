package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
        Tile tile =  chessboard.findTileFromChessboard(selectedTile.getName());
        Move move = new Move(chessboard, tile);
        List<Tile> possibleMoves = move.showPossibleMoves();
        // póki co zrobiłem tylko wypisanie pól dostepnych dla wybranej figury
        // tak myślałem, że ta lista z możliwymi polami może wrócić do fronta i podświetli się pola możliwe dla wybranej figury
    }
}
