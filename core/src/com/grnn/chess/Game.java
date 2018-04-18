package com.grnn.chess;

import com.grnn.chess.AI.AI;
import com.grnn.chess.objects.*;
import javafx.geometry.Pos;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
    private Move aiMove;

    private boolean removed;
    private int[] removedPieces;

    private boolean blackPutInCheck;
    private boolean whitePutInCheck;

    private int gameId;

    static int currid = 1;

    public static void setCurrid(int id) {
        currid = id;
    }

    public Game(int aiLevel, Player player1, Player player2){
        if(player1!=null) {
            this.player1 = player1;
        }
        if(player2!=null) {
            this.player2 = player2;
        } else {
            aiPlayer = new AI(aiLevel);
        }
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
        for(Integer count : removedPieces){ //TODO what even is this
            count = 0;
        }
    }

    public Board getBoard(){
        return board;
    }

    public int[] getRemovedPieces(){
        return removedPieces;
    }
    /**
     * @return true if its whites turn
     */
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
    public boolean isAi() {return aiPlayer != null ? true : false;}

    /**
     * ai's move method
     * @return "best" move for ai
     */
    public Move aiMove(){
        if(aiPlayer!=null && !turn){
            aiMove = aiPlayer.calculateBestMove(board);
            AbstractChessPiece victim = board.getPieceAt(aiMove.getToPos());
            if(victim !=null){
                board.removePiece(victim);
                updatePieceCounter(victim);
            }
            aiMove.getPiece().startMoving();
            handleCheckChecking();
            turn = !turn;
            return aiMove;
        }
        return null;
    }

    /**
     * used once, do not use this method
     * @return aiMove
     */
    public Move getAiMove() {
        if (aiMove == null) aiMove = aiPlayer.calculateBestMove(board);
        return aiMove;
    }

    /**
     * Method for selecting the first piece
     * @param selectedPosition
     * @return A list of lists of valid positions. In the first position validmoves, second position capturemoves and third castlingmoves
     */
    public void selectFirstPiece(Position selectedPosition){
        firstPiece = board.getPieceAt(selectedPosition);
        if(firstPiece != null && firstPiece.isWhite() == turn){
            validMoves = firstPiece.getValidMoves(board);
            captureMoves = firstPiece.getCaptureMoves(board);
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
//                    board.movePiece(board.getPosition(firstPiece), secondPosition);
                    firstPiece.startMoving();
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
//            board.movePiece(board.getPosition(firstPiece), secondPosition);
            firstPiece.startMoving();
            handlingCasting(secondPosition);
            handleCheckChecking();
            reset();
            turn = !turn;
            removed = false;
        } else {
            reset();
            removed = false;
        }
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
    private void handlingCasting(Position secondPos){
        if(potentialPiece!=null)return;
        Position potentialPos = secondPos;
        if(!castlingMoves.contains(potentialPos)) return;

        Position rookOriginalPos = null;
        Position rookNewPos = null;

        if (firstPiece.isWhite()){
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

    public static synchronized void playSound(final String url) {
        try {
            File f = new File("Sound/"+url);
            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream( f );
            clip.open(ais);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public String getCastlingMoves() {
        AbstractChessPiece whiteKing = board.getPieceAt(board.getKingPos(true));
        AbstractChessPiece blackKing = board.getPieceAt(board.getKingPos(false));

        AbstractChessPiece whiteLeftRook = board.getPieceAt(new Position(0, 0));
        AbstractChessPiece whiteRightRook = board.getPieceAt(new Position(7, 0));
        AbstractChessPiece blackRightRook = board.getPieceAt(new Position(0, 7));
        AbstractChessPiece blackLeftRook = board.getPieceAt(new Position(7, 7));

        String moves = "";

        if(!whiteKing.hasMoved) {
            if(whiteLeftRook instanceof Rook && !whiteLeftRook.hasMoved) {
                moves += "K";
            }
            if(whiteRightRook instanceof Rook && !blackRightRook.hasMoved) {
                moves += "Q";
            }
        }

        if(!blackKing.hasMoved) {
            if(blackLeftRook instanceof Rook && !blackLeftRook.hasMoved) {
                moves += "k";
            }
            if(blackRightRook instanceof Rook && !blackRightRook.hasMoved) {
                moves += "q";
            }
        }

        return moves == "" ? "-" : moves;
    }

    public int fullMoveNumber() {
        return 1 + (board.getMoveHistory().size() / 2);
    }

    public int halfMoveNumber() {
        return 0;
    }

    public String getEnPassantSquare() {
        ArrayList<Move> moveHistory = board.getMoveHistory();
        if(moveHistory.size() == 0)
            return "-";

        Move lastMove = moveHistory.get(moveHistory.size() - 1);
        AbstractChessPiece lastMovedPiece = board.getPieceAt(lastMove.getToPos());
        int xDist = Math.abs(lastMove.getToPos().getX() - lastMove.getFromPos().getX());

        if(lastMovedPiece instanceof Pawn && xDist == 2) {
            if(lastMovedPiece.isWhite()) {
                return lastMove.getToPos().north().toAlgebraic();
            } else {
                return lastMove.getToPos().south().toAlgebraic();
            }
        }

        return "-";
    }

    public String getBoardState() {
        String out = "";
        for (int y = board.size() - 1; y >= 0; y--) {
            int emptyCount = 0;
            for (int x = 0; x < board.size(); x++) {
                Position p = new Position(x, y);
                AbstractChessPiece piece = board.getPieceAt(p);
                if (piece != null) {
                    if(emptyCount > 0) {
                        out += emptyCount;
                        emptyCount = 0;
                    }

                    out += piece;
                } else {
                    emptyCount += 1;
                }
            }
            if(emptyCount > 0) {
                out += emptyCount;
            }
            if(y != 0) {
                out += "/";
            }
        }

        return out;
    }

    public String toFen() {
        String out = "";

        out += getBoardState();
        out += " " + (getTurn() ? "w" : "b");
        out += " " + getCastlingMoves();
        out += " " + getEnPassantSquare();
        out += " " + halfMoveNumber();
        out += " " + fullMoveNumber();



        return out;
    }
}