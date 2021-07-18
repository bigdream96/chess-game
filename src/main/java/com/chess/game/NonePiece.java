package com.chess.game;

import static com.chess.game.PieceStatus.*;
import static com.chess.game.PlayerType.*;

final class NonePiece implements Piece {
    @Override
    public PieceType getPieceType() { return PieceType.NONE; }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.NONE;
    }

    public static NonePiece create() { return new NonePiece(); }

    @Override
    public PieceStatus move(ChessBoard board, PlayerType playerType, Position position, Position targetPosition) {
        return INVALID_MOVE;
    }

    @Override
    public String toString() {
        return "[" + PieceType.NONE + "," + PlayerType.NONE + "]";
    }
}
