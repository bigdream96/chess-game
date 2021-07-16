package com.chess.ui;

import java.util.Arrays;
import java.util.List;

public final class BoardPrinter {
    void showChessBoard(List<List<String>> board) {
        System.out.println("  --------------------------------------");
        for(int i=0; i<board.size(); i++) {
            System.out.print(8-i);
            for(int j=0; j<board.get(i).size(); j++) {
                String value = board.get(i).get(j);
                System.out.print(" | " + getPieceChar(value));
            }
            System.out.print(" |" + "\n");
            System.out.println("  --------------------------------------");
        }
        System.out.println("   　A　  B　  C　  D　  E　  F　  G　  H  ");
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
            return '　';
    }
}
