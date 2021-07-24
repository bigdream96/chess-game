package com.chess.game;

import java.util.LinkedList;
import java.util.List;

import static com.chess.game.PieceStatus.*;
import static com.chess.game.PieceType.*;
import static com.chess.game.PlayerType.*;
import static com.chess.game.GameStatus.*;
import static java.lang.Math.*;

public final class ChessRule implements Rule {

    private final ChessGameNotation chessGameNotation;

    public ChessRule(ChessGameNotation chessGameNotation) {
        this.chessGameNotation = chessGameNotation;
    }

    @Override
    public GameResult judge(PlayerType playerType, ChessBoard board, Piece piece, PieceStatus pieceStatus) {
        if (pieceStatus == INVALID_MOVE)
            return GameResult.of(playerType, piece.getPieceType(), board.getPosition(piece), AGAIN);

        GameStatus gameStatus;
        King enemyKing = (King)board.getPlayerPiece(getEnemyPlayerType(playerType), KING);

        if(pieceStatus == PROMOTION)
            gameStatus = PAWN_PROMOTION;
        else if(enemyKing.isCheckmate(board) && enemyKing.isCheck(board))
            gameStatus = CHECKMATE;
        else if(enemyKing.isStalemate(board))
            gameStatus = STALEMATE;
        else if(checkLackOfPiece(playerType, board))
            gameStatus = LACK_OF_CHESS_PIECES;
        else if(checkFiftyMoveRule(playerType))
            gameStatus = FIFTY_MOVE_RULE;
        else if(checkThreeIsomorphicRepetitions(playerType, NotationItem.of(piece, board.getPosition(piece), pieceStatus)))
            gameStatus = THREE_ISOMORPHIC_REPETITIONS;
        else
            gameStatus = CONTINUE;

        chessGameNotation.add(playerType, NotationItem.of(piece, board.getPosition(piece), pieceStatus));

        return GameResult.of(playerType, piece.getPieceType(), board.getPosition(piece), gameStatus);
    }

    private boolean checkFiftyMoveRule(PlayerType playerType) {
        return chessGameNotation.countNotation(playerType) >= 50
            && chessGameNotation.countPiece(playerType, PAWN) == 0
            && chessGameNotation.countPieceStatus(playerType, TAKES) == 0;
    }

    private boolean checkThreeIsomorphicRepetitions(PlayerType playerType, NotationItem notationItem) {
        int result = 1;

        for(NotationItem item : chessGameNotation.get(playerType))
            if(notationItem.equals(item))
                result++;

        return result >= 3;
    }

    private boolean checkLackOfPiece(PlayerType playerType, ChessBoard board) {
        return isOnlyKing(playerType, board)
            || isOnlyKingAndBishopOrKnight(playerType, board)
            || isOnlyKingAndBishopIsSameColor(playerType, board);
    }

    private boolean isOnlyKing(PlayerType playerType, ChessBoard board) {
        List<Piece> pieces = new LinkedList<>();
        pieces.addAll(board.getPlayerPieces(playerType));
        pieces.addAll(board.getPlayerPieces(getEnemyPlayerType(playerType)));

        return (pieces.size() == 2) && board.containsPiece(WHITE, KING) && board.containsPiece(BLACK, KING);
    }

    private boolean isOnlyKingAndBishopOrKnight(PlayerType playerType, ChessBoard board) {
        List<Piece> pieces = new LinkedList<>();
        pieces.addAll(board.getPlayerPieces(playerType));
        pieces.addAll(board.getPlayerPieces(getEnemyPlayerType(playerType)));

        for(Piece piece : pieces) {
            if(piece.getPieceType() == BISHOP || piece.getPieceType() == KNIGHT) {
                return (pieces.size() == 3) && board.containsPiece(WHITE, KING) && board.containsPiece(BLACK, KING);
            }
        }

        return false;
    }

    private boolean isOnlyKingAndBishopIsSameColor(PlayerType playerType, ChessBoard board) {
        List<Bishop> bishops = new LinkedList<>();
        List<Piece> pieces = new LinkedList<>();
        pieces.addAll(board.getPlayerPieces(playerType));
        pieces.addAll(board.getPlayerPieces(getEnemyPlayerType(playerType)));

        if(pieces.size() != 4)
            return false;

        for(Piece piece : pieces)
            if(!(piece.getPieceType() == PieceType.KING || piece.getPieceType() == BISHOP))
                return false;
            else if(piece.getPieceType() == BISHOP)
                bishops.add((Bishop)piece);

        if(bishops.size() != 2)
            return false;

        int n1 = abs(board.getPosition(bishops.get(0)).getX() + board.getPosition(bishops.get(0)).getY());
        int n2 = abs(board.getPosition(bishops.get(1)).getX() + board.getPosition(bishops.get(1)).getY());

        return (n1 % 2 == 0 && n2 % 2 == 0) || (n1 % 2 == 1 && n2 % 2 == 1);
    }
}
