package com.chess.ui;

import static com.chess.ui.FormatType.*;
import static java.lang.System.*;

public final class ConsoleFormatter {
    void println() {
        out.println();
    }
    void print(String text) {
        out.println(text);
    }

    void print(FormatType type, String title, String text) {
        println();
        out.println("=============== " + title + " ================");
        out.println(text);
        out.println("=========================================");
        if(type == MESSAGE) {
            out.print("입력>>");
        }
    }

    void print(FormatType type, String title, String... texts) {
        println();
        out.println("=============== " + title + " ================");
        for(String text : texts) {
            out.println(text);
        }
        out.println("=========================================");
        if(type == MESSAGE) {
            out.print("입력>>");
        }
    }
}