package com.grnn.chess;

import com.grnn.chess.exceptions.IllegalMoveException;
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
    private ArrayList<Move> moveHistory;

    private ArrayList<Position> positions;

    public Board() {
        moveHistory = new ArrayList<Move>();
        removedPieces = new ArrayList<AbstractChessPiece>();
        positions = new ArrayList<Position>();

        for (int i = 0; i < size; i++) {
            grid.add(new ArrayList<AbstractChessPiece>(size));
            for (int j = 0; j < size; j++) {
                grid.get(i).add(null);
                positions.add(new Position(j, i));
            }
        }
    }


    public void removePiece(AbstractChessPiece piece) {
        removedPieces.add(piece);
    }

    public ArrayList<AbstractChessPiece> getRemovedPieces() {
        return removedPieces;
    }

    public void movePiece(Position startPos, Position endPos) {
        AbstractChessPiece piece = getPieceAt(startPos);

        if (piece.getValidMoves(this).contains(endPos) || piece.getCaptureMoves(this).contains(endPos)) {

            //if (isValidMove(startPos, endPos)) {
            setPiece(piece, endPos);
            setPiece(null, startPos);
            piece.move();
            moveHistory.add(new Move(endPos, startPos, piece));
            enPassant();

            //} else {
            //    throw new IllegalMoveException("movePiece was called with illegal arguments");
            //}
        }
    }

    public Position getKingPos(boolean kingIsWhite){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if (getPieceAt(new Position(i, j)) instanceof King && getPieceAt(new Position(i, j)).isWhite()==kingIsWhite){
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    private boolean isValidMove(Position startPos, Position endPos) {
        AbstractChessPiece piece = getPieceAt(startPos);
        boolean containsCastlingMove;
        if(piece instanceof King) {
            ArrayList<Position> castlingMoves = ((King) piece).getCastlingMoves(this, startPos);
            containsCastlingMove = castlingMoves.contains(endPos);
        } else {
            containsCastlingMove = false;
        }

        return piece.getValidMoves(this).contains(endPos)
                || piece.getCaptureMoves(this).contains(endPos)
                || containsCastlingMove;
    }

    // TODO: AI is not always black

    /**
     * Gets possible moves the AI can do.
     * @return possible moves the AI can do.
     */
    public ArrayList<Move> getPossibleAIMoves() { //Aka get black's moves

        ArrayList<Move> possibleMoves = new ArrayList<Move>();

        for(Position position : positions){
            AbstractChessPiece piece = this.getPieceAt(position);
            if(piece!=null && !piece.isWhite()) {
                ArrayList<Position> posList = piece.getValidMoves(this);
                posList.addAll(piece.getCaptureMoves(this));
                if (!posList.isEmpty()){
                    for (Position toMove : posList) {
                        Move newMove = new Move(toMove, position, piece);
                        possibleMoves.add(newMove);
                    }
                }
            }
        }
        return possibleMoves;
    }


    public Board updateBoard() {
        return null;
    }

    public int size() {
        return grid.size();
    }

    /**
     * Initializes the board with pieces in standard starting positions
     */
    public void initializeBoard() {

        for (int i = 0; i < size(); i++) {
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
        if (pawnCanPromote(piece, y)) {
            piece = new Queen(piece.isWhite());
        }
        grid.get(y).set(x, piece);
    }

    private boolean pawnCanPromote(AbstractChessPiece piece, int y) {
        if (piece instanceof Pawn) {
            if ((piece.isWhite() && y == size() - 1)
                    || (!piece.isWhite() && y == 0)) {
                return true;
            }
        }
        return false;
    }

    public AbstractChessPiece getPieceAt(Position p) {
        if (posIsWithinBoard(p)) {
            return grid.get(p.getY()).get(p.getX());
        }
        return null;
    }

    public Position getPosition(AbstractChessPiece piece) {
        for (int y = 0; y < size(); y++) {
            for (int x = 0; x < size(); x++) {
                Position p = new Position(x, y);
                if (getPieceAt(p) != null && getPieceAt(p).equals(piece))
                    return p;
            }
        }
        return null;
    }

    public boolean posIsWithinBoard(Position pos) {
        return (pos.getX() >= 0 && pos.getX() < size && pos.getY() >= 0 && pos.getY() < size);
    }

    public boolean equals(Board other) {
        return grid.equals(other.grid);
    }

    public ArrayList<Move> getMoveHistory() {
        return moveHistory;
    }

    public String toString() {
        String out = "";
        for (int y = size - 1; y >= 0; y--) {
            out += y + "|";
            for (int x = 0; x < size(); x++) {
                Position p = new Position(x, y);
                AbstractChessPiece piece = getPieceAt(p);
                if (x != 0) {
                    out += "|";
                }
                if (piece != null) {
                    out += piece;
                } else {
                    if (y != size() - 1) {
                        out += "_";
                    } else {
                        out += " ";
                    }
                }
            }
            out += "\n";
        }
        out += "  A|B|C|D|E|F|G|H\n";
        return out;
    }

    /**
     * Handles the en passant move
     */
    public void enPassant() {
        if (moveHistory.size() > 2) {
            Move lastMove = moveHistory.get(moveHistory.size() - 1);
            Position lastFromPos = lastMove.getFromPos();
            Position lastToPos = lastMove.getToPos();
            AbstractChessPiece lastPiece = lastMove.getPiece();

            Move conditionMove = moveHistory.get(moveHistory.size() - 2);
            Position conditionFromPos = conditionMove.getFromPos();
            Position conditionToPos = conditionMove.getToPos();
            AbstractChessPiece conditionPiece = conditionMove.getPiece();
            if(lastPiece.getClass() == Pawn.class && conditionPiece.getClass() == Pawn.class) {
                if (conditionToPos.getX() == lastToPos.getX()) {
                    if (lastPiece.isWhite() && lastFromPos.getY() == 4 && lastToPos.getY() == 5) {
                        if (!conditionPiece.isWhite() && conditionFromPos.getY() == 6 && conditionToPos.getY() == 4) {
                            setPiece(null, conditionToPos);
                        }
                    } else if (lastFromPos.getY() == 3 && lastToPos.getY() == 2) {
                        if (conditionPiece.isWhite() && conditionFromPos.getY() == 1 && conditionToPos.getY() == 3) {
                            setPiece(null, conditionToPos);
                        }

                    }
                }
            }
        }
    }
}