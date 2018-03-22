package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Helge Mikael Landro, 21.03.2018
 * A class to represent the login menu. This is the first menu the player will see in the game.
 */
public class LoginState extends State {

    // Variables
    private Texture background, pieces;
    private Texture loginBtn, kingBlack;
    private int Xplay, Yplay, CountKing;

    /**
     * Constructor for the Login State
     * @param gsm, the GameStateManager
     */
    public LoginState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("Graphics/Menu/Menu_background.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
        loginBtn = new Texture("Graphics/Menu/menu_button_login.png");
        kingBlack = new Texture("Graphics/Menu/KingBlack.png");
        Xplay = 400;
        Yplay = 340;
        CountKing = -200;
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
        CountKing ++;
        sb.draw(background, 0,0);
        sb.draw(pieces, 70, 0);

        if(CountKing > 450){
            CountKing --;
        }

        sb.draw(kingBlack, CountKing, Yplay-346);
        sb.draw(loginBtn, Xplay, Yplay);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        pieces.dispose();
        loginBtn.dispose();
    }
}