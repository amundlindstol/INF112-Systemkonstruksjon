package com.grnn.chess.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Helge Mikael Landro, 21.03.2018
 * A class to represent the login menu.
 */

public class LoginState extends State {

    // Variables
    private Texture background, pieces, kingBlack, queenBlack;
    private Texture playBtn;
    private int Xplay, Yplay, Count, CountKing;


    public LoginState(GameStateManager gsm) {

        super(gsm);
    }


    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {

    }
}
