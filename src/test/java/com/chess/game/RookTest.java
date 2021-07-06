package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("룩")
class RookTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
    }

    @Test
    @DisplayName("기본이동")
    void move_rook() {
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 7));
        Rook rook = (Rook)chessBoard.getPiece(Position.of(7, 7));

        pawn.move(chessBoard, Position.of(6, 7), Position.of(4, 7));
        rook.move(chessBoard, Position.of(7, 7), Position.of(5, 7));
        rook.move(chessBoard, Position.of(5, 7), Position.of(5, 0));

        assertEquals(rook, chessBoard.getPiece(Position.of(5, 0)));
    }

    @Test
    @DisplayName("잘못된이동")
    void invalid_move_rook() {
        Rook rook = (Rook)chessBoard.getPiece(Position.of(7, 7));

        // 보드판의 범위를 벗어났을 때
        rook.move(chessBoard, Position.of(7, 7), Position.of(8, 8));

        // 자기 자신의 기물이 목표위치일 때
        rook.move(chessBoard, Position.of(7, 7), Position.of(7, 0));

        // 룩 이동범위에 다른 기물이 있는 경우
        rook.move(chessBoard, Position.of(7, 7), Position.of(5, 7));

        // 동일 플레이어의 기물을 공격한 경우
        rook.move(chessBoard, Position.of(7,7), Position.of(6, 7));

        assertEquals(rook, chessBoard.getPiece(Position.of(7, 7)));
    }
}
