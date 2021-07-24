import com.chess.game.*;
import com.chess.ui.*;

import java.util.Scanner;

import static com.chess.game.PlayerType.*;

public final class AppConfig {
    private final ChessGameNotation CHESS_GAME_NOTATION = new ChessGameNotation();
    private final Rule RULE = new ChessRule(chessGameNotation());
    private final ChessBoardSetting CHESS_BOARD_SETTING = new ChessBoardSetting();
    private final ChessBoard CHESS_BOARD = new ChessBoard(rule(), chessBoardSetting().create());
    private final OSVerifier OS_VERIFIER = new OSVerifier();
    private final ChessUI CHESS_UI = new ChessUI(new Scanner(System.in), new BoardPrinter(osVerifier()), new ConsoleFormatter());


    public ChessGameNotation chessGameNotation() { return CHESS_GAME_NOTATION; }
    public Rule rule() { return RULE; }
    public ChessBoardSetting chessBoardSetting() { return CHESS_BOARD_SETTING; }
    public ChessBoard chessBoard() { return CHESS_BOARD; }
    public OSVerifier osVerifier() { return OS_VERIFIER; }
    public UI ui() { return CHESS_UI; }
    public HumanPlayer whitePlayer() { return new HumanPlayer(WHITE, ui(), chessBoard()); }
    public HumanPlayer blackPlayer() { return new HumanPlayer(BLACK, ui(), chessBoard()); }
}
