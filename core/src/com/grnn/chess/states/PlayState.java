package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.AI.AI;
import com.grnn.chess.Board;
import com.grnn.chess.Position;
import com.grnn.chess.TranslateToCellPos;
import com.grnn.chess.objects.AbstractChessPiece;
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
    private TranslateToCellPos translator;
    private Boolean turn;
    private Boolean aiPlayer;
    private AI ai;
    private BitmapFont font;
    private Boolean removed;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("GUI2.png");
        bgBoard = new Texture("sjakk2.png");
        pieceTexures = new ArrayList<Texture>();
        positions = new ArrayList<Position>();
        board = new Board();
        board.addPieces();
        selected = null;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
        translator = new TranslateToCellPos();
        turn = true;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        removed = false;

        potentialTex = new Texture("ChessPieces/Potential.png");
        captureTex = new Texture("ChessPieces/Capture.png");



        for( int y = 40, yi = 0; y<560; y+=65, yi++){
            for(int x=40, xi= 0; x<560; x+=65, xi++){
                AbstractChessPiece piece = board.getPieceAt(new Position(xi,yi));

                Position pos = new Position(xi,yi);
                System.out.println(pos);
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
        if (turn) {
            font.draw(batch, "Venter på at du skal gjøre neste trekk", 635, 295);
            if (removed) {
                font.draw(batch, "Datamaskinen tok en av dine brikker. FAEN I HELVETE :(", 635, 315);
            }
        } else {
            font.draw(batch, "Venter på at Datamaskin skal gjøre neste trekk", 635, 380);
            if (removed) {
                font.draw(batch, "Du tok en brikke! Bra jobbet :)", 635, 315);
            }
        }

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


    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY());
        if (x>0 && x< 601 && y>0 && y<601) {
            //first selected piece
            if (Gdx.input.justTouched() && selected == null) {
                selected = translator.toCellPos(x, y);
                AbstractChessPiece selectedPiece = board.getPieceAt(selected);
                if (selectedPiece != null && selectedPiece.getColor() == turn) {
                    potentialMoves = selectedPiece.getValidMoves(board);
                    captureMoves = selectedPiece.getCaptureMoves(board);
                } else {
                    selected = null;
                }
            }
            //second selected piece
            else if (Gdx.input.justTouched() && selected != null) {
                Position potentialPos = translator.toCellPos(x, y);
                AbstractChessPiece potentialPiece = board.getPieceAt(potentialPos);
                Boolean valid = potentialMoves.contains(potentialPos) || captureMoves.contains(potentialPos);
                if (potentialPiece != null) {
                    if (valid) {
                        board.removePiece(potentialPiece);
                        removed = true;
                        board.movePiece(selected, potentialPos);
                        reset();
                        turn = !turn;
                    } else if (potentialPiece.getColor() == turn) {
                        reset();
                        potentialMoves = potentialPiece.getValidMoves(board);
                        captureMoves = potentialPiece.getCaptureMoves(board);
                        selected = potentialPos;
                    } else {
                        reset();
                    }
                } else if (potentialPiece == null && valid) {
                    board.movePiece(selected, potentialPos);
                    reset();
                    turn = !turn;
                } else {
                    reset();
                }

            }
        }else if(Gdx.input.justTouched()){
            Gdx.app.exit();
        }

    }
     public void reset(){
        selected = null;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
     }
}