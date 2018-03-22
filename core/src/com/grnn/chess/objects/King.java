package com.grnn.chess.objects;


import com.grnn.chess.Board;
import com.grnn.chess.Position;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class King extends AbstractChessPiece {
    private final int value = Integer.MAX_VALUE;
    public boolean isInCheck;
    String letterRepresentation = "k";
    private boolean hasMoved;

    public King(boolean w) {
        super(w);
        hasMoved = false;
        setImage("King");
    }

    public String toString() {
        return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
    }

    public ArrayList<Position> getValidMoves(Board board) {
        ArrayList<Position> validMoves = new ArrayList<Position>();
        Position kingPos = getPosition(board);

        if (board.posIsWithinBoard(kingPos.west()) && !isSameColor(board.getPieceAt(kingPos.west())))
            validMoves.add(kingPos.west());

        if (board.posIsWithinBoard(kingPos.east()) && !isSameColor(board.getPieceAt(kingPos.east())))
            validMoves.add(kingPos.east());

        if (board.posIsWithinBoard(kingPos.north()) && !isSameColor(board.getPieceAt(kingPos.north())))
            validMoves.add(kingPos.north());

        if (board.posIsWithinBoard(kingPos.south()) && !isSameColor(board.getPieceAt(kingPos.south())))
            validMoves.add(kingPos.south());

        if (board.posIsWithinBoard(kingPos.north().west()) && !isSameColor(board.getPieceAt(kingPos.north().west())))
            validMoves.add(kingPos.north().west());

        if (board.posIsWithinBoard(kingPos.north().east()) && !isSameColor(board.getPieceAt(kingPos.north().east())))
            validMoves.add(kingPos.north().east());

        if (board.posIsWithinBoard(kingPos.south().west()) && !isSameColor(board.getPieceAt(kingPos.south().west())))
            validMoves.add(kingPos.south().west());

        if (board.posIsWithinBoard(kingPos.south().east()) && !isSameColor(board.getPieceAt(kingPos.south().east())))
            validMoves.add(kingPos.south().east());

        validMoves.addAll(getCastlingMoves(board, kingPos));

        return validMoves;
    }

    public ArrayList<Position> getCastlingMoves(Board board, Position kingPos) {
        ArrayList<Position> validMoves = new ArrayList<Position>();

        if (this.hasMoved)
            return validMoves;

        validMoves.addAll(getCastlingMoveEast(board, kingPos));
        validMoves.addAll(getCastlingMoveWest(board, kingPos));
        return validMoves;
    }

    private boolean canDoCastlingWest(Board board, Position kingPos) {
        System.out.println("canDoCastlingWest");
        King king = (King) board.getPieceAt(kingPos);
        AbstractChessPiece pieceWestCorner = null;

        if (king.isWhite) {
            Position posWestCorner = new Position(0, 0);
            pieceWestCorner = board.getPieceAt(posWestCorner);

        } else {
            Position posWestCorner = new Position(0, 7);
            pieceWestCorner = board.getPieceAt(posWestCorner);
        }

        for (Position posToCheck = kingPos.west(); posToCheck.getX() > 0; posToCheck.west()) {
            if (board.getPieceAt(posToCheck) != null)
                return false;
        }

        return pieceWestCorner != null && pieceWestCorner instanceof Rook && !((Rook) pieceWestCorner).hasMoved();

    }

    private ArrayList<Position> getCastlingMoveWest(Board board, Position kingPos) {
        System.out.println("getCastlingMoveWest");
        ArrayList<Position> validMoves = new ArrayList<Position>();

        if (!canDoCastlingWest(board, kingPos)) {
            return validMoves;
        }
        validMoves.add(kingPos.west(2));

        return validMoves;
    }

    private ArrayList<Position> getCastlingMoveEast(Board board, Position kingPos) {
        System.out.println("getCastlingMoveEast");

        ArrayList<Position> validMoves = new ArrayList<Position>();

        if (!canDoCastlingEast(board, kingPos)) {
            return validMoves;
        }
        validMoves.add(kingPos.east(2));

        return validMoves;

    }

    private boolean canDoCastlingEast(Board board, Position kingPos) {
        System.out.println("canDoCastlingEast");

        King king = (King) board.getPieceAt(kingPos);
        AbstractChessPiece pieceEastCorner;

        if (king.isWhite) {
            Position posEastCorner = new Position(7, 0);
            pieceEastCorner = board.getPieceAt(posEastCorner);

        } else {
            Position posEastCorner = new Position(7, 7);
            pieceEastCorner = board.getPieceAt(posEastCorner);
        }

        for (Position posToCheck = kingPos.east(); posToCheck.getX() < board.size() - 1; posToCheck = posToCheck.east()) {
            System.out.println(board.getPieceAt(posToCheck) + " at pos: " + posToCheck);
            if (board.getPieceAt(posToCheck) != null) {
                System.out.println("ska ikkje skje");
                return false;
            }
        }
        return pieceEastCorner != null && pieceEastCorner instanceof Rook && !((Rook) pieceEastCorner).hasMoved();
    }

    public boolean willKingBePutInCheckByMoveTo(Board board, AbstractChessPiece king, Position pos) {
        Position posToCheck = pos.north();
        if (board.posIsWithinBoard(posToCheck)) {
            if (king.getPosition(board).equals(posToCheck))
                return true;
        }
        posToCheck = pos.south();
        if (board.posIsWithinBoard(posToCheck)) {
            if (king.getPosition(board).equals(posToCheck))
                return true;
        }
        posToCheck = pos.west();
        if (board.posIsWithinBoard(posToCheck)) {
            if (king.getPosition(board).equals(posToCheck))
                return true;
        }
        posToCheck = pos.east();
        if (board.posIsWithinBoard(posToCheck)) {
            if (king.getPosition(board).equals(posToCheck))
                return true;
        }
        return false;
    }

    public boolean willThisKingBePutInCheckByMoveTo(Board board, Position pos) {
       for (int i=0; i<board.size(); i++) {
           for (int j=0; j<board.size(); j++) {
               Position posOtherPiece = new Position(i,j);
               AbstractChessPiece otherPiece = board.getPieceAt(posOtherPiece);
               if (!isSameColor(otherPiece)){
                   if (!(otherPiece instanceof Pawn)) {
                       if (otherPiece.getValidMoves(board).contains(pos))
                           return true;
                   }
                   else {
                       if(otherPiece.isWhite){
                           if(otherPiece.getPosition(board).north(1).east(1).equals(pos))
                               return true;
                           if(otherPiece.getPosition(board).north(1).west(1).equals(pos))
                               return true;
                       }
                       else {
                           if(otherPiece.getPosition(board).south(1).east(1).equals(pos))
                               return true;
                           if(otherPiece.getPosition(board).south(1).west(1).equals(pos))
                               return true;
                       }
                   }
               }
           }
       }
        return false;
    }
/*
    public boolean isInStalemate(Board board){
        int[][] offsets = { {1,0}, {0,1}, {-1,0}, {0,-1}, {1,1}, {-1,1}, {-1,-1}, {1,-1} };
        Position kingPos = board.getPosition(this);
        Position pos;
        for (int[] moves: offsets){
            pos = new Position(kingPos+moves[0], kingPos+moves[1]);
            if (board.posIsWithinBoard(pos) && !willThisKingBePutInCheckByMoveTo(board, pos))
                return false;
        }
        return true;
    }*/

    public int getValue() {
        return value;
    }
}
