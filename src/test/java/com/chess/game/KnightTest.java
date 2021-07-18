package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chess.game.PlayerType.WHITE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("나이트")
class KnightTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
    }

    @Test
    @DisplayName("기본이동")
    void move_knight() {
        Knight knight = (Knight)chessBoard.getPiece(Position.of(7,6));

        knight.move(chessBoard, WHITE, Position.of(7,6), Position.of(5,5));
        knight.move(chessBoard, WHITE, Position.of(5,5), Position.of(4,3));
        knight.move(chessBoard, WHITE, Position.of(4,3), Position.of(2,2));
        knight.move(chessBoard, WHITE, Position.of(2,2), Position.of(4,1));
        knight.move(chessBoard, WHITE, Position.of(4,1), Position.of(5,3));

        assertEquals(knight, chessBoard.getPiece(Position.of(5, 3)));
    }
    
    @Test
    @DisplayName("잘못된이동")
    void invalid_move_knight() {
        Knight knight = (Knight)chessBoard.getPiece(Position.of(7,6));

        // 보드판 범위를 벗어난 경우
        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(9, 7));

        // 같은 위치로 이동한 경우
        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(7, 6));

        // 동일 플레이어의 기물을 공격한 경우
        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(9, 7));

        // 행마법에 어긋난경우
        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(4, 5));

        assertEquals(knight, chessBoard.getPiece(Position.of(7, 6)));
    }
}