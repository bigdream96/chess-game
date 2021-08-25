package com.chess.game;

import java.util.*;
import java.util.ArrayList;

public final class ChessBoard {
    private static final int MAX_NUM_OF_LINE = 8;

    private final Rule rule;
    private final Piece[][] board;

    public ChessBoard(Rule rule, Piece[][] board) {
        this.rule = rule;
        this.board = board;
    }

    public static int getMaxNumOfLine() {
        return MAX_NUM_OF_LINE;
    }

    public Piece[][] getBoard() {
        return board.clone();
    }

    /* 기물검색하기 */
    Piece getPiece(Position position) {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                Piece piece = board[i][j];

                if(position.getX() == i && position.getY() == j)
                    return piece;
            }
        }

        return NullPiece.create();
    }

    /* 특정범위 기물리스트 검색하기 */
    List<Piece> getPieces(Position start, Position end) {
        List<Piece> list = new ArrayList<>();

        for(int i=start.getX(); i<=end.getX(); i++)
            for(int j=start.getY(); j<=end.getY(); j++)
                list.add(getPiece(Position.of(i, j)));

        return list;
    }

    /* 모든기물가져오기 */
    List<Piece> getAllPieces() {
        List<Piece> list = new ArrayList<>();

        for (Piece[] pieces : board)
            list.addAll(Arrays.asList(pieces).subList(0, board[0].length));

        return list;
    }

    /* 특정플레이어의 기물리스트 가져오기 */
    List<AbstractPiece> getPlayerPieces(PlayerType playerType) {
        List<AbstractPiece> pieces = new ArrayList<>();

        for(Piece piece : getAllPieces())
            if(piece instanceof AbstractPiece && piece.getPlayerType() == playerType)
                pieces.add((AbstractPiece)piece);

        return pieces;
    }

    /* 특정플레이어의 기물 가져오기 */
    Piece getPlayerPiece(PlayerType playerType, PieceType pieceType) {
        for(Piece piece : getAllPieces())
            if(piece instanceof AbstractPiece
            && piece.getPlayerType() == playerType
            && piece.getPieceType() == pieceType)
                return piece;

        return NullPiece.create();
    }

    /* 기물있는지 확인하기 */
    boolean containsPiece(PlayerType playerType, PieceType pieceType) {
        List<AbstractPiece> pieces = getPlayerPieces(playerType);

        for(Piece piece : pieces)
            if(piece.getPieceType() == pieceType)
                return true;

        return false;
    }

    /* 새로운기물 만들기 */
    void newPiece(PlayerType playerType, PieceType pieceType, Position position) {
        switch (pieceType) {
            case QUEEN:
                board[position.getX()][position.getY()] = new Queen(playerType);
                break;
            case ROOK:
                board[position.getX()][position.getY()] = new Rook(playerType);
                break;
            case BISHOP:
                board[position.getX()][position.getY()] = new Bishop(playerType);
                break;
            case KNIGHT:
                board[position.getX()][position.getY()] = new Knight(playerType);
                break;
            default:
                board[position.getX()][position.getY()] = NullPiece.create();
                break;
        }
    }

    /* 기물놓기 */
    void setPiece(Piece piece, Position targetPosition) {
        Position position = getPosition(piece);
        board[position.getX()][position.getY()] = NullPiece.create();
        board[targetPosition.getX()][targetPosition.getY()] = piece;
    }

    /* 기물삭제 */
    void deletePiece(Piece piece) {
        Position position = getPosition(piece);
        board[position.getX()][position.getY()] = NullPiece.create();
    }

    /* 기물위치가져오기 */
    Position getPosition(Piece piece) {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[0].length; j++) {
                Piece searchPiece = getPiece(Position.of(i, j));

                if(piece == searchPiece) {
                    return Position.of(i, j);
                }
            }
        }

        throw new IllegalArgumentException("위치를 찾을 수 없습니다.");
    }

    /* 유효한 위치값인지 확인 */
    boolean validPiecePosition(Position position) {
        return (0 <= position.getX() && position.getX() < MAX_NUM_OF_LINE) && (0 <= position.getY() && position.getY() < MAX_NUM_OF_LINE);
    }

    /* 해당 범위에 아무것도 없는지 확인 */
    boolean rangeEmpty(Position start, Position end) {
        List<Piece> pieces = getPieces(start, end);

        for(Piece piece : pieces)
            if(piece instanceof AbstractPiece)
                return false;

        return true;
    }

    /* 해당 범위로 가면 적플레이어 기물의 공격범위인지 확인 */
    boolean rangeAttackPossible(PlayerType enemyPlayer, Position start, Position end) {
        List<Piece> targetPieces = start.getY() > end.getY() ? getPieces(end, start) : getPieces(start, end);
        List<AbstractPiece> pieces = getPlayerPieces(enemyPlayer);

        for(AbstractPiece piece : pieces)
            for(Piece targetPiece : targetPieces)
                if(piece.isPossibleAttack(this, getPosition(piece), getPosition(targetPiece)))
                    return true;

        return false;
    }

    /* 기물두기 */
    public synchronized GameResult put(PlayerType playerType, PieceType pieceType, Position position, Position targetPosition) {
        Piece piece = getPiece(position);
        PieceStatus status = piece.move(this, playerType, position, targetPosition);
        return rule.judge(playerType, this, piece, status);
    }

    /* 기물교체하기(프로모션) */
    public synchronized void promotePiece(PlayerType playerType, PieceType pieceType, Position position) {
        Piece piece = getPiece(position);
        deletePiece(piece);
        newPiece(playerType, pieceType, position);
    }
}
