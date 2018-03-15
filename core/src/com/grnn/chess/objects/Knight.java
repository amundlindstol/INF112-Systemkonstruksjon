package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;

public class Knight extends AbstractChessPiece {

	String letterRepresentation = "h";

	public Knight(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public ArrayList<Position> getValidMoves (Board board) {

		ArrayList<Position> validMoves = new ArrayList<Position>();

		// Position of knight
		Position posKnight = getPosition(board);

		// If white
		if (isWhite) {
			if (board.getPieceAt(posKnight.north(2).east(1)) == null
					|| (board.getPieceAt(posKnight.north(2).east(1)) != null
					&& !board.getPieceAt(posKnight.north(2).east(1)).isWhite)) {
				validMoves.add(posKnight.north(2).east(1));
			}
			if (board.getPieceAt(posKnight.north(2).west(1)) == null
					|| (board.getPieceAt(posKnight.north(2).west(1)) != null
					&& !board.getPieceAt(posKnight.north(2).west(1)).isWhite)) {
				validMoves.add(posKnight.north(2).west(1));
			}
			if (board.getPieceAt(posKnight.west(2).north(1)) == null
					|| (board.getPieceAt(posKnight.west(2).north(1)) != null
					&& !board.getPieceAt(posKnight.west(2).north(1)).isWhite)) {
				validMoves.add(posKnight.west(2).north(1));
			}
			if (board.getPieceAt(posKnight.west(2).south(1)) == null
					|| (board.getPieceAt(posKnight.west(2).south(1)) != null
					&& !board.getPieceAt(posKnight.west(2).south(1)).isWhite)) {
				validMoves.add(posKnight.west(2).south(1));
			}
			if (board.getPieceAt(posKnight.east(2).north(1)) == null
					|| (board.getPieceAt(posKnight.east(2).north(1)) != null
					&& !board.getPieceAt(posKnight.east(2).north(1)).isWhite)) {
				validMoves.add(posKnight.east(2).north(1));
			}
			if (board.getPieceAt(posKnight.east(2).south(1)) == null
					|| (board.getPieceAt(posKnight.east(2).south(1)) != null
					&& !board.getPieceAt(posKnight.east(2).south(1)).isWhite)) {
				validMoves.add(posKnight.east(2).south(1));
			}
			if (board.getPieceAt(posKnight.south(2).west(1)) == null
					|| (board.getPieceAt(posKnight.south(2).west(1)) != null
					&& !board.getPieceAt(posKnight.south(2).west(1)).isWhite)) {
				validMoves.add(posKnight.south(2).west(1));
			}
			if (board.getPieceAt(posKnight.south(2).east(1)) == null
					|| (board.getPieceAt(posKnight.south(2).east(1)) != null
					&& !board.getPieceAt(posKnight.south(2).east(1)).isWhite)) {
				validMoves.add(posKnight.south(2).east(1));
			}
		}
		else {
			if (board.getPieceAt(posKnight.north(2).east(1)) == null
					|| (board.getPieceAt(posKnight.north(2).east(1)) != null
					&& board.getPieceAt(posKnight.north(2).east(1)).isWhite)) {
				validMoves.add(posKnight.north(2).east(1));
			}
			if (board.getPieceAt(posKnight.north(2).west(1)) == null
					|| (board.getPieceAt(posKnight.north(2).west(1)) != null
					&& board.getPieceAt(posKnight.north(2).west(1)).isWhite)) {
				validMoves.add(posKnight.north(2).west(1));
			}
			if (board.getPieceAt(posKnight.west(2).north(1)) == null
					|| (board.getPieceAt(posKnight.west(2).north(1)) != null
					&& board.getPieceAt(posKnight.west(2).north(1)).isWhite)) {
				validMoves.add(posKnight.west(2).north(1));
			}
			if (board.getPieceAt(posKnight.west(2).south(1)) == null
					|| (board.getPieceAt(posKnight.west(2).south(1)) != null
					&& board.getPieceAt(posKnight.west(2).south(1)).isWhite)) {
				validMoves.add(posKnight.west(2).south(1));
			}
			if (board.getPieceAt(posKnight.east(2).north(1)) == null
					|| (board.getPieceAt(posKnight.east(2).north(1)) != null
					&& board.getPieceAt(posKnight.east(2).north(1)).isWhite)) {
				validMoves.add(posKnight.east(2).north(1));
			}
			if (board.getPieceAt(posKnight.east(2).south(1)) == null
					|| (board.getPieceAt(posKnight.east(2).south(1)) != null
					&& board.getPieceAt(posKnight.east(2).south(1)).isWhite)) {
				validMoves.add(posKnight.east(2).south(1));
			}
			if (board.getPieceAt(posKnight.south(2).west(1)) == null
					|| (board.getPieceAt(posKnight.south(2).west(1)) != null
					&& board.getPieceAt(posKnight.south(2).west(1)).isWhite)) {
				validMoves.add(posKnight.south(2).west(1));
			}
			if (board.getPieceAt(posKnight.south(2).east(1)) == null
					|| (board.getPieceAt(posKnight.south(2).east(1)) != null
					&& board.getPieceAt(posKnight.south(2).east(1)).isWhite)) {
				validMoves.add(posKnight.south(2).east(1));
			}
		}

		return validMoves;
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}
}
