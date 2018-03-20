package com.grnn.chess.objects;


import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected String image = "ChessPieces/";
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

    public boolean equals(AbstractChessPiece otherPiece) { return this.hashCode() == otherPiece.hashCode(); }

    public ArrayList<Position> getValidMoves(Board board) {
        return new ArrayList<Position>();
    }

    public ArrayList<Position> getCaptureMoves(Board board) {

        List<Position> captureMoves = new ArrayList<Position>();
        List<Position> validMoves = getValidMoves(board);

        for(Position position : validMoves) {
            if (!board.getPieceAt(position).isSameColor(this)) {
                captureMoves.add(position);
            }
        }

        return (ArrayList<Position>) captureMoves;
    }

    public boolean isSameColor(AbstractChessPiece otherPiece){
        if(otherPiece == null)
            return false;
        return getColor() == otherPiece.getColor();
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

	public void setImage(String image){
        this.image += image;
    }
	public String getImage() {
        String imageS = isWhite ? image + "W" : image + "B";

        if(this instanceof Bishop || this instanceof Rook || this instanceof Knight) {
            imageS += "L";
        }

        return imageS + ".png";
    }

}





