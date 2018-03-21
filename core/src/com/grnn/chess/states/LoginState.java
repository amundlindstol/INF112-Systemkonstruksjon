package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Helge Mikael Landro, 21.03.2018
 * A class to represent the login menu.
 */

public class LoginState extends State {

    // Variables
    private Texture background, pieces;
    private Texture loginBtn, createBtn;
    private int Xplay, Yplay;


    public LoginState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("Menu/Menu_background.png");
        pieces = new Texture("Menu/Menu_pieces.png");
        loginBtn = new Texture("Menu/menu_button_login.png");
        Xplay = 400;
        Yplay = 340;
    }


    @Override
    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());
        int texturePosX = Xplay;
        int  texturePosY = Yplay;
        if (x > texturePosX && y > texturePosY && x < loginBtn.getWidth()+texturePosX && y < loginBtn.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(new MenuState(gsm));
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
        sb.draw(pieces, 0, 0);
        sb.draw(loginBtn, Xplay, Yplay);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
