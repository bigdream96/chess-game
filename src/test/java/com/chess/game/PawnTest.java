package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chess.game.PieceStatus.*;
import static com.chess.game.PlayerType.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("폰")
class PawnTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), new ChessBoardSetting(), new ChessPromotionManager());
    }

    @Test
    @DisplayName("행마")
    void move_pawn() {
        Pawn pawn = (Pawn)chessBoard.searchPiece(Position.of(6, 0));

        pawn.move(chessBoard, WHITE, Position.of(6, 0), Position.of(4, 0));
        pawn.move(chessBoard, WHITE, Position.of(4, 0), Position.of(3, 0));

        assertEquals(pawn, chessBoard.searchPiece(Position.of(3, 0)));
    }

    @Test
    @DisplayName("앙파상")
    void en_passant() {
        Piece pawn1 = chessBoard.searchPiece(Position.of(1, 1));
        Piece pawn2 = chessBoard.searchPiece(Position.of(6, 0));

        pawn1.move(chessBoard, BLACK, Position.of(1, 1), Position.of(3, 1));
        pawn1.move(chessBoard, BLACK, Position.of(3, 1), Position.of(4, 1));
        pawn2.move(chessBoard, WHITE, Position.of(6, 0), Position.of(4, 0));
        PieceStatus status = pawn1.move(chessBoard, BLACK, Position.of(4, 1), Position.of(5, 0));

        assertEquals(pawn1, chessBoard.searchPiece(Position.of(5, 0)));
        assertEquals(PieceType.NONE, chessBoard.searchPiece(Position.of(4, 0)).getPieceType());
        assertEquals(EN_PASSANT, status);
    }

    @Test
    @DisplayName("프로모션")
    void promotion() {
        Piece blackPawn = chessBoard.searchPiece(Position.of(1, 7));
        Piece blackRook = chessBoard.searchPiece(Position.of(0, 7));
        chessBoard.deletePiece(blackPawn);
        chessBoard.deletePiece(blackRook);

        Pawn whitePawn = (Pawn)chessBoard.searchPiece(Position.of(6, 7));
        whitePawn.move(chessBoard, WHITE, Position.of(6, 7), Position.of(5, 7));
        whitePawn.move(chessBoard, WHITE, Position.of(5, 7), Position.of(4, 7));
        whitePawn.move(chessBoard, WHITE, Position.of(4, 7), Position.of(3, 7));
        whitePawn.move(chessBoard, WHITE, Position.of(3, 7), Position.of(2, 7));
        whitePawn.move(chessBoard, WHITE, Position.of(2, 7), Position.of(1, 7));
        PieceStatus status = whitePawn.move(chessBoard, WHITE, Position.of(1, 7), Position.of(0, 7));

        assertEquals(PROMOTION, status);
    }

    @Test
    @DisplayName("보드판_범위를 벗어난_경우")
    void out_of_board_range() {
        Piece pawn = chessBoard.searchPiece(Position.of(6, 0));
        Piece knight = chessBoard.searchPiece(Position.of(7, 1));

        knight.move(chessBoard, WHITE, Position.of(7, 1), Position.of(5, 0));
        pawn.move(chessBoard, WHITE, Position.of(6, 0), Position.of(6, -1));

        assertEquals(pawn, chessBoard.searchPiece(Position.of(6, 0)));
    }

    @Test
    @DisplayName("가는길_중간에_기물이_있는_경우")
    void if_there_is_piece_on_the_way() {
        Piece pawn = chessBoard.searchPiece(Position.of(6, 0));
        Piece knight = chessBoard.searchPiece(Position.of(7, 1));

        knight.move(chessBoard, WHITE, Position.of(7, 1), Position.of(5, 0));
        pawn.move(chessBoard, WHITE, Position.of(6, 0), Position.of(4, 0));

        assertEquals(pawn, chessBoard.searchPiece(Position.of(6, 0)));
    }

    @Test
    @DisplayName("같은_위치로_이동한_경우")
    void moved_to_the_same_location() {
        Piece pawn = chessBoard.searchPiece(Position.of(6, 0));
        Piece knight = chessBoard.searchPiece(Position.of(7, 1));

        knight.move(chessBoard, WHITE, Position.of(7, 1), Position.of(5, 0));
        pawn.move(chessBoard, WHITE, Position.of(6, 0), Position.of(6, 0));

        assertEquals(pawn, chessBoard.searchPiece(Position.of(6, 0)));
    }

    @Test
    @DisplayName("동일플레이어의_기물을_공격한_경우")
    void attacking_the_same_player_pieces() {
        Piece pawn = chessBoard.searchPiece(Position.of(6, 0));
        Piece knight = chessBoard.searchPiece(Position.of(7, 1));

        knight.move(chessBoard, WHITE, Position.of(7, 1), Position.of(5, 0));
        pawn.move(chessBoard, WHITE, Position.of(6, 0), Position.of(5, 0));

        assertEquals(pawn, chessBoard.searchPiece(Position.of(6, 0)));
    }

}