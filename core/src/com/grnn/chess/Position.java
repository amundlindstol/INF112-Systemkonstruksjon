package com.grnn.chess;


import javafx.geometry.Pos;

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

	//Returns a new position 'steps' tiles further north
	public Position north(int steps) { return new Position(posX,posY+steps); }

	public Position west(int steps) { return new Position(posX-steps,posY); }

	public Position east(int steps) { return new Position(posX+steps,posY); }

	public Position south(int steps) { return new Position(posX,posY-steps); }

	public boolean equals(Position otherPos) {
		return getX() == otherPos.getX() && getY() == otherPos.getY();
	}
}
