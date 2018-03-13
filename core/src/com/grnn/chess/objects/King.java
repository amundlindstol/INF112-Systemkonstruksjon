package com.grnn.chess.objects;


public class King extends AbstractChessPiece {
	String letterRepresentation = "k";
	public King(boolean w) {
		super(w);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

}
