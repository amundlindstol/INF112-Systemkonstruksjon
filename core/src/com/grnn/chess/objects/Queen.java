package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;

public class Queen extends AbstractChessPiece{
	private final int value = 9;
	String letterRepresentation = "q";

	public Queen(boolean isWhite) {
		super(isWhite);
		setImage("Queen");
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	public ArrayList<Position> getValidMoves(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();

		Position queenPos = getPosition(board);
		System.out.println(queenPos.getX() + "," + queenPos.getY() + " " + this.isWhite);
		validMoves.addAll(getValidVerticalAndHorizontalMoves(board, queenPos));
		validMoves.addAll(getValidDiagonalMoves(board, queenPos));


		return board.removeMovesThatWillPutOwnKingInCheck(this, validMoves);
	}

	public ArrayList<Position> getValidVerticalAndHorizontalMoves(Board board, Position queenPos){	//Get the position of the rook
		ArrayList<Position> validMoves = new ArrayList<Position>();
		int i=1;

		do {
			if (board.posIsWithinBoard(queenPos.west(i)) && !isSameColor(board.getPieceAt(queenPos.west(i)))){
				validMoves.add(queenPos.west(i));
			}
			else break;
		} while (board.getPieceAt(queenPos.west(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(queenPos.east(i)) && !isSameColor(board.getPieceAt(queenPos.east(i)))){
			validMoves.add(queenPos.east(i));
		}
		else break;
		} while (board.getPieceAt(queenPos.east(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(queenPos.north(i)) && !isSameColor(board.getPieceAt(queenPos.north(i)))){
			validMoves.add(queenPos.north(i));
		}
		else break;
		} while (board.getPieceAt(queenPos.north(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(queenPos.south(i)) && !isSameColor(board.getPieceAt(queenPos.south(i)))){
			validMoves.add(queenPos.south(i));
		}
		else break;
		} while (board.getPieceAt(queenPos.south(i++))==null);

		return validMoves;
	}

	public ArrayList<Position> getValidDiagonalMoves(Board board, Position queenPos) {
		ArrayList<Position> validMoves = new ArrayList<Position>();

		int i=1;

		do {
			if (board.posIsWithinBoard(queenPos.west(i).north(i)) && !isSameColor(board.getPieceAt(queenPos.west(i).north(i)))){
				validMoves.add(queenPos.west(i).north(i));
			}
			else break;
		} while (board.getPieceAt(queenPos.west(i).north(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(queenPos.east(i).north(i)) && !isSameColor(board.getPieceAt(queenPos.east(i).north(i)))){
			validMoves.add(queenPos.east(i).north(i));
		}
		else break;
		} while (board.getPieceAt(queenPos.east(i).north(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(queenPos.west(i).south(i)) && !isSameColor(board.getPieceAt(queenPos.west(i).south(i)))){
			validMoves.add(queenPos.west(i).south(i));
		}
		else break;
		} while (board.getPieceAt(queenPos.west(i).south(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(queenPos.east(i).south(i)) && !isSameColor(board.getPieceAt(queenPos.east(i).south(i)))){
			validMoves.add(queenPos.east(i).south(i));
		}
		else break;
		} while (board.getPieceAt(queenPos.east(i).south(i++))==null);

		return validMoves;//(ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}



		public int getValue() {
		return value;
	}
}
