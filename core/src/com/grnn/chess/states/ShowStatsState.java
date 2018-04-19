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


public class ShowStatsState extends State {

    private Skin skin;
    private Stage stage;
    private Texture background, pieces;
    private Player currentPlayer;
    private TextButton menuButton;
    private int xPos;
    private int yPos;
    private PlayerData playerData;

    public ShowStatsState (GameStateManager gsm, Player currentPlayer, PlayerData playerData) {
        super(gsm);
        this.playerData = playerData;
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        this.currentPlayer = currentPlayer;
        background = new Texture("Graphics/Menu/Menu_background.png");
        xPos = 690;
        yPos = 200;
        menuButton = new TextButton("Tilbake", skin);
        menuButton.setPosition((float) (xPos - menuButton.getWidth()*1.08), yPos - menuButton.getHeight() - 25);

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
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
