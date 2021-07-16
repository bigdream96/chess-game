package com.chess.message;

import com.chess.game.*;

import java.util.LinkedList;
import java.util.List;

public final class SystemMessage {
    private final List<List<String>> board;
    private final String playerType;
    private final String gameStatus;
    private PieceType lastPieceType;
    private Position lastPosition;

    private SystemMessage(List<List<String>> board, String playerType, String gameStatus) {
        this.board = board;
        this.playerType = playerType;
        this.gameStatus = gameStatus;
    }

    private SystemMessage(List<List<String>> board, String playerType, String gameStatus, PieceType lastPieceType, Position lastPosition) {
        this.board = board;
        this.playerType = playerType;
        this.gameStatus = gameStatus;
        this.lastPieceType = lastPieceType;
        this.lastPosition = lastPosition;
    }

    public List<List<String>> getBoard() {
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

    private static List<List<String>> makeBoard(List<List<Piece>> board) {
        List<List<String>> strBoard = new LinkedList<>();

        for(int i = 0; i< board.size(); i++) {
            strBoard.add(new LinkedList<>());
            for(int j = 0; j< board.get(0).size(); j++) {
                strBoard.get(i).add((board.get(i).get(j).getPieceType() + "," + board.get(i).get(j).getPlayerType()));
            }
        }

        return strBoard;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                "board=" + board +
                ", playerType=" + playerType +
                ", gameStatus=" + gameStatus +
                ", lastPieceType=" + lastPieceType +
                ", lastPosition=" + lastPosition +
                '}';
    }
}
