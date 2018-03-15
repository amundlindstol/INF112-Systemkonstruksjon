package com.grnn.chess.objects;


import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;
import java.util.Arrays;

public class King extends AbstractChessPiece {
	private final int value = Integer.MAX_VALUE;
	String letterRepresentation = "k";
	public boolean isInCheck;
	public King(boolean w) {
		super(w);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	//TODO: actually implement this
	public ArrayList<Position> getValidMoves(Board board) {
		return (ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}

	//TODO: actually implement this
	public ArrayList<Position> getCaptureMoves(Board board) {
		return (ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}

	public boolean willKingBePutInCheckByMoveTo(Board board, AbstractChessPiece king, Position pos){
		Position posToCheck = pos.north(1);
		if (board.posIsWithinBoard(posToCheck)) {
			if (king.getPosition(board).equals(posToCheck))
				return true;
		}
		posToCheck = pos.south(1);
		if (board.posIsWithinBoard(posToCheck)){
			if (king.getPosition(board).equals(posToCheck))
				return true;
		}
		posToCheck = pos.west(1);
		if (board.posIsWithinBoard(posToCheck)) {
			if (king.getPosition(board).equals(posToCheck))
				return true;
		}
		posToCheck = pos.east(1);
		if (board.posIsWithinBoard(posToCheck)){
			if (king.getPosition(board).equals(posToCheck))
				return true;
		}
		return false;
	}

	public boolean willThisKingBePutInCheckByMoveTo(Board board, Position pos){

		return false;
	}

	public int getValue() {
		return value;
	}
}
