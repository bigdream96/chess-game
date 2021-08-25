package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chess.game.PlayerType.BLACK;
import static com.chess.game.PlayerType.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("킹")
class KingTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
    }

    @Test
    @DisplayName("행마")
    void move_king() {
        Piece pawn = chessBoard.getPiece(Position.of(6, 4));
        Piece king = chessBoard.getPiece(Position.of(7, 4));

        pawn.move(chessBoard, WHITE, Position.of(6, 4), Position.of(4, 4));
        king.move(chessBoard, WHITE, Position.of(7, 4), Position.of(6, 4));

        assertEquals(king, chessBoard.getPiece(Position.of(6, 4)));
        assertEquals(pawn, chessBoard.getPiece(Position.of(4, 4)));
    }

    @Test
    @DisplayName("화이트킹_오른쪽_캐슬링")
    void white_king_right_castling() {
        Piece pawn1 = chessBoard.getPiece(Position.of(6, 5));
        Piece pawn2 = chessBoard.getPiece(Position.of(6, 6));
        Piece bishop = chessBoard.getPiece(Position.of(7, 5));
        Piece knight = chessBoard.getPiece(Position.of(7, 6));
        Piece king = chessBoard.getPiece(Position.of(7, 4));
        Piece rook = chessBoard.getPiece(Position.of(7, 7));

        pawn1.move(chessBoard, WHITE, Position.of(6, 5), Position.of(4, 5));
        pawn2.move(chessBoard, WHITE, Position.of(6, 6), Position.of(4, 6));
        bishop.move(chessBoard, WHITE, Position.of(7, 5), Position.of(5, 7));
        knight.move(chessBoard, WHITE, Position.of(7, 6), Position.of(5, 5));
        king.move(chessBoard, WHITE, Position.of(7, 4), Position.of(7, 6));

        assertEquals(king, chessBoard.getPiece(Position.of(7, 6)));
        assertEquals(rook, chessBoard.getPiece(Position.of(7, 5)));
    }

    @Test
    @DisplayName("화이트킹_왼쪽_캐슬링")
    void white_king_left_castling() {
        Piece pawn1 = chessBoard.getPiece(Position.of(6, 1));
        Piece pawn2 = chessBoard.getPiece(Position.of(6, 2));
        Piece pawn3 = chessBoard.getPiece(Position.of(6, 3));
        Piece knight = chessBoard.getPiece(Position.of(7, 1));
        Piece bishop = chessBoard.getPiece(Position.of(7, 2));
        Queen queen = (Queen)chessBoard.getPiece(Position.of(7, 3));
        Piece king = chessBoard.getPiece(Position.of(7, 4));
        Piece rook = chessBoard.getPiece(Position.of(7, 0));

        pawn1.move(chessBoard, WHITE, Position.of(6, 1), Position.of(4, 1));
        pawn2.move(chessBoard, WHITE, Position.of(6, 2), Position.of(4, 2));
        pawn3.move(chessBoard, WHITE, Position.of(6, 3), Position.of(4, 3));
        knight.move(chessBoard, WHITE, Position.of(7, 1), Position.of(5, 0));
        bishop.move(chessBoard, WHITE, Position.of(7, 2), Position.of(2, 7));
        queen.move(chessBoard, WHITE, Position.of(7, 3), Position.of(5, 1));
        king.move(chessBoard, WHITE, Position.of(7, 4), Position.of(7, 2));

        assertEquals(king, chessBoard.getPiece(Position.of(7, 2)));
        assertEquals(rook, chessBoard.getPiece(Position.of(7, 3)));
    }

    @Test
    @DisplayName("블랙킹_왼쪽_캐슬링")
    void black_king_left_castling() {
        Piece pawn1 = chessBoard.getPiece(Position.of(1, 1));
        Piece pawn2 = chessBoard.getPiece(Position.of(1, 2));
        Piece pawn3 = chessBoard.getPiece(Position.of(1, 3));
        Piece knight = chessBoard.getPiece(Position.of(0, 1));
        Piece bishop = chessBoard.getPiece(Position.of(0, 2));
        Queen queen = (Queen)chessBoard.getPiece(Position.of(0, 3));
        Piece king = chessBoard.getPiece(Position.of(0, 4));
        Piece rook = chessBoard.getPiece(Position.of(0, 0));

        pawn1.move(chessBoard, BLACK, Position.of(1, 1), Position.of(3, 1));
        pawn2.move(chessBoard, BLACK, Position.of(1, 2), Position.of(3, 2));
        pawn3.move(chessBoard, BLACK, Position.of(1, 3), Position.of(3, 3));
        knight.move(chessBoard, BLACK, Position.of(0, 1), Position.of(2, 0));
        bishop.move(chessBoard, BLACK, Position.of(0, 2), Position.of(5, 7));
        queen.move(chessBoard, BLACK, Position.of(0, 3), Position.of(2, 1));
        king.move(chessBoard, BLACK, Position.of(0, 4), Position.of(0, 2));

        assertEquals(king, chessBoard.getPiece(Position.of(0, 2)));
        assertEquals(rook, chessBoard.getPiece(Position.of(0, 3)));
    }

    @Test
    @DisplayName("블랙킹_오른쪽_캐슬링")
    void black_king_right_castling() {
        Piece pawn1 = chessBoard.getPiece(Position.of(1, 5));
        Piece pawn2 = chessBoard.getPiece(Position.of(1, 6));
        Piece bishop = chessBoard.getPiece(Position.of(0, 5));
        Piece knight = chessBoard.getPiece(Position.of(0, 6));
        Piece king = chessBoard.getPiece(Position.of(0, 4));
        Piece rook = chessBoard.getPiece(Position.of(0, 7));

        pawn1.move(chessBoard, BLACK, Position.of(1, 5), Position.of(3, 5));
        pawn2.move(chessBoard, BLACK, Position.of(1, 6), Position.of(3, 6));
        bishop.move(chessBoard, BLACK, Position.of(0, 5), Position.of(2, 7));
        knight.move(chessBoard, BLACK, Position.of(0, 6), Position.of(2, 5));
        king.move(chessBoard, BLACK, Position.of(0, 4), Position.of(0, 6));

        assertEquals(king, chessBoard.getPiece(Position.of(0, 6)));
        assertEquals(rook, chessBoard.getPiece(Position.of(0, 5)));
    }

    @Test
    @DisplayName("보드판_범위를 벗어난_경우")
    void out_of_board_range() {
        Piece king = chessBoard.getPiece(Position.of(7, 4));

        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(8, 4));

        assertEquals(king, chessBoard.getPiece(Position.of(7,4)));
    }

    @Test
    @DisplayName("가는길_중간에_기물이_있는_경우")
    void if_there_is_piece_on_the_way() {
        Piece king = chessBoard.getPiece(Position.of(7, 4));

        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(6, 4));

        assertEquals(king, chessBoard.getPiece(Position.of(7,4)));
    }

    @Test
    @DisplayName("같은_위치로_이동한_경우")
    void moved_to_the_same_location() {
        Piece king = chessBoard.getPiece(Position.of(7, 4));

        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(7, 4));

        assertEquals(king, chessBoard.getPiece(Position.of(7,4)));
    }

    @Test
    @DisplayName("동일플레이어의_기물을_공격한_경우")
    void attacking_the_same_player_pieces() {
        Piece king = chessBoard.getPiece(Position.of(7, 4));

        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(7, 5));

        assertEquals(king, chessBoard.getPiece(Position.of(7,4)));
    }
}
