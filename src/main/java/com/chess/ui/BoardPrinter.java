package com.chess.ui;

import java.util.Arrays;
import java.util.List;

import static com.chess.ui.SystemType.*;

public final class BoardPrinter {
    private final OSVerifier osVerifier;
    private SystemType systemType;

    public BoardPrinter(OSVerifier osVerifier) {
        this.osVerifier = osVerifier;
        checkSystemOS();
    }

    private void checkSystemOS() {
        systemType = osVerifier.check();
    }

    void showChessBoard(List<List<String>> board) {
        System.out.println("  ---------------------------------" + (systemType == WINDOWS ? "-----" : ""));
        for(int i=0; i<board.size(); i++) {
            System.out.print(8-i);
            for(int j=0; j<board.get(i).size(); j++) {
                String value = board.get(i).get(j);
                System.out.print(" | " + getPieceChar(value));
            }
            System.out.print(" |" + "\n");
            System.out.println("  ---------------------------------" + (systemType == WINDOWS ? "-----" : ""));
        }
        System.out.println("   　A " + getBlankChar() + " B " + getBlankChar() + " C " + getBlankChar() + " D " + getBlankChar() + " E " + getBlankChar() + " F " + getBlankChar() + " G " + getBlankChar() + " H ");
    }

    private char getPieceChar(String value) {
        List<String> list = Arrays.asList(value.split(","));
        String pieceType = list.get(0);
        String playerType = list.get(1);

        if(playerType.equals("BLACK"))
            return PieceBlackChar.valueOf(pieceType).getValue();
        else if(playerType.equals("WHITE"))
            return PieceWhiteChar.valueOf(pieceType).getValue();
        else
            return getBlankChar();
    }

    private char getBlankChar() {
        if(systemType == WINDOWS) {
            return '　';
        } else if(systemType == MAC) {
            return ' ';
        } else {
            return ' ';
        }
    }
}
