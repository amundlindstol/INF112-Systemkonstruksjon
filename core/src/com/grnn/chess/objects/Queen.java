package com.grnn.chess.objects;

public class Queen extends AbstractChessPiece{
	String letterRepresentation = "q";
	String image = "queen.png";
	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	public String getImage(){ return image;}
}
