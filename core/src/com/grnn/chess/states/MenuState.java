package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Brent on 6/26/2015.
 */
public class MenuState extends State{
    private Texture background;
    private Texture playBtn;
//    private Button playBtn, playVsAI, settings;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("badlogic.jpg");
        playBtn = new Texture("badlogic.jpg");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
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
        sb.draw(playBtn, 0, 0);
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