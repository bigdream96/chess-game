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

    @Override
    public PieceType getPieceType() {
        return super.getPieceType();
    }

    @Override
    public PlayerType getPlayerType() {
        return super.getPlayerType();
    }

    boolean isInitPosition() {
        return initPosition;
    }

    Position getPrevPosition() { return prevPosition; }

    @Override
    public PieceStatus move(ChessBoard board, Position position, Position targetPosition) {
        return super.move(board, position, targetPosition);
    }

    @Override
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        Piece targetPiece = board.getPiece(targetPosition);
        int n1 = targetPosition.getX() - position.getX();
        int n2 = targetPosition.getY() - position.getY();

        if(getPlayerType() == WHITE && n1 > 0)
            return false;
        else if(getPlayerType() == BLACK && n1 < 0)
            return false;

        if(abs(n2) == 0) {
            if(isInitPosition()) {
                if(abs(n1) == 2) {
                    Position middlePosition = Position.of(position.getX()+(n1 > 0 ? 1 : -1), position.getY());
                    Piece middlePiece = board.getPiece(middlePosition);
                    return (targetPiece instanceof NonePiece) && (middlePiece instanceof NonePiece);
                } else {
                    return (targetPiece instanceof NonePiece) && (abs(n1) == 1);
                }
            } else {
                return (targetPiece instanceof NonePiece) && (abs(n1) == 1);
            }
        } else {
            if(checkEnPassant(board, position, targetPosition)) {
                return (targetPiece instanceof NonePiece) && (abs(n1) == 1);
            } else {
                return (targetPiece instanceof AbstractPiece) && (abs(n1) == 1) && (abs(n2) == 1);
            }
        }
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        // 프로모션체크
        if((getPlayerType() == WHITE && targetPosition.getX() == 0)
        || (getPlayerType() == BLACK && targetPosition.getX() == 7)) {
            board.setPiece(this, targetPosition);
            return PROMOTION;
        }

        // 앙파상체크
        if(checkEnPassant(board, position, targetPosition)) {
            Piece piece = board.getPiece(Position.of(position.getX(), targetPosition.getY()));
            board.deletePiece(piece);
            board.setPiece(this, targetPosition);
            return EN_PASSANT;
        }

        board.setPiece(this, targetPosition);

        if(initPosition) initPosition = false;
        prevPosition = Position.copy(position);

        return board.getPiece(targetPosition) instanceof NonePiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        if(checkPieceRange(board, position, targetPosition)) {
            Piece targetPiece = board.getPiece(targetPosition);
            int n1 = targetPosition.getX() - position.getX();
            int n2 = targetPosition.getY() - position.getY();

            return abs(n1) == 1 && abs(n2) == 1 && targetPiece instanceof AbstractPiece;
        }

        return false;
    }

    // 앙파상체크
    private boolean checkEnPassant(ChessBoard board, Position position, Position targetPosition) {
        if(abs(targetPosition.getX() - position.getX()) == 1 && abs(targetPosition.getY() - position.getY()) == 1) {
            Piece piece = board.getPiece(Position.of(position.getX(), targetPosition.getY()));

            if(piece.getPieceType() == PAWN) {
                Pawn pawn = (Pawn)piece;
                Position pawnPosition = board.getPosition(pawn);

                return abs(pawn.getPrevPosition().getX() - pawnPosition.getX()) == 2;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
