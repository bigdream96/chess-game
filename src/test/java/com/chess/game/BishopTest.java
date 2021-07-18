package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chess.game.PlayerType.WHITE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("비숍")
class BishopTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
    }

    @Test
    @DisplayName("기본이동")
    void move_bishop() {
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 4));
        Bishop bishop = (Bishop)chessBoard.getPiece(Position.of(7, 5));

        pawn.move(chessBoard, WHITE, Position.of(6, 4), Position.of(4, 4));
        bishop.move(chessBoard, WHITE, Position.of(7, 5), Position.of(4, 2));
        assertEquals(bishop, chessBoard.getPiece(Position.of(4, 2)));

        bishop.move(chessBoard, WHITE, Position.of(4, 2), Position.of(2, 4));
        assertEquals(bishop, chessBoard.getPiece(Position.of(2, 4)));

        bishop.move(chessBoard, WHITE, Position.of(2, 4), Position.of(4, 6));
        assertEquals(bishop, chessBoard.getPiece(Position.of(4, 6)));

        bishop.move(chessBoard, WHITE, Position.of(4, 6), Position.of(5, 5));
        assertEquals(bishop, chessBoard.getPiece(Position.of(5, 5)));
    }

    @Test
    @DisplayName("잘못된이동")
    void invalid_move_bishop() {
        Bishop bishop = (Bishop)chessBoard.getPiece(Position.of(7, 5));

        // 보드판 범위를 벗어난 경우
        bishop.move(chessBoard, WHITE, Position.of(7,5), Position.of(8, 3));

        // 가는 길 중간에 기물이 있는 경우
        bishop.move(chessBoard, WHITE, Position.of(7,5), Position.of(5, 3));

        // 같은 위치로 이동한 경우
        bishop.move(chessBoard, WHITE, Position.of(7,5), Position.of(7, 5));

        // 동일 플레이어의 기물을 공격한 경우
        bishop.move(chessBoard, WHITE, Position.of(7,5), Position.of(6, 6));

        // 행마법에 어긋난경우
        bishop.move(chessBoard, WHITE, Position.of(7,5), Position.of(7, 4));

        assertEquals(bishop, chessBoard.getPiece(Position.of(7,5)));
    }
}
