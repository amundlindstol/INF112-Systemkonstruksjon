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


public class ShowStatsState extends State {

    private Skin skin;
    private Stage stage;
    private Texture background, pieces;
    private Player currentPlayer;
    private TextButton menuButton;
    private int xPos;
    private int yPos;
    private PlayerData playerData;
    private BitmapFont fontText;
    private String text;

    public ShowStatsState (GameStateManager gsm, Player currentPlayer, PlayerData playerData) {
        super(gsm);
        this.playerData = playerData;
        stage = new Stage(new ScreenViewport(), new PolygonSpriteBatch());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/skin/rainbow-ui.json"));
        this.currentPlayer = currentPlayer;
        background = new Texture("Graphics/Menu/Menu_background.png");
        xPos = 690;
        yPos = 130;
        menuButton = new TextButton("Tilbake", skin);
        menuButton.setPosition((float) (xPos - menuButton.getWidth()*1.08), yPos - menuButton.getHeight() - 25);

        fontText = new BitmapFont();
        fontText.setColor(Color.WHITE);
       // text = playerData.getTopTenPlayers().get(0).getName();
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

        for(int i = 0; i < 10; i++){

        }

        fontText.draw(sb, playerData.getTopTenPlayers().get(0).getName(), 390, 430);
        fontText.draw(sb, playerData.getTopTenPlayers().get(1).getName(), 390, 400);
        fontText.draw(sb, playerData.getTopTenPlayers().get(2).getName(), 390, 370);
        fontText.draw(sb, playerData.getTopTenPlayers().get(3).getName(), 390, 340);
        fontText.draw(sb, playerData.getTopTenPlayers().get(4).getName(), 390, 310);
        fontText.draw(sb, playerData.getTopTenPlayers().get(5).getName(), 390, 280);
        fontText.draw(sb, playerData.getTopTenPlayers().get(6).getName(), 390, 250);
        fontText.draw(sb, playerData.getTopTenPlayers().get(7).getName(), 390, 230);
        fontText.draw(sb, playerData.getTopTenPlayers().get(8).getName(), 390, 200);
        fontText.draw(sb, playerData.getTopTenPlayers().get(9).getName(), 390, 170);


        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(0).rating, 600, 430);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(1).rating, 600, 400);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(2).rating, 600, 370);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(3).rating, 600, 340);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(4).rating, 600, 310);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(5).rating, 600, 280);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(6).rating, 600, 250);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(7).rating, 600, 220);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(8).rating, 600, 190);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(9).rating, 600, 160);


        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(0).getNoOfWins(), 700, 430);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(1).getNoOfWins(), 700, 400);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(2).getNoOfWins(), 700, 370);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(3).getNoOfWins(), 700, 340);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(4).getNoOfWins(), 700, 310);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(5).getNoOfWins(), 700, 280);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(6).getNoOfWins(), 700, 250);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(7).getNoOfWins(), 700, 220);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(8).getNoOfWins(), 700, 190);
        fontText.draw(sb, "" + playerData.getTopTenPlayers().get(9).getNoOfWins(), 700, 160);




        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
    }
}
