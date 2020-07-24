package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

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

    private final Color color;
    private Piece piece;
    private final String name;
    private final int row;
    private final int column;
    private String backlightSymbol = "N";


    private Tile(Color color, int row, int column) {
        this.color = color;
        this.name = columnNames.get(column) + (row + 1);
        this.row = row;
        this.column = column;
    }



    public static Tile whiteTile(int row, int column) {
        return new Tile(Color.WHITE, row, column);
    }

    public static Tile blackTile(int row, int column) {
        return new Tile(Color.BLACK, row, column);
    }
}

