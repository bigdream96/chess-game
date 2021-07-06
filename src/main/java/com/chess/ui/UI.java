package com.chess.ui;

import com.chess.message.UserMessage;
import com.chess.message.SystemMessage;

public interface UI {
    UserMessage input(SystemMessage systemMessage);
    void output(SystemMessage systemMessage);
    void close();
}
