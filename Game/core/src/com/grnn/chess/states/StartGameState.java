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
    private TextButton playBtn, statsBtn, crazyHouseBtn, playOnlineBtn;
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
        crazyHouseBtn = new TextButton("Crazyhouse", skin);
        crazyHouseBtn.setSize(crazyHouseBtn.getWidth(), crazyHouseBtn.getHeight()-30);
        crazyHouseBtn.setPosition(Gdx.graphics.getWidth()/2-crazyHouseBtn.getWidth()/2, Gdx.graphics.getHeight()-248);
        playBtn = new TextButton("Spill", skin);
        playBtn.setSize(playBtn.getWidth(), playBtn.getHeight()-30);
        statsBtn = new TextButton("Statistikk", skin);
        xPosStatsBtn = (int) (Gdx.graphics.getWidth()/2 - statsBtn.getWidth()/2 - 10);
        yPosStatsBtn = 245;
        statsBtn.setPosition(xPosStatsBtn + 13, yPosStatsBtn + 41);
        statsBtn.setSize(statsBtn.getWidth(), statsBtn.getHeight()-30);

        xPosPlayBtn = (int) (Gdx.graphics.getWidth()/2 - playBtn.getWidth()/2 - 10);
        yPosPlayBtn = 340;
        Count = 20;
        currentPlayer = player;

        playOnlineBtn = new TextButton("online", skin);
        playOnlineBtn.setSize(playOnlineBtn.getWidth(), playOnlineBtn.getHeight()-30);
        playOnlineBtn.setPosition(Gdx.graphics.getWidth()/2-crazyHouseBtn.getWidth()/2 + 78, Gdx.graphics.getHeight()-382);

        stage.addActor(statsBtn);
        stage.addActor(crazyHouseBtn);
        stage.addActor(playBtn);
        stage.addActor(playOnlineBtn);

    }

    @Override
    public void handleInput() {
        if (playBtn.isPressed()) {
            gsm.set(new SelectPlayerState(gsm, currentPlayer, playerData));
        } else if(statsBtn.isPressed()) {
            gsm.set(new ShowStatsState(gsm, currentPlayer, playerData));
        } else if (crazyHouseBtn.isPressed()) {
            gsm.set(new PlayState(gsm, 0, currentPlayer, new Player("CrazyPlayer", "", false), playerData, "crazyhouse"));
        } else if (playOnlineBtn.isPressed()) {
            gsm.set(new SelectPlayerOnlineState(gsm, currentPlayer, playerData));

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
            playBtn.setPosition(++xPosPlayBtn, yPosPlayBtn + 80);
        }
        else if (Count <= 80){
            playBtn.setPosition(--xPosPlayBtn, yPosPlayBtn + 80);
        }
        else {
            Count = 0;
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
    }
}