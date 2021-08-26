package com.chess.game;

import static com.chess.game.PieceType.*;
import static com.chess.game.PlayerType.*;
import static com.chess.game.PieceStatus.*;
import static java.lang.Math.*;

final class Pawn extends AbstractPiece {
    private boolean initPosition;
    private Position prevPosition;

    Pawn(PlayerType playerType, Position position) {
        super(PAWN, playerType);
        prevPosition = position;
        initPosition = true;
    }

    boolean isInitPosition() {
        return initPosition;
    }

    Position getPrevPosition() { return prevPosition; }

    @Override
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        Piece targetPiece = board.searchPiece(targetPosition);
        int xDiff = targetPosition.getX() - position.getX();
        int yDiff = targetPosition.getY() - position.getY();

        if(getPlayerType() == WHITE && xDiff > 0)
            return false;
        else if(getPlayerType() == BLACK && xDiff < 0)
            return false;

        if(abs(yDiff) == 0) {
            if(isInitPosition()) {
                if(abs(xDiff) == 2) {
                    Position middlePosition = Position.of(position.getX()+(xDiff > 0 ? 1 : -1), position.getY());
                    Piece middlePiece = board.searchPiece(middlePosition);
                    return (targetPiece instanceof NullPiece) && (middlePiece instanceof NullPiece);
                } else {
                    return (targetPiece instanceof NullPiece) && (abs(xDiff) == 1);
                }
            } else {
                return (targetPiece instanceof NullPiece) && (abs(xDiff) == 1);
            }
        } else {
            if(checkEnPassant(board, position, targetPosition)) {
                return (targetPiece instanceof NullPiece) && (abs(xDiff) == 1);
            } else {
                return (targetPiece instanceof AbstractPiece) && (abs(xDiff) == 1) && (abs(yDiff) == 1);
            }
        }
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        if(checkPromotion(board, position, targetPosition)) {
            board.setPiece(this, targetPosition);
            return PROMOTION;
        }

        if(checkEnPassant(board, position, targetPosition)) {
            Piece piece = board.searchPiece(Position.of(position.getX(), targetPosition.getY()));
            board.deletePiece(piece);
            board.setPiece(this, targetPosition);
            return EN_PASSANT;
        }

        board.setPiece(this, targetPosition);

        if(isInitPosition()) initPosition = false;
        prevPosition = Position.copy(position);

        return board.searchPiece(targetPosition) instanceof NullPiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        if(checkPieceRange(board, position, targetPosition)) {
            Piece targetPiece = board.searchPiece(targetPosition);
            int xDiff = targetPosition.getX() - position.getX();
            int yDiff = targetPosition.getY() - position.getY();

            return abs(xDiff) == 1 && abs(yDiff) == 1 && targetPiece instanceof AbstractPiece;
        }

        return false;
    }

    private boolean checkPromotion(ChessBoard board, Position position, Position targetPosition) {
        return (getPlayerType() == WHITE && targetPosition.getX() == 0)
                || (getPlayerType() == BLACK && targetPosition.getX() == 7);
    }

    private boolean checkEnPassant(ChessBoard board, Position position, Position targetPosition) {
        if(abs(targetPosition.getX() - position.getX()) == 1 && abs(targetPosition.getY() - position.getY()) == 1) {
            Piece piece = board.searchPiece(Position.of(position.getX(), targetPosition.getY()));

            if(piece.getPieceType() == PAWN) {
                Pawn pawn = (Pawn)piece;
                Position pawnPosition = board.searchPosition(pawn);

                return abs(pawn.getPrevPosition().getX() - pawnPosition.getX()) == 2;
            }
        }

        return false;
    }
}
