package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rook extends AbstractChessPiece {
	String letterRepresentation = "t";

	public Rook(boolean isWhite) {
		super(isWhite);
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
}