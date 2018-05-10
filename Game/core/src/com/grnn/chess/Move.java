package com.grnn.chess;

import com.grnn.chess.objects.AbstractChessPiece;
import static java.lang.Double.isNaN;
import static java.lang.Math.signum;

public class Move implements Comparable<Move> {

    private Position toPos;
    private Position fromPos;
    private AbstractChessPiece piece;
    public double value = Double.NaN;


    public Move(Position toPos, Position fromPos, AbstractChessPiece piece) {
        this.toPos = toPos;
        this.fromPos = fromPos;
        this.piece = piece;
    }

    public Position getToPos(){ return toPos; }

    public Position getFromPos() { return fromPos; }

    public AbstractChessPiece getPiece() { return piece; }


    @Override
    public int compareTo(Move move) {
        if (isNaN(value) && isNaN(move.value)) {
            return 0;
        } else if (isNaN(value)) {
            return -1;
        } else if (isNaN(move.value)) {
            return 1;
        }
        return (int) signum(move.value - value);
    }


    @Override
    public String toString() {
        return "Move from pos " + fromPos + " to " + toPos + " with piece: " + piece;

    }
}
