package com.grnn.chess.objects;

public class Pawn extends AbstractChessPiece {
	String letterRepresentation = "p";
	String image = "core/assets/badlogic.jpg";

	public Pawn(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	public String getImage() { return image;}
}
