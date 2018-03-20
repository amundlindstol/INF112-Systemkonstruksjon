package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * @author Amund 15.03.18
 */
public class MenuState extends State {
    private Texture background, pieces, kingBlack;
    private Texture playBtn;
    private int Xplay, Yplay, Count, CountKing;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("Menu/Menu_background.png");
        pieces = new Texture("Menu/Menu_pieces.png");
        playBtn = new Texture("Menu/menu_button.png");
        kingBlack = new Texture("Menu/KingBlack.png");
       //Xplay = Gdx.graphics.getWidth()/2-playBtn.getWidth()/2;
        // Yplay = Gdx.graphics.getHeight()/2-playBtn.getHeight()/2;
        Xplay = 400;
        Yplay = 380;
        Count = 20;
        CountKing = -200;
    }

    @Override
    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());
        int texturePosX = Xplay;
        int  texturePosY = Yplay;
        if (x > texturePosX && y > texturePosY && x < playBtn.getWidth()+texturePosX && y < playBtn.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(new SelectAIState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
//        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);

        Count++;
        CountKing++;

        // Animate button
        if(Count < 40 ) {
            Xplay++;
            sb.draw(playBtn, Xplay, Yplay);
        }
        else if (Count < 80){
            Xplay--;
        }
        else {
            Count = 0;
        }

        if(CountKing > 450){
            CountKing--;
        }

        // Animate King
        sb.draw(kingBlack, CountKing, Yplay-382);


        sb.draw(pieces, 0, 0);
        sb.draw(playBtn, Xplay, Yplay);

//        sb.draw(play, 50, 50);
//        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}