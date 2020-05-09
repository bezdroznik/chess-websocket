package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

@Getter
@Setter
public class Tile {

    private static final Map<Integer, String> columnNames = Map.ofEntries(
            entry(0,"A"),
            entry(1,"B"),
            entry(2,"C"),
            entry(3,"D"),
            entry(4,"E"),
            entry(5,"F"),
            entry(6,"G"),
            entry(7,"H"));

    private Color color;
    private Piece piece;
    private String name;

    private Tile(Color color) {
        this.color = color;
    }

    private enum Color {
        WHITE, BLACK
    }

    public static Tile whiteTile() {
        return new Tile(Color.WHITE);
    }

    public static Tile blackTile() {
        return new Tile(Color.BLACK);
    }

    public Tile setName(int row, int column) {
        this.name = columnNames.get(column);
        this.name += String.valueOf(row + 1);
        return this;
    }

    @Override
    public String toString() {
        if (piece != null) {
            return piece.toString();
        }
        return color.toString();
    }
}

