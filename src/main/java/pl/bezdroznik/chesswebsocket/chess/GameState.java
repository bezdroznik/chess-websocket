package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

@Setter
@Getter
public class GameState {

    public GameState() {
        this.chessboard = Chessboard.getStandardChessboard();
        this.turn = Turn.WHITE;
    }

    Chessboard chessboard;
    Turn turn;
    Move move;

    public void analyze(SelectedTile selectedTile) {
        Tile tile =  chessboard.findTileFromChessboard(selectedTile.getName());
        Piece piece = tile.getPiece();
        if (turn.equals(piece.getColor())) {
            move.showPossibleMoves(tile, chessboard);
        }
        // póki co zrobiłem tylko wypisanie pól dostepnych dla wybranej figury
        // tak myślałem, że ta lista z możliwymi polamu może wrócić do fronta i podświetli się pola możliwe dla wybranej figury
    }



}
