package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.Board;
import com.grnn.chess.Position;
import com.grnn.chess.TranslateToCellPos;
import com.grnn.chess.objects.AbstractChessPiece;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import javafx.geometry.Pos;

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
    private TranslateToCellPos translator;
    private Boolean turn;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("GUI2.png");
        bgBoard = new Texture("sjakk2.png");
        board = new Board();
        board.addPieces();
        selected = null;
        potentialMoves = new ArrayList<Position>();
        translator = new TranslateToCellPos();
        turn = true;
    }

    @Override
    public void update(float dt) {handleInput();}

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(bgBoard, 0, 0);
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
       // batch.draw(new Texture(board.getPieceAt(new Position(3,0)).getImage()),40,40);
        //batch.draw(new Texture(board.getPieceAt(new Position(3,1)).getImage()), 3*(600/9), 2*(600/9));
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

        if(Gdx.input.justTouched() && selected==null){
            selected = translator.toCellPos(x,y);
            AbstractChessPiece selectedPiece = board.getPieceAt(selected);
            if(selectedPiece == null){
                System.out.println("selected is null");
                selected = null;
            }else {
                if (selectedPiece.getColor() == turn){
                    potentialMoves = selectedPiece.getValidMoves(board);
                } else {
                    selected = null;
                }
            }
        }
        else if(Gdx.input.justTouched() && selected != null){
            Position potentialPos = translator.toCellPos(x,y);
            System.out.println("selected is not null and potential" + potentialPos);
            if(potentialMoves.contains(potentialPos)) {
                System.out.println("moving");
                board.movePiece(selected, potentialPos);
                selected = null;
                potentialMoves = new ArrayList<Position>();
                turn = !turn;
            }

        }

    }
}