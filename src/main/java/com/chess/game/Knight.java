package com.chess.game;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceStatus.*;
import static java.lang.Math.*;

final class Knight extends AbstractPiece {
    Knight(PlayerType playerType) {
        super(KNIGHT, playerType);
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
        int n1 = abs(targetPosition.getX() - position.getX());
        int n2 = abs(targetPosition.getY() - position.getY());

        return 0 < n1 && n1 <= 2 && 0 < n2 && n2 <= 2;
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

    @Override
    public String toString() {
        return super.toString();
    }
}
