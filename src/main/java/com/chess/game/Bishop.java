package com.chess.game;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceStatus.*;
import static java.lang.Math.*;

final class Bishop extends AbstractPiece {
    Bishop(PlayerType playerType) {
        super(BISHOP, playerType);
    }

    @Override
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        int x = position.getX();
        int y = position.getY();
        int targetX = targetPosition.getX();
        int targetY = targetPosition.getY();
        int lineDiff = targetX - x;
        int diagonalDiff = targetY - y;
        int lineDiffAndDiagonalDiff = lineDiff + diagonalDiff;

        boolean isRange = abs(lineDiff) - abs(diagonalDiff) == 0;
        if(!isRange) return false;

        if(lineDiffAndDiagonalDiff == 0) {
            if(lineDiff > diagonalDiff) {
                for(int i=1; i<lineDiff; i++) {
                    if(board.getPiece(Position.of(x + i, y - i)) instanceof AbstractPiece) {
                        return false;
                    }
                }
            } else {
                for(int i=1; i<diagonalDiff; i++) {
                    if(board.getPiece(Position.of(x - i, y + i)) instanceof AbstractPiece) {
                        return false;
                    }
                }
            }
        } else if(lineDiffAndDiagonalDiff > 0) {
            for(int i=1; i<lineDiff; i++) {
                if(board.getPiece(Position.of(x + i, y + i)) instanceof AbstractPiece) {
                    return false;
                }
            }
        } else {
            for(int i=1; i<abs(lineDiff); i++) {
                if(board.getPiece(Position.of(x - i, y - i)) instanceof AbstractPiece) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        board.setPiece(this, targetPosition);
        return board.getPiece(targetPosition) instanceof NonePiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }
}
