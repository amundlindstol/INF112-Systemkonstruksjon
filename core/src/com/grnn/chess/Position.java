package com.grnn.chess;

/**
 * Created by hakon on 12.03.2018.
 */
public class Position {
	private int posX;
	private int posY;

	public Position(int x, int y) {
		posX = x;
		posY = y;
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public boolean equals(Position otherPos) {
		return getX() == otherPos.getX() && getY() == otherPos.getY();
	}
}
