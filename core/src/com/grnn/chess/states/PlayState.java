package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.*;
import com.grnn.chess.AI.AI;
import com.grnn.chess.objects.*;

import java.util.ArrayList;

/**
 * @author Amund 15.03.18
 */
public class PlayState extends State {

    // Variables
    Board board;
    Texture bg;
    Texture bgBoard;
    Texture potentialTex;
    Texture captureTex;
    ArrayList<Texture> pieceTexures;
    ArrayList<Position> positions;
    private Position selected;
    private ArrayList<Position> potentialMoves;
    private ArrayList<Position> captureMoves;
    private ArrayList<Position> castlingMoves;
    private TranslateToCellPos translator;
    private Boolean turn;
    private Boolean aiPlayer;
    private AI ai;
    private BitmapFont fontText;
    private BitmapFont fontCounter;
    private Boolean removed;
    private String text;
    private Player humanPlayer;
    private int pawnCounter, bishopCounter, kingCounter, queenCounter, rookCounter, knightCounter;
    private int pawnCounterPlayer, bishopCounterPlayer, kingCounterPlayer, queenCounterPlayer, rookCounterPlayer, knightCounterPlayer;


    public PlayState(GameStateManager gsm, boolean aiPlayer, Player player) {
        super(gsm);
        bg = new Texture("Graphics/GUI/GUI.png");
        bgBoard = new Texture("Graphics/GUI/ChessBoard.png");
        pieceTexures = new ArrayList<Texture>();
        positions = new ArrayList<Position>();
        board = new Board();
        board.addPieces();
        selected = null;
        humanPlayer = player;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
        castlingMoves = new ArrayList<Position>();
        translator = new TranslateToCellPos();
        turn = true;
        fontText = new BitmapFont();
        fontText.setColor(Color.BLACK);
        fontCounter = new BitmapFont();
        fontCounter.setColor(Color.WHITE);
        removed = false;
        this.aiPlayer = aiPlayer;
        pawnCounter = 0;
        bishopCounter = 0;
        kingCounter = 0;
        queenCounter = 0;
        knightCounter = 0;
        rookCounter = 0;
        pawnCounterPlayer = 0;
        bishopCounterPlayer = 0;
        kingCounterPlayer = 0;
        queenCounterPlayer = 0;
        knightCounterPlayer = 0;
        rookCounterPlayer = 0;
        potentialTex = new Texture("Graphics/ChessPieces/Potential.png");
        captureTex = new Texture("Graphics/ChessPieces/Capture.png");

        if(aiPlayer){
            ai = new AI();
        }

        for( int y = 40, yi = 0; y<560; y+=65, yi++){
            for(int x=40, xi= 0; x<560; x+=65, xi++){
                AbstractChessPiece piece = board.getPieceAt(new Position(xi,yi));

                Position pos = new Position(xi, yi);
                positions.add(pos);

            }
        }
    }

    @Override
    public void update(float dt) {handleInput();}

    @Override
    public void render(SpriteBatch batch) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bgBoard, 0, 0);

        if(aiPlayer){
            if (turn) {
                text = "Venter på at du skal gjøre neste trekk.";
                if (removed) {
                    text = "Bra jobbet! Du tok en brikke.";
                }

            } else {
                if (removed) {
                    text = "Uff. Datamaskinen tok en brikke av deg. FAEN I HELVETE!!";
                }
            }
        }

        else{
            if (turn) {
                text = "Venter på at du skal gjøre neste trekk.";
                if (removed) {
                    text = "Uff. Datamaskinen tok en brikke av deg. FAEN I HELVETE!!";
                }

            } else {
                text = "Venter på at datamaskinen skal gjøre neste trekk.";

                if (removed) {
                    text = "Bra jobbet! Du tok en brikke. Det er datamaskinen sin tur.";
                }
            }
        }

        fontText.draw(batch, text, 645, 334);
        fontCounter.draw(batch, "" + pawnCounter, 668, 420);
        fontCounter.draw(batch, "" + bishopCounter, 727, 420);
        fontCounter.draw(batch, "" + knightCounter, 785, 420);
        fontCounter.draw(batch, "" + rookCounter, 843, 420);
        fontCounter.draw(batch, "" + queenCounter, 900, 420);
        fontCounter.draw(batch, "" + kingCounter, 959, 420);

        fontCounter.draw(batch, "" + pawnCounterPlayer, 668, 100);
        fontCounter.draw(batch, "" + bishopCounterPlayer, 727, 100);
        fontCounter.draw(batch, "" + knightCounterPlayer, 785, 100);
        fontCounter.draw(batch, "" + rookCounterPlayer, 843, 100);
        fontCounter.draw(batch, "" + queenCounterPlayer, 900, 100);
        fontCounter.draw(batch, "" + kingCounterPlayer, 959, 100);

        // Username
        fontCounter.draw(batch, "" + humanPlayer.getName() , 700, 250);



        for(int i=0; i<positions.size() ; i++) {
            Position piecePos = positions.get(i);
            AbstractChessPiece piece = board.getPieceAt(piecePos);
            if (piece != null) {
                Texture pieceTex = new Texture(piece.getImage());
                pieceTexures.add(pieceTex);
                batch.draw(pieceTex, translator.toPixels(piecePos.getX(), piecePos.getY())[0], translator.toPixels(piecePos.getX(), piecePos.getY())[1]);
            }
        }

        if (!potentialMoves.isEmpty()) {
            for (Position potPos : potentialMoves) {
                int[] pos = translator.toPixels(potPos.getX(), potPos.getY());
                batch.draw(potentialTex, pos[0], pos[1]);
            }
        }
        if (!captureMoves.isEmpty()) {
            for (Position capPos : captureMoves) {
                int[] pos = translator.toPixels(capPos.getX(), capPos.getY());
                batch.draw(captureTex, pos[0], pos[1]);
            }
        }
        batch.end();
        if (!pieceTexures.isEmpty()) {
            for (Texture oldTexture : pieceTexures) {
                if (oldTexture.isManaged()) {
                    oldTexture.dispose();
                }
            }
        }
    }

    /**
     * Method to update the GUI's counter for removed pieces
     * @param removedPiece, the piece that is removed
     * @param player, boolean to tell which player
     */
    public void updatePieceCounter(AbstractChessPiece removedPiece, Boolean player){

        if(player){
            if(removedPiece instanceof Pawn){
                if(player)
                    pawnCounterPlayer ++;
                else
                    pawnCounter ++;
            }
            else if(removedPiece instanceof Bishop){
                if(player)
                    bishopCounterPlayer ++;
                else
                    bishopCounter ++;
            }
            else if(removedPiece instanceof King){
                if(player)
                    kingCounterPlayer ++;
                else
                    kingCounter ++;
            }
            else if(removedPiece instanceof Queen){
                if(player)
                    queenCounterPlayer ++;
                else
                    queenCounter ++;
            }
            else if(removedPiece instanceof Rook){
                if(player)
                    rookCounterPlayer ++;
                else{
                    rookCounter ++;
                }
            }
            else if(removedPiece instanceof Knight){
                if(player)
                    knightCounterPlayer ++;
                else
                    knightCounter ++;
            }
        }
    }

    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY());
        if (x>40 && x< 560 && y>40 && y<560) {
            //AI
            if(aiPlayer && !turn){
                Move aiMove = ai.calculateBestMove(board);
                AbstractChessPiece victim = board.getPieceAt(aiMove.getToPos());
                if(victim !=null) {
                    board.removePiece(victim);
                }
                board.movePiece(aiMove.getFromPos(),aiMove.getToPos());
                turn = !turn;
            }

            //first selected piece
            if (Gdx.input.justTouched() && selected == null) {
                selected = translator.toCellPos(x, y);
                AbstractChessPiece selectedPiece = board.getPieceAt(selected);
                if (selectedPiece != null && selectedPiece.isWhite() == turn) {
                    potentialMoves = selectedPiece.getValidMoves(board);
                    captureMoves = selectedPiece.getCaptureMoves(board);
                } else {
                    selected = null;
                }
            }
            //second selected piece
            if (Gdx.input.justTouched() && selected != null) {
                Position potentialPos = translator.toCellPos(x, y);
                AbstractChessPiece potentialPiece = board.getPieceAt(potentialPos);
                Boolean validMove = potentialMoves.contains(potentialPos) || captureMoves.contains(potentialPos) || castlingMoves.contains(potentialPos);
                if (potentialPiece != null) {
                    if (validMove) {
                        board.removePiece(potentialPiece);
                        removed = true;
                        updatePieceCounter(potentialPiece, turn);
                        board.movePiece(selected, potentialPos);
                        reset();
                        turn = !turn;
                    } else if (potentialPiece.isWhite() == turn) {
                        reset();
                        potentialMoves = potentialPiece.getValidMoves(board);
                        captureMoves = potentialPiece.getCaptureMoves(board);
                        selected = potentialPos;
                        removed = false;
                    } else {
                        removed = false;
                        reset();
                    }
                } else if (potentialPiece == null && validMove) {
                    handleCastling(potentialPos);
                    board.movePiece(selected, potentialPos);
                    reset();
                    turn = !turn;
                    removed = false;
                } else {
                    reset();
                    removed = false;
                }
            }
        }
        else if (Gdx.input.justTouched()) {
            Gdx.app.exit();
        }
    }

    /**
     * Moves the rook if the king does castling.
     *
     * @param potentialPos The position the king is trying to move to.
     */

    private void handleCastling(Position potentialPos) {
        if (!castlingMoves.contains(potentialPos))
            return;

        AbstractChessPiece potentialPiece = board.getPieceAt(selected);
        Position rookOriginalPos = null;
        Position rookNewPos = null;

        if (potentialPiece.isWhite()) {
            if (potentialPos.equals(new Position(2, 0))) {
                rookOriginalPos = new Position(0, 0);
                rookNewPos = new Position(3, 0);
            } else if (potentialPos.equals(new Position(6, 0))) {
                rookOriginalPos = new Position(7, 0);
                rookNewPos = new Position(5, 0);
            }
        } else {
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

    @Override
    public void dispose() {
        bg.dispose();
        bgBoard.dispose();
        for(Texture tex : pieceTexures){
            tex.dispose();
        }
        potentialTex.dispose();
        captureTex.dispose();
        System.out.println("PlayState Disposed");
    }


    public void reset() {
        selected = null;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
    }
}