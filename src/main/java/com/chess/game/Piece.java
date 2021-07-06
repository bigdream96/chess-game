package com.chess.game;

public interface Piece {
    PieceType getPieceType();
    PlayerType getPlayerType();
    PieceStatus move(ChessBoard board, Position position, Position targetPosition);
}
