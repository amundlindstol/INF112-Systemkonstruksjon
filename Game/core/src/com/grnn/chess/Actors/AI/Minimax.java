package com.grnn.chess.Actors.AI;

import com.grnn.chess.Board;
import com.grnn.chess.Move;
import com.grnn.chess.objects.King;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.grnn.chess.Actors.AI.IAUtils.iterableToSortedList;

public class Minimax implements IA<Move> {

    private Board board;
    private final long threeSeconds = 3 * (long) Math.pow(10, 3);
    private long startTime;


    public Minimax(Board board) {
        this.board = board;
    }


    @Override
    public List<Move> getBestMoves(final int depth, Iterable<Move> possibleMoves) {
        if (depth <= 0) {
            throw new IllegalArgumentException("Search depth MUST be > 0");
        }
        startTime = System.currentTimeMillis();
        List<Move> orderedMoves = iterableToSortedList(possibleMoves);
        minimax(orderedMoves, depth, 1);
        Collections.sort(orderedMoves);
        return orderedMoves;
    }

    @Override
    public boolean isOver(boolean isWhite) { //TODO:
        return board.getKingPos(isWhite) == null;
    }

    @Override
    public void makeMove(Move move) {

        board = board.copyBoard();
        board.movePiece(move.getFromPos(), move.getToPos());
    }


    @Override
    public double maxEvaluateValue() {
        return Integer.MAX_VALUE;
    }



    /**
     * Minimax algorithm
     * @param initialMoves Avaliable moves
     * @param depth Search depth.
     * @param who 1 if white and -1 if black
     * @return Best score
     */
    private double minimax(Iterable<Move> initialMoves, final int depth, final int who) {
        boolean isWhite = who > 0;

        long timeUsedThisFar = System.currentTimeMillis() - startTime;
        if (depth == 0 || isOver(isWhite) || timeUsedThisFar >= threeSeconds) {
            return who * board.calculateBoardForActor(isWhite);
        }
        Iterator<Move> moves = (initialMoves != null ? initialMoves : board.getPossibleAIMoves(isWhite)).iterator();
        if (!moves.hasNext()) {
            return minimaxScore(depth, who);
        }
        if (who > 0) {
            // max
            double score;
            double bestScore = -maxEvaluateValue();
            while (moves.hasNext()) {
                Move move = moves.next();
                Board originalBoard = board;
                makeMove(move);
                score = minimaxScore(depth, who);
                board = originalBoard;

                //unmakeMove(move);
                if (initialMoves != null) {
                    move.value = score;
                }
                if (score > bestScore) {
                    bestScore = score;
                }
            }
            return bestScore;
        } else {
            // min
            double score;
            double bestScore = maxEvaluateValue();
            while (moves.hasNext()) {
                Move move = moves.next();
                Board originalBoard = board;
                makeMove(move);
                score = minimaxScore(depth, who);

                board = originalBoard;
                if (initialMoves != null) {
                    move.value = score;
                }
                if (score < bestScore) {
                    bestScore = score;
                }
            }
            return bestScore;
        }
    }

    protected double minimaxScore(final int depth, final int who) {
        return minimax(null, depth - 1, -who);
    }

}