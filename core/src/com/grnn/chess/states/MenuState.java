package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * @author Amund 15.03.18
 */
public class MenuState extends State {
    private Texture background;
    private Texture playBtn;
    private int Xplay, Yplay;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("Menu/Menu.png");
        playBtn = new Texture("Menu/menu_button.png");
       //Xplay = Gdx.graphics.getWidth()/2-playBtn.getWidth()/2;
        // Yplay = Gdx.graphics.getHeight()/2-playBtn.getHeight()/2;
        Xplay = 350;
        Yplay = 330;
    }

    @Override
    public void handleInput() {
        justTryingToHandleSomeStuff(playBtn, new PlayState(gsm), Xplay, Yplay);
    }

    private void justTryingToHandleSomeStuff(Texture texture, State state, int texturePosX, int texturePosY) {
        //translate mouse (0,0) to lower left
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());

        if (x > texturePosX && y > texturePosY && x < texture.getWidth()+texturePosX && y < texture.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(state);
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