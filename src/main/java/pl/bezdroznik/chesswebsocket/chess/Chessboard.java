package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.*;
import java.util.Arrays;

@Getter
@Setter
public class Chessboard {

    private Tile[][] rows;

    private Chessboard() {
    }

    public static Chessboard getStandardChessboard() {
        Chessboard chessboard = new Chessboard();
        chessboard.fillWithTiles();
        chessboard.fillWithPieces();
        return chessboard;
    }

    public void fillWithPieces(){
        this.fillWhitePieces();
        this.fillBlackPieces();
    }

    private void fillWhitePieces(){
        Tile[] whitePawnsRow = this.rows[1];
        Tile[] whitePiecesRow = this.rows[0];

        fillPawns(whitePawnsRow, Piece.Color.WHITE);
        fillMajorPieces(whitePiecesRow, Piece.Color.WHITE);
    }

    private void fillMajorPieces(Tile[] tiles, Piece.Color color) {
        tiles[0].setPiece(new Rook(color));
        tiles[7].setPiece(new Rook(color));
        tiles[1].setPiece(new Knight(color));
        tiles[6].setPiece(new Knight(color));
        tiles[2].setPiece(new Bishop(color));
        tiles[5].setPiece(new Bishop(color));
        tiles[3].setPiece(new Queen(color));
        tiles[4].setPiece(new King(color));
    }

    private void fillPawns(Tile[] pawnsRow, Piece.Color color) {
        Arrays.stream(pawnsRow)
                .forEach(tile -> tile.setPiece(new Pawn(color)));
    }

    private void fillBlackPieces() {
        Tile[] blackPawnsRow = this.rows[6];
        Tile[] blackPiecesRow = this.rows[7];

        fillPawns(blackPawnsRow, Piece.Color.BLACK);
        fillMajorPieces(blackPiecesRow, Piece.Color.BLACK);
    }

    public void fillWithTiles() {
        this.rows = new Tile[8][8];
        for (int row = 0; row < rows.length; row++) {
            for (int column = 0; column < rows[row].length ; column++) {
                if ((row + column) % 2 == 0){
                    rows[row][column] = Tile.whiteTile().setName(row, column);
                } else {
                    rows[row][column] = Tile.blackTile().setName(row, column);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

         for (Tile[] row : rows) {
            for (Tile column : row) {
                sb.append(column.toString())
                .append("\t");
            }
            sb.append("\n");
         }
         return sb.toString();
    }
}
