package com.grnn.chess.objects;

import com.grnn.chess.Move;
import com.grnn.chess.Position;
import com.grnn.chess.Board;


import java.util.ArrayList;

public class Pawn extends AbstractChessPiece {
	private final int value = 1;
	String letterRepresentation = "p";


	public Pawn(boolean isWhite) {
		super(isWhite);
		setImage("Pawn");
	}

	@Override
	public ArrayList<Position> getValidMoves(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();

		//Get the position of the pawn
		Position pawnPos = getPosition(board);


		if(isWhite){
			if(!hasMoved){
				if(board.getPieceAt(pawnPos.north(2))==null) {
					validMoves.add(pawnPos.north(2));
				}
			}
			if(board.getPieceAt(pawnPos.north(1))==null){
				validMoves.add(pawnPos.north(1));
			}
			if(board.getPieceAt(pawnPos.east(1).north(1))!=null && !isSameColor(board.getPieceAt(pawnPos.east(1).north(1)))){
				validMoves.add(pawnPos.east(1).north(1));
			}
			if(board.getPieceAt(pawnPos.west(1).north(1))!=null && !isSameColor(board.getPieceAt(pawnPos.west(1).north(1)))){
			validMoves.add(pawnPos.west(1).north(1));
			}

		}else {
			if(!hasMoved){
				if(board.getPieceAt(pawnPos.south(2))==null) {
					validMoves.add(pawnPos.south(2));
				}
			}
			if(board.getPieceAt(pawnPos.south(1))==null){
				validMoves.add(pawnPos.south(1));
			}
			if(board.getPieceAt(pawnPos.east(1).south(1))!=null && !isSameColor(board.getPieceAt(pawnPos.east(1).south(1)))){
				validMoves.add(pawnPos.east(1).south(1));
			}
			if(board.getPieceAt(pawnPos.west(1).south(1))!=null && !isSameColor(board.getPieceAt(pawnPos.east(1).south(1)))){
				validMoves.add(pawnPos.west(1).south(1));
			}

		}
		return validMoves;
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}


	public boolean willKingBePutInCheckByMoveTo(Board board, AbstractChessPiece king, Position pos){
		if (isWhite) {
			if (board.posIsWithinBoard(pos.north(1).east(1)))
				if (king.getPosition(board).equals(pos.north(1).east(1)))
					return true;
			if (board.posIsWithinBoard(pos.north(1).west(1)))
				if (king.getPosition(board).equals(pos.north(1).west(1)))
					return true;
		}
		else {
			if (board.posIsWithinBoard(pos.south(1).east(1)))
				if (king.getPosition(board).equals(pos.south(1).east(1)))
					return true;
			if (board.posIsWithinBoard(pos.south(1).west(1)))
				if (king.getPosition(board).equals(pos.south(1).west(1)))
					return true;
		}
		return false;
	}

	public int getValue() {
		// Position pos = getPosition() 	How to get
		return value;
	}
}
