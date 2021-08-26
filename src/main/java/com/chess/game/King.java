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
        int xDiff = abs(targetPosition.getX() - position.getX());
        int yDiff = abs(targetPosition.getY() - position.getY());
        boolean isRange = (0 <= xDiff && xDiff <= 1) && (0 <= yDiff && yDiff <= 1);
        return isRange || checkCastling(board, position, targetPosition);
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        int yDiff = targetPosition.getY() - position.getY();

        if(checkCastling(board, position, targetPosition)) {
            Position rookPosition = Position.of(position.getX(), position.getY()+(yDiff > 0 ? 3 : -4));
            Rook rook = (Rook)board.searchPiece(rookPosition);

            board.setPiece(rook, Position.of(rookPosition.getX(), rookPosition.getY()+(yDiff > 0 ? -2 : 3)));
            board.setPiece(this, Position.of(position.getX(), position.getY()+(yDiff > 0 ? 2 : -2)));

            return CASTLING;
        }

        board.setPiece(this, targetPosition);
        if(isInitPosition()) initPosition = false;

        return board.searchPiece(targetPosition) instanceof NullPiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }

    boolean checkCastling(ChessBoard board, Position position, Position targetPosition) {
        int yDiff = targetPosition.getY() - position.getY();

        if(isInitPosition()
        && targetPosition.getX() == position.getX()
        && !isCheck(board)
        && !board.rangeAttackPossible(getEnemyPlayerType(getPlayerType()), position, targetPosition)
        && abs(yDiff) == 2) {
            Position rookPosition = Position.of(position.getX(), position.getY()+(yDiff > 0 ? 3 : -4));
            Piece p = board.searchPiece(rookPosition);

            if(p.getPieceType() == ROOK) {
                Rook rook = (Rook)p;

                return rook.isInitPosition()
                    && board.rangeEmpty(Position.of(position.getX(), position.getY() + (yDiff > 0 ? 1 : -3)),
                                        Position.of(position.getX(), position.getY() + (yDiff > 0 ? 2 : -1)));
            }
        }

        return false;
    }

    boolean isCheck(ChessBoard board) {
        List<AbstractPiece> pieces = board.searchPlayerPieces(getEnemyPlayerType(getPlayerType()));

        for(AbstractPiece piece : pieces)
            if(piece.checkPieceRange(board, board.searchPosition(piece), board.searchPosition(this)))
                return true;

        return false;
    }

    boolean isCheckmate(ChessBoard board) {
        PlayerType enemyPlayerType = getEnemyPlayerType(getPlayerType());
        Position selfPosition = board.searchPosition(this);
        Map<Position, Boolean> possiblePositions = new LinkedHashMap<>();
        Map<AbstractPiece, Boolean> attackEnemyPieces = new LinkedHashMap<>();
        List<AbstractPiece> pieces = board.searchPlayerPieces(getPlayerType());
        List<AbstractPiece> enemyPieces = board.searchPlayerPieces(enemyPlayerType);

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                Position position = Position.of(selfPosition.getX()+i, selfPosition.getY()+j);
                if(board.validPiecePosition(position))
                    if(board.searchPiece(position).getPlayerType() != getPlayerType())
                        possiblePositions.put(position, false);
            }
        }

        for(AbstractPiece enemyPiece : enemyPieces) {
            for (Position possiblePosition : possiblePositions.keySet()) {
                if (enemyPiece.validate(board, enemyPlayerType, board.searchPosition(enemyPiece), possiblePosition)) {
                    possiblePositions.put(possiblePosition, true);
                    attackEnemyPieces.put(enemyPiece, false);
                }
            }
        }

        for(AbstractPiece piece : pieces) {
            for (AbstractPiece enemyPiece : attackEnemyPieces.keySet()) {
                if (piece.validate(board, getPlayerType(), board.searchPosition(piece), board.searchPosition(enemyPiece))) {
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
