package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Helge Mikael Landro, 21.03.2018
 * A class to represent the main menu.
 * This is the initial state
 */
public class MainMenuState extends State {

    // Variables
    private Texture background, pieces;
    private Texture loginBtn, registerBtn, kingBlack;
    private int Xplay, Yplay, Xreg, Yreg, CountKing;

    /**
     * Constructor for the Login State
     * @param gsm, the GameStateManager
     */
    public MainMenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("Graphics/Menu/Menu_background.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
        loginBtn = new Texture("Graphics/Menu/menu_button_login.png");
        registerBtn = new Texture("Graphics/Menu/menu_button_reg.png");
        kingBlack = new Texture("Graphics/Menu/KingBlack.png");
        Xplay = 400;
        Yplay = 340;
        CountKing = -200;
        Xreg = Xplay;
        Yreg = Yplay-loginBtn.getHeight();
    }

    @Override
    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());
        int texturePosX = Xplay;
        int texturePosY = Yplay;
        if (x > texturePosX && y > texturePosY && x < loginBtn.getWidth()+texturePosX && y < loginBtn.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(new LoginUserState(gsm)); //change to MenuState if u want to skip login
        }
        texturePosX = Xreg;
        texturePosY = Yreg;
        if (x > texturePosX && y > texturePosY && x < registerBtn.getWidth()+texturePosX && y < registerBtn.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(new RegisterUserState(gsm));
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
        sb.draw(pieces, 0, 0);
        sb.draw(loginBtn, Xplay, Yplay);
        sb.draw(registerBtn, Xreg, Yreg);

        if(CountKing > 450){
            CountKing --;
        }

        sb.draw(kingBlack, CountKing, Yplay-346);
        sb.draw(pieces, 0, 0);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        pieces.dispose();
        loginBtn.dispose();
        registerBtn.dispose();
    }
}