package com.grnn.chess;

import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.grnn.chess.AI.AI;
import com.grnn.chess.objects.*;
import com.grnn.chess.states.SelectPlayerState;

import java.util.ArrayList;

public class Game {

    private Board board;
    private Player player1;
    private Player player2;
    private AI aiPlayer;
    private boolean turn;
    private AbstractChessPiece firstPiece;
    private ArrayList<Position> validMoves;
    private ArrayList<Position> captureMoves;
    private ArrayList<Position> castlingMoves;
    private AbstractChessPiece potentialPiece;

    private boolean removed;
    private int[] removedPieces;

    private boolean blackPutInCheck;
    private boolean whitePutInCheck;

    private int gameId;

    static int currid = 1;

    public static void setCurrid(int id) {
        currid = id;
    }

    public Game(){
        player1 = new Player("Spiller1", "asd");
        player2 = new Player("Spiller2", "asd");
        gameId = ++currid;

        board = new Board();
        board.initializeBoard();
        validMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
        castlingMoves = new ArrayList<Position>();
        firstPiece = null;
        potentialPiece = null;
        turn = true;

        blackPutInCheck = false;
        whitePutInCheck = false;

        removedPieces = new int[12];
        for(Integer count : removedPieces){
            count = 0;
        }
    }

    public Board getBoard(){
        return board;
    }

    public int[] getRemovedPieces(){
        return removedPieces;
    }
    public Boolean getTurn(){
        return turn;
    }
    public Boolean pieceHasNotBeenSelected(){
        return (firstPiece==null);
    }

    public ArrayList<Position> getValidMoves() {
        return validMoves;
    }

    public ArrayList<Position> getCaptureMoves() {
        return captureMoves;
    }

    private void startGame(boolean aiPlayer) {
        // initialize Ai player
        if(aiPlayer) this.aiPlayer = new AI();



    }


    public void aiMove(){
        if(aiPlayer!=null && !turn){
            Move aiMove = aiPlayer.calculateBestMove(board);
            AbstractChessPiece victim = board.getPieceAt(aiMove.getToPos());
            if(victim !=null){
                board.removePiece(victim);
                updatePieceCounter(victim);
            }
            board.movePiece(aiMove.getFromPos(),aiMove.getToPos());
            handleCheckChecking();
            turn = !turn;
        }
    }

    /**
     * Method for selecting the first piece
     * @param selectedPosition
     * @return A list of lists of valid positions. In the first position validmoves, second position capturemoves and third castlingmoves
     */
    public void selectFirstPiece(Position selectedPosition){
        firstPiece = board.getPieceAt(selectedPosition);
        System.out.println("SelectedFirstPiece");
        if(firstPiece != null && firstPiece.isWhite() == turn){
            validMoves = firstPiece.getValidMoves(board);
            captureMoves = firstPiece.getCaptureMoves(board);
            System.out.println("Updated moves");
            if(firstPiece instanceof King){
                castlingMoves = ((King) firstPiece).getCastlingMoves(board,selectedPosition);
            }
        } else {
            firstPiece = null;
        }
    }

    public void moveFirstSelectedPieceTo(Position secondPosition){
        potentialPiece = board.getPieceAt(secondPosition);
        Boolean validMove = validMoves.contains(secondPosition) || captureMoves.contains(secondPosition) || castlingMoves.contains(secondPosition);
        if(potentialPiece != null) {
                if(validMove) {
                    board.removePiece(potentialPiece);
                    removed = true;
                    updatePieceCounter(potentialPiece);
                    board.movePiece(board.getPosition(firstPiece), secondPosition);
                    handleCheckChecking();
                    turn = !turn;
                    reset();
                } else if(potentialPiece.isWhite()==turn){
                    reset();
                    selectFirstPiece(secondPosition);
                    removed = false;
                } else {
                    removed = false;
                    reset();
                }
        } else if(potentialPiece == null && validMove){
            handlingCasting();
            board.movePiece(board.getPosition(firstPiece),secondPosition);
            handleCheckChecking();
            reset();
            turn = !turn;
            removed = false;
        } else {
            reset();
            removed = false;
        }
        validMoves.clear();
        captureMoves.clear();
    }

    private void reset(){
        firstPiece = null;
        validMoves.clear();
        captureMoves.clear();
        castlingMoves.clear();
    }
    private void endGame(int res) {
        return;
    }

    private Player announceWinner() {
        return null;
    }

    public void handleCheckChecking(){
        Position kingPos = board.getKingPos(!turn);
        if(kingPos != null){
            King king = (King) board.getPieceAt(kingPos);
            king.isInCheck = king.willThisKingBePutInCheckByMoveTo(board, kingPos);
            if(king.isInCheck){
                if(turn){
                    blackPutInCheck = true;
                }else{
                    whitePutInCheck = true;
                }
                System.out.println("SJAKK");
            }
        }
    }

    /**
     * Moves the rook if the king does castling.
     *
     */
    private void handlingCasting(){
        if(potentialPiece==null)return;
        Position potentialPos = board.getPosition(potentialPiece);
        if(!castlingMoves.contains(potentialPos)) return;

        Position rookOriginalPos = null;
        Position rookNewPos = null;

        if (potentialPiece.isWhite()){
            if(potentialPos.equals(new Position(2,0))){
                rookOriginalPos = new Position(0, 0);
                rookNewPos = new Position(3, 0);
            } else if (potentialPos.equals(new Position(6, 0))) {
                rookOriginalPos = new Position(7, 0);
                rookNewPos = new Position(5, 0);
            }
        }else {
            if (potentialPos.equals(new Position(2, 7))) {
                rookOriginalPos = new Position(0, 7);
                rookNewPos = new Position(3, 7);
            } else if (potentialPos.equals(new Position(6, 7))) {
                rookOriginalPos = new Position(7, 7);
                rookNewPos = new Position(5, 7);
            }
        }
        board.movePiece(rookOriginalPos, rookNewPos);
    }


    /**
     * Method to update the counter for removed pieces
     * @param removedPiece, the piece that is removed
     */
    private void updatePieceCounter(AbstractChessPiece removedPiece){
        if(removedPiece instanceof Pawn){
            if(!turn)
                removedPieces[0]++;
            else
                removedPieces[6]++;
        }else if(removedPiece instanceof Bishop){
            if(!turn)
                removedPieces[1]++;
            else
                removedPieces[7]++;
        }else if(removedPiece instanceof King){
            if(!turn)
                removedPieces[2]++;
            else
                removedPieces[8]++;
        }else if(removedPiece instanceof Queen){
            if(!turn)
                removedPieces[3]++;
            else
                removedPieces[9]++;
        }else if(removedPiece instanceof Rook){
            if(!turn)
                removedPieces[4]++;
            else
                removedPieces[10]++;
        }else if(removedPiece instanceof Knight){
            if(!turn)
                removedPieces[5]++;
            else
                removedPieces[11]++;
        }
    }

    public String getText(){
        String text = "";
        if(aiPlayer!=null){
            if (turn) {
                text = "Venter på at du skal gjøre neste trekk.";
                if (removed) {
                    text = "Bra jobbet! Du tok en brikke.";
                }
            } else {
                if(removed) {
                    text = "Uff. Datamaskinen tok en brikke av deg.";
                }
            }
        }
        else{
            if (turn) {
                text = "Venter på at du skal gjøre neste trekk.";
                if (removed) {
                    text = "Uff. Du mistet en brikke. Det er din tur.";
                }

            } else {
                text = "Venter på at vennen din skal gjøre neste trekk.";

                if (removed) {
                    text = "Bra jobbet! Du tok en brikke. Det er vennen din sin tur.";
                }
            }
        }
        return text;
    }
}