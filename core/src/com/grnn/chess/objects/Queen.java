package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;

public class Queen extends AbstractChessPiece{
	private final int value = 9;
	String letterRepresentation = "q";
	String image = "queen.png";
	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	public String getImage(){ return image;}

	public ArrayList<Position> getValidMoves(Board board) {
		Position pawnPos = getPosition(board);
		System.out.println(pawnPos.getX()+","+pawnPos.getY()+" "+this.isWhite);

		if(isWhite){

		}else {

		}
		return null;
	}

	public ArrayList<Position> getValidMovesWest(Board board) {
		Position pawnPos = getPosition(board);

		int i = 1;
		do {
			if (board.posIsWithinBoard(pawnPos.west(i)) && !isSameColor(board.getPieceAt(pawnPos.west(i)))){
				validMoves.add(pawnPos.west(i));
			}
			else break;
		} while (board.getPieceAt(pawnPos.west(i++))==null);

	}


		public int getValue() {
		return value;
	}
}
