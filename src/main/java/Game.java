import com.chess.game.HumanPlayer;

class Game {
    private static final AppConfig appConfig = new AppConfig();

    public static void main(String[] args) {
        HumanPlayer p1 = appConfig.whitePlayer();
        HumanPlayer p2 = appConfig.blackPlayer();

        while(true) {
            p1.play();
            p2.play();
        }
    }
}
