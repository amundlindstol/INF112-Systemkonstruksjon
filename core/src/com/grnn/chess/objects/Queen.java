package com.grnn.chess.objects;

public class Queen extends AbstractChessPiece{
	String letterRepresentation = "q";
	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}
}
