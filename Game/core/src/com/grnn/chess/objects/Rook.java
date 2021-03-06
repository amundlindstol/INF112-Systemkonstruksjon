package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;

public class Rook extends AbstractChessPiece {
	private final int value = 5;
	String letterRepresentation = "r";
	protected String image = "ChessPieces/Rook";
	private boolean hasMoved;

	public Rook(boolean isWhite, boolean hasMoved) {
		super(isWhite);
		setImage("Rook");
	}

    public void move(){
        hasMoved = true;
    }

	public String toString() {
		return isWhite ? letterRepresentation.toUpperCase() : letterRepresentation;
	}

	//TODO: actually implement this
    
    public ArrayList<Position> getValidMoves(Board board) {
        return board.removeMovesThatWillPutOwnKingInCheck(this, getPossibleMovesIgnoringCheck(board));
    }

    public ArrayList<Position> getPossibleMovesIgnoringCheck(Board board){
    //public ArrayList<Position> getValidMoves(Board board){
        ArrayList<Position> validMoves = new ArrayList<Position>();

		//Get the position of the rook
		Position rookPos = getPosition(board);

		int i=1;

		do {
		    if (board.posIsWithinBoard(rookPos.west(i)) && !isSameColor(board.getPieceAt(rookPos.west(i)))){
                validMoves.add(rookPos.west(i));
		    }
		    else break;
		} while (board.getPieceAt(rookPos.west(i++))==null);
		i = 1;

        do {
        	if (board.posIsWithinBoard(rookPos.east(i)) && !isSameColor(board.getPieceAt(rookPos.east(i)))){
                validMoves.add(rookPos.east(i));
            }
            else break;
        } while (board.getPieceAt(rookPos.east(i++))==null);
		i = 1;

        do {
        	if (board.posIsWithinBoard(rookPos.north(i)) && !isSameColor(board.getPieceAt(rookPos.north(i)))){
                validMoves.add(rookPos.north(i));
            }
            else break;
        } while (board.getPieceAt(rookPos.north(i++))==null);
        i = 1;

        do {
        	if (board.posIsWithinBoard(rookPos.south(i)) && !isSameColor(board.getPieceAt(rookPos.south(i)))){
                validMoves.add(rookPos.south(i));
            }
            else break;
        } while (board.getPieceAt(rookPos.south(i++))==null);

        return validMoves;
	}


	//TODO: actually implement this

    public boolean willKingBePutInCheckByMoveTo(Board board, AbstractChessPiece king, Position pos){
        Position posToCheck = pos.north(1);
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.north(1);
        }
        posToCheck = pos.south(1);
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.south(1);
        }
        posToCheck = pos.west(1);
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.west(1);
        }
        posToCheck = pos.east(1);
        while (board.posIsWithinBoard(posToCheck)){
            if (board.getPieceAt(posToCheck)!=null){
                if (board.getPieceAt(posToCheck).equals(king))
                    return true;
                break;
            }
            posToCheck = posToCheck.east(1);
        }

        return false;
	}

	public int getValue() {
		return value;
	}

    public boolean hasMoved() {
        return hasMoved;
    }
}
