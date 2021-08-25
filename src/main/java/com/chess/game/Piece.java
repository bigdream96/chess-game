package com.chess.game;

public interface Piece {
    PieceType getPieceType();
    PlayerType getPlayerType();
//    Position getPosition();
    PieceStatus move(ChessBoard board, PlayerType playerType, Position position, Position targetPosition);
}
