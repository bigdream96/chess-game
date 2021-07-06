package com.chess.message;

import com.chess.game.ChessBoard;
import com.chess.game.GameStatus;
import com.chess.game.PieceType;
import com.chess.game.Position;

import static com.chess.game.ChessBoard.*;
import static com.chess.game.GameStatus.*;
import static java.lang.Integer.*;

public final class UserMessage {
    private PieceType pieceType;            // 기물유형
    private Position position;              // 기물이전위치
    private Position targetPosition;        // 기물다음위치
    private final GameStatus gameStatus;    // 게임상태

    public UserMessage(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public UserMessage(PieceType pieceType, Position position, Position targetPosition, GameStatus gameStatus) {
        this.pieceType = pieceType;
        this.position = position;
        this.targetPosition = targetPosition;
        this.gameStatus = gameStatus;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Position getPosition() {
        return position;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public GameStatus getGameStatus() { return gameStatus; }

    private static PieceType convertToPieceType(String pieceType) {
        return PieceType.valueOf(pieceType.toUpperCase());
    }

    private static Position convertToPos(String prevPos) {
        int x = MAX_RANKS - parseInt(prevPos.split("")[1]);
        int y = (prevPos.split("")[0].charAt(0))-'a';
        return Position.of(x, y);
    }

    public static UserMessage of(String gameStatus) {
        return new UserMessage(GameStatus.valueOf(gameStatus));
    }

    public static UserMessage of(String pieceType, String prevPos, String afterPos, String gameStatus) {
        return new UserMessage(convertToPieceType(pieceType),
                               convertToPos(prevPos),
                               convertToPos(afterPos),
                               GameStatus.valueOf(gameStatus));
    }

    public static UserMessage of(String pieceType, Position prevPos, Position afterPos, String gameStatus) {
        return new UserMessage(convertToPieceType(pieceType), prevPos, afterPos, GameStatus.valueOf(gameStatus));
    }

    public static boolean validate(String pieceType, String prevPos, String afterPos) {
        try {
            PieceType.valueOf(pieceType.toUpperCase());

            if(prevPos.length() != 2 && afterPos.length() != 2) {
                return false;
            }

            char prevPosX = (prevPos.split("")[1]).charAt(0);
            char prevPosY = (prevPos.split("")[0]).charAt(0);
            char afterPosX = (afterPos.split("")[1]).charAt(0);
            char afterPosY = (afterPos.split("")[0]).charAt(0);

            if(('1' <= prevPosX && prevPosX <= '8') && ('1' <= afterPosX && afterPosX <= '8')
                    && ('a' <= prevPosY && prevPosY <= 'h') && ('a' <= afterPosY && afterPosY <= 'h')) {
                return true;
            }
        } catch(Exception e) {
            return false;
        }

        return false;
    }

    @Override
    public String toString() {
        return "InputMessage{" +
                "pieceType=" + pieceType +
                ", position=" + position +
                ", targetPosition=" + targetPosition +
                '}';
    }
}
