package com.chess.game;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceStatus.*;
import static java.lang.Math.*;

final class Knight extends AbstractPiece {
    Knight(PlayerType playerType) {
        super(KNIGHT, playerType);
    }

    @Override
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        int lineDiff = abs(targetPosition.getX() - position.getX());
        int diagonalDiff = abs(targetPosition.getY() - position.getY());

        return (0 < lineDiff && lineDiff <= 2) && (0 < diagonalDiff && diagonalDiff <= 2);
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        board.setPiece(this, targetPosition);
        return board.getPiece(targetPosition) instanceof NullPiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }
}
