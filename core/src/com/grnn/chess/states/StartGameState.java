package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.PlayerData;

/**
 * @author Amund 15.03.18
 */
public class StartGameState extends State {
    private Texture background, pieces;
    private TextButton playBtn, statsBtn;
    private int xPosPlayBtn, yPosPlayBtn, Count;
    private int xPosStatsBtn, yPosStatsBtn;
    private Player currentPlayer;

    private Skin skin;
    private Stage stage;

    private PlayerData playerData;

    /**
     * main menu when logged in
     * @param gsm
     * @param player player currently logged in
     */
    public StartGameState(GameStateManager gsm, Player player, PlayerData playerData) {
        super(gsm);
        // init stage and listener
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));

        this.playerData = playerData;
        this.currentPlayer = player;
        background = new Texture("Graphics/Menu/Menu_background.png");
        pieces = new Texture("Graphics/Menu/Menu_pieces.png");
        playBtn = new TextButton("Spill", skin);
        stage.addActor(playBtn);

        statsBtn = new TextButton("Statistikk", skin);

        xPosStatsBtn = (int) (Gdx.graphics.getWidth()/2 - statsBtn.getWidth()/2 - 10);
        yPosStatsBtn = 245;
        statsBtn.setPosition(xPosStatsBtn, yPosStatsBtn);

        stage.addActor(statsBtn);

        xPosPlayBtn = (int) (Gdx.graphics.getWidth()/2 - playBtn.getWidth()/2 - 10);
        yPosPlayBtn = 340;
        Count = 20;
        currentPlayer = player;
    }

    @Override
    public void handleInput() {
        if (playBtn.isPressed()) {
            gsm.set(new SelectPlayerState(gsm, currentPlayer, playerData));
        } else if(statsBtn.isPressed()) {
            gsm.set(new ShowStatsState(gsm, currentPlayer, playerData));
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

        //animate button
        animate();

        sb.draw(pieces, 0, 0);
        sb.end();
        stage.draw();
    }

    private void animate() {
        Count++;
        if (Count <= 40 ) {
            playBtn.setPosition(++xPosPlayBtn, yPosPlayBtn);
        }
        else if (Count <= 80){
            playBtn.setPosition(--xPosPlayBtn, yPosPlayBtn);
        }
        else {
            Count = 0;
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        System.out.println("Menu State Disposed");
    }
}