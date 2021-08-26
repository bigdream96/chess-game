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
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), new ChessBoardSetting(), new ChessPromotionManager());
    }

    @Test
    @DisplayName("행마")
    void move_queen() {
        Piece pawn = chessBoard.searchPiece(Position.of(6, 3));
        Piece queen = chessBoard.searchPiece(Position.of(7, 3));

        pawn.move(chessBoard, WHITE, Position.of(6, 3), Position.of(4, 3));
        queen.move(chessBoard, WHITE, Position.of(7, 3), Position.of(5, 3));
        queen.move(chessBoard, WHITE, Position.of(5, 3), Position.of(3, 5));
        queen.move(chessBoard, WHITE, Position.of(3, 5), Position.of(3, 7));
        queen.move(chessBoard, WHITE, Position.of(3, 7), Position.of(4, 6));
        queen.move(chessBoard, WHITE, Position.of(4, 6), Position.of(4, 5));
        queen.move(chessBoard, WHITE, Position.of(4, 5), Position.of(5, 5));

        assertEquals(queen, chessBoard.searchPiece(Position.of(5, 5)));
    }

    @Test
    @DisplayName("보드판_범위를 벗어난_경우")
    void out_of_board_range() {
        Piece queen = chessBoard.searchPiece(Position.of(7, 3));

        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(8,3));

        assertEquals(queen, chessBoard.searchPiece(Position.of(7, 3)));
    }

    @Test
    @DisplayName("가는길_중간에_기물이_있는_경우")
    void if_there_is_piece_on_the_way() {
        Piece queen = chessBoard.searchPiece(Position.of(7, 3));

        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(5,3));

        assertEquals(queen, chessBoard.searchPiece(Position.of(7, 3)));
    }

    @Test
    @DisplayName("같은_위치로_이동한_경우")
    void moved_to_the_same_location() {
        Piece queen = chessBoard.searchPiece(Position.of(7, 3));

        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(7,3));

        assertEquals(queen, chessBoard.searchPiece(Position.of(7, 3)));
    }

    @Test
    @DisplayName("동일플레이어의_기물을_공격한_경우")
    void attacking_the_same_player_pieces() {
        Piece queen = chessBoard.searchPiece(Position.of(7, 3));

        queen.move(chessBoard, WHITE, Position.of(7,3), Position.of(7,2));

        assertEquals(queen, chessBoard.searchPiece(Position.of(7, 3)));
    }
}