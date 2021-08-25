package com.chess.game;

import static com.chess.game.PieceStatus.*;

final class NullPiece implements Piece {
    @Override
    public PieceType getPieceType() { return PieceType.NONE; }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.NONE;
    }

    public static NullPiece create() { return new NullPiece(); }

    @Override
    public PieceStatus move(ChessBoard board, PlayerType playerType, Position position, Position targetPosition) {
        return INVALID_MOVE;
    }

    @Override
    public String toString() {
        return "[" + PieceType.NONE + "," + PlayerType.NONE + "]";
    }
}
