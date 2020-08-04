package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.*;
import java.util.Arrays;

@Getter
@Setter
public class Chessboard {

    private Tile[][] tiles;

    private Chessboard() {
    }

    public static Chessboard getStandardChessboard() {
        Chessboard chessboard = new Chessboard();
        chessboard.fillWithTiles();
        chessboard.fillWithPieces();
        return chessboard;
    }

    private void fillWithTiles() {
        this.tiles = new Tile[8][8];
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[row].length ; column++) {
                if ((row + column) % 2 == 0){
                    tiles[row][column] = Tile.whiteTile(row, column);
                } else {
                    tiles[row][column] = Tile.blackTile(row, column);
                }
            }
        }
    }

    private void fillWithPieces(){
        this.fillWhitePieces();
        this.fillBlackPieces();
    }

    private void fillWhitePieces(){
        Tile[] whitePawnsRow = this.tiles[1];
        Tile[] whiteMajorPiecesRow = this.tiles[0];

        fillPawns(whitePawnsRow, Color.WHITE);
        fillMajorPieces(whiteMajorPiecesRow, Color.WHITE);
    }

    private void fillBlackPieces() {
        Tile[] blackPawnsRow = this.tiles[6];
        Tile[] blackMajorPiecesRow = this.tiles[7];
        fillPawns(blackPawnsRow, Color.BLACK);
        fillMajorPieces(blackMajorPiecesRow, Color.BLACK);
    }

    private void fillPawns(Tile[] pawnsRow, Color color) {
        Arrays.stream(pawnsRow)
                .forEach(tile -> tile.setPiece(new Pawn(color)));
    }

    private void fillMajorPieces(Tile[] tiles, Color color) {
        tiles[0].setPiece(new Rook(color));
        tiles[7].setPiece(new Rook(color));
        tiles[1].setPiece(new Knight(color));
        tiles[6].setPiece(new Knight(color));
        tiles[2].setPiece(new Bishop(color));
        tiles[5].setPiece(new Bishop(color));
        tiles[3].setPiece(new Queen(color));
        tiles[4].setPiece(new King(color));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

         for (Tile[] row : tiles) {
            for (Tile column : row) {
                sb.append(column.toString())
                .append("\t");
            }
            sb.append("\n");
         }
         return sb.toString();
    }
}