package com.grnn.chess.objects;

public class Bishop extends AbstractChessPiece {
	String letterRepresentation = "b";
	public Bishop(boolean isWhite) {
		super(isWhite);
	}
	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}
}
