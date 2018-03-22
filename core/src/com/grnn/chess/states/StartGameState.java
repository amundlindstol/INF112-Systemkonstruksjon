package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.grnn.chess.Player;

/**
 * @author Amund 15.03.18
 */
public class StartGameState extends State {
    private Texture background, pieces, kingBlack;
    private Texture playBtn;
    private int Xplay, Yplay, Count;
    private Player currentPlayer;

    public StartGameState(GameStateManager gsm) { //TODO this constructor should be deleted when never used
        super(gsm);
        new StartGameState(gsm, new Player("delete", "me"));
    }

    /**
     * main menu when logged in
     * @param gsm
     * @param player player currently logged in
     */
    public StartGameState(GameStateManager gsm, Player player) {
        super(gsm);
        this.currentPlayer = player;
        background = new Texture("Graphics/Menu/Menu_background.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
        playBtn = new Texture("Graphics/Menu/menu_button.png");
        //Xplay = Gdx.graphics.getWidth()/2-playBtn.getWidth()/2;
        // Yplay = Gdx.graphics.getHeight()/2-playBtn.getHeight()/2;
        Xplay = 400;
        Yplay = 340;
        Count = 20;
        currentPlayer = player;
    }

    @Override
    public void handleInput() {
        int x = Math.abs(Gdx.input.getX());
        int y = Math.abs(Gdx.input.getY()-Gdx.graphics.getHeight());
        int texturePosX = Xplay;
        int  texturePosY = Yplay;
        if (x > texturePosX && y > texturePosY && x < playBtn.getWidth()+texturePosX && y < playBtn.getHeight()+texturePosY && Gdx.input.justTouched()) {
            gsm.set(new SelectPlayerState(gsm, currentPlayer));
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

        sb.draw(pieces, 0, 0);
        sb.draw(playBtn, Xplay, Yplay);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }
}