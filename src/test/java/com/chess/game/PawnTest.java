package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceStatus.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("폰")
class PawnTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
    }

    @Test
    @DisplayName("기본이동")
    void move_pawn() {
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 0));

        pawn.move(chessBoard, Position.of(6, 0), Position.of(4, 0));
        pawn.move(chessBoard, Position.of(4, 0), Position.of(3, 0));

        assertEquals(pawn, chessBoard.getPiece(Position.of(3, 0)));
    }

    @Test
    @DisplayName("잘못된이동")
    void invalid_move_pawn() {
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 0));
        Knight knight = (Knight)chessBoard.getPiece(Position.of(7, 1));

        knight.move(chessBoard, Position.of(7, 1), Position.of(5, 0));

        // 보드판 범위를 벗어난 경우
        pawn.move(chessBoard, Position.of(6, 0), Position.of(6, -1));

        // 가는 길 중간에 기물이 있는 경우
        pawn.move(chessBoard, Position.of(6, 0), Position.of(4, 0));

        // 같은 위치로 이동한 경우
        pawn.move(chessBoard, Position.of(6, 0), Position.of(6, 0));

        // 동일 플레이어의 기물을 공격한 경우
        pawn.move(chessBoard, Position.of(6, 0), Position.of(5, 0));

        // 행마법에 어긋난경우
        pawn.move(chessBoard, Position.of(6, 0), Position.of(5, 1));
        assertEquals(pawn, chessBoard.getPiece(Position.of(6, 0)));
    }

    @Test
    @DisplayName("앙파상")
    void en_passant() {
        Pawn pawn1 = (Pawn)chessBoard.getPiece(Position.of(1, 1));
        Pawn pawn2 = (Pawn)chessBoard.getPiece(Position.of(6, 0));

        pawn1.move(chessBoard, Position.of(1, 1), Position.of(3, 1));
        pawn1.move(chessBoard, Position.of(3, 1), Position.of(4, 1));
        pawn2.move(chessBoard, Position.of(6, 0), Position.of(4, 0));
        PieceStatus status = pawn1.move(chessBoard, Position.of(4, 1), Position.of(5, 0));

        assertEquals(pawn1, chessBoard.getPiece(Position.of(5, 0)));
        assertEquals(NONE, chessBoard.getPiece(Position.of(4, 0)).getPieceType());
        assertEquals(EN_PASSANT, status);
    }

    @Test
    @DisplayName("프로모션")
    void promotion() {
        Pawn blackPawn = (Pawn)chessBoard.getPiece(Position.of(1, 7));
        Rook blackRook = (Rook)chessBoard.getPiece(Position.of(0, 7));
        chessBoard.deletePiece(blackPawn);
        chessBoard.deletePiece(blackRook);

        Pawn whitePawn = (Pawn)chessBoard.getPiece(Position.of(6, 7));
        whitePawn.move(chessBoard, Position.of(6, 7), Position.of(5, 7));
        whitePawn.move(chessBoard, Position.of(5, 7), Position.of(4, 7));
        whitePawn.move(chessBoard, Position.of(4, 7), Position.of(3, 7));
        whitePawn.move(chessBoard, Position.of(3, 7), Position.of(2, 7));
        whitePawn.move(chessBoard, Position.of(2, 7), Position.of(1, 7));
        PieceStatus status = whitePawn.move(chessBoard, Position.of(1, 7), Position.of(0, 7));

        assertEquals(PROMOTION, status);
    }
}