package com.grnn.chess;

import com.grnn.chess.objects.AbstractChessPiece;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board);

        //AbstractChessPiece piece = board.getPieceAt(new Position(6,6));


        ArrayList<Position> validMoves = board.getPieceAt(new Position(6,1)).getValidMoves(board);
        System.out.println("Found "+validMoves.size()+" valid moves:");
        for(int i=0; i<validMoves.size(); i++) {
            System.out.print(validMoves.get(i).getX() + ", ");
            System.out.println(validMoves.get(i).getY());
        }

        System.out.println(board);
    }
}
