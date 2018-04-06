package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.util.ArrayList;

public class Knight extends AbstractChessPiece {
    private final int value = 3;
    String letterRepresentation = "h";

    public Knight(boolean isWhite) {
        super(isWhite);
        setImage("Knight");
    }

    @Override
    public ArrayList<Position> getValidMoves(Board board) {

        ArrayList<Position> validMoves = new ArrayList<Position>();

        // Position of knight
        Position posKnight = getPosition(board);

        // add valid positions.
        addValidMove2North1East(board, posKnight, validMoves);
        addValidMove2West1North(board, posKnight, validMoves);
        addValidMove2North1West(board, posKnight, validMoves);
        addValidMove2West1South(board, posKnight, validMoves);
        addValidMove2East1North(board, posKnight, validMoves);
        addValidMove2East1South(board, posKnight, validMoves);
        addValidMove2South1West(board, posKnight, validMoves);
        addValidMove2South1East(board, posKnight, validMoves);

        return board.removeMovesThatWillPutOwnKingInCheck(this, validMoves);
    }

    private void addValidMove2South1East(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.south(2).east();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private void addValidMove2South1West(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.south(2).west();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private void addValidMove2East1South(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.east(2).south();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private void addValidMove2East1North(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.east(2).north();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private void addValidMove2West1South(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.west(2).south();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private void addValidMove2West1North(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.west(2).north();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private void addValidMove2North1East(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.north(2).east();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private void addValidMove2North1West(Board board, Position posKnight, ArrayList<Position> validMoves) {
        Position posToMoveTo = posKnight.north(2).west();

        if (canMoveToPos(board, posToMoveTo)) {
            validMoves.add(posToMoveTo);
        }
    }

    private boolean canMoveToPos(Board board, Position posToMoveTo) {
        return board.getPieceAt(posToMoveTo) == null
                && board.posIsWithinBoard(posToMoveTo)
                || (board.getPieceAt(posToMoveTo) != null
                && !isSameColor(board.getPieceAt(posToMoveTo)));
    }

    public String toString() {
        return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
    }

    public int getValue() {
        return value;
    }

}