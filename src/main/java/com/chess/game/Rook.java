package com.chess.game;

import java.util.List;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceStatus.*;

final class Rook extends AbstractPiece {
    private boolean initPosition;

    Rook(PlayerType playerType) {
        super(ROOK, playerType);
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
        if(!(position.getX() == targetPosition.getX() || position.getY() == targetPosition.getY()))
            return false;

        List<Piece> pieces;
        if(position.getX() == targetPosition.getX())
            if(position.getY() > targetPosition.getY())
                pieces = board.getPieces(targetPosition, position);
            else
                pieces = board.getPieces(position, targetPosition);
        else if(position.getX() > targetPosition.getX())
            pieces = board.getPieces(targetPosition, position);
        else
            pieces = board.getPieces(position, targetPosition);

        pieces.remove(board.getPiece(position));
        pieces.remove(board.getPiece(targetPosition));

        for(Piece piece : pieces)
            if(piece instanceof AbstractPiece)
                return false;

        return true;
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        board.setPiece(this, targetPosition);
        if(initPosition) initPosition = false;
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
