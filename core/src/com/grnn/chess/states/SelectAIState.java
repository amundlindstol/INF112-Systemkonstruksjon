package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A class to represent the menu for select AI level for the game
 * @author Helge Mikael Landro, 19.03.2018
 */
public class SelectAIState extends State {

    private Texture background;
    private Texture playBtn, playBtn2, playBtn3;
    private int xPlay, yPlay;

    public SelectAIState(GameStateManager gsm){
        super(gsm);
        background = new Texture("Menu/Menu.png");
        playBtn = new Texture("Menu/menu_button_AI_easy.png");
        playBtn2 = new Texture("Menu/menu_button_AI_medium.png");
        playBtn3 = new Texture("Menu/menu_button_AI_hard.png");
        xPlay = 450;
        yPlay = 440;

    }

    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());
        int texturePosX = xPlay;
        int  texturePosY = yPlay;
        if (x > texturePosX && y > texturePosY && x < playBtn.getWidth()+texturePosX && y < playBtn.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(playBtn, xPlay, yPlay);
        sb.draw(playBtn2, xPlay, yPlay - 60);
        sb.draw(playBtn3, xPlay, yPlay - 120);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        playBtn2.dispose();
        playBtn3.dispose();
        System.out.println("SelectAI State Disposed");

    }
}
