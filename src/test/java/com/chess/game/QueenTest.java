package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chess.game.PlayerType.WHITE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("퀸")
class QueenTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
    }

    @Test
    @DisplayName("기본이동")
    void move_queen() {
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 3));
        Queen queen = (Queen)chessBoard.getPiece(Position.of(7, 3));

        pawn.move(chessBoard, WHITE, Position.of(6, 3), Position.of(4, 3));
        queen.move(chessBoard, WHITE, Position.of(7, 3), Position.of(5, 3));
        queen.move(chessBoard, WHITE, Position.of(5, 3), Position.of(3, 5));
        queen.move(chessBoard, WHITE, Position.of(3, 5), Position.of(3, 7));
        queen.move(chessBoard, WHITE, Position.of(3, 7), Position.of(4, 6));
        queen.move(chessBoard, WHITE, Position.of(4, 6), Position.of(4, 5));
        queen.move(chessBoard, WHITE, Position.of(4, 5), Position.of(5, 5));

        assertEquals(queen, chessBoard.getPiece(Position.of(5, 5)));
    }

    @Test
    @DisplayName("잘못된이동")
    void invalid_move_queen() {
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 3));
        Queen queen = (Queen)chessBoard.getPiece(Position.of(7, 3));

        // 보드판 범위를 벗어난 경우
        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(8,3));

        assertEquals(queen, chessBoard.getPiece(Position.of(7, 3)));

        // 가는 길 중간에 기물이 있는 경우
        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(5,3));

        assertEquals(queen, chessBoard.getPiece(Position.of(7, 3)));

        pawn.move(chessBoard, WHITE, Position.of(6, 3), Position.of(4, 3));

        // 같은 위치로 이동한 경우
        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(7,3));

        assertEquals(queen, chessBoard.getPiece(Position.of(7, 3)));

        // 동일 플레이어의 기물을 공격한 경우
        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(7,2));

        assertEquals(queen, chessBoard.getPiece(Position.of(7, 3)));

        // 행마법에 어긋난경우
        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(5,3));
        queen.move(chessBoard, WHITE, Position.of(5,3), Position.of(3,4));

        assertEquals(queen, chessBoard.getPiece(Position.of(5, 3)));
    }
}