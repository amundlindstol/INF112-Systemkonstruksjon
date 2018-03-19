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
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	//TODO: actually implement this
	public ArrayList<Position> getValidMoves(Board board) {
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

		return validMoves;
	}
	/*
	public boolean willKingBePutInCheckByMoveTo(Board board, AbstractChessPiece king, Position pos){
        Position posToCheck = pos.north(1).west((1));
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.north(1).west(1);
        }

        posToCheck = pos.north(1).east(1);
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.north(1).east(1);
        }

        posToCheck = pos.south(1).west(1);
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.south(1).west(1);
        }

        posToCheck = pos.south(1).east(1);
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.south(1).east(1);
        }

        return false;

    }
        */

	//TODO: actually implement this
	public ArrayList<Position> getCaptureMoves(Board board) {
		return (ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}

	public int getValue() {
		return value;
	}
}
