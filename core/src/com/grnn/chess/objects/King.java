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

	public ArrayList<Position> getValidMoves(Board board) {
        ArrayList<Position> validMoves = new ArrayList<Position>();
        Position kingPos = getPosition(board);

        if (board.posIsWithinBoard(kingPos.west(1)) && !isSameColor(board.getPieceAt(kingPos.west(1))))
            validMoves.add(kingPos.west(1));

        if (board.posIsWithinBoard(kingPos.east(1)) && !isSameColor(board.getPieceAt(kingPos.east(1))))
            validMoves.add(kingPos.east(1));

        if (board.posIsWithinBoard(kingPos.north(1)) && !isSameColor(board.getPieceAt(kingPos.north(1))))
            validMoves.add(kingPos.north(1));

        if (board.posIsWithinBoard(kingPos.south(1)) && !isSameColor(board.getPieceAt(kingPos.south(1))))
            validMoves.add(kingPos.south(1));

        if (board.posIsWithinBoard(kingPos.north(1).west(1)) && !isSameColor(board.getPieceAt(kingPos.north(1).west(1))))
            validMoves.add(kingPos.north(1).west(1));

        if (board.posIsWithinBoard(kingPos.north(1).east(1)) && !isSameColor(board.getPieceAt(kingPos.north(1).east(1))))
            validMoves.add(kingPos.north(1).east(1));

        if (board.posIsWithinBoard(kingPos.south(1).west(1)) && !isSameColor(board.getPieceAt(kingPos.south(1).west(1))))
            validMoves.add(kingPos.south(1).west(1));

        if (board.posIsWithinBoard(kingPos.south(1).east(1)) && !isSameColor(board.getPieceAt(kingPos.south(1).east(1))))
            validMoves.add(kingPos.south(1).east(1));

        return validMoves;
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
