package com.grnn.chess.objects;

import com.grnn.chess.Board;
import com.grnn.chess.Position;
//import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract class to represent a chess piece
 */
public abstract class AbstractChessPiece {
	protected boolean hasMoved = false;
    protected boolean isMoving = false;
    protected boolean isWhite;
    protected boolean validMove;
    //protected ArrayList<Position> validMoves;
    protected ArrayList<Position> captureMoves;
    protected String letterRepresentation = "";
    protected String image = "Graphics/ChessPieces/";
    protected final int value = 0; // Should value be set in the abstract class?

    /**
     * Contructur of an AbstractChessPiece
     * @param isWhite
     */
    public AbstractChessPiece(boolean isWhite) {
        this.isWhite = isWhite;
    }
    /**
     * Returns the color of the piece
     * @return True if color is white otherwise false
     */
    public boolean isWhite(){
        return isWhite;
    }

    /**
     * Returns the position of the piece
     * @param board The board that the piece is on
     * @return The position of the piece in the board
     */
    public Position getPosition(Board board) {
        return board.getPosition(this);
    }

    /**
     * Check if another piece is equal to this piece
     * @param otherPiece the other piece
     * @return True if the hashcode of this piece equals the hashcode of the other piece
     */
    public boolean equals(AbstractChessPiece otherPiece) { return this.hashCode() == otherPiece.hashCode(); }

    /**
     * Finds the valid move for this piece in this board
     * @param board The board that the piece is on
     * @return A list of positions that the piece can move to
     */
    public ArrayList<Position> getValidMoves(Board board) {
        return new ArrayList<Position>();
    }

    public ArrayList<Position> getValidMovesIgnoringCheck(Board board) {
        return new ArrayList<Position>();
    }

    /**
     * Finds the valid moves for this piece where the piece can capture the opponents piece.
     * @param board The board that the piece is on
     * @return A list of positions that the piece can capture the opponents piece on
     */
    public ArrayList<Position> getCaptureMoves(Board board) {

        List<Position> captureMoves = new ArrayList<Position>();
        List<Position> validMoves = getValidMoves(board);

        for(Position position : validMoves) {
            AbstractChessPiece pieceAtPos = board.getPieceAt(position);
            if (pieceAtPos != null && !pieceAtPos.isSameColor(this)) {
                captureMoves.add(position);
            }
        }

        return (ArrayList<Position>) captureMoves;
    }

    /**
     * Check is another piece have the same color as this
     * @param otherPiece the other piece
     * @return True if this piece has same color as the other otherwise false
     */
    public boolean isSameColor(AbstractChessPiece otherPiece){
        if(otherPiece == null)
            return false;
        return isWhite() == otherPiece.isWhite();
    }

    /**
     * callback called when piece has been moved, used to set hasMoved
     */
    public void move() {
    	hasMoved = true;
	}

    /**
     * is piece startMoving? used to animate movement
     */
    public boolean isMoving() {
        return isMoving;
    }

    public void startMoving() {
        isMoving = true;
    }
    public void stopMoving() {
        isMoving = false;
    }

    /**
     * Returns stringrepresentation of the piece
     * @return
     */
    public String toString() {
		return isWhite ? letterRepresentation : letterRepresentation.toUpperCase();
	}

    /**
     * Set image for the piece
     * @param image
     */
	public void setImage(String image){
        this.image += image;
    }

    /**
     * Get the right image path for the piece
     * @return the relative path of the image for the piece
     */
	public String getImage() {
        String imageS = isWhite ? image + "W" : image + "B";

        if(this instanceof Bishop || this instanceof Rook || this instanceof Knight) {
            imageS += "L";
        }

        return imageS + ".png";
    }
}