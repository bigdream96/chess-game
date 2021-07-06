package com.chess.game;

import java.util.Objects;

final class NotationItem {
    private final Piece piece;
    private final Position position;
    private final PieceStatus pieceStatus;

    private NotationItem(Piece piece, Position position, PieceStatus pieceStatus) {
        this.piece = piece;
        this.position = position;
        this.pieceStatus = pieceStatus;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }

    public PieceStatus getPieceStatus() {
        return pieceStatus;
    }

    public static NotationItem of(Piece piece, Position position, PieceStatus pieceStatus) {
        return new NotationItem(piece, position, pieceStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotationItem)) return false;
        NotationItem that = (NotationItem) o;
        return getPiece().getPieceType() == that.getPiece().getPieceType()
            && getPosition().equals(that.getPosition())
            && getPieceStatus() == that.getPieceStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPiece(), getPosition(), getPieceStatus());
    }

    @Override
    public String toString() {
        return "NotationItem{" +
                "piece=" + piece +
                ", position=" + position +
                ", pieceStatus=" + pieceStatus +
                '}';
    }
}
