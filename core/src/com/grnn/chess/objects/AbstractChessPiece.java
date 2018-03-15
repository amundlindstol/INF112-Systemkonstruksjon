package com.grnn.chess.objects;


import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;

/**
 * Abstract class to represent a chess piece
 */
public abstract class AbstractChessPiece {
	protected boolean hasMoved = false;
    protected boolean isWhite;
    protected boolean validMove;
    protected ArrayList<Position> validMoves;
    protected ArrayList<Position> captureMoves;
    protected String letterRepresentation = "";
    protected String image = "";
    protected final int value = 0; // Should value be set in the abstract class?

    //private Direction askedToGo;

    /*public Position getPosition(){

    }*/

    public AbstractChessPiece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean getColor(){
        return isWhite;
    }

    public Position getPosition(Board board) {
        return board.getPosition(this);
    }

    public boolean equals(Object otherPiece) { return this.hashCode() == otherPiece.hashCode(); }

    public ArrayList<Position> getValidMoves(Board board) {
        return new ArrayList<Position>();
    }

    public ArrayList<Position> getCaptureMoves() {
        return new ArrayList<Position>();
    }

    /*
    callback called when piece has been moved, used to set hasMoved
     */
    public void move() {
    	hasMoved = true;
	}

    public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	public String getImage(){ return image; }
}





