package com.chess.game;

public enum PlayerType {
    WHITE, BLACK, NONE;

    public static PlayerType getEnemyPlayerType(PlayerType playerType) {
        return playerType == WHITE ? BLACK : WHITE;
    }
}
