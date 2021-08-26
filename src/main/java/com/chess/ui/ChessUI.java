package com.chess.ui;

import com.chess.message.UserMessage;
import com.chess.message.SystemMessage;

import java.util.Scanner;

import static com.chess.ui.FormatType.*;

public final class ChessUI implements UI {
    private final Scanner scanner;
    private final BoardPrinter boardPrinter;
    private final ConsoleFormatter consoleFormatter;

    public ChessUI(Scanner scanner, BoardPrinter boardPrinter, ConsoleFormatter consoleFormatter) {
        this.scanner = scanner;
        this.boardPrinter = boardPrinter;
        this.consoleFormatter = consoleFormatter;
    }

    @Override
    public synchronized UserMessage input(SystemMessage systemMessage) {
        return menu(systemMessage);
    }

    @Override
    public synchronized void output(SystemMessage systemMessage) {
        System.out.println();
        consoleFormatter.print(getResultMessage(systemMessage));
        System.out.println();

        if(systemMessage.getGameStatus().equals("CHECKMATE")
        || systemMessage.getGameStatus().equals("STALEMATE"))
            boardPrinter.showChessBoard(systemMessage.getBoard());

    }

    @Override
    public void close() { scanner.close(); }

    private UserMessage menu(SystemMessage systemMessage) {
        if(systemMessage.getGameStatus().equals("PAWN_PROMOTION")) {
            return promotion(systemMessage);
        } else {
            System.out.println();
            consoleFormatter.print(systemMessage.getPlayerType() + " Player의 차례입니다.");
            boardPrinter.showChessBoard(systemMessage.getBoard());

            while (true) {
                consoleFormatter.print(MESSAGE, "메뉴 선택", "1. 기물이동", "2. 게임기권");

                int num;
                String value = scanner.next();
                if (value.length() != 1 || !Character.isDigit(value.charAt(0))) {
                    consoleFormatter.print(ERROR, "오류메시지", "입력값이 올바르지 않습니다. 다시 입력해주세요.");
                    continue;
                } else {
                    num = Integer.parseInt(value);
                }

                select:
                while (true) {
                    switch (num) {
                        case 1:
                            consoleFormatter.print(MESSAGE, "기물 선택",
                                                            "이동할 기물종류와 원위치, 목표위치를 입력해주세요.",
                                                                    "킹(KING)/퀸(QUEEN)/룩(ROOK)/비숍(BISHOP)/나이트(KNIGHT)/폰(PAWN)",
                                                                    "Ex) pawn a2 a4");

                            String input1 = scanner.next();
                            String input2 = scanner.next();
                            String input3 = scanner.next();

                            if (UserMessage.validate(input1, input2, input3)) {
                                return UserMessage.of(input1, input2, input3, "CONTINUE");
                            } else {
                                consoleFormatter.print(ERROR, "오류메시지", "입력값이 올바르지 않습니다. 다시 입력해주세요.");
                                continue select;
                            }
                        case 2:
                            return UserMessage.of("ABSTENTION");
                        default:
                            consoleFormatter.print(MESSAGE, "잘못된 메뉴번호입니다. 다시 입력해주세요.");
                            System.out.println();
                    }
                }
            }
        }
    }

    private UserMessage promotion(SystemMessage systemMessage) {
        while(true) {
            consoleFormatter.print(MESSAGE,"기물 선택",
                                           "폰의 프로모션입니다. 퀸, 룩, 비숍 중 하나를 선택해서 바꿀 수 있습니다.",
                                                "1. 퀸", "2. 룩", "3. 비숍");

            int num = scanner.nextInt();
            String piece = "";
            switch (num) {
                case 1:
                    piece = "QUEEN";
                    break;
                case 2:
                    piece = "ROOK";
                    break;
                case 3:
                    piece = "BISHOP";
                    break;
                default:
                    consoleFormatter.print(MESSAGE, "잘못 입력하셨습니다. 다시 입력해주세요.");
                    continue;
            }

            return UserMessage.of(piece, systemMessage.getLastPosition(), systemMessage.getLastPosition(), "PAWN_PROMOTION");
        }
    }

    private String getResultMessage(SystemMessage systemMessage) {
        String msg = "";
        String status = systemMessage.getGameStatus();

        switch(status) {
            case "AGAIN":
                msg = "동작이 올바르지 않습니다. 다시 시도해주세요.";
                break;
            case "ABSTENTION":
                msg = systemMessage.getPlayerType() + "플레이어가 기권하였습니다.";
                break;
            case "CHECKMATE":
                msg = "체크메이트입니다.";
                break;
            case "STALEMATE":
                msg = "스테일메이트입니다.";
                break;
            case "LACK_OF_CHESS_PIECES":
                msg = "기물부족으로 인한 무승부입니다.";
                break;
            case "FIFTY_MOVE_RULE":
                msg = "50수가 넘었습니다..";
                break;
            case "THREE_ISOMORPHIC_REPETITIONS":
                msg = "3회 동형반복입니다.";
                break;
            case "PAWN_PROMOTION":
                msg = "폰의 프로모션입니다.";
                break;
            case "CONTINUE":
                msg = systemMessage.getPlayerType() + "님의 차례가 종료되었습니다.";
                break;
        }

        return msg;
    }
}
