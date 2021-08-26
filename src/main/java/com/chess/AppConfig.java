package com.chess;

import com.chess.game.*;
import com.chess.ui.*;

import java.io.Console;
import java.util.Scanner;

import static com.chess.game.PlayerType.*;

public final class AppConfig {
    private final ChessGameNotation CHESS_GAME_NOTATION = new ChessGameNotation();
    private final Rule RULE = new ChessRule(chessGameNotation());
    private final ChessBoardSetting CHESS_BOARD_SETTING = new ChessBoardSetting();
    private final ChessPromotionManager CHESS_PROMOTION_MANAGER = new ChessPromotionManager();
    private final ChessBoard CHESS_BOARD = new ChessBoard(rule(), chessBoardSetting(), chessPromotionManager());
    private final OSVerifier OS_VERIFIER = new OSVerifier();
    private final Scanner SCANNER = new Scanner(System.in);
    private final BoardPrinter BOARD_PRINTER = new BoardPrinter(osVerifier());
    private final ConsoleFormatter CONSOLE_FORMATTER = new ConsoleFormatter();
    private final ChessUI CHESS_UI = new ChessUI(scanner(), boardPrinter(), consoleFormatter());

    private ChessGameNotation chessGameNotation() {
        return CHESS_GAME_NOTATION;
    }

    private Rule rule() {
        return RULE;
    }

    private ChessBoardSetting chessBoardSetting() {
        return CHESS_BOARD_SETTING;
    }

    private ChessPromotionManager chessPromotionManager() {
        return CHESS_PROMOTION_MANAGER;
    }

    private ChessBoard chessBoard() {
        return CHESS_BOARD;
    }

    private OSVerifier osVerifier() {
        return OS_VERIFIER;
    }

    private Scanner scanner() {
        return SCANNER;
    }

    private BoardPrinter boardPrinter() {
        return BOARD_PRINTER;
    }

    private ConsoleFormatter consoleFormatter() {
        return CONSOLE_FORMATTER;
    }

    private UI ui() {
        return CHESS_UI;
    }

    public Player createPlayer(PlayerType playerType) {
        return new HumanPlayer(playerType, ui(), chessBoard());
    }
}
