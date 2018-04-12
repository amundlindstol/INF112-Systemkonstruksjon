package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.*;
import com.grnn.chess.objects.*;
//import javafx.geometry.Pos;

import java.util.ArrayList;

/**
 * @author Amund 15.03.18
 */
public class PlayState extends State {

    // Variables
    Game game;
    Board board;

    Texture bg;
    Texture bgBoard;
    Texture potentialTex;
    Texture captureTex;
    ArrayList<Texture> pieceTexures;
    ArrayList<Position> positions;
    Position prevMove;


    private ArrayList<Position> potentialMoves;
    private ArrayList<Position> captureMoves;
    private ArrayList<Position> castlingMoves;
    private TranslateToCellPos translator;

    private ArrayList<Position> animationPath;
    private int animationIndex;
    private boolean pieceIsMoving;
    private Move prevAImove;

    private Boolean activegame;
    private BitmapFont fontText;
    private BitmapFont fontCounter;

    private String text;
    private String player1Name;
    private String player2Name;

    private int[]removedPieces;


    public PlayState(GameStateManager gsm, int aiPlayer, Player player1, Player player2) {
        super(gsm);
        //textures
        bg = new Texture("Graphics/GUI/GUI.png");
        bgBoard = new Texture("Graphics/GUI/ChessBoard.png");
        pieceTexures = new ArrayList<Texture>();
        positions = new ArrayList<Position>();

        //game
        game = new Game(aiPlayer,player1,player2);
        board = game.getBoard();
        potentialMoves = game.getValidMoves();
        captureMoves = game.getCaptureMoves();
        text = game.getText();
        removedPieces = game.getRemovedPieces();
        player1Name = player1.name;
        if(player2!=null) {
            player2Name = player2.name;
        }else{
            player2Name = "Datamaskin";
        }

        translator = new TranslateToCellPos();
        pieceIsMoving = false;
        animationIndex = 0;
        animationPath = new ArrayList<Position>();

        fontText = new BitmapFont();
        fontText.setColor(Color.BLACK);
        fontCounter = new BitmapFont();
        fontCounter.setColor(Color.WHITE);

        potentialTex = new Texture("Graphics/ChessPieces/Potential.png");
        captureTex = new Texture("Graphics/ChessPieces/Capture.png");
        activegame = true;


        for( int y = 40, yi = 0; y<560; y+=65, yi++){
            for(int x=40, xi= 0; x<560; x+=65, xi++){
                AbstractChessPiece piece = board.getPieceAt(new Position(xi,yi));

                Position pos = new Position(xi, yi);
                positions.add(pos);

            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        board = game.getBoard();
        potentialMoves = game.getValidMoves();
        captureMoves = game.getCaptureMoves();
        text = game.getText();
        removedPieces = game.getRemovedPieces();
    }

    @Override
    public void render(SpriteBatch batch) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bgBoard, 0, 0);

        if(removedPieces[2]==1){
            text = "Du vant "+ player1Name +", gratulerer!";
            activegame = false;
        }
        else if(removedPieces[8]==1){
            text = "Du vant " + player1Name + ", du må nok øve mer..."; //TODO wrong output
            activegame = false;
        }

        fontText.draw(batch, text, 645, 334);
        fontCounter.draw(batch, "" + removedPieces[0], 668, 418);
        fontCounter.draw(batch, "" + removedPieces[1], 739, 418);
        fontCounter.draw(batch, "" + removedPieces[2], 810, 418);
        fontCounter.draw(batch, "" + removedPieces[3], 883, 418);
        fontCounter.draw(batch, "" + removedPieces[4], 960, 418);
        fontCounter.draw(batch, "" + removedPieces[5], 1037, 418);

        fontCounter.draw(batch, "" + removedPieces[6], 668, 105);
        fontCounter.draw(batch, "" + removedPieces[7], 739, 105);
        fontCounter.draw(batch, "" + removedPieces[8], 810, 105);
        fontCounter.draw(batch, "" + removedPieces[9], 882, 105);
        fontCounter.draw(batch, "" + removedPieces[10], 959, 105);
        fontCounter.draw(batch, "" + removedPieces[11], 1037, 105);

        // Player names
        fontCounter.draw(batch, "" + player1Name , 726, 241);
        fontCounter.draw(batch, "" + player2Name , 723, 555);

        //iterate through cells
        for(int i=0; i<positions.size() ; i++) {
            Position piecePos = positions.get(i);
            int[] pos = translator.toPixels(piecePos.getX(), piecePos.getY());
            AbstractChessPiece piece = board.getPieceAt(piecePos);

            if (piece != null && piece.isMoving()) { //should this piece change its location?
                if (game.isAi() && game.getTurn()) { //ai piece
                    if (prevAImove == null) prevAImove = game.getAiMove();
                    pos = animatePiece(piece, piecePos, pos, true);
                } else
                    pos = animatePiece(piece, piecePos, pos, false);
            }

            if (piece != null) {
                Texture pieceTex = new Texture(piece.getImage());
                pieceTexures.add(pieceTex);
                batch.draw(pieceTex, pos[0], pos[1]);
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


    private int[] animatePiece(AbstractChessPiece piece, Position piecePos, int[] pos, boolean ai) {
        if (pieceIsMoving && piece.isMoving()) {
            if (animationIndex == animationPath.size() && animationPath.size() > 0) { //reached end of list
                pieceIsMoving = false;
                piece.stopMoving();
                //THIS IS WHERE THE ACTUAL MOVING HAPPENS
                board.movePiece(piecePos, translator.toCellPos(animationPath.get(animationIndex-1).getX(), translator.translateY(animationPath.get(animationIndex-1).getY())));
                pos[0] = animationPath.get(animationIndex-1).getX();
                pos[1] = animationPath.get(animationIndex-1).getY();
                animationPath.clear();
                return pos;
            }
            pos[0] = animationPath.get(animationIndex).getX();
            pos[1] = animationPath.get(animationIndex).getY();
            if (animationPath.size() > animationIndex+10) animationIndex+=10; //faster animation
            else animationIndex++;
        } else if (!pieceIsMoving && piece.isMoving()) {
            if (ai)
                generateAnimationPath(prevAImove.getFromPos(), prevAImove.getToPos());
            else
                generateAnimationPath(piecePos, prevMove);
            pieceIsMoving = true;
            animationIndex = 0;
            game.playSound("movePiece.wav");
        }
        return pos;
    }

    private void generateAnimationPath(Position startPos, Position endPos) {
         TranslateToCellPos translator = new TranslateToCellPos();
        int[] startPixelPos = translator.toPixels(startPos.getX(), startPos.getY());
        int[] endPixelPos = translator.toPixels(endPos.getX(), endPos.getY());

        while (startPixelPos[0] != endPixelPos[0] || startPixelPos[1] != endPixelPos[1]) {
            if (shorterDistTo(true, startPixelPos,1, endPixelPos) == 1) {
                startPixelPos[0]++;
            } else if (shorterDistTo(true, startPixelPos,1, endPixelPos) != 0) {
                startPixelPos[0]--;
            }
            if (shorterDistTo(false, startPixelPos,1, endPixelPos) == 1) {
                startPixelPos[1]++;
            } else if (shorterDistTo(false, startPixelPos,1, endPixelPos) != 0) {
                startPixelPos[1]--;
            }
            animationPath.add(new Position(startPixelPos[0], startPixelPos[1]));
        }
    }

    //is it shorter distance? 1=yes -1=no 0=same
    private int shorterDistTo(boolean changeInXaxis, int[] startPixelPo, int variance, int[] endPixelPo) {
        double startVal = Math.sqrt((Math.pow(Math.abs(startPixelPo[0]-endPixelPo[0]), 2))+(Math.pow(startPixelPo[1]-endPixelPo[1], 2)));
        double nextVal;
        if (changeInXaxis) {
            if (startPixelPo[0] == endPixelPo[0]) {return 0;}
            nextVal = Math.sqrt((Math.pow(Math.abs(startPixelPo[0] + variance - endPixelPo[0]), 2)) + (Math.pow(startPixelPo[1] - endPixelPo[1], 2)));
        } else {
            if (startPixelPo[1] == endPixelPo[1]) {return 0;}
            nextVal = Math.sqrt((Math.pow(Math.abs(startPixelPo[0] - endPixelPo[0]), 2)) + (Math.pow(startPixelPo[1] + variance - endPixelPo[1], 2)));
        }
        if (nextVal < startVal)
            return 1;
        else if (nextVal > startVal)
            return -1;
        return 0;
    }


    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY());
        Boolean notSelected = game.pieceHasNotBeenSelected();

        if (x>40 && x< 560 && y>40 && y<560 && activegame && !pieceIsMoving) {

            //AI
            if (!game.getTurn() && game.isAi()) {
                prevAImove = game.aiMove();
                return;
            }

            //first selected piece
            Position selected = null;
            if (Gdx.input.justTouched() && notSelected) {
                game.playSound("selectPiece.wav");
                selected = translator.toCellPos(x, y);
                game.selectFirstPiece(selected);
            }
            //second selected piece
            else if (Gdx.input.justTouched() && !game.pieceHasNotBeenSelected()) {
                Position potentialPos = translator.toCellPos(x, y);
                game.moveFirstSelectedPieceTo(potentialPos);
                prevMove = potentialPos;
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
}