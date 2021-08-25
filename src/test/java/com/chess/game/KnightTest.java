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
    @DisplayName("행마")
    void move_knight() {
        Piece knight = chessBoard.getPiece(Position.of(7,6));

        knight.move(chessBoard, WHITE, Position.of(7,6), Position.of(5,5));
        knight.move(chessBoard, WHITE, Position.of(5,5), Position.of(4,3));
        knight.move(chessBoard, WHITE, Position.of(4,3), Position.of(2,2));
        knight.move(chessBoard, WHITE, Position.of(2,2), Position.of(4,1));
        knight.move(chessBoard, WHITE, Position.of(4,1), Position.of(5,3));

        assertEquals(knight, chessBoard.getPiece(Position.of(5, 3)));
    }

    @Test
    @DisplayName("보드판_범위를 벗어난_경우")
    void out_of_board_range() {
        Piece knight = chessBoard.getPiece(Position.of(7,6));

        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(9, 7));

        assertEquals(knight, chessBoard.getPiece(Position.of(7, 6)));
    }

    @Test
    @DisplayName("같은_위치로_이동한_경우")
    void moved_to_the_same_location() {
        Piece knight = chessBoard.getPiece(Position.of(7,6));

        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(7, 6));

        assertEquals(knight, chessBoard.getPiece(Position.of(7, 6)));
    }

    @Test
    @DisplayName("동일플레이어의_기물을_공격한_경우")
    void attacking_the_same_player_pieces() {
        Piece knight = chessBoard.getPiece(Position.of(7,6));

        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(9, 7));

        assertEquals(knight, chessBoard.getPiece(Position.of(7, 6)));
    }
}