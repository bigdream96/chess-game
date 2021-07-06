package com.chess.game;

import static com.chess.game.ChessBoard.*;
import static com.chess.game.PlayerType.*;

public final class ChessBoardSetting {

    public Piece[][] create() {
        Piece[][] board = new Piece[MAX_FILES][MAX_RANKS];

        for(int i=0; i<board.length; i++) {
            switch (i) {
                case 0:
                    board[i] = getFirstPieceList(BLACK);
                    break;
                case 1:
                    board[i] = getPawnList(i, BLACK);
                    break;
                case 2: case 3: case 4: case 5:
                    board[i] = getEmptyPieceList();
                    break;
                case 6:
                    board[i] = getPawnList(i, WHITE);
                    break;
                case 7:
                    board[i] = getFirstPieceList(WHITE);
                    break;
                default:
                    break;
            }
        }

        return board;
    }

    private Piece[] getFirstPieceList(PlayerType playerType) {
        Piece[] pieces = new Piece[MAX_RANKS];

        for(int i=0; i<pieces.length; i++) {
            switch (i) {
                case 0: case 7:
                    pieces[i] = new Rook(playerType);
                    break;
                case 1: case 6:
                    pieces[i] = new Knight(playerType);
                    break;
                case 2: case 5:
                    pieces[i] = new Bishop(playerType);
                    break;
                case 3:
                    pieces[i] = new Queen(playerType);
                    break;
                case 4:
                    pieces[i] = new King(playerType);
                    break;
                default:
                    break;
            }
        }

        return pieces;
    }

    private Piece[] getPawnList(int n, PlayerType playerType) {
        Piece[] pieces = new Piece[MAX_RANKS];

        for(int i=0; i<pieces.length; i++) {
            pieces[i] = new Pawn(playerType, Position.of(n, i));
        }

        return pieces;
    }

    private Piece[] getEmptyPieceList() {
        Piece[] pieces = new Piece[MAX_RANKS];

        for(int i=0; i<pieces.length; i++) {
            pieces[i] = new NonePiece();
        }

        return pieces;
    }
}
