package pl.bezdroznik.chesswebsocket.chess;

import java.util.Arrays;
import java.util.List;

public class BacklightTiles {

    public static void setBacklightSymbol(Chessboard chessboard, List<Tile> possibleTiles) {
        resetBacklightSymbol(chessboard);
        for (Tile[] tiles : chessboard.getTiles()) {
            Arrays.stream(tiles)
                    .filter(possibleTiles::contains)
                    .forEach(tile -> tile.setBacklightSymbol("Y"));
        }
    }

    private static void resetBacklightSymbol(Chessboard chessboard) {
        for (Tile[] tiles : chessboard.getTiles()) {
            Arrays.stream(tiles)
                    .forEach(tile -> tile.setBacklightSymbol("N"));
        }
    }
}