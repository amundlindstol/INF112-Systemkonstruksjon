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
			if(board.getPieceAt(pawnPos.north())==null){
				validMoves.add(pawnPos.north());
			}
			if(board.getPieceAt(pawnPos.east().north())!=null && !isSameColor(board.getPieceAt(pawnPos.east().north()))){
				validMoves.add(pawnPos.east().north());
			}
			if(board.getPieceAt(pawnPos.west().north())!=null && !isSameColor(board.getPieceAt(pawnPos.west().north()))){
				validMoves.add(pawnPos.west().north());
			}
		}else {
			if(!hasMoved){
				if(board.getPieceAt(pawnPos.south(2))==null) {
					validMoves.add(pawnPos.south(2));
				}
			}
			if(board.getPieceAt(pawnPos.south())==null){
				validMoves.add(pawnPos.south());
			}
			if(board.getPieceAt(pawnPos.east().south())!=null && !isSameColor(board.getPieceAt(pawnPos.east().south()))){
				validMoves.add(pawnPos.east().south());
			}
			if(board.getPieceAt(pawnPos.west().south())!=null && !isSameColor(board.getPieceAt(pawnPos.east().south()))){
				validMoves.add(pawnPos.west().south());
			}
		}
		return validMoves;
	}


	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}


	public boolean willKingBePutInCheckByMoveTo(Board board, AbstractChessPiece king, Position pos){
		if (isWhite) {
			if (board.posIsWithinBoard(pos.north().east()))
				if (king.getPosition(board).equals(pos.north().east()))
					return true;
			if (board.posIsWithinBoard(pos.north().west()))
				if (king.getPosition(board).equals(pos.north().west()))
					return true;
		}
		else {
			if (board.posIsWithinBoard(pos.south().east()))
				if (king.getPosition(board).equals(pos.south().east()))
					return true;
			if (board.posIsWithinBoard(pos.south().west()))
				if (king.getPosition(board).equals(pos.south().west()))
					return true;
		}
		return false;
	}

	public int getValue() {
		// Position pos = getPosition() 	How to get
		return value;
	}
}
