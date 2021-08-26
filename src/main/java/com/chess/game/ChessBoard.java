package com.chess.game;

import java.util.*;
import java.util.ArrayList;

import static com.chess.game.PieceType.*;

public final class ChessBoard {
    private static final int MAX_NUM_OF_LINE = 8;

    private final Piece[][] board;
    private final Rule rule;
    private final ChessBoardSetting chessBoardSetting;
    private final ChessPromotionManager chessPromotionManager;

    public ChessBoard(Rule rule, ChessBoardSetting chessBoardSetting, ChessPromotionManager chessPromotionManager) {
        this.rule = rule;
        this.chessBoardSetting = chessBoardSetting;
        board = chessBoardSetting.create();
        this.chessPromotionManager = chessPromotionManager;
    }

    public static int getMaxNumOfLine() {
        return MAX_NUM_OF_LINE;
    }

    public Piece[][] getBoard() {
        return board.clone();
    }

    public synchronized GameResult put(PlayerType playerType, PieceType pieceType, Position position, Position targetPosition) {
        Piece piece = searchPiece(position);
        PieceStatus status = piece.move(this, playerType, position, targetPosition);
        return rule.judge(playerType, this, piece, status);
    }

    public synchronized void promote(PlayerType playerType, PieceType newPieceType, Position newPosition) {
        Piece piece = searchPiece(newPosition);
        deletePiece(piece);
        Piece newPiece = chessPromotionManager.createNewPiece(playerType, newPieceType, newPosition);
        board[newPosition.getX()][newPosition.getY()] = newPiece;
    }

    Piece searchPiece(Position position) {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                Piece piece = board[i][j];

                if(position.getX() == i && position.getY() == j)
                    return piece;
            }
        }

        return NullPiece.create();
    }

    List<Piece> searchPieces(Position start, Position end) {
        List<Piece> list = new ArrayList<>();

        for(int i=start.getX(); i<=end.getX(); i++)
            for(int j=start.getY(); j<=end.getY(); j++)
                list.add(searchPiece(Position.of(i, j)));

        return list;
    }

    List<Piece> searchAllPieces() {
        List<Piece> list = new ArrayList<>();

        for (Piece[] pieces : board)
            list.addAll(Arrays.asList(pieces).subList(0, board[0].length));

        return list;
    }

    List<AbstractPiece> searchPlayerPieces(PlayerType playerType) {
        List<AbstractPiece> pieces = new ArrayList<>();

        for(Piece piece : searchAllPieces())
            if(piece instanceof AbstractPiece && piece.getPlayerType() == playerType)
                pieces.add((AbstractPiece)piece);

        return pieces;
    }

    Piece searchPlayerKing(PlayerType playerType) {
        for(Piece piece : searchAllPieces())
            if(piece.getPlayerType() == playerType
            && piece.getPieceType() == KING)
                return piece;

        return NullPiece.create();
    }

    boolean containsPiece(PlayerType playerType, PieceType pieceType) {
        List<AbstractPiece> pieces = searchPlayerPieces(playerType);

        for(Piece piece : pieces)
            if(piece.getPieceType() == pieceType)
                return true;

        return false;
    }

    void setPiece(Piece piece, Position targetPosition) {
        Position position = searchPosition(piece);
        board[position.getX()][position.getY()] = NullPiece.create();
        board[targetPosition.getX()][targetPosition.getY()] = piece;
    }

    void deletePiece(Piece piece) {
        Position position = searchPosition(piece);
        board[position.getX()][position.getY()] = NullPiece.create();
    }

    Position searchPosition(Piece piece) {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                Piece searchPiece = searchPiece(Position.of(i, j));

                if(piece == searchPiece) {
                    return Position.of(i, j);
                }
            }
        }

        throw new IllegalArgumentException("위치를 찾을 수 없습니다.");
    }

    boolean validPiecePosition(Position position) {
        return (0 <= position.getX() && position.getX() < MAX_NUM_OF_LINE) && (0 <= position.getY() && position.getY() < MAX_NUM_OF_LINE);
    }

    boolean rangeEmpty(Position start, Position end) {
        List<Piece> pieces = searchPieces(start, end);

        for(Piece piece : pieces)
            if(piece instanceof AbstractPiece)
                return false;

        return true;
    }

    boolean rangeAttackPossible(PlayerType enemyPlayer, Position start, Position end) {
        List<Piece> targetPieces = start.getY() > end.getY() ? searchPieces(end, start) : searchPieces(start, end);
        List<AbstractPiece> pieces = searchPlayerPieces(enemyPlayer);

        for(AbstractPiece piece : pieces)
            for(Piece targetPiece : targetPieces)
                if(piece.isPossibleAttack(this, searchPosition(piece), searchPosition(targetPiece)))
                    return true;

        return false;
    }
}
