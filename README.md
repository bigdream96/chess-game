체스게임
===============

![체스판](https://user-images.githubusercontent.com/35022991/124528773-00f30100-de44-11eb-9dfd-196dcf3f84d2.PNG)

### 주제
안녕하세요. 저는 초보 개발자로써 객체지향에 관심을 가지게 되었는데 
체스게임을 객체지향적으로 설계하고 구현했습니다.
별도의 DB연동은 하지 않고, GUI환경이나 웹은 배제하고 콘솔UI를 베이스로 하였습니다.
게임규칙은 위키피디아 [체스규칙](https://ko.wikipedia.org/wiki/%EC%B2%B4%EC%8A%A4_%EA%B7%9C%EC%B9%99 "체스규칙")을 참고해서 구현했습니다. 
또한 콘솔UI라는 특성상 모든 규칙을 구현할 수 없어서 일부 규칙은 제외하였습니다.

### 개발환경
* Java8 (Programing Language)   
* Maven (Build Tool)   
* Junit5 (Test Framework)   
* IntelliJ (IDE)  

### 실행방법
* Java를 실행할 수 있는 환경
    * 인텔리제이와 같은 IDE에서 실행
    * 콘솔에서 Jar파일로 실행(UTF-8 인코딩)

### 객체설계
![체스게임설계](https://user-images.githubusercontent.com/35022991/124465955-02cfac80-ddd1-11eb-8d11-601673c5f4a9.png)

### 객체소개

#### Game
Game객체는 게임을 플레이하기 위해 AppConfig객체로부터 플레이어객체를 요청합니다.

#### AppConfig
AppConfig객체는 플레이어객체가 필요로 하는 의존객체(UI, 체스보드, 기물 등)를 생성하고 
Player 객체에게 의존객체를 주입해서 반환해줍니다.  
만약 스프링 프레임워크를 사용한다면 좀 더 편리하게 의존성주입을 해줄 수 있지만 
지금은 순수 자바만을 사용하므로 객체생성의 역할을 담당하는 객체를 만들었습니다.

```java
public final class AppConfig {
    private final ChessGameNotation CHESS_GAME_NOTATION = new ChessGameNotation();
    private final Rule RULE = new ChessRule(chessGameNotation());
    private final ChessBoardSetting CHESS_BOARD_SETTING = new ChessBoardSetting();
    private final ChessBoard CHESS_BOARD = new ChessBoard(rule(), chessBoardSetting().create());
    private final ChessUI CHESS_UI = new ChessUI(new Scanner(System.in), new BoardPrinter(), new ConsoleFormatter());

    public ChessGameNotation chessGameNotation() { return CHESS_GAME_NOTATION; }
    public Rule rule() { return RULE; }
    public ChessBoardSetting chessBoardSetting() { return CHESS_BOARD_SETTING; }
    public ChessBoard chessBoard() { return CHESS_BOARD; }
    public UI ui() { return CHESS_UI; }
    public HumanPlayer whitePlayer() { return new HumanPlayer(WHITE, ui(), chessBoard()); }
    public HumanPlayer blackPlayer() { return new HumanPlayer(BLACK, ui(), chessBoard()); }
}
```

#### Player
Player 객체는 게임을 플레이하기 위해 UI객체에게 사용자로부터 명령을 입력 받을 것을 요청합니다.  
사용자 명령에 따라 체스판에 기물을 두거나 기권을 하면 게임을 종료하게 됩니다.

#### Message
게임결과를 유저에게 보여주는 SystemMessage객체와 유저가 입력한 명령인 UserMessage객체를 정의하였습니다.  
게임 상의 객체와 UI객체 사이의 값을 적절하게 변환해주는 역할을 합니다.

#### UI
UI객체는 사용자명령을 입력받고 게임 결과를 출력합니다.

#### ChessBoard
ChessBoard객체는 체스판에 배치된 기물들을 갖고 있으며, 기물에 접근하거나 기물을 둘 수 있는 다양한 API를 제공합니다.  
플레이어 객체가 기물두기 메서드를 사용해 기물을 두고, 기물객체들이 기물접근 메서드를 사용하게 됩니다.

![img](https://user-images.githubusercontent.com/35022991/126866643-75cc33bb-0392-407f-99b5-59815f56d7f1.png)

#### ChessBoardSetting
ChessBoardSetting객체는 체스판에 필요한 요소를 생성하고 게임 규칙에 따라 기물들을
적절한 초기위치에 배치합니다.

#### Piece
![체스기물설계](https://user-images.githubusercontent.com/35022991/124468974-ba19f280-ddd4-11eb-82ba-c76b45db84b9.PNG)

기물 고유의 행마법에 따라 움직입니다. 최상위에는 Piece인터페이스를 정의하였습니다.

"기물없음"을 단순히 null로 표현하기보다는 별도의 오브젝트로 표현하는 게 나을 것 같아서 
NonePiece클래스를 만들었습니다. 

모든 기물의 공통적인 특징을 모아서 AbstractPiece라는 추상클래스를 정의하고, 
모든 기물이 해당클래스를 확장해서 구현했습니다. 외부에서 move메서드를 사용해서 
움직이게 됩니다. validate메서드를 사용해서 모든기물의 공통적인 유효성검사를 체크하고, 
checkPieceRange메서드를 통해 각 기물별 행마법이 올바른지 확인합니다. 모든 유효성검사가
통과되면 logic메서드를 통해 실제 행마를 하게 됩니다.

공통로직은 AbstractPiece에 정의되어있고, 
각 기물은 추상메서드인 범위체크(checkPieceRange), 행마(logic), 
특정위치공격체크(isPossibleAttack)만을 구현하면 됩니다.

```java
public interface Piece {
    PieceType getPieceType();
    PlayerType getPlayerType();
    PieceStatus move(ChessBoard board, PlayerType playerType, Position position, Position targetPosition);
}

```
```java
final class NonePiece implements Piece {
    @Override
    public PieceType getPieceType() { return PieceType.NONE; }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.NONE;
    }

    public static NonePiece create() { return new NonePiece(); }

    @Override
    public PieceStatus move(ChessBoard board, PlayerType playerType, Position position, Position targetPosition) {
        return INVALID_MOVE;
    }

    @Override
    public String toString() {
        return "[" + PieceType.NONE + "," + PlayerType.NONE + "]";
    }
}
```
```java
abstract class AbstractPiece implements Piece {
    private final PieceType pieceType;
    private final PlayerType playerType;

    AbstractPiece(PieceType pieceType, PlayerType playerType) {
        this.pieceType = pieceType;
        this.playerType = playerType;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public PlayerType getPlayerType() {
        return playerType;
    }

    @Override
    public PieceStatus move(ChessBoard board, PlayerType playerType, Position position, Position targetPosition) {
        if(validate(board, playerType, position, targetPosition)) {
            return logic(board, position, targetPosition);
        } else {
            return INVALID_MOVE;
        }
    }

    boolean validate(ChessBoard board, PlayerType playerType, Position position, Position targetPosition) {
        Piece targetPiece = board.getPiece(targetPosition);

        if(position.equals(targetPosition) || !board.validPiecePosition(targetPosition))
            return false;
        if(getPieceType() == NONE || targetPiece.getPieceType() == KING)
            return false;
        if(getPlayerType() == targetPiece.getPlayerType())
            return false;
        if(getPlayerType() != playerType)
            return false;

        return checkPieceRange(board, position, targetPosition);
    }

    // 범위체크
    abstract boolean checkPieceRange(ChessBoard board, Position position, Position targetPosition);

    // 행마
    abstract PieceStatus logic(ChessBoard board, Position position, Position targetPosition);

    // 해당위치 공격가능한지 체크
    abstract boolean isPossibleAttack(ChessBoard board, Position position, Position targetPosition);

    @Override
    public String toString() {
        return "[" + pieceType + "," + playerType + "]";
    }
}
```

#### Rule
기물 배치 후 경기종료여부(체크메이트/기권/무승부)를 판단합니다.

### 단위테스트  
Junit를 사용해 체스게임 주요객체에 대한 단위테스트를 진행하였습니다.
* 체스보드 기물 접근 및 조작
* 기물 행마성공/실패
* 킹의 캐슬링
* 폰의 프로모션과 앙파상
* 체크메이트
* 무승부(스테일메이트, 50수 규칙, 3회 동형반복 등)

![체스게임_단위테스트결과](https://user-images.githubusercontent.com/35022991/125237765-322a7000-e321-11eb-8de2-2be6433bbe0f.PNG)

### 학습서적  
* [자바의 정석](http://www.yes24.com/Product/Goods/24259565?OzSrank=3, "자바의 정석")
* [객체지향 사실과 오해](http://www.yes24.com/Product/Goods/18249021, "객체지향 사실과 오해")
* [자바와 JUnit을 활용한 실용주의 단위테스트](http://www.yes24.com/Product/Goods/75189146, "자바와 JUnit을 활용한 실용주의 단위테스트")
