package com.grnn.chess.Actors.AI;

import com.grnn.chess.Actors.IActor;
import com.grnn.chess.Board;
import com.grnn.chess.Move;
import com.grnn.chess.Actors.AI.Minimax;

import java.util.ArrayList;
import java.util.List;


public class AI implements IActor{
    // TODO: AI isn't always black

    private int level;
    private boolean isWhite;

    public AI(int level, boolean isWhite){
        this.level = level;
        this.isWhite = isWhite;
    }

    /**
     * Change the color of AI, will be used in showing best avalible move.
     * @param isWhite True for white, false for black.
     */
    public void setAiColor(boolean isWhite){
        this.isWhite = isWhite;
    }

    // TODO: Not doing random move


    public Move calculateBestMove(Board board) {
        if(level == 1) {
            return calculateBestMoveEasy(board);
        } else if(level == 2) {
            return calculateBestMoveIntermediate(board);
        } else if(level == 3) { // TODO: Hard AI
            return calculateBestMoveIntermediate(board);
        }
        return null;
    }

    /**
     * Calculates the best move the easy AI can do
     * @param board The board
     * @return The best move
     */
    public Move calculateBestMoveEasy(Board board) {
        ArrayList<Move> moves = board.getPossibleAIMoves(isWhite);
        if(moves.isEmpty()) return null;

        return moves.get((int)(Math.random() * (moves.size() - 1)));
    }
    /**
     * Calculates the best move the intermediate AI can do
     * @param board The board
     * @return The best move
     */
    public Move calculateBestMoveIntermediate(Board board) {
        ArrayList<Move> moves = board.getPossibleAIMoves(isWhite);
        if (moves.isEmpty()) return null;
        Minimax minimax = new Minimax(board);
        List<Move> sortedMoves = minimax.getBestMoves(3, moves);

        if (sortedMoves.isEmpty()) return null;


        return sortedMoves.get(sortedMoves.size()-1);
    }

        @Override
    public boolean isWhite() {
        return isWhite;
    }

    private int evaluateMove(Move moveToCalculate) {
        return 0; // TODO: implement
    }
}
