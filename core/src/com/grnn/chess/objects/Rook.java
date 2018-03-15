package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Rook extends AbstractChessPiece {
	private final int value = 5;
	String letterRepresentation = "t";

	public Rook(boolean isWhite) {
		super(isWhite);
	}

	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	//TODO: actually implement this
	public ArrayList<Position> getValidMoves(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();

		//Get the position of the rook
		Position rookPos = getPosition(board);
		System.out.println(rookPos.getX()+","+rookPos.getY()+" "+this.isWhite);

		int i=1;

		do {
		    if (board.posIsWithinBoard(rookPos.west(i))){
                validMoves.add(rookPos.west(i));
		    }
		    else break;
		} while (board.getPieceAt(rookPos.west(i++))==null);
		i = 1;

        do { if (board.posIsWithinBoard(rookPos.east(i))){
                validMoves.add(rookPos.east(i));
            }
            else break;
        } while (board.getPieceAt(rookPos.east(i++))==null);
		i = 1;

        do { if (board.posIsWithinBoard(rookPos.north(i))){
                validMoves.add(rookPos.north(i));
            }
            else break;
        } while (board.getPieceAt(rookPos.north(i++))==null);
        i = 1;

        do { if (board.posIsWithinBoard(rookPos.north(i))){
                validMoves.add(rookPos.south(i));
            }
            else break;
        } while (board.getPieceAt(rookPos.south(i++))==null);

		return (ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}


	//TODO: actually implement this
	public ArrayList<Position> getCaptureMoves(Board board) {
		return (ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}

	public int getValue() {
		return value;
	}
}
