package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.Board;
import com.grnn.chess.Position;
import com.grnn.chess.TranslateToCellPos;
import com.grnn.chess.objects.AbstractChessPiece;

/**
 * @author Amund 15.03.18
 */
public class PlayState extends State {
    Board board;
    Texture bg;
    Texture bgBoard;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("GUI2.png");
        bgBoard = new Texture("sjakk2.png");
        board = new Board();
        board.addPieces();
    }

    @Override
    public void update(float dt) {}

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

    @Override
    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());
        System.out.println(x+", "+y);
        int texturePosX = 600;
        int  texturePosY = 600;
        if (x > texturePosX && y > texturePosY && x < bgBoard.getWidth()+texturePosX && y < bgBoard.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
        }
    }
}