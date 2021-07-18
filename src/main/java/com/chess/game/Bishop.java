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
        int positionX = position.getX();
        int positionY = position.getY();
        int targetPositionX = targetPosition.getX();
        int targetPositionY = targetPosition.getY();
        int n1 = targetPositionX - positionX;
        int n2 = targetPositionY - positionY;
        int n3 = n1 + n2;

        if(!(abs(targetPositionX - positionX) - abs(targetPositionY - positionY) == 0)) {
            return false;
        }

        if(n3 == 0) {
            if(n1 > n2) {
                for(int i=1; i<n1; i++) {
                    if(board.getPiece(Position.of(positionX + i, positionY - i)) instanceof AbstractPiece) {
                        return false;
                    }
                }
            } else {
                for(int i=1; i<n2; i++) {
                    if(board.getPiece(Position.of(positionX - i, positionY + i)) instanceof AbstractPiece) {
                        return false;
                    }
                }
            }
        } else if(n3 > 0) {
            for(int i=1; i<n1; i++) {
                if(board.getPiece(Position.of(positionX + i, positionY + i)) instanceof AbstractPiece) {
                    return false;
                }
            }
        } else {
            for(int i=1; i<abs(n1); i++) {
                if(board.getPiece(Position.of(positionX - i, positionY - i)) instanceof AbstractPiece) {
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
