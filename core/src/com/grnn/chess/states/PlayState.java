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
    Board board;
    Texture bg;
    Texture bgBoard;
    private Position selected;
    private ArrayList<Position> potentialMoves;
    private ArrayList<Position> captureMoves;
    private TranslateToCellPos translator;
    private Boolean turn;
    private Boolean aiPlayer;
    private AI ai;
    private BitmapFont font;
    private Boolean playerTurn;



    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("GUI2.png");
        bgBoard = new Texture("sjakk2.png");
        board = new Board();
        board.addPieces();
        selected = null;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
        translator = new TranslateToCellPos();
        turn = true;
        playerTurn = true;
        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    @Override
    public void update(float dt) {handleInput();}

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bgBoard, 0, 0);

        if(turn){
            font.draw(batch, "Venter på at Lord Riple skal gjøre neste trekk", 630, 380);
        }
        else{
            font.draw(batch, "Venter på at Datamaskin skal gjøre neste trekk", 630, 380);
        }
        
        for(int y = 40, yi=0; y<560 ; y+=65, yi++ ){
            for(int x=40, xi=0; x<560; x+=65, xi++){
                AbstractChessPiece piece = board.getPieceAt(new Position(xi,yi));
                if(piece != null){
                    String img = piece.getImage();
                    batch.draw(new Texture(img), x, y);
                }
            }
        }
        if(!potentialMoves.isEmpty()) {
            for (Position potPos : potentialMoves) {
                int[] pos = translator.toPixels(potPos.getX(), potPos.getY());
                batch.draw(new Texture("ChessPieces/Potential.png"), pos[0], pos[1]);
            }
        }
        if(!captureMoves.isEmpty()) {
            for(Position capPos : captureMoves) {
                int[] pos = translator.toPixels(capPos.getX(), capPos.getY());
                batch.draw(new Texture("ChessPieces/Capture.png"), pos[0], pos[1]);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bgBoard.dispose();

        System.out.println("PlayState Disposed");
    }


    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY());

        //first selected piece
        if(Gdx.input.justTouched() && selected==null){
            selected = translator.toCellPos(x,y);
            AbstractChessPiece selectedPiece = board.getPieceAt(selected);
            if(selectedPiece != null && selectedPiece.getColor() == turn){
                potentialMoves = selectedPiece.getValidMoves(board);
                captureMoves = selectedPiece.getCaptureMoves(board);
            }else {
                selected = null;
            }
        }
        //second selected piece
        else if(Gdx.input.justTouched() && selected != null) {
            Position potentialPos = translator.toCellPos(x, y);
            AbstractChessPiece potentialPiece = board.getPieceAt(potentialPos);
            Boolean valid = potentialMoves.contains(potentialPos) || captureMoves.contains(potentialPos);
            if (potentialPiece != null){
                if (valid) {
                    board.removePiece(potentialPiece);
                    board.movePiece(selected, potentialPos);
                    reset();
                    turn = !turn;
                }
                else if (potentialPiece.getColor()==turn){
                    reset();
                    potentialMoves = potentialPiece.getValidMoves(board);
                    captureMoves = potentialPiece.getCaptureMoves(board);
                    selected = potentialPos;
                }else{
                    reset();
                }
            }else if(potentialPiece == null && valid) {
                board.movePiece(selected, potentialPos);
                reset();
                turn = !turn;
            }else {
                reset();
            }
        }

    }
     public void reset(){
        selected = null;
        potentialMoves = new ArrayList<Position>();
        captureMoves = new ArrayList<Position>();
     }
}