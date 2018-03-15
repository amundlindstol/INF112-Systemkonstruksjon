package com.grnn.chess.objects;

import com.grnn.chess.Position;
import com.grnn.chess.Board;


import java.util.ArrayList;

public class Pawn extends AbstractChessPiece {
	private final int value = 1;
	String letterRepresentation = "p";
	String image = "badlogic.jpg";

	public Pawn(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public ArrayList<Position> getValidMoves(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();

		//Get the position of the pawn
		Position pawnPos = getPosition(board);
		System.out.println(pawnPos.getX()+","+pawnPos.getY()+" "+this.isWhite);


		if(isWhite){
			if(!hasMoved){
				if(board.getPieceAt(pawnPos.north(2))==null) {
					validMoves.add(pawnPos.north(2));
				}
			}
			if(board.getPieceAt(pawnPos.north(1))==null){
				validMoves.add(pawnPos.north(1));
			}
			if(board.getPieceAt(pawnPos.east(1).north(1))!=null){
				validMoves.add(pawnPos.east(1).north(1));
			}
			if(board.getPieceAt(pawnPos.west(1).north(1))!=null){
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
			if(board.getPieceAt(pawnPos.east(1).south(1))!=null){
				validMoves.add(pawnPos.east(1).south(1));
			}
			if(board.getPieceAt(pawnPos.west(1).south(1))!=null){
				validMoves.add(pawnPos.west(1).south(1));
			}

		}
		return validMoves;
	}


	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	public String getImage() { return image;}

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
