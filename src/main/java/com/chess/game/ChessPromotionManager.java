package com.chess.game;

public class ChessPromotionManager {
    Piece createNewPiece(PlayerType playerType, PieceType pieceType, Position position) {
        Piece piece;

        switch (pieceType) {
            case QUEEN:
                piece = new Queen(playerType);
                break;
            case ROOK:
                piece = new Rook(playerType);
                break;
            case BISHOP:
                piece = new Bishop(playerType);
                break;
            case KNIGHT:
                piece = new Knight(playerType);
                break;
            default:
                throw new IllegalArgumentException("승진할 수 없는 기물입니다.");
        }

        return piece;
    }
}
