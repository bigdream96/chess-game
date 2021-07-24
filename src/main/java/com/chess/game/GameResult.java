package com.chess.game;

final class GameResult {
    private final PlayerType playerType;
    private final GameStatus gameStatus;
    private final PieceType pieceType;
    private final Position position;

    private GameResult(PlayerType playerType, PieceType pieceType, Position position, GameStatus gameStatus) {
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

    public static GameResult of(PlayerType playerType, PieceType pieceType, Position position, GameStatus gameStatus) {
        return new GameResult(playerType, pieceType, position, gameStatus);
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
