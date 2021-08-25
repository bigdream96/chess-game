package com.chess.game;

import java.util.*;

import static com.chess.game.PieceType.*;
import static com.chess.game.PlayerType.*;
import static com.chess.game.PieceStatus.*;
import static java.lang.Math.*;

final class King extends AbstractPiece {
    private boolean initPosition;

    King(PlayerType playerType) {
        super(KING, playerType);
        initPosition = true;
    }

    boolean isInitPosition() {
        return initPosition;
    }

    @Override
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        int lineDiff = abs(targetPosition.getX() - position.getX());
        int diagonalDiff = abs(targetPosition.getY() - position.getY());
        boolean isRange = (0 <= lineDiff && lineDiff <= 1) && (0 <= diagonalDiff && diagonalDiff <= 1);
        return isRange || checkCastling(board, position, targetPosition);
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        int diagonalDiff = targetPosition.getY() - position.getY();

        if(checkCastling(board, position, targetPosition)) {
            Position rookPosition = Position.of(position.getX(), position.getY()+(diagonalDiff > 0 ? 3 : -4));
            Rook rook = (Rook)board.getPiece(rookPosition);

            board.setPiece(rook, Position.of(rookPosition.getX(), rookPosition.getY()+(diagonalDiff > 0 ? -2 : 3)));
            board.setPiece(this, Position.of(position.getX(), position.getY()+(diagonalDiff > 0 ? 2 : -2)));

            return CASTLING;
        }

        board.setPiece(this, targetPosition);
        if(isInitPosition()) initPosition = false;

        return board.getPiece(targetPosition) instanceof NullPiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }

    boolean checkCastling(ChessBoard board, Position position, Position targetPosition) {
        int diagonalDiff = targetPosition.getY() - position.getY();

        if(isInitPosition()
        && targetPosition.getX() == position.getX()
        && !isCheck(board)
        && !board.rangeAttackPossible(getEnemyPlayerType(getPlayerType()), position, targetPosition)
        && abs(diagonalDiff) == 2) {
            Position rookPosition = Position.of(position.getX(), position.getY()+(diagonalDiff > 0 ? 3 : -4));
            Piece p = board.getPiece(rookPosition);

            if(p.getPieceType() == ROOK) {
                Rook rook = (Rook)p;

                return rook.isInitPosition()
                    && board.rangeEmpty(Position.of(position.getX(), position.getY() + (diagonalDiff > 0 ? 1 : -3)),
                                        Position.of(position.getX(), position.getY() + (diagonalDiff > 0 ? 2 : -1)));
            }
        }

        return false;
    }

    boolean isCheck(ChessBoard board) {
        List<AbstractPiece> pieces = board.getPlayerPieces(getEnemyPlayerType(getPlayerType()));

        for(AbstractPiece piece : pieces)
            if(piece.checkPieceRange(board, board.getPosition(piece), board.getPosition(this)))
                return true;

        return false;
    }

    boolean isCheckmate(ChessBoard board) {
        PlayerType enemyPlayerType = getEnemyPlayerType(getPlayerType());
        Position selfPosition = board.getPosition(this);
        Map<Position, Boolean> possiblePositions = new LinkedHashMap<>();
        Map<AbstractPiece, Boolean> attackEnemyPieces = new LinkedHashMap<>();
        List<AbstractPiece> pieces = board.getPlayerPieces(getPlayerType());
        List<AbstractPiece> enemyPieces = board.getPlayerPieces(enemyPlayerType);

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                Position position = Position.of(selfPosition.getX()+i, selfPosition.getY()+j);
                if(board.validPiecePosition(position))
                    if(board.getPiece(position).getPlayerType() != getPlayerType())
                        possiblePositions.put(position, false);
            }
        }

        for(AbstractPiece enemyPiece : enemyPieces) {
            for (Position possiblePosition : possiblePositions.keySet()) {
                if (enemyPiece.validate(board, enemyPlayerType, board.getPosition(enemyPiece), possiblePosition)) {
                    possiblePositions.put(possiblePosition, true);
                    attackEnemyPieces.put(enemyPiece, false);
                }
            }
        }

        for(AbstractPiece piece : pieces) {
            for (AbstractPiece enemyPiece : attackEnemyPieces.keySet()) {
                if (piece.validate(board, getPlayerType(), board.getPosition(piece), board.getPosition(enemyPiece))) {
                    attackEnemyPieces.put(enemyPiece, true);
                }
            }
        }

        return possiblePositions.size() > 0
            && !possiblePositions.containsValue(false)
            && !attackEnemyPieces.containsValue(true);
    }

    boolean isStalemate(ChessBoard board) {
        return !isCheck(board) && isCheckmate(board);
    }
}
