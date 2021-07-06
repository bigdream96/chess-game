package com.chess.game;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceStatus.*;

abstract class AbstractPiece implements Piece {
    private final PieceType pieceType;
    private final PlayerType playerType;

    AbstractPiece(PieceType pieceType, PlayerType playerType) {
        this.pieceType = pieceType;
        this.playerType = playerType;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public PlayerType getPlayerType() {
        return playerType;
    }

    @Override
    public PieceStatus move(ChessBoard board, Position position, Position targetPosition) {
        if(validate(board, position, targetPosition)) {
            return logic(board, position, targetPosition);
        } else {
            return INVALID_MOVE;
        }
    }

    boolean validate(ChessBoard board, Position position, Position targetPosition) {
        Piece piece = board.getPiece(position);
        Piece targetPiece = board.getPiece(targetPosition);

        if(position.equals(targetPosition) || !board.validPiecePosition(targetPosition))
            return false;
        if(targetPiece.getPieceType() == KING)
            return false;
        if(piece.getPlayerType() == targetPiece.getPlayerType())
            return false;

        return checkPieceRange(board, position, targetPosition);
    }

    // 범위체크
    abstract boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition);

    // 행마
    abstract PieceStatus logic(ChessBoard board, Position position, Position targetPosition);
    
    // 해당위치 공격가능한지 체크
    abstract boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition);

    @Override
    public String toString() {
        return "[" + pieceType + "," + playerType + "]";
    }
}
