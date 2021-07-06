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

    @Override
    public PieceStatus move(ChessBoard board, Position position, Position targetPosition) {
        return super.move(board, position, targetPosition);
    }

    @Override
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        int n1 = abs(targetPosition.getX() - position.getX());
        int n2 = abs(targetPosition.getY() - position.getY());
        return checkCastling(board, position, targetPosition) || ( (0 <= n1 && n1 <=1) && (0 <= n2 && n2 <=1) );
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        int n = targetPosition.getY() - position.getY();

        // 캐슬링인 경우
        if(checkCastling(board, position, targetPosition)) {
            Position rookPosition = Position.of(position.getX(), position.getY()+(n > 0 ? 3 : -4));
            Rook rook = (Rook)board.getPiece(rookPosition);

            board.setPiece(rook, Position.of(rookPosition.getX(), rookPosition.getY()+(n > 0 ? -2 : 3)));
            board.setPiece(this, Position.of(position.getX(), position.getY()+(n > 0 ? 2 : -2)));

            return CASTLING;
        }

        board.setPiece(this, targetPosition);
        if(initPosition) initPosition = false;

        return board.getPiece(targetPosition) instanceof NonePiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }

    boolean checkCastling(ChessBoard board, Position position, Position targetPosition) {
        int n = targetPosition.getY() - position.getY();

        // 캐슬링인 경우
        if(isInitPosition()
        && targetPosition.getX() == position.getX()
        && !isCheck(board)
        && !board.rangeAttackPossible(getEnemyPlayerType(getPlayerType()), position, targetPosition)
        && abs(n) == 2) {
            Position rookPosition = Position.of(position.getX(), position.getY()+(n > 0 ? 3 : -4));
            Piece p = board.getPiece(rookPosition);

            if(p.getPieceType() == ROOK) {
                Rook rook = (Rook)p;

                return rook.isInitPosition()
                    && board.rangeEmpty(Position.of(position.getX(), position.getY() + (n > 0 ? 1 : -3)),
                                        Position.of(position.getX(), position.getY() + (n > 0 ? 2 : -1)));
            }
        }

        return false;
    }

    /* 해당 킹이 체크인지 확인 */
    boolean isCheck(ChessBoard board) {
        List<AbstractPiece> pieces = board.getPlayerPieces(getEnemyPlayerType(getPlayerType()));

        for(AbstractPiece piece : pieces)
            if(piece.checkPieceRange(board, board.getPosition(piece), board.getPosition(this)))
                return true;

        return false;
    }
    
    /* 해당 킹이 체크메이트인지 확인 */
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
                if (enemyPiece.validate(board, board.getPosition(enemyPiece), possiblePosition)) {
                    possiblePositions.put(possiblePosition, true);
                    attackEnemyPieces.put(enemyPiece, false);
                }
            }
        }

        for(AbstractPiece piece : pieces) {
            for (AbstractPiece enemyPiece : attackEnemyPieces.keySet()) {
                if (piece.validate(board, board.getPosition(piece), board.getPosition(enemyPiece))) {
                    attackEnemyPieces.put(enemyPiece, true);
                }
            }
        }

        return possiblePositions.size() > 0
            && !possiblePositions.containsValue(false)
            && !attackEnemyPieces.containsValue(true);
    }
    
    /* 해당 킹이 스테일메이트인지 확인 */
    boolean isStalemate(ChessBoard board) {
        return !isCheck(board) && isCheckmate(board);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
