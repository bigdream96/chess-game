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
    boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(position, targetPosition) && !checkPiecesOnTheWay(board, position, targetPosition);
    }

    @Override
    PieceStatus logic(ChessBoard board, Position position, Position targetPosition) {
        board.setPiece(this, targetPosition);
        if(initPosition) initPosition = false;
        return board.searchPiece(targetPosition) instanceof NullPiece ? ONE_MOVE : TAKES;
    }

    @Override
    boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition) {
        return checkPieceRange(board, position, targetPosition);
    }

    boolean isInitPosition() {
        return initPosition;
    }

    boolean checkPiecesOnTheWay(ChessBoard board, Position position, Position targetPosition) {
        List<Piece> pieces;
        if(position.getX() == targetPosition.getX())
            if(position.getY() > targetPosition.getY())
                pieces = board.searchPieces(targetPosition, position);
            else
                pieces = board.searchPieces(position, targetPosition);
        else if(position.getX() > targetPosition.getX())
            pieces = board.searchPieces(targetPosition, position);
        else
            pieces = board.searchPieces(position, targetPosition);

        pieces.remove(board.searchPiece(position));
        pieces.remove(board.searchPiece(targetPosition));

        for(Piece piece : pieces)
            if(piece instanceof AbstractPiece)
                return true;

        return false;
    }

    private boolean checkPieceRange(Position position, Position targetPosition) {
        return position.getX() == targetPosition.getX() || position.getY() == targetPosition.getY();
    }
}
