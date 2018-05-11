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

    public Move(String from, String to){
        int x = Integer.parseInt(from.substring(0,1));
        int y = Integer.parseInt(from.substring(1,2));
        this.fromPos = new Position(x,y);

        x = Integer.parseInt(to.substring(0, 1));
        y = Integer.parseInt(to.substring(1, 2));
        this.toPos = new Position(x,y);

        piece = null;
    }

    public Move(Position fromPos, Position toPos){
        this.toPos = toPos;
        this.fromPos = fromPos;
        this.piece = null;
    }

    public String getFromPosInDatabaseFormat(){
        int x = fromPos.getX();
        int y = fromPos.getY();

        return Integer.toString(x)+Integer.toString(y);
    }

    public String getToPosInDatabaseFormat(){
        int x = toPos.getX();
        int y = toPos.getY();

        return Integer.toString(x)+Integer.toString(y);    }


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
