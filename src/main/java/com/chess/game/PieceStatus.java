package com.chess.game;

public enum PieceStatus {
    INVALID_MOVE,       // 잘못된 이동
    ONE_MOVE,           // 1수
    TAKES,              // 기물포획
    CASTLING,           // 캐슬링
    PROMOTION,          // 프로모션
    EN_PASSANT,         // 앙파상
}
