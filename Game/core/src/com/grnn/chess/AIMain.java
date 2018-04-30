package com.grnn.chess;

import com.grnn.chess.Actors.AI.AI;

import java.util.ArrayList;
import java.util.Scanner;

public class AIMain {

    public static void main(String[] args) {
        Board board = new Board();
        board.initializeBoard();
        System.out.println("welcome to chess3000, get ready to play!");

        AI aiBlack = new AI(2, false);
        AI aiWhite = new AI(2, true);


        for(int i = 0; i < 20; i++) {
            Move bestMoveWhite = aiWhite.calculateBestMove(board);
            System.out.println("White doing move " + bestMoveWhite);
            board.movePiece(bestMoveWhite.getToPos(), bestMoveWhite.getFromPos());

            Move bestMoveBlack = aiBlack.calculateBestMove(board);
            System.out.println("White doing move " + bestMoveBlack);
            board.movePiece(bestMoveBlack.getToPos(), bestMoveBlack.getFromPos());
        }



    }

    public static void print(ArrayList<Position> vMoves) {
        System.out.println("These are your potential moves, choose one(0-"+(vMoves.size()-1)+")");
        for(int i=0; i<vMoves.size(); i++){

            System.out.println(i+": ("+vMoves.get(i).getX()+", "+vMoves.get(i).getY()+")");
        }
    }
}
