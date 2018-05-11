package com.grnn.chess.objects;

import com.grnn.chess.Move;
import com.grnn.chess.Position;
import com.grnn.chess.Board;


import java.util.ArrayList;

public class Pawn extends AbstractChessPiece {
	private final int value = 1;
	String letterRepresentation = "p";

    /**
     * Consturtur of pawn, will set image to "Pawn"
     * @param isWhite
     */
	public Pawn(boolean isWhite, boolean hasMoved) {
		super(isWhite);
		this.hasMoved = hasMoved;

		setImage("Pawn");
	}

	public String toString() {
		return isWhite ? letterRepresentation.toUpperCase() : letterRepresentation;
	}

	
	@Override
	public ArrayList<Position> getValidMoves(Board board) {
		return board.removeMovesThatWillPutOwnKingInCheck(this, getPossibleMovesIgnoringCheck(board));
	}
	
	public ArrayList<Position> getPossibleMovesIgnoringCheck(Board board){
		Position pawnPos = getPosition(board);
	//public ArrayList<Position> getValidMoves(Board board){
		ArrayList<Position> validMoves = new ArrayList<Position>();
		//Get the position of the pawn
		if(isWhite){
			if(board.getPieceAt(pawnPos.north())==null) {
				if (!hasMoved && board.getPieceAt(pawnPos.north(2)) == null) {
					validMoves.add(pawnPos.north(2));
				}
				validMoves.add(pawnPos.north());
			}
			if(board.getPieceAt(pawnPos.east().north())!=null && !isSameColor(board.getPieceAt(pawnPos.east().north()))){
				validMoves.add(pawnPos.east().north());
			}
			if(board.getPieceAt(pawnPos.west().north())!=null && !isSameColor(board.getPieceAt(pawnPos.west().north()))){
				validMoves.add(pawnPos.west().north());
			}

		}else {
			if(board.getPieceAt(pawnPos.south())==null) {
				if (!hasMoved && board.getPieceAt(pawnPos.south(2)) == null) {
					validMoves.add(pawnPos.south(2));
				}
				validMoves.add(pawnPos.south());
			}
			if(board.getPieceAt(pawnPos.east().south())!=null && !isSameColor(board.getPieceAt(pawnPos.east().south()))){
				validMoves.add(pawnPos.east().south());
			}
			if(board.getPieceAt(pawnPos.west().south())!=null && !isSameColor(board.getPieceAt(pawnPos.west().south()))) {
				validMoves.add(pawnPos.west().south());
			}
		}
		return validMoves;
	}

	@Override
	public ArrayList<Position> getCaptureMoves(Board board) {
		Position pawnPos = getPosition(board);
		ArrayList<Position> captureMoves = new ArrayList<Position>();
		ArrayList<Position> validMoves = getValidMoves(board);
		for (Position pos : validMoves)
			if (board.getPieceAt(pos)!= null)
				captureMoves.add(pos);

		Position enPass = enPassant(board,pawnPos);
		if (enPass != null) {
			captureMoves.add(enPass);
		}
		return captureMoves;
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

    /**
     * This method returns the position that a pawn can capture if enPassant is possible.
     * @param board the board of the pawn.
     * @param pawnPos the pawns position on the board.
     * @return A possible position for the pawn to capture.
     */
	public Position enPassant(Board board, Position pawnPos){
		ArrayList<Move> moveHistory = board.getMoveHistory();

		if (moveHistory.size() > 2) {

			Move conditionMove = moveHistory.get(moveHistory.size() - 1);
			Position conditionFromPos = conditionMove.getFromPos();
			Position conditionToPos = conditionMove.getToPos();
			AbstractChessPiece conditionPiece = conditionMove.getPiece();

			if (conditionToPos.getX() == pawnPos.getX() + 1 || conditionToPos.getX() == pawnPos.getX() - 1)
				if (this.isWhite() && pawnPos.getY() == 4) {
					if (!conditionPiece.isWhite() && conditionFromPos.getY() == 6 && conditionToPos.getY() == 4) {
						return conditionToPos.north();
					}
				} else if (!this.isWhite() && pawnPos.getY() == 3) {
					if (conditionPiece.isWhite() && conditionFromPos.getY() == 1 && conditionToPos.getY() == 3) {
						return conditionToPos.south();
					}

				}
		}
		return null;
	}
}
