package com.grnn.chess;

import com.grnn.chess.AI.AI;

import java.util.ArrayList;
import java.util.Scanner;

public class AIMain {

    public static void main(String[] args) {
        Board board = new Board();
        board.addPieces();
        Scanner input = new Scanner(System.in);
        System.out.println("welcome to chess3000, get ready to play!");
        System.out.println("Player one, give me your name: ");
        String player1 = input.nextLine();
        AI ai = new AI();
        Move bestMove = ai.calculateBestMove(board);
        System.out.println("Doing move " + bestMove);



    }

    public static void print(ArrayList<Position> vMoves) {
        System.out.println("These are your potential moves, choose one(0-"+(vMoves.size()-1)+")");
        for(int i=0; i<vMoves.size(); i++){

            System.out.println(i+": ("+vMoves.get(i).getX()+", "+vMoves.get(i).getY()+")");
        }
    }
}
