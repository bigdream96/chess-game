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
        int xDiff = targetX - x;
        int yDiff = targetY - y;
        int xDiffAndYDiff = xDiff + yDiff;

        boolean isRange = abs(xDiff) - abs(yDiff) == 0;
        if(!isRange) return false;

        if(xDiffAndYDiff == 0) {
            if(xDiff > yDiff) {
                for(int i=1; i<xDiff; i++) {
                    if(board.searchPiece(Position.of(x + i, y - i)) instanceof AbstractPiece) {
                        return false;
                    }
                }
            } else {
                for(int i=1; i<yDiff; i++) {
                    if(board.searchPiece(Position.of(x - i, y + i)) instanceof AbstractPiece) {
                        return false;
                    }
                }
            }
        } else if(xDiffAndYDiff > 0) {
            for(int i=1; i<xDiff; i++) {
                if(board.searchPiece(Position.of(x + i, y + i)) instanceof AbstractPiece) {
                    return false;
                }
            }
        } else {
            for(int i=1; i<abs(xDiff); i++) {
                if(board.searchPiece(Position.of(x - i, y - i)) instanceof AbstractPiece) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        board.setPiece(this, targetPosition);
        return board.searchPiece(targetPosition) instanceof NullPiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }
}
