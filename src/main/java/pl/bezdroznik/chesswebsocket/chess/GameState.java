package pl.bezdroznik.chesswebsocket.chess;

import lombok.Getter;
import pl.bezdroznik.chesswebsocket.chess.movesRules.*;
import pl.bezdroznik.chesswebsocket.chess.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class GameState {

    public GameState() {
        this.chessboard = Chessboard.getStandardChessboard();
        this.turn = Turn.WHITE;
        updateBacklightSymbol();
    }

    private final Chessboard chessboard;
    private Turn turn;
    private List<Tile> possibleMoves;
    private boolean isTileSelected = false;
    private Tile currentTile;

    public void analyze(SelectedTile selectedTile) {
        if (findAllTilesOccupiedByPiecesThatCanMove(turn).isEmpty()){
            System.out.println("Checkmate!! trzeba przerwać program"); // trzeba to bedzie wysłąć metodą do fronta
        } else {
            Tile currentTile = findTileFromChessboard(selectedTile.getName());
            if (currentTile.getPiece() != null && !isTileSelected && checkTurn(currentTile)) {
                showPossibleMoves(currentTile);
            } else if (isTileSelected && this.currentTile.getPiece() != null) {
                move(currentTile);
            }
            updateBacklightSymbol();
        }
    }

    private Tile findTileFromChessboard(String selectedName) {
        for (Tile[] tiles : chessboard.getTiles()) {
            for (Tile tile : tiles) {
                String TileName = tile.getName();
                if (selectedName.equals(TileName)) {
                    return tile;
                }
            }
        }
        return null;
    }

    private boolean checkTurn(Tile currentTile) {
        boolean whitePlayerTurn = turn == Turn.WHITE && currentTile.getPiece().getColor() == Piece.Color.WHITE;
        boolean blackPlayerTurn = turn == Turn.BLACK && currentTile.getPiece().getColor() == Piece.Color.BLACK;
        return whitePlayerTurn || blackPlayerTurn;
    }

    private void changeTurn() {
        if (turn == Turn.WHITE) {
            turn = Turn.BLACK;
        } else {
            turn = Turn.WHITE;
        }
    }

    private void showPossibleMoves(Tile selectedTile) {
        Move move = new Move(chessboard, selectedTile);
        possibleMoves = move.showPossibleMoves();
        if (!possibleMoves.isEmpty()) {
            isTileSelected = true;
            currentTile = selectedTile;
        }
    }

    private void move(Tile selectedTile) {
        if (possibleMoves.contains(selectedTile)) {
            EnPassant.enPassanMove(chessboard, currentTile, selectedTile);
            Castling castling = new Castling(chessboard, currentTile, selectedTile);
            if (castling.canCastling()) {
                castling.castlingMove();
            } else {
                selectedTile.setPiece(currentTile.getPiece());
                currentTile.setPiece(null);
                selectedTile.getPiece().didMove = true;
            }

            Promotion promotion = new Promotion(selectedTile); // to zmienie, jest slabe w sumie. niepotrzebny obiekt
            if(promotion.canPromotePawn()){
                selectedTile.setPiece(promotion.promotion("queen")); // trzeba to zmienić żeby gracz wybierał a nie pisane z palca
            }
            changeTurn();
        }
        isTileSelected = false;
    }

    public static List<Tile> findAllTilesOccupiedByPiecesOfTheSameColor(Chessboard chessboard, Piece.Color colorOfSearchedPieces){
        ArrayList <Tile> tilesOccupiedByPiecesOfTheSameColor  = new ArrayList<>();
        Tile[][]tiles = chessboard.getTiles();
        for (Tile[] row : tiles) {
            Arrays.stream(row)
                    .filter(tile -> tile.getPiece() != null)
                    .filter(tile -> tile.getPiece().getColor() == colorOfSearchedPieces)
                    .forEach(tilesOccupiedByPiecesOfTheSameColor::add);
        }
        return tilesOccupiedByPiecesOfTheSameColor;
    }

    // metoda GameState.findAllTilesOccupiedByPiecesOfTheSameColor(chessboard, currentPlayerColor) nie powinna być w GameState,
    // ale też nie w Check czy Chessboard. Może zrobić oddzielną klasę z metodami static do wyszukiwania z chessboard w sumie to 3 metody

    private List<Tile> findAllTilesOccupiedByPiecesThatCanMove(Turn turn) {
        ArrayList<Tile> allTilesOccupiedByPiecesThatCanMove = new ArrayList<>();
        Piece.Color currentPlayerColor;
        if (turn == Turn.WHITE){
            currentPlayerColor = Piece.Color.WHITE;
        } else{
            currentPlayerColor = Piece.Color.BLACK;
        }
        List<Tile> allTilesOccupiedByCurrentPlayerPieces = findAllTilesOccupiedByPiecesOfTheSameColor(chessboard, currentPlayerColor);
        for (Tile currentPlayerPieceTile : allTilesOccupiedByCurrentPlayerPieces) {
            Move move = new Move(chessboard, currentPlayerPieceTile);
            if(!move.showPossibleMoves().isEmpty()){
                allTilesOccupiedByPiecesThatCanMove.add(currentPlayerPieceTile);
            }
        }
        return allTilesOccupiedByPiecesThatCanMove;
    }

    private void updateBacklightSymbol(){
        if (isTileSelected && !possibleMoves.isEmpty()){
            BacklightTiles.setBacklightSymbol(chessboard, possibleMoves);
        } else {
            BacklightTiles.setBacklightSymbol(chessboard, findAllTilesOccupiedByPiecesThatCanMove(turn));
        }
    }

}