package com.chess.game;

public interface Rule {
    GameResult judge(PlayerType playerType, ChessBoard board, Piece piece, PieceStatus pieceStatus);
}
