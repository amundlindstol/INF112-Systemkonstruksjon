package com.grnn.chess.objects;

public class Knight extends AbstractChessPiece {
	private final int value = 3;
	String letterRepresentation = "h";
	public Knight(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	public int getValue() {
		return value;
	}
}
