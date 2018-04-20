package com.grnn.chess;

import com.grnn.chess.objects.AbstractChessPiece;

public class Move {

    private Position toPos;
    private Position fromPos;
    private AbstractChessPiece piece;

    public Move(Position toPos, Position fromPos, AbstractChessPiece piece) {
        this.toPos = toPos;
        this.fromPos = fromPos;
        this.piece = piece;
    }

    public Position getToPos(){ return toPos; }

    public Position getFromPos() { return fromPos; }

    public AbstractChessPiece getPiece() { return piece; }

    @Override
    public String toString() {
        return "Move from pos " + fromPos + " to " + toPos + " with piece: " + piece;

    }
}
