package com.chess.ui;

import static com.chess.ui.FormatType.*;

public final class ConsoleFormatter {
    void print(String text) {
        System.out.println(text);
    }

    void print(FormatType type, String title, String text) {
        System.out.println();

        StringBuffer buffer = new StringBuffer();
        buffer.append("=============== ");
        buffer.append(title);
        buffer.append(" ================");
        buffer.append("\n");
        buffer.append(text);
        buffer.append("\n");
        buffer.append("=========================================");
        System.out.println(buffer);

        if(type == MESSAGE) {
            System.out.print("입력>>");
        }
    }

    void print(FormatType type, String title, String... texts) {
        System.out.println();

        StringBuffer buffer = new StringBuffer();
        buffer.append("=============== ");
        buffer.append(title);
        buffer.append(" ================");
        System.out.println(buffer);

        for(String text : texts) {
            System.out.println(text);
        }

        System.out.println("=========================================");
        if(type == MESSAGE) {
            System.out.print("입력>>");
        }
    }
}