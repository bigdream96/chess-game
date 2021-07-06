package com.chess.game;

final class GameResult {
    private final PlayerType playerType;
    private final GameStatus gameStatus;
    private PieceType pieceType;
    private Position position;

    public GameResult(PlayerType playerType, GameStatus gameStatus) {
        this.playerType = playerType;
        this.gameStatus = gameStatus;
    }

    public GameResult(PlayerType playerType, PieceType pieceType, Position position, GameStatus gameStatus) {
        this.playerType = playerType;
        this.pieceType = pieceType;
        this.position = position;
        this.gameStatus = gameStatus;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "playerType=" + playerType +
                ", gameStatus=" + gameStatus +
                ", pieceType=" + pieceType +
                ", position=" + position +
                '}';
    }
}
