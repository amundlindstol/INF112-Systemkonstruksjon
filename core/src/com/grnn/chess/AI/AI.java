package com.grnn.chess.AI;

import com.grnn.chess.Board;
import com.grnn.chess.Game;
import com.grnn.chess.Move;

import java.util.ArrayList;
import java.util.Random;


public class AI {
    // TODO: AI isn't always black

    int level;

    public AI(int level){
        this.level = level;
    }
    // TODO: Not doing random move

    /**
     * Calculates the best move the AI can do
     * @param board The board
     * @return The best move
     */
    public Move calculateBestMove(Board board) {
        ArrayList<Move> moves = board.getPossibleAIMoves();
        if(moves.isEmpty()) return null;
        return moves.get((int)(Math.random() * (moves.size() - 1)));
    }

}
