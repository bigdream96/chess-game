package com.chess.game;

import java.util.List;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceStatus.*;
import static java.lang.Math.abs;

final class Queen extends AbstractPiece {
    Queen(PlayerType playerType) {
        super(QUEEN, playerType);
    }

    @Override
    public PieceType getPieceType() {
        return super.getPieceType();
    }

    @Override
    public PlayerType getPlayerType() {
        return super.getPlayerType();
    }

    @Override
    public PieceStatus move(ChessBoard board, Position position, Position targetPosition) {
        return super.move(board, position, targetPosition);
    }

    @Override
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        if(position.getX() == targetPosition.getX() || position.getY() == targetPosition.getY())
            return checkVerticalPieces(board, position, targetPosition);
        else if(abs(targetPosition.getX() - position.getX()) - abs(targetPosition.getY() - position.getY()) == 0)
            return checkDiagonalPieces(board, position, targetPosition);
        else
            return false;
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

    private boolean checkVerticalPieces(ChessBoard board, Position position, Position targetPosition) {
        List<Piece> pieces;

        if(position.getX() == targetPosition.getX())
            if(position.getY() > targetPosition.getY())
                pieces = board.getPieces(targetPosition, position);
            else
                pieces = board.getPieces(position, targetPosition);
        else if(position.getY() == targetPosition.getY())
            if(position.getX() > targetPosition.getX())
                pieces = board.getPieces(targetPosition, position);
            else
                pieces = board.getPieces(position, targetPosition);
        else
            return false;

        pieces.remove(board.getPiece(position));
        pieces.remove(board.getPiece(targetPosition));

        for(Piece piece : pieces)
            if(piece instanceof AbstractPiece)
                return false;

        return true;
    }

    private boolean checkDiagonalPieces(ChessBoard board, Position position, Position targetPosition) {
        int positionX = position.getX();
        int positionY = position.getY();
        int targetPositionX = targetPosition.getX();
        int targetPositionY = targetPosition.getY();
        int n1 = targetPositionX - positionX;
        int n2 = targetPositionY - positionY;
        int n3 = n1 + n2;

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
    public String toString() {
        return super.toString();
    }
}
