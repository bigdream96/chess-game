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
        StringBuffer buffer = new StringBuffer();

        buffer.append("  ");
        buffer.append("---------------------------------");
        buffer.append((systemType == WINDOWS ? "-----" : ""));
        buffer.append("\n");
        for(int i=0; i<board.length; i++) {
            buffer.append(8 - i);
            for(int j=0; j<board[i].length; j++) {
                buffer.append(" | ");
                buffer.append(getPieceChar(board[i][j]));
            }
            buffer.append(" | ");
            buffer.append("\n");
            buffer.append("  ");
            buffer.append("---------------------------------");
            buffer.append((systemType == WINDOWS ? "-----" : ""));
            buffer.append("\n");
        }
        buffer.append("   　");
        buffer.append("A ");
        for(int i=66; i<=72; i++) {
            buffer.append(" ");
            buffer.append(getBlankChar());
            buffer.append((char)i);
            buffer.append(" ");
        }

        System.out.println(buffer);
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
