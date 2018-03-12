package com.grnn.chess.objects;

public abstract class AbstractChessPiece {
    private boolean isWhite;
    private boolean validMove;

    /*public Position getPosition(){
        return null;
    }*/

    public boolean getColor(){
        return isWhite;
    }
}





