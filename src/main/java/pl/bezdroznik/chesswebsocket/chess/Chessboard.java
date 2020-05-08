package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import lombok.Setter;
import pl.bezdroznik.chesswebsocket.chess.pieces.*;

@Getter
@Setter
public class Chessboard {

    private Tile[][] board;

    private Chessboard() {
        this.board = new Tile[8][8];
    }

    public static Chessboard fillWithPieces(Chessboard chessboard){
        fillWhitePieces(chessboard);
        fillBlackPieces(chessboard);

        return chessboard;
    }

    private static Chessboard fillWhitePieces(Chessboard chessboard){
        final int WHITE_PAWNS_ROW = 1;
        fillPawns(chessboard, WHITE_PAWNS_ROW, Piece.Color.WHITE);
        Tile[] whitePiecesRow = chessboard.board[0];
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

    private static void fillPawns(Chessboard chessboard, int pawnsRow, Piece.Color color) {
        for (Tile tile : chessboard.board[pawnsRow]) {
            tile.setPiece(new Pawn(color));
        }
    }

    private static Chessboard fillBlackPieces(Chessboard chessboard) {
        final int BLACK_PAWNS_ROW = 6;
        fillPawns(chessboard, BLACK_PAWNS_ROW, Piece.Color.BLACK);
        Tile[] blackPiecesRow = chessboard.board[7];
        fillMajorPieces(blackPiecesRow, Piece.Color.BLACK);

        return chessboard;

    }

    public static Chessboard fillChessboardWithTiles() {
        Chessboard chessboard = new Chessboard();
        Tile[][] board = chessboard.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length ; column++) {
                if ((row + column) % 2 == 0){
                    board[row][column] = Tile.whiteTile();
                } else {
                    board[row][column] = Tile.blackTile();
                }
            }
        }
        fillWithPieces(chessboard);
        return chessboard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

         for (Tile[] row : board) {
            for (Tile column : row) {
                sb.append(column.toString())
                .append("\t");
            }
            sb.append("\n");
         }
         return sb.toString();
    }


}
