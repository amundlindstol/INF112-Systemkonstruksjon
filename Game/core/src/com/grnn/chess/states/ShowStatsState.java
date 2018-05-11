package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.PlayerData;

import java.util.ArrayList;


public class ShowStatsState extends State {

    private Skin skin;
    private Stage stage;
    private Texture background;
    private Texture scoreboard;
    private Player currentPlayer;
    private TextButton menuButton;
    private int xPos;
    private int yPos;
    private PlayerData playerData;
    private ArrayList<Player> topTenPlayers;
    private BitmapFont fontText;

    public ShowStatsState (GameStateManager gsm, Player currentPlayer, PlayerData playerData) {
        super(gsm);
        this.playerData = playerData;
        if(!playerData.isOffline()) {
            topTenPlayers = playerData.getTopTenPlayers();
        }
        this.currentPlayer = currentPlayer;
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        background = new Texture("Graphics/Menu/Menu_background.png");
        xPos = 712;
        yPos = 130;
        menuButton = new TextButton("Tilbake", skin);
        menuButton.setPosition((float) (xPos - menuButton.getWidth()*1.08), yPos - menuButton.getHeight() - 15);
        menuButton.setSize(280,60);
        scoreboard = new Texture("Graphics/Menu/scoreboard.png");

        fontText = new BitmapFont();
        fontText.setColor(Color.WHITE);
        stage.addActor(menuButton);
    }

    @Override
    protected void handleInput() {
        if (menuButton.isPressed()) {
            if(playerData.isOffline()){
                gsm.set(new SelectPlayerState(gsm,new Player("Spiller1","", true),playerData));
            }else {
                gsm.set(new StartGameState(gsm, currentPlayer, playerData));
            }
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
        sb.draw(scoreboard,-20 , 0);

        if(!playerData.isOffline()) {

            fontText.draw(sb, "Navn", 365, 420);
            fontText.draw(sb, "Score", 600, 420);
            fontText.draw(sb, "Vunnet", 700, 420);

            for (int i = 0, j = 390; i < 10; i++, j -= 30) {
                fontText.draw(sb, topTenPlayers.get(i).getName(), 365, j);
                fontText.draw(sb, "" + topTenPlayers.get(i).rating, 600, j);
                fontText.draw(sb, "" + topTenPlayers.get(i).getNoOfWins(), 700, j);
            }
        }
        else{
           fontText.draw(sb, "You are not connected to the database", Gdx.graphics.getWidth()/2-110,390);
        }

        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        fontText.dispose();
        skin.dispose();
        System.out.println("ShowStats State Disposed");
    }
}