package com.chess.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.chess.game.GameStatus.*;
import static com.chess.game.PieceStatus.*;
import static com.chess.game.PieceType.*;
import static com.chess.game.PlayerType.BLACK;
import static com.chess.game.PlayerType.WHITE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("체스규칙")
class ChessRuleTest {

    ChessBoardSetting chessBoardSetting;
    ChessBoard chessBoard;
    ChessRule chessRule;

    @BeforeEach
    void init() {
        chessBoardSetting = new ChessBoardSetting();
        chessBoard = new ChessBoard(new ChessRule(new ChessGameNotation()), chessBoardSetting.create());
        chessRule = new ChessRule(new ChessGameNotation());
    }

    @Test
    @DisplayName("체크메이트")
    void checkmate_king() {
        King whiteKing = (King)chessBoard.getPiece(Position.of(7, 4));
        Pawn whitePawn1 = (Pawn)chessBoard.getPiece(Position.of(6, 5));
        Pawn whitePawn2 = (Pawn)chessBoard.getPiece(Position.of(6, 6));
        Pawn blackPawn = (Pawn)chessBoard.getPiece(Position.of(1, 4));
        Queen blackQueen = (Queen)chessBoard.getPiece(Position.of(0, 3));

        whitePawn1.move(chessBoard, WHITE, Position.of(6, 5), Position.of(5, 5));
        blackPawn.move(chessBoard, BLACK, Position.of(1, 4), Position.of(3, 4));
        whitePawn2.move(chessBoard, WHITE, Position.of(6, 6), Position.of(4, 6));
        blackQueen.move(chessBoard, BLACK, Position.of(0, 3), Position.of(4, 7));

        assertTrue(whiteKing.isCheckmate(chessBoard));
    }

    @Test
    @DisplayName("스테일메이트")
    void stalemate_king() {
        List<AbstractPiece> blackPieces = chessBoard.getPlayerPieces(BLACK);
        List<AbstractPiece> whitePieces = chessBoard.getPlayerPieces(WHITE);

        for(AbstractPiece blackPiece : blackPieces) {
            if(blackPiece.getPieceType() != KING && blackPiece.getPieceType() != QUEEN) {
                chessBoard.deletePiece(blackPiece);
            }
        }

        for(AbstractPiece whitePiece : whitePieces) {
            if(whitePiece.getPieceType() != KING) {
                chessBoard.deletePiece(whitePiece);
            }
        }

        King whiteKing = (King)chessBoard.getPiece(Position.of(7, 4));
        Queen blackQueen = (Queen)chessBoard.getPiece(Position.of(0, 3));

        whiteKing.move(chessBoard, WHITE, Position.of(7, 3), Position.of(7, 4));
        whiteKing.move(chessBoard, WHITE, Position.of(7, 4), Position.of(7, 5));
        whiteKing.move(chessBoard, WHITE, Position.of(7, 5), Position.of(7, 6));
        whiteKing.move(chessBoard, WHITE, Position.of(7, 6), Position.of(7, 7));
        blackQueen.move(chessBoard, BLACK, Position.of(0, 3), Position.of(6, 3));
        blackQueen.move(chessBoard, BLACK, Position.of(6, 3), Position.of(6, 5));

        assertTrue(whiteKing.isStalemate(chessBoard));
    }

    @Test
    @DisplayName("킹만_남은경우")
    void only_king() {
        List<AbstractPiece> blackPieces = chessBoard.getPlayerPieces(BLACK);
        List<AbstractPiece> whitePieces = chessBoard.getPlayerPieces(WHITE);

        for(AbstractPiece blackPiece : blackPieces) {
            if(blackPiece.getPieceType() != KING) {
                chessBoard.deletePiece(blackPiece);
            }
        }

        for(AbstractPiece whitePiece : whitePieces) {
            if(whitePiece.getPieceType() != KING) {
                chessBoard.deletePiece(whitePiece);
            }
        }

        GameResult result = chessRule.judge(WHITE, chessBoard, chessBoard.getPlayerPiece(WHITE, KING), ONE_MOVE);

        assertEquals(LACK_OF_CHESS_PIECES, result.getGameStatus());
    }

    @Test
    @DisplayName("킹과_나이트가_남은경우")
    void is_only_king_and_knight() {
        List<AbstractPiece> blackPieces = chessBoard.getPlayerPieces(BLACK);
        List<AbstractPiece> whitePieces = chessBoard.getPlayerPieces(WHITE);
        List<Knight> whiteKnights = new ArrayList<>();

        // 블랙 킹만 남기기
        for(AbstractPiece blackPiece : blackPieces) {
            if(blackPiece.getPieceType() != KING) {
                chessBoard.deletePiece(blackPiece);
            }
        }

        // 화이트 킹과 나이트2개만 남기기
        for(AbstractPiece whitePiece : whitePieces) {
            if(whitePiece.getPieceType() != KING) {
                if(whitePiece.getPieceType() == KNIGHT) {
                    whiteKnights.add((Knight)whitePiece);
                } else {
                    chessBoard.deletePiece(whitePiece);
                }
            }
        }

        // 화이트 나이트 1개 삭제
        chessBoard.deletePiece(whiteKnights.get(0));

        GameResult result = chessRule.judge(WHITE, chessBoard, chessBoard.getPlayerPiece(WHITE, KING), ONE_MOVE);

        assertEquals(LACK_OF_CHESS_PIECES, result.getGameStatus());
    }

    @Test
    @DisplayName("킹과_비숍이_남은경우")
    void is_only_king_and_bishop() {
        List<AbstractPiece> blackPieces = chessBoard.getPlayerPieces(BLACK);
        List<AbstractPiece> whitePieces = chessBoard.getPlayerPieces(WHITE);
        List<Bishop> whiteBishops = new ArrayList<>();

        // 블랙 킹만 남기기
        for(AbstractPiece blackPiece : blackPieces) {
            if(blackPiece.getPieceType() != KING) {
                chessBoard.deletePiece(blackPiece);
            }
        }

        // 화이트 킹과 나이트2개만 남기기
        for(AbstractPiece whitePiece : whitePieces) {
            if(whitePiece.getPieceType() != KING) {
                if(whitePiece.getPieceType() == BISHOP) {
                    whiteBishops.add((Bishop)whitePiece);
                } else {
                    chessBoard.deletePiece(whitePiece);
                }
            }
        }

        // 화이트 나이트 1개 삭제
        chessBoard.deletePiece(whiteBishops.get(0));

        GameResult result = chessRule.judge(WHITE, chessBoard, chessBoard.getPlayerPiece(WHITE, KING), ONE_MOVE);

        assertEquals(LACK_OF_CHESS_PIECES, result.getGameStatus());
    }

    @Test
    @DisplayName("킹과_비숍이_남고_양측의_비숍이_같은_색인_경우")
    void is_only_king_and_bishop_is_same_color() {
        List<AbstractPiece> blackPieces = chessBoard.getPlayerPieces(BLACK);
        List<AbstractPiece> whitePieces = chessBoard.getPlayerPieces(WHITE);
        List<Bishop> blackBishops = new ArrayList<>();
        List<Bishop> whiteBishops = new ArrayList<>();

        // 블랙 킹과 나이트2개만 남기기
        for(AbstractPiece blackPiece : blackPieces) {
            if(blackPiece.getPieceType() != KING) {
                if(blackPiece.getPieceType() == BISHOP) {
                    blackBishops.add((Bishop)blackPiece);
                } else {
                    chessBoard.deletePiece(blackPiece);
                }
            }
        }

        // 화이트 킹과 나이트2개만 남기기
        for(AbstractPiece whitePiece : whitePieces) {
            if(whitePiece.getPieceType() != KING) {
                if(whitePiece.getPieceType() == BISHOP) {
                    whiteBishops.add((Bishop)whitePiece);
                } else {
                    chessBoard.deletePiece(whitePiece);
                }
            }
        }

        // 블랙 나이트 1개, 화이트 나이트 1개 삭제
        chessBoard.deletePiece(blackBishops.get(0));
        chessBoard.deletePiece(whiteBishops.get(1));

        GameResult result = chessRule.judge(WHITE, chessBoard, chessBoard.getPlayerPiece(WHITE, KING), ONE_MOVE);

        assertEquals(LACK_OF_CHESS_PIECES, result.getGameStatus());
    }

    @Test
    @DisplayName("50수_규칙")
    void fifty_move_rule() {
        for(int i=0; i<50; i++) {
            chessRule.judge(WHITE, chessBoard, chessBoard.getPlayerPiece(WHITE, KING), ONE_MOVE);
        }

        GameResult result = chessRule.judge(WHITE, chessBoard, chessBoard.getPlayerPiece(WHITE, KING), ONE_MOVE);

        assertEquals(FIFTY_MOVE_RULE, result.getGameStatus());
    }

    @Test
    @DisplayName("3회_동형반복")
    void Three_isomorphic_repetitions_of_chess() {
        Knight knight = (Knight)chessBoard.getPiece(Position.of(7, 1));
        chessBoard.put(WHITE, KNIGHT, Position.of(7, 1), Position.of(5, 0));
        chessBoard.put(WHITE, KNIGHT, Position.of(5, 0), Position.of(7, 1));
        chessBoard.put(WHITE, KNIGHT, Position.of(7, 1), Position.of(5, 0));
        chessBoard.put(WHITE, KNIGHT, Position.of(5, 0), Position.of(7, 1));
        chessBoard.put(WHITE, KNIGHT, Position.of(7, 1), Position.of(5, 0));
        GameResult result = chessBoard.put(WHITE, KNIGHT, Position.of(5, 0), Position.of(7, 1));

        assertEquals(THREE_ISOMORPHIC_REPETITIONS, result.getGameStatus());
    }
}