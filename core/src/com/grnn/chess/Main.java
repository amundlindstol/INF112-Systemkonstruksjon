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

        boolean isWhite = true;
        while (true) {
            System.out.println(board);
            if (isWhite) {
                System.out.println(player1 + " its your move, what piece do you want to move? (x,y)");
                if(chooseMove(input, board, isWhite)){
                    isWhite = false;
                }else {
                    System.out.println("Not valid "+player1+", try again");                }
            }else{
                System.out.println(player2 + " its your move, what piece do you want to move? (x,y)");
                if(chooseMove(input, board, isWhite)){
                    isWhite = true;
                }else {
                    System.out.println("Not valid "+player2+", try again");

                }
            }
        }



    }

    public static void print(ArrayList<Position> vMoves) {
        System.out.println("These are your potential moves, choose one(0-"+(vMoves.size()-1)+")");
        for(int i=0; i<vMoves.size(); i++){

            System.out.println(i+": ("+vMoves.get(i).getX()+", "+vMoves.get(i).getY()+")");
        }
    }

    public static boolean chooseMove(Scanner input, Board board, Boolean turn){
        int x = input.nextInt();
        int y = input.nextInt();
        Position potential = new Position(x, y);
        AbstractChessPiece piece = board.getPieceAt(potential);
        if(piece == null) return false;
        if(piece.getColor()!=turn) return false;

        ArrayList<Position> vMoves = board.getPieceAt(potential).getValidMoves(board);
        removeNotValidMoves(vMoves);

        if(vMoves.size()==0) return false;
        print(vMoves);
        int mNumber = input.nextInt();
        board.movePiece(potential, vMoves.get(mNumber));
        return true;
    }

    public static void removeNotValidMoves(ArrayList<Position> moves){
        for(Position pos : moves){
            int x = pos.getX();
            int y = pos.getY();
            if(x<0 || x>7 || y<0 || y>7){
                moves.remove(pos);
            }
        }
    }
}
