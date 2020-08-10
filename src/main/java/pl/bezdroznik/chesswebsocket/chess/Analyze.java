package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.movesRules.Castling;
import pl.bezdroznik.chesswebsocket.chess.movesRules.EnPassant;
import pl.bezdroznik.chesswebsocket.chess.movesRules.Move;
import pl.bezdroznik.chesswebsocket.chess.movesRules.Promotion;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Analyze {

    public static void analyze(GameState gs, SelectedTile selectedTile, String userName) {
        if (!gs.isTileSelected && findAllTilesOccupiedByPiecesThatCanMove(gs).isEmpty()) {
            System.out.println("Checkmate!! trzeba przerwać program"); // trzeba to bedzie wysłąć metodą do fronta
        } else {
            Tile currentTile = findTileFromChessboard(gs, selectedTile.getName());
            if (currentTile.getPiece() != null && !gs.isTileSelected && checkTurn(gs, currentTile.getPiece(), userName)) {
                Position position = new Position(gs, currentTile, null);
                gs.possibleMoves = Move.findPossibleMoves(position);
                if (!gs.possibleMoves.isEmpty()) {
                    gs.currentTile = currentTile;
                    gs.isTileSelected = true;
                }
            } else if (gs.isTileSelected && checkTurn(gs, gs.currentTile.getPiece(), userName)) {
                move(gs, currentTile);
            }
            updateBacklightSymbol(gs);
        }
    }

    private static Tile findTileFromChessboard(GameState gs, String selectedName) {
        for (Tile[] tiles : gs.tiles) {
            for (Tile tile : tiles) {
                String TileName = tile.getName();
                if (selectedName.equals(TileName)) {
                    return tile;
                }
            }
        }
        return null;
    }

    private static boolean checkTurn(GameState gs, Piece currentPiece, String userName) {
        boolean canWhitePlayerMove = userName.equals(gs.WHITE_PLAYER.name) && gs.turn == gs.WHITE_PLAYER.color
                &&  gs.turn == currentPiece.getColor();
        boolean canBlackPlayerMove = userName.equals(gs.BLACK_PLAYER.name) && gs.turn == gs.BLACK_PLAYER.color
                &&  gs.turn == currentPiece.getColor();
        return canWhitePlayerMove || canBlackPlayerMove;
    }

    private static void changeTurn(GameState gs) {
        if (gs.turn == Color.WHITE) {
            gs.turn = Color.BLACK;
        } else {
            gs.turn = Color.WHITE;
        }
    }

    private static void move(GameState gs, Tile selectedTile) {
        if (gs.possibleMoves.contains(selectedTile)) {
            EnPassant.enPassanMove(gs, selectedTile);
            Position position = new Position(gs, gs.currentTile, selectedTile);
            if (Castling.canCastle(position)) {
                Castling.castleMove(position);
            } else {
                selectedTile.setPiece(gs.currentTile.getPiece());
                gs.currentTile.setPiece(null);
                selectedTile.getPiece().hasMove = true;
            }

            if (Promotion.canPromotePawn(selectedTile)) {
                gs.promotionPawnTile = selectedTile;
                gs.canPromote = true;
                selectedTile.setPiece(Promotion.promotion(gs.promotionPawnTile, "queen"));
            }
            changeTurn(gs);
        }
        gs.isTileSelected = false;
    }

    public static void promotion(GameState gs, SelectedTile selectedTile) {
        gs.promotionPawnTile.setPiece(Promotion.promotion(gs.promotionPawnTile, selectedTile.getName()));
        gs.promotionPawnTile.getPiece().hasMove = true;
        gs.canPromote = false;
    }

    public static List<Tile> findAllTilesOccupiedByPiecesOfTheSameColor(Tile[][] tiles, Color color) {
        List<Tile> tilesOccupiedByCurrentPlayerPieces = new ArrayList<>();
        for (Tile[] row : tiles) {
            Arrays.stream(row)
                    .filter(tile -> tile.getPiece() != null)
                    .filter(tile -> tile.getPiece().getColor() == color)
                    .forEach(tilesOccupiedByCurrentPlayerPieces::add);
        }
        return tilesOccupiedByCurrentPlayerPieces;
    }

    private static List<Tile> findAllTilesOccupiedByPiecesThatCanMove(GameState gs) {
        List<Tile> allTilesOccupiedByPiecesThatCanMove = new ArrayList<>();
        Position position = new Position(gs, null, null);
        List<Tile> allTilesOccupiedByCurrentPlayerPieces = findAllTilesOccupiedByPiecesOfTheSameColor(gs.tiles, gs.turn);
        for (Tile currentPlayerPieceTile : allTilesOccupiedByCurrentPlayerPieces) {
            position.currentTile = currentPlayerPieceTile;
            if (!Move.findPossibleMoves(position).isEmpty()) {
                allTilesOccupiedByPiecesThatCanMove.add(currentPlayerPieceTile);
            }
        }
        return allTilesOccupiedByPiecesThatCanMove;
    }

    private static void updateBacklightSymbol(GameState gs) {
        if (gs.isTileSelected && !gs.possibleMoves.isEmpty()) {
            BacklightTiles.setBacklightSymbol(gs.chessboard, gs.possibleMoves);
        } else {
            BacklightTiles.setBacklightSymbol(gs.chessboard, findAllTilesOccupiedByPiecesThatCanMove(gs));
        }
    }

}