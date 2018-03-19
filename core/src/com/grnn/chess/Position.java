package com.grnn.chess;



/**
 * Created by hakon on 12.03.2018.
 */
public class Position implements Comparable<Object>{
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

	@Override
	public int compareTo(Object o) {
		if(!(o instanceof Position)) {
			return -1;
		} else {
			Position otherPos = (Position) o;
			if (getX() > otherPos.getX()) {
				return -1;
			} else if(getX() < otherPos.getX()) {
				return 1;
			} else if (getY() > otherPos.getY()){
				return -1;
			} else if (getY() < otherPos.getY()) {
				return 1;
			} else {
				return 0;
			}

		}
	}

	@Override
	public boolean equals(Object otherPos) {
		if (compareTo(otherPos) == 0)
			return true;
		return false;
	}

	public String toString() {
		return "(" + posX + ", " + posY + ")";
	}
}
