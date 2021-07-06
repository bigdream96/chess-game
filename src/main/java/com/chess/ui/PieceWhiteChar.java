package com.chess.ui;

enum PieceWhiteChar {
    KING("KING", '♔'),
    QUEEN("QUEEN",'♕'),
    ROOK("ROOK", '♖'),
    BISHOP("BISHOP", '♗'),
    KNIGHT("KNIGHT", '♘'),
    PAWN("PAWN", '♙');

    private final String name;
    private final char value;

    PieceWhiteChar(String name, char value)  {
        this.name = name;
        this.value = value;
    }

    String getName() { return name; }
    char getValue() { return value; }
}
