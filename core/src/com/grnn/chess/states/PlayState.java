package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.Board;
import com.grnn.chess.Position;

public class PlayState extends State {
    Board board;
    Texture bg;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("sjakk2.png");
        board = new Board();
        board.addPieces();
    }

    @Override
    public void update(float dt) {}

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(new Texture(board.getPieceAt(new Position(3,0)).getImage()),40,40);
        batch.draw(new Texture(board.getPieceAt(new Position(3,1)).getImage()), 3*(600/9), 2*(600/9));
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        //TODO dispose stuff
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            System.out.println("touched");
            gsm.set(new MenuState(gsm));
        }
    }
}
