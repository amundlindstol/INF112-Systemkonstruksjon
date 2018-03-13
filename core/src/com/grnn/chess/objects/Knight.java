package com.grnn.chess.objects;

public class Knight extends AbstractChessPiece {
	String letterRepresentation = "h";
	public Knight(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}
}
