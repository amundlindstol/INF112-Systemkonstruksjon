package com.grnn.chess.objects;

public class Pawn extends AbstractChessPiece {
	String letterRepresentation = "p";
	public Pawn(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}
}
