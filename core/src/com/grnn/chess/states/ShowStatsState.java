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
        topTenPlayers = playerData.getTopTenPlayers();
        this.currentPlayer = currentPlayer;
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        background = new Texture("Graphics/Menu/Menu_background.png");
        xPos = 700;
        yPos = 130;
        menuButton = new TextButton("Tilbake", skin);
        menuButton.setPosition((float) (xPos - menuButton.getWidth()*1.08), yPos - menuButton.getHeight() - 25);
        menuButton.setSize(280,60);

        fontText = new BitmapFont();
        fontText.setColor(Color.WHITE);
        stage.addActor(menuButton);
    }

    @Override
    protected void handleInput() {
        if (menuButton.isPressed()) {
            gsm.set(new StartGameState(gsm, currentPlayer, playerData));
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

        for(int i=0, j=430; i<10; i++, j-=30) {
            fontText.draw(sb, topTenPlayers.get(i).getName(), 390, j);
            fontText.draw(sb,""+topTenPlayers.get(i).rating,600,j);
            fontText.draw(sb,""+topTenPlayers.get(i).getNoOfWins(),700,j);
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