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
        return board.searchPiece(targetPosition) instanceof NullPiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }

    private boolean checkVerticalPieces(ChessBoard board, Position position, Position targetPosition) {
        List<Piece> pieces;

        if(position.getX() == targetPosition.getX())
            if(position.getY() > targetPosition.getY())
                pieces = board.searchPieces(targetPosition, position);
            else
                pieces = board.searchPieces(position, targetPosition);
        else if(position.getY() == targetPosition.getY())
            if(position.getX() > targetPosition.getX())
                pieces = board.searchPieces(targetPosition, position);
            else
                pieces = board.searchPieces(position, targetPosition);
        else
            return false;

        pieces.remove(board.searchPiece(position));
        pieces.remove(board.searchPiece(targetPosition));

        for(Piece piece : pieces)
            if(piece instanceof AbstractPiece)
                return false;

        return true;
    }

    private boolean checkDiagonalPieces(ChessBoard board, Position position, Position targetPosition) {
        int x = position.getX();
        int y = position.getY();
        int targetX = targetPosition.getX();
        int targetY = targetPosition.getY();
        int xDiff = targetX - x;
        int yDiff = targetY - y;
        int xDiffAndYDiff = xDiff + yDiff;

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
}
