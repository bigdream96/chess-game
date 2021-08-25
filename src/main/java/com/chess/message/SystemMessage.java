package com.chess.message;

import com.chess.game.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SystemMessage {
    private final String[][] board;
    private final String playerType;
    private final String gameStatus;
    private PieceType lastPieceType;
    private Position lastPosition;

    public SystemMessage(String[][] board, String playerType, String gameStatus) {
        this.board = board;
        this.playerType = playerType;
        this.gameStatus = gameStatus;
    }

    public SystemMessage(String[][] board, String playerType, String gameStatus, PieceType lastPieceType, Position lastPosition) {
        this.board = board;
        this.playerType = playerType;
        this.gameStatus = gameStatus;
        this.lastPieceType = lastPieceType;
        this.lastPosition = lastPosition;
    }

    public String[][] getBoard() {
        return board;
    }

    public String getPlayerType() {
        return playerType;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public PieceType getLastPieceType() { return lastPieceType; }

    public Position getLastPosition() { return lastPosition; }

    public static SystemMessage of(ChessBoard board, PlayerType playerType, GameStatus gameStatus) {
        return new SystemMessage(makeBoard(board.getBoard()), playerType.name(), gameStatus.name());
    }

    public static SystemMessage of(ChessBoard board, PlayerType playerType, GameStatus gameStatus, PieceType lastPieceType, Position lastPosition) {
        return new SystemMessage(makeBoard(board.getBoard()), playerType.name(), gameStatus.name(), lastPieceType, lastPosition);
    }

    private static String[][] makeBoard(Piece[][] board) {
        String[][] strBoard = new String[board.length][board[0].length];

        for(int i = 0; i< board.length; i++) {
            for(int j = 0; j< board[0].length; j++) {
                strBoard[i][j] = board[i][j].getPieceType() + "," + board[i][j].getPlayerType();
            }
        }

        return strBoard;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                "board=" + Arrays.toString(board) +
                ", playerType=" + playerType +
                ", gameStatus=" + gameStatus +
                ", lastPieceType=" + lastPieceType +
                ", lastPosition=" + lastPosition +
                '}';
    }
}
