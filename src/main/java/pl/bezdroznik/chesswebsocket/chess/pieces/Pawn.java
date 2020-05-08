package pl.bezdroznik.chesswebsocket.chess.pieces;

public class Pawn extends Piece {
    public Pawn(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
