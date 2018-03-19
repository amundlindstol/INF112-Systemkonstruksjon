package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;


import java.util.ArrayList;
import java.util.Arrays;

public class Bishop extends AbstractChessPiece {
	String letterRepresentation = "b";
	private final int value = 3;
	public Bishop(boolean isWhite) {
		super(isWhite);
	}
	public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

	//TODO: actually implement this
	public ArrayList<Position> getValidMoves(Board board) {
		ArrayList<Position> validMoves = new ArrayList<Position>();

		//Get the position of the rook
		Position bishopPos = getPosition(board);

		int i=1;

		do {
			if (board.posIsWithinBoard(bishopPos.west(i).north(i)) && !isSameColor(board.getPieceAt(bishopPos.west(i).north(i)))){
				validMoves.add(bishopPos.west(i));
			}
			else break;
		} while (board.getPieceAt(bishopPos.west(i).north(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(bishopPos.east(i).north(i)) && !isSameColor(board.getPieceAt(bishopPos.east(i).north(i)))){
			validMoves.add(bishopPos.east(i));
		}
		else break;
		} while (board.getPieceAt(bishopPos.east(i).north(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(bishopPos.west(i).south(i)) && !isSameColor(board.getPieceAt(bishopPos.west(i).south(i)))){
			validMoves.add(bishopPos.west(i).south(i));
		}
		else break;
		} while (board.getPieceAt(bishopPos.west(i).south(i++))==null);
		i = 1;

		do { if (board.posIsWithinBoard(bishopPos.east(i).south(i)) && !isSameColor(board.getPieceAt(bishopPos.east(i).south(i)))){
			validMoves.add(bishopPos.east(i).south(i));
		}
		else break;
		} while (board.getPieceAt(bishopPos.east(i).south(i++))==null);

		return validMoves;//(ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}

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

	//TODO: actually implement this
	public ArrayList<Position> getCaptureMoves(Board board) {
		return (ArrayList<Position>) Arrays.asList(board.getPosition(this));
	}

	public int getValue() {
		return value;
	}
}
