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
        int[][] offsets = { {1,0}, {0,1}, {-1,0}, {0,-1}, {1,1}, {-1,1}, {-1,-1}, {1,-1} };
        Position pos;

        for (int[] moves: offsets){
            pos = new Position(kingPos.getX()+moves[0], kingPos.getY()+moves[1]);
            if (board.posIsWithinBoard(pos) && !isSameColor(board.getPieceAt(pos)) && !willThisKingBePutInCheckByMoveTo(board,pos))
                validMoves.add(pos);
        }

        validMoves.addAll(getCastlingMoves(board, kingPos));

        return validMoves;
    }

    /**
     * Gets the possible moves involved in castling
     * @param board The board
     * @param kingPos Position of the king
     * @return The possible castling positions for the king
     */
    public ArrayList<Position> getCastlingMoves(Board board, Position kingPos) {
        ArrayList<Position> validMoves = new ArrayList<Position>();

        if (this.hasMoved)
            return validMoves;

        validMoves.addAll(getCastlingMoveEast(board, kingPos));
        validMoves.addAll(getCastlingMoveWest(board, kingPos));
        return validMoves;
    }

    /**
     * Checks whether or not the king can do castling in direction west.
     * @param board The board.
     * @param kingPos Position of the king.
     * @return If the king can do castling west.
     */
    private boolean canDoCastlingWest(Board board, Position kingPos) {
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
    /**
     * Gets the possible moves involved in castling west
     * @param board The board
     * @param kingPos Position of the king
     * @return The possible castling positions for the king in direction west
     */
    private ArrayList<Position> getCastlingMoveWest(Board board, Position kingPos) {
        ArrayList<Position> validMoves = new ArrayList<Position>();

        if (!canDoCastlingWest(board, kingPos)) {
            return validMoves;
        }
        validMoves.add(kingPos.west(2));

        return validMoves;
    }

    /**
     * Gets the possible moves involved in castling west
     * @param board The board
     * @param kingPos Position of the king
     * @return The possible castling positions for the king in direction west
     */
    private ArrayList<Position> getCastlingMoveEast(Board board, Position kingPos) {

        ArrayList<Position> validMoves = new ArrayList<Position>();

        if (!canDoCastlingEast(board, kingPos)) {
            return validMoves;
        }
        validMoves.add(kingPos.east(2));

        return validMoves;

    }

    /**
     * Checks whether or not the king can do castling in direction east.
     * @param board The board.
     * @param kingPos Position of the king.
     * @return If the king can do castling east.
     */
    private boolean canDoCastlingEast(Board board, Position kingPos) {

        King king = (King) board.getPieceAt(kingPos);
        AbstractChessPiece pieceEastCorner;

        if (king.isWhite) {
            Position posEastCorner = new Position(5, 0); //TODO: This is wrong, should be x = 7, y = 0;
            pieceEastCorner = board.getPieceAt(posEastCorner);

        } else {
            Position posEastCorner = new Position(5, 7);
            pieceEastCorner = board.getPieceAt(posEastCorner);
        }

        for (Position posToCheck = kingPos.east(); posToCheck.getX() < board.size() - 1; posToCheck = posToCheck.east()) {
            if (board.getPieceAt(posToCheck) != null) {
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
               if (otherPiece!= null && !isSameColor(otherPiece)){
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


    public boolean hasNoLegalMoves(Board board){
        ArrayList<Position> validMoves = getValidMoves(board);
        for (Position p : validMoves) {
            if (!willThisKingBePutInCheckByMoveTo(board, p))
                return false;
        }
        return true;
    }

/*
    public boolean isInStalemate(Board board){
        int[][] offsets = { {1,0}, {0,1}, {-1,0}, {0,-1}, {1,1}, {-1,1}, {-1,-1}, {1,-1} };
        Position kingPos = board.getPosition(this);
        Position pos;
        for (int[] moves: offsets){
            pos = new Position(kingPos+moves[0], kingPos+moves[1]);
            if (board.posIsWithinBoard(pos) && !willThisKingBePutInCheckByMoveTo(board, pos))
>>>>>>> origin/DEV
                return false;
        }
        return true;
    }*/

    public boolean isInStalemate(Board board){
        return (hasNoLegalMoves(board) && !isInCheck);
    }


    public int getValue() {
        return value;
    }
}
