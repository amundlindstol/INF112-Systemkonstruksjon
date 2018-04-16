package com.grnn.chess.objects;


import com.grnn.chess.Board;
import com.grnn.chess.Position;
//import javafx.geometry.Pos;

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
        setImage("King");
    }

    public void move(){
        hasMoved = true;
    }

    public String toString() {
        return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
    }

    /**
     * Returns a list of the kings neighbouring positions the king can move to without being put in check
     * @param board The board
     * @return List of positions the king can move to
     */
    public ArrayList<Position> getValidMoves(Board board) {
        ArrayList<Position> validMoves = new ArrayList<Position>();
        Position kingPos = getPosition(board);
        ArrayList<Position> neighbourSquares = getNeighbourSquares(board, kingPos);
        for (Position pos : neighbourSquares){
            if ((board.getPieceAt(pos)==null || !isSameColor(board.getPieceAt(pos))) && !willThisKingBePutInCheckByMoveTo(board, pos)) {
                validMoves.add(pos);
            }
        }

        validMoves.addAll(getCastlingMoves(board, kingPos));

        return validMoves;
    }

    /**
     * @param board The board
     * @param kingPos Position of the king
     * @return The squares adjacent to the king
     */
    private ArrayList<Position> getNeighbourSquares(Board board, Position kingPos){
        ArrayList<Position> neighbourSquares = new ArrayList<Position>();
        int[][] offsets = { {1,0}, {0,1}, {-1,0}, {0,-1}, {1,1}, {-1,1}, {-1,-1}, {1,-1} };
        Position pos;

        for (int[] moves: offsets){
            pos = new Position(kingPos.getX()+moves[0], kingPos.getY()+moves[1]);
            if (board.posIsWithinBoard(pos))
                neighbourSquares.add(pos);
        }
        return neighbourSquares;
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

        for (Position posToCheck = kingPos.west(); posToCheck.getX() > 0; posToCheck = posToCheck.west()) {
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
            Position posEastCorner = new Position(7, 0); //TODO: This is wrong, should be x = 7, y = 0;
            pieceEastCorner = board.getPieceAt(posEastCorner);

        } else {
            Position posEastCorner = new Position(7, 7);
            pieceEastCorner = board.getPieceAt(posEastCorner);
        }

        for (Position posToCheck = kingPos.east(); posToCheck.getX() < board.size() - 1; posToCheck = posToCheck.east()) {
            if (board.getPieceAt(posToCheck) != null) {
                return false;
            }
        }
        return pieceEastCorner != null && pieceEastCorner instanceof Rook && !((Rook) pieceEastCorner).hasMoved();
    }

    /**
     * Checks whether or not this king will put himself in check by moving to pos
     * @param board The board
     * @param pos The position to check
     * @return If the king will put himself in check by moving to pos
     */
    public boolean willThisKingBePutInCheckByMoveTo(Board board, Position pos) {
       for (int i=0; i<board.size(); i++) {
           for (int j=0; j<board.size(); j++) {
               Position posOtherPiece = new Position(i,j);
               AbstractChessPiece otherPiece = board.getPieceAt(posOtherPiece);
               if (otherPiece!= null && !isSameColor(otherPiece)){
                   if (otherPiece instanceof Pawn) {
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
                   else if (otherPiece instanceof King){
                       if (getNeighbourSquares(board, pos).contains(otherPiece.getPosition(board)))
                           return true;
                   }
                   else {
                       if (otherPiece.getPossibleMovesIgnoringCheck(board).contains(pos)){
                           return true;
                       }
                   }
               }
           }
       }
        return false;
    }

    /**
     * @param board The board
     * @return True if the king has no valid moves
     */
    public boolean hasNoLegalMoves(Board board){
        ArrayList<Position> validMoves = getValidMoves(board);
        for (Position p : validMoves) {
            if (!willThisKingBePutInCheckByMoveTo(board, p))
                return false;
        }
        return true;
    }

    /**
     * @param board The board
     * @return True if the king is InStalemate
     */
    public boolean isInStalemate(Board board){
        return (hasNoLegalMoves(board) && !isInCheck);
    }

    public int getValue() {
        return value;
    }
}
