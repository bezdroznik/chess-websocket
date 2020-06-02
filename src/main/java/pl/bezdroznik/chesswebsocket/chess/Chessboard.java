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

// jakby szachownica byla static to by nie trzeba dawac caly czas w funkcjach jako argument
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
        Tile[] whitePawnsRow = this.tiles[1];
        Tile[] whitePiecesRow = this.tiles[0];

        fillPawns(whitePawnsRow, Piece.Color.WHITE);
        fillMajorPieces(whitePiecesRow, Piece.Color.WHITE);
    }

    private void fillBlackPieces() {
        Tile[] blackPawnsRow = this.tiles[6];
        Tile[] blackPiecesRow = this.tiles[7];

        fillPawns(blackPawnsRow, Piece.Color.BLACK);
        fillMajorPieces(blackPiecesRow, Piece.Color.BLACK);
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

    public void fillWithTiles() {
        this.tiles = new Tile[8][8];
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[row].length ; column++) {
                if ((row + column) % 2 == 0){
                    tiles[row][column] = Tile.whiteTile().setName(row, column);
                } else {
                    tiles[row][column] = Tile.blackTile().setName(row, column);
                }
            }
        }
    }

    public Tile findTileFromChessboard(String selectedName) { // to chyba nie powinno tu byc, Chessboard powinno tylko odpowiadac za tworzenie szachownicy
        for (Tile[] tiles : tiles) {
            for (Tile tile : tiles) {
                String TileName = tile.getName();
                if (selectedName.equals(TileName)) {
                    return tile;
                }
            }
        }
        return null;
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
