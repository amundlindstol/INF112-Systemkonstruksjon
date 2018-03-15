package com.grnn.chess;
import com.grnn.chess.objects.*;

import java.util.ArrayList;

/**
 * Class to represent a board
 */
public class Board {

    // Variables
    private int size = 8;
    private ArrayList<ArrayList<AbstractChessPiece>> grid = new ArrayList<ArrayList<AbstractChessPiece>>(size);
    private ArrayList<AbstractChessPiece> removedPieces;

    public Board() {
        for(int i = 0; i < size; i++) {
            grid.add(new ArrayList<AbstractChessPiece>(size));
            for (int j = 0; j < size; j++)
                grid.get(i).add(null);
        }

        addPieces();
    }
    public AbstractChessPiece removePiece(){
        return null;
    }

    public void movePiece(Position startPos, Position endPos) {
        AbstractChessPiece piece = getPieceAt(startPos);

        //TODO :assert that the move is valid

        setPiece(piece, endPos);
        setPiece(null, startPos);
        piece.move();
    }

    public Board updateBoard(){
        return null;
    }

    public int size() {
        return grid.size();
    }

    public void addPieces() {


        for(int i = 0; i < size(); i++) {
            setPiece(new Pawn(true), i, 1);
            setPiece(new Pawn(false), i, 6);
        }

        setPiece(new Rook(true), 0, 0);
        setPiece(new Rook(true), 7, 0);

        setPiece(new Rook(false), 0, 7);
        setPiece(new Rook(false), 7, 7);

        setPiece(new Knight(true), 1, 0);
        setPiece(new Knight(true), 6, 0);

        setPiece(new Knight(false), 1, 7);
        setPiece(new Knight(false), 6, 7);

        setPiece(new Bishop(true), 2, 0);
        setPiece(new Bishop(true), 5, 0);
        setPiece(new Bishop(false), 2, 7);
        setPiece(new Bishop(false), 5, 7);

        setPiece(new Queen(true), 3, 0);
        setPiece(new Queen(false), 3, 7);

        setPiece(new King(true), 4, 0);
        setPiece(new King(false), 4, 7);


    }

    public void setPiece(AbstractChessPiece piece, Position pos) {
        setPiece(piece, pos.getX(), pos.getY());
    }

    public void setPiece(AbstractChessPiece piece, int x, int y) {
        grid.get(y).set(x, piece);
    }

    public AbstractChessPiece getPieceAt(Position p) {
        return grid.get(p.getY()).get(p.getX());
    }

    public Position getPosition(AbstractChessPiece piece) {
        for(int y = 0; y < size(); y++) {
            for(int x = 0; x < size(); x++) {
                Position p = new Position(x, y);
                if(getPieceAt(p).equals(piece))
                    return p;
            }
        }
        return null;
    }

    public boolean posIsWithinBoard(Position pos){
        return (pos.getX()>=0 && pos.getX()< size && pos.getY()>=0 && pos.getY()< size );
    }

    public String toString() {
        String out = "";
        for(int y = size - 1; y >= 0; y--) {
            out += y + "|";
            for(int x = 0; x < size(); x++) {
                Position p = new Position(x, y);
                AbstractChessPiece piece = getPieceAt(p);
                if(x != 0) {
                    out += "|";
                }
                if(piece != null) {
                    out += piece;
                } else {
                    if(y != size() - 1) {
                        out += "_";
                    }else{
                        out += " ";
                    }
                }
            }
            out += "\n";
        }
        out += "  A|B|C|D|E|F|G|H\n";
        return out;
    }
}