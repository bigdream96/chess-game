package com.chess.ui;

public final class BoardPrinter {
    void showChessBoard(String[][] board) {
        System.out.println("  --------------------------------------");
        for(int i=0; i<board.length; i++) {
            System.out.print(8-i);
            for(int j=0; j<board[i].length; j++) {
                String value = board[i][j];
                System.out.print(" | " + getPieceChar(value));
            }
            System.out.print(" |" + "\n");
            System.out.println("  --------------------------------------");
        }
        System.out.println("   　A　  B　  C　  D　  E　  F　  G　  H  ");
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
            return '　';
    }
}
