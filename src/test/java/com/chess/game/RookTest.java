package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chess.game.PlayerType.WHITE;
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
        Piece pawn = chessBoard.getPiece(Position.of(6, 7));
        Piece rook = chessBoard.getPiece(Position.of(7, 7));

        pawn.move(chessBoard, WHITE, Position.of(6, 7), Position.of(4, 7));
        rook.move(chessBoard, WHITE, Position.of(7, 7), Position.of(5, 7));
        rook.move(chessBoard, WHITE, Position.of(5, 7), Position.of(5, 0));

        assertEquals(rook, chessBoard.getPiece(Position.of(5, 0)));
    }

    @Test
    @DisplayName("보드판_범위를 벗어난_경우")
    void out_of_board_range() {
        Piece rook = chessBoard.getPiece(Position.of(7, 7));

        rook.move(chessBoard, WHITE, Position.of(7, 7), Position.of(8, 8));

        assertEquals(rook, chessBoard.getPiece(Position.of(7, 7)));
    }

    @Test
    @DisplayName("가는길_중간에_기물이_있는_경우")
    void if_there_is_piece_on_the_way() {
        Piece rook = chessBoard.getPiece(Position.of(7, 7));

        rook.move(chessBoard, WHITE, Position.of(7, 7), Position.of(5, 7));

        assertEquals(rook, chessBoard.getPiece(Position.of(7, 7)));
    }

    @Test
    @DisplayName("같은_위치로_이동한_경우")
    void moved_to_the_same_location() {
        Piece rook = chessBoard.getPiece(Position.of(7, 7));

        rook.move(chessBoard, WHITE, Position.of(7, 7), Position.of(7, 0));

        assertEquals(rook, chessBoard.getPiece(Position.of(7, 7)));
    }

    @Test
    @DisplayName("동일플레이어의_기물을_공격한_경우")
    void attacking_the_same_player_pieces() {
        Piece rook = chessBoard.getPiece(Position.of(7, 7));

        rook.move(chessBoard, WHITE, Position.of(7,7), Position.of(6, 7));

        assertEquals(rook, chessBoard.getPiece(Position.of(7, 7)));
    }

}
