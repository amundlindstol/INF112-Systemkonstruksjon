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

    public Move(String from, String to){
        int x = Integer.parseInt(from.substring(0,1));
        int y = Integer.parseInt(from.substring(1,2));
        this.fromPos = new Position(x,y);

        x = Integer.parseInt(to.substring(0, 1));
        y = Integer.parseInt(to.substring(1, 2));
        this.toPos = new Position(x,y);

        piece = null;
    }

    public Move(Position toPos, Position fromPos){
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
    public boolean equals(Object other){
        if(other == null ) return false;
        Move otherMove = (Move) other;
        boolean posequals = (this.fromPos.equals(otherMove.toPos) && this.toPos.equals(otherMove.fromPos));
        if(this.piece==null && otherMove==null) return posequals;
        else return (posequals && this.piece.equals(otherMove.piece));

    }
    @Override
    public String toString() {
        return "Move from pos " + fromPos + " to " + toPos + " with piece: " + piece;

    }
}
