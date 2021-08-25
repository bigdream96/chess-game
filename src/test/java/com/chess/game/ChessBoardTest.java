package com.chess.game;

import org.junit.jupiter.api.*;

import java.util.List;

import static com.chess.game.PieceType.*;
import static com.chess.game.PieceType.NONE;
import static com.chess.game.PlayerType.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("체스판")
class ChessBoardTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
    }

    @Test
    @DisplayName("기물검색")
    void search_piece() {
        Piece piece = chessBoard.getPiece(Position.of(1, 4));

        assertEquals(PAWN, piece.getPieceType());
    }

    @Test
    @DisplayName("기물리스트_범위검색")
    void search_list_piece() {
        List<Piece> pieces = chessBoard.getPieces(Position.of(6, 0), Position.of(6, 7));

        assertEquals(8, pieces.size());

        for (Piece piece : pieces) {
            assertEquals(PAWN, piece.getPieceType());
            assertEquals(WHITE, piece.getPlayerType());
        }
    }

    @Test
    @DisplayName("모든기물_검색")
    void search_all_pieces() {
        List<Piece> allPieces = chessBoard.getAllPieces();

        assertEquals(64, allPieces.size());
    }
    
    @Test
    @DisplayName("특정플레이어_기물리스트_검색")
    void search_player_pieces() {
        List<AbstractPiece> whitePieces = chessBoard.getPlayerPieces(WHITE);
        List<AbstractPiece> blackPieces = chessBoard.getPlayerPieces(BLACK);

        for(AbstractPiece piece : whitePieces) {
            assertEquals(WHITE, piece.getPlayerType());
        }

        for(AbstractPiece piece : blackPieces) {
            assertEquals(BLACK, piece.getPlayerType());
        }
    }

    @Test
    @DisplayName("기물배치")
    void put_piece() {
        Piece befPiece = chessBoard.getPiece(Position.of(6, 0));

        assertEquals(PAWN, befPiece.getPieceType());

        chessBoard.setPiece(befPiece, Position.of(4, 0));

        Piece befPiece2 = chessBoard.getPiece(Position.of(6, 0));
        Piece aftPiece = chessBoard.getPiece(Position.of(4, 0));

        assertEquals(NONE, befPiece2.getPieceType());
        assertEquals(PAWN, aftPiece.getPieceType());
    }

    @Test
    @DisplayName("기물삭제")
    void remove_piece() {
        Piece pawn = chessBoard.getPiece(Position.of(6, 0));
        chessBoard.deletePiece(pawn);

        assertEquals(NONE, chessBoard.getPiece(Position.of(6, 0)).getPieceType());
    }

    @Test
    @DisplayName("기물위치검색")
    void get_piece_position() {
        Position position = Position.of(1, 7);
        Piece pawn = chessBoard.getPiece(position);
        Position pawnPosition = chessBoard.getPosition(pawn);

        assertEquals(position, pawnPosition);
    }

    @Test
    @DisplayName("기물위치_유효성검사")
    void valid_piece_position() {
        Position position1 = Position.of(2, 1);
        Position position2 = Position.of(-2, 1);

        assertTrue(chessBoard.validPiecePosition(position1));
        assertFalse(chessBoard.validPiecePosition(position2));
    }

    @Test
    @DisplayName("해당범위가_비었는지_확인")
    void range_empty() {
        Position start = Position.of(3, 1);
        Position end = Position.of(5, 3);

        assertTrue(chessBoard.rangeEmpty(start, end));
    }

    @Test
    @DisplayName("해당범위가_공격받는지_확인")
    void range_attack() {
        Position start = Position.of(2, 5);
        Position end = Position.of(2, 6);

        assertTrue(chessBoard.rangeAttackPossible(BLACK, start, end));
    }
}
