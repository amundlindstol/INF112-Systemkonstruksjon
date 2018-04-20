package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Bishop extends AbstractChessPiece {
	String letterRepresentation = "b";
	private final int value = 3;

	public Bishop(boolean isWhite) {
		super(isWhite); setImage("Bishop");
	}
	public String toString() {
		return isWhite ? letterRepresentation.toUpperCase() : letterRepresentation;
	}

	//TODO: actually implement this
	public ArrayList<Position> getValidMoves(Board board) {
		return board.removeMovesThatWillPutOwnKingInCheck(this, getPossibleMovesIgnoringCheck(board));
	}

	public ArrayList<Position> getPossibleMovesIgnoringCheck(Board board){
		ArrayList<Position> validMoves = new ArrayList<Position>();

		//Get the position of the rook
		validMoves.addAll(getValidMovesNorthWest(board));
		validMoves.addAll(getValidMovesNorthEast(board));
		validMoves.addAll(getValidMovesSouthWest(board));
		validMoves.addAll(getValidMovesSouthEast(board));
		return validMoves;

	}

	private ArrayList<Position> getValidMovesSouthEast(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();
		Position bishopPos = getPosition(board);

		Position southEast = bishopPos.south().east();

		while(board.posIsWithinBoard(southEast) && board.getPieceAt(southEast) == null) {
			validMoves.add(southEast);
			southEast = southEast.south().east();
		}

		if(board.posIsWithinBoard(southEast) && !board.getPieceAt(southEast).isSameColor(this)) {
			validMoves.add(southEast);
		}

		return validMoves;

	}

	private ArrayList<Position> getValidMovesSouthWest(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();
		Position bishopPos = getPosition(board);

		Position southWest = bishopPos.south().west();
		while(board.posIsWithinBoard(southWest) && board.getPieceAt(southWest) == null) {
			validMoves.add(southWest);
			southWest = southWest.south().west();
		}

		if(board.posIsWithinBoard(southWest) && !board.getPieceAt(southWest).isSameColor(this)) {
			validMoves.add(southWest);
		}

		return validMoves;
	}

	private ArrayList<Position> getValidMovesNorthEast(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();
		Position bishopPos = getPosition(board);

		Position northEast = bishopPos.north().east();
		while(board.posIsWithinBoard(northEast) && board.getPieceAt(northEast) == null) {
			validMoves.add(northEast);
			northEast = northEast.north().east();
		}

		if(board.posIsWithinBoard(northEast) && !board.getPieceAt(northEast).isSameColor(this)) {
			validMoves.add(northEast);
		}

		return validMoves;
	}
	

	private ArrayList<Position> getValidMovesNorthWest(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();
		Position bishopPos = getPosition(board);

		Position northWest = bishopPos.north().west();
		while(board.posIsWithinBoard(northWest) && board.getPieceAt(northWest) == null) {
			validMoves.add(northWest);
			northWest = northWest.north().west();
		}

		if(board.posIsWithinBoard(northWest) && !board.getPieceAt(northWest).isSameColor(this)) {
			validMoves.add(northWest);
		}
		return validMoves;
	}
	public int getValue() {
		return value;
	}
}
