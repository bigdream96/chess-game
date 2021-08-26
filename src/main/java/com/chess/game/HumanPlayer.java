package com.chess.game;

import com.chess.message.UserMessage;
import com.chess.message.SystemMessage;
import com.chess.ui.UI;

import static com.chess.game.GameStatus.*;

public final class HumanPlayer implements Player {
    private final PlayerType playerType;
    private final UI ui;
    private final ChessBoard chessBoard;

    public HumanPlayer(PlayerType playerType, UI ui, ChessBoard chessBoard) {
        this.playerType = playerType;
        this.ui = ui;
        this.chessBoard = chessBoard;
    }

    @Override
    public void play() {
        boolean b = true;
        while(b) {
            UserMessage message = ui.input(SystemMessage.of(chessBoard, playerType, CONTINUE));

            if(message.getGameStatus() == ABSTENTION) {
                ui.output(SystemMessage.of(chessBoard, playerType, message.getGameStatus()));
                exit();
            }

            GameResult result = chessBoard.put(playerType, message.getPieceType(), message.getPosition(), message.getTargetPosition());
            GameStatus status = result.getGameStatus();
            switch(status) {
                case AGAIN:
                    ui.output(SystemMessage.of(chessBoard, playerType, status, result.getPieceType(), result.getPosition()));
                    continue;
                case CONTINUE:
                    b = false;
                    break;
                case PAWN_PROMOTION:
                    UserMessage promotionMessage = ui.input(SystemMessage.of(chessBoard, playerType, status, result.getPieceType(), result.getPosition()));
                    chessBoard.promote(playerType, promotionMessage.getPieceType(), promotionMessage.getPosition());
                    ui.output(SystemMessage.of(chessBoard, playerType, status));
                    b = false;
                    break;
                case CHECKMATE:
                case STALEMATE:
                case LACK_OF_CHESS_PIECES:
                case FIFTY_MOVE_RULE:
                case THREE_ISOMORPHIC_REPETITIONS:
                    ui.output(SystemMessage.of(chessBoard, playerType, status, result.getPieceType(), result.getPosition()));
                    exit();
                    break;
            }
        }
    }

    private void exit() {
        ui.close();
        System.exit(0);
    }
}
