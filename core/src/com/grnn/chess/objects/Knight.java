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
        return board.removeMovesThatWillPutOwnKingInCheck(this, getPossibleMovesIgnoringCheck(board));
    }
    
    /** Finds all the legal moves of a knight
     * @param board The board that the piece is on
     * @return List of valid moves a knight can make
     */
    public ArrayList<Position> getPossibleMovesIgnoringCheck(Board board) {
        ArrayList<Position> validMoves = new ArrayList<Position>();
        Position knightPos = getPosition(board);
        AbstractChessPiece knight = board.getPieceAt(knightPos);
        King king = new King(knight.isWhite);
        ArrayList<Position> neighbourSquares = getKnightSquares(board, knightPos);
        for (Position pos : neighbourSquares){
            if (board.getPieceAt(pos) == null
                    && board.posIsWithinBoard(pos)
                    || (board.getPieceAt(pos) != null
                    && !isSameColor(board.getPieceAt(pos)))) {
                validMoves.add(pos);
            }
        }
        return validMoves; //)board.removeIllegalMoves(board, validMoves, this, king);
    }
    
    /** Finds all possible squares a knight can move to within the board
     * @param board The board
     * @param knightPos Position of a knight
     * @return A list of all possible squares a knight can move to
     */
    private ArrayList<Position> getKnightSquares (Board board, Position knightPos) {
        ArrayList<Position> knightSquares = new ArrayList<Position>();
        int [][] offSet = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        Position pos;

        for (int[] moves : offSet) {
            pos = new Position(knightPos.getX()+moves[0], knightPos.getY()+moves[1]);
            if (board.posIsWithinBoard(pos))
                knightSquares.add(pos);
        }

        return knightSquares;
    }

    public String toString() {
        return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
    }

    public int getValue() {
        return value;
    }
}