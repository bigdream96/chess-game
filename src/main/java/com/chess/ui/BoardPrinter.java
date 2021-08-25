package com.chess.ui;

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

    void showChessBoard(String[][] board) {
        System.out.println("  ---------------------------------" + (systemType == WINDOWS ? "-----" : ""));
        for(int i=0; i<board.length; i++) {
            System.out.print(8-i);
            for(int j=0; j<board[i].length; j++) {
                String value = board[i][j];
                System.out.print(" | " + getPieceChar(value));
            }
            System.out.print(" |" + "\n");
            System.out.println("  ---------------------------------" + (systemType == WINDOWS ? "-----" : ""));
        }
        System.out.println("   　A " + getBlankChar() + " B " + getBlankChar() + " C " + getBlankChar() + " D " + getBlankChar() + " E " + getBlankChar() + " F " + getBlankChar() + " G " + getBlankChar() + " H ");
    }

    private char getPieceChar(String value) {
        String[] arr = value.split(",");
        String pieceType = arr[0];
        String playerType = arr[1];

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
