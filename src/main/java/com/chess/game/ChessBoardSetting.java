package com.chess.game;

import java.util.LinkedList;
import java.util.List;

import static com.chess.game.ChessBoard.*;
import static com.chess.game.PlayerType.*;

public final class ChessBoardSetting {

    public List<List<Piece>> create() {
        List<List<Piece>> board = new LinkedList<>();

        for(int i = 0; i< MAX_NUM_OF_LINE; i++) {
            board.add(new LinkedList<>());

            switch (i) {
                case 0:
                    board.get(i).addAll(getFirstPieceList(BLACK));
                    break;
                case 1:
                    board.get(i).addAll(getPawnList(i, BLACK));
                    break;
                case 2: case 3: case 4: case 5:
                    board.get(i).addAll(getEmptyPieceList());
                    break;
                case 6:
                    board.get(i).addAll(getPawnList(i, WHITE));
                    break;
                case 7:
                    board.get(i).addAll(getFirstPieceList(WHITE));
                    break;
                default:
                    break;
            }
        }

        return board;
    }

    private List<Piece> getFirstPieceList(PlayerType playerType) {
        List<Piece> pieces = new LinkedList<>();

        for(int i = 0; i < MAX_NUM_OF_LINE; i++) {
            switch (i) {
                case 0: case 7:
                    pieces.add(new Rook(playerType));
                    break;
                case 1: case 6:
                    pieces.add(new Knight(playerType));
                    break;
                case 2: case 5:
                    pieces.add(new Bishop(playerType));
                    break;
                case 3:
                    pieces.add(new Queen(playerType));
                    break;
                case 4:
                    pieces.add(new King(playerType));
                    break;
                default:
                    break;
            }
        }

        return pieces;
    }

    private List<Piece> getPawnList(int n, PlayerType playerType) {
        List<Piece> pieces = new LinkedList<>();

        for(int i = 0; i< MAX_NUM_OF_LINE; i++) {
            pieces.add(new Pawn(playerType, Position.of(n, i)));
        }

        return pieces;
    }

    private List<Piece> getEmptyPieceList() {
        List<Piece> pieces = new LinkedList<>();

        for(int i = 0; i< MAX_NUM_OF_LINE; i++) {
            pieces.add(new NonePiece());
        }

        return pieces;
    }
}
