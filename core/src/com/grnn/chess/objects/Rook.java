package com.grnn.chess.objects;

public class Rook extends AbstractChessPiece {
	String letterRepresentation = "t";
	public Rook(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}
}
