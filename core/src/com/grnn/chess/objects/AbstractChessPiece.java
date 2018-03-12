package com.grnn.chess.objects;


/**
 * Abstract class to represent a chess piece
 */
public abstract class AbstractChessPiece {
    private boolean isWhite;
    private boolean validMove;
    //private Direction askedToGo;

    /*public Position getPosition(){

    }*/

    public boolean getColor(){
        return isWhite;
    }
}





