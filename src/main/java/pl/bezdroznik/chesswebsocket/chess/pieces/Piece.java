package pl.bezdroznik.chesswebsocket.chess.pieces;

public abstract class Piece {

    public Piece(Color color) {
        this.color = color;
    }

    public enum Color {
        WHITE, BLACK
    }

    Color color;



}
