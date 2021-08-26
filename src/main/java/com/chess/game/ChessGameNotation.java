package com.chess.game;

import java.util.*;

import static com.chess.game.PieceStatus.*;
import static com.chess.game.PieceType.*;
import static com.chess.game.PlayerType.*;

public final class ChessGameNotation {
    private final Map<PlayerType, List<NotationItem>> playerNotationItems = new LinkedHashMap<>();

    {
        playerNotationItems.put(WHITE, new ArrayList<>());
        playerNotationItems.put(BLACK, new ArrayList<>());
    }

    void add(PlayerType playerType, NotationItem notationItem) {
        playerNotationItems.get(playerType).add(notationItem);
    }

    List<NotationItem> get(PlayerType playerType) {
        return playerNotationItems.get(playerType);
    }

    int countNotation(PlayerType playerType) {
        return playerNotationItems.get(playerType).size();
    }

    int countPieceStatus(PlayerType playerType, PieceStatus pieceStatus) {
        int result = 0;

        for(NotationItem notationItem : playerNotationItems.get(playerType))
            if(notationItem.getPieceStatus() == pieceStatus)
                result++;

        return result;
    }

    int countPiece(PlayerType playerType, PieceType pieceType) {
        int result = 0;

        for(NotationItem notationItem : playerNotationItems.get(playerType))
            if(notationItem.getPiece().getPieceType() == pieceType)
                result++;

        return result;
    }
}
