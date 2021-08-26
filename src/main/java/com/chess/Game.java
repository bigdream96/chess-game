package com.chess;

import com.chess.game.Player;

import static com.chess.game.PlayerType.*;

class Game {
    private static final AppConfig appConfig = new AppConfig();

    public static void main(String[] args) {
        Player p1 = appConfig.createPlayer(WHITE);
        Player p2 = appConfig.createPlayer(BLACK);

        while(true) {
            p1.play();
            p2.play();
        }
    }
}
