package com.chess.game;

import com.chess.ui.BoardPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.chess.game.GameStatus.*;
import static com.chess.game.PieceType.*;
import static com.chess.game.PlayerType.*;
import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("기본이동")
    void move_king() {
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 4));
        King king = (King)chessBoard.getPiece(Position.of(7, 4));

        pawn.move(chessBoard, WHITE, Position.of(6, 4), Position.of(4, 4));
        king.move(chessBoard, WHITE, Position.of(7, 4), Position.of(6, 4));

        assertEquals(king, chessBoard.getPiece(Position.of(6, 4)));
        assertEquals(pawn, chessBoard.getPiece(Position.of(4, 4)));
    }

    @Test
    @DisplayName("잘못된이동")
    void invalid_move_king() {
        King king = (King)chessBoard.getPiece(Position.of(7, 4));

        // 보드판 범위를 벗어난 경우
        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(8, 4));

        // 가는 길 중간에 기물이 있는 경우
        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(6, 4));

        // 같은 위치로 이동한 경우
        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(7, 4));

        // 동일 플레이어의 기물을 공격한 경우
        king.move(chessBoard, WHITE, Position.of(7,4), Position.of(7, 5));

        assertEquals(king, chessBoard.getPiece(Position.of(7,4)));
    }

    @Test
    @DisplayName("체크")
    void check_king() {
        King king = (King)chessBoard.getPiece(Position.of(7, 4));
        Pawn pawn = (Pawn)chessBoard.getPiece(Position.of(6, 4));
        Knight knight = (Knight)chessBoard.getPiece(Position.of(0, 6));

        pawn.move(chessBoard, WHITE, Position.of(6, 4), Position.of(4, 4));
        pawn.move(chessBoard, WHITE, Position.of(4, 4), Position.of(3, 4));
        king.move(chessBoard, WHITE, Position.of(7, 4), Position.of(6, 4));
        king.move(chessBoard, WHITE, Position.of(6, 4), Position.of(5, 4));
        king.move(chessBoard, WHITE, Position.of(5, 4), Position.of(4, 4));
        knight.move(chessBoard, BLACK, Position.of(0, 6), Position.of(2, 5));

        assertTrue(king.isCheck(chessBoard));
    }

    @Test
    @DisplayName("화이트킹_오른쪽_캐슬링")
    void white_king_right_castling() {
        Pawn pawn1 = (Pawn)chessBoard.getPiece(Position.of(6, 5));
        Pawn pawn2 = (Pawn)chessBoard.getPiece(Position.of(6, 6));
        Bishop bishop = (Bishop)chessBoard.getPiece(Position.of(7, 5));
        Knight knight = (Knight)chessBoard.getPiece(Position.of(7, 6));
        King king = (King)chessBoard.getPiece(Position.of(7, 4));
        Rook rook = (Rook)chessBoard.getPiece(Position.of(7, 7));

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
        Pawn pawn1 = (Pawn)chessBoard.getPiece(Position.of(6, 1));
        Pawn pawn2 = (Pawn)chessBoard.getPiece(Position.of(6, 2));
        Pawn pawn3 = (Pawn)chessBoard.getPiece(Position.of(6, 3));
        Knight knight = (Knight)chessBoard.getPiece(Position.of(7, 1));
        Bishop bishop = (Bishop)chessBoard.getPiece(Position.of(7, 2));
        Queen queen = (Queen)chessBoard.getPiece(Position.of(7, 3));
        King king = (King)chessBoard.getPiece(Position.of(7, 4));
        Rook rook = (Rook)chessBoard.getPiece(Position.of(7, 0));

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
        Pawn pawn1 = (Pawn)chessBoard.getPiece(Position.of(1, 1));
        Pawn pawn2 = (Pawn)chessBoard.getPiece(Position.of(1, 2));
        Pawn pawn3 = (Pawn)chessBoard.getPiece(Position.of(1, 3));
        Knight knight = (Knight)chessBoard.getPiece(Position.of(0, 1));
        Bishop bishop = (Bishop)chessBoard.getPiece(Position.of(0, 2));
        Queen queen = (Queen)chessBoard.getPiece(Position.of(0, 3));
        King king = (King)chessBoard.getPiece(Position.of(0, 4));
        Rook rook = (Rook)chessBoard.getPiece(Position.of(0, 0));

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
        Pawn pawn1 = (Pawn)chessBoard.getPiece(Position.of(1, 5));
        Pawn pawn2 = (Pawn)chessBoard.getPiece(Position.of(1, 6));
        Bishop bishop = (Bishop)chessBoard.getPiece(Position.of(0, 5));
        Knight knight = (Knight)chessBoard.getPiece(Position.of(0, 6));
        King king = (King)chessBoard.getPiece(Position.of(0, 4));
        Rook rook = (Rook)chessBoard.getPiece(Position.of(0, 7));

        pawn1.move(chessBoard, BLACK, Position.of(1, 5), Position.of(3, 5));
        pawn2.move(chessBoard, BLACK, Position.of(1, 6), Position.of(3, 6));
        bishop.move(chessBoard, BLACK, Position.of(0, 5), Position.of(2, 7));
        knight.move(chessBoard, BLACK, Position.of(0, 6), Position.of(2, 5));
        king.move(chessBoard, BLACK, Position.of(0, 4), Position.of(0, 6));

        assertEquals(king, chessBoard.getPiece(Position.of(0, 6)));
        assertEquals(rook, chessBoard.getPiece(Position.of(0, 5)));
    }
}
