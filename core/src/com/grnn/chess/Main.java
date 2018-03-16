package com.grnn.chess;

import com.grnn.chess.objects.AbstractChessPiece;
import com.grnn.chess.Position;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner input = new Scanner(System.in);
        System.out.println("welcome to chess3000, get ready to play!");
        System.out.println("Player one, give me your name: ");
        String player1 = input.nextLine();
        System.out.println("Player two, give me your name: ");
        String player2 = input.nextLine();

        boolean playerOne = true;
        while (true) {
            System.out.println(board);
            if (playerOne) {
                System.out.println(player1 + " its your move, what piece do you want to move? (x,y)");
                board = chooseMove(input, board);
                playerOne = false;
            }else{
                System.out.println(player2 + " its your move, what piece do you want to move? (x,y)");
                board = chooseMove(input,board);
                playerOne = true;
            }
        }



    }

    public static void print(ArrayList<Position> vMoves) {
        System.out.println("These are your potential moves, choose one(0-"+(vMoves.size()-1)+")");
        for(int i=0; i<vMoves.size(); i++){

            System.out.println(i+": ("+vMoves.get(i).getX()+", "+vMoves.get(i).getY()+")");
        }
    }

    public static Board chooseMove(Scanner input, Board board){
        int x = input.nextInt();
        int y = input.nextInt();
        Position potential = new Position(x, y);
        ArrayList<Position> vMoves = board.getPieceAt(potential).getValidMoves(board);
        print(vMoves);
        int mNumber = input.nextInt();
        board.movePiece(potential, vMoves.get(mNumber));
        return board;
    }
}
