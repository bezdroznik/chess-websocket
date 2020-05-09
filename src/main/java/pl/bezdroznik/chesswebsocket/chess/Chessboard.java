package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.*;

@Getter
@Setter
public class Chessboard {

    private Tile[][] row;

    private Chessboard() {
        this.row = new Tile[8][8];
    }

    public static Chessboard fillWithPieces(Chessboard chessboard){
        fillWhitePieces(chessboard);
        fillBlackPieces(chessboard);

        return chessboard;
    }

    private static Chessboard fillWhitePieces(Chessboard chessboard){
        Tile[] whitePawnsRow = chessboard.row[1];
        Tile[] whitePiecesRow = chessboard.row[0];

        fillPawns(whitePawnsRow, Piece.Color.WHITE);
        fillMajorPieces(whitePiecesRow, Piece.Color.WHITE);

        return chessboard;
    }

    private static void fillMajorPieces(Tile[] tiles, Piece.Color color) {
        tiles[0].setPiece(new Rook(color));
        tiles[7].setPiece(new Rook(color));
        tiles[1].setPiece(new Knight(color));
        tiles[6].setPiece(new Knight(color));
        tiles[2].setPiece(new Bishop(color));
        tiles[5].setPiece(new Bishop(color));
        tiles[3].setPiece(new Queen(color));
        tiles[4].setPiece(new King(color));
    }

    private static void fillPawns(Tile[] pawnsRow, Piece.Color color) {
        for (Tile tile : pawnsRow) {
            tile.setPiece(new Pawn(color));
        }
    }

    private static Chessboard fillBlackPieces(Chessboard chessboard) {
        Tile[] blackPawnsRow = chessboard.row[6];
        Tile[] blackPiecesRow = chessboard.row[7];

        fillPawns(blackPawnsRow, Piece.Color.BLACK);
        fillMajorPieces(blackPiecesRow, Piece.Color.BLACK);

        return chessboard;

    }

    public static Chessboard fillChessboardWithTiles() {
        Chessboard chessboard = new Chessboard();
        Tile[][] board = chessboard.getRow();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length ; column++) {
                if ((row + column) % 2 == 0){
                    board[row][column] = Tile.whiteTile().setName(row, column);
                } else {
                    board[row][column] = Tile.blackTile().setName(row, column);
                }
            }
        }
        fillWithPieces(chessboard);
        return chessboard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

         for (Tile[] row : row) {
            for (Tile column : row) {
                sb.append(column.toString())
                .append("\t");
            }
            sb.append("\n");
         }
         return sb.toString();
    }
}
