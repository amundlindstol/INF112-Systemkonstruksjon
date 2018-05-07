package com.grnn.chess.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.grnn.chess.Actors.Player;
import com.grnn.chess.multiPlayer.MultiPlayer;
//import com.grnn.chess.multiPlayer.MultiPlayer;

import java.util.ArrayList;


public class SelectPlayerOnlineState extends State {

    private MultiPlayer multiplayer;
    private Skin skin;
    private Stage stage;
    private Texture background;
    private Player currentPlayer;
    private TextButton menuButton, createGameBtn;
    private int xPos;
    private int yPos;
    private ArrayList<ArrayList<String>> gameList;
    private BitmapFont fontText;

    public SelectPlayerOnlineState(GameStateManager gsm, Player currentPlayer) {
        super(gsm);
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
        createGameBtn = new TextButton("Opprett spill", skin);
        createGameBtn.setPosition(Gdx.graphics.getWidth()/2 - createGameBtn.getWidth()/2, Gdx.graphics.getHeight() - 200);
        createGameBtn.setSize(createGameBtn.getWidth(),60);
        stage.addActor(menuButton);
        stage.addActor(createGameBtn);

//        multiplayer = new MultiPlayer();
        gameList = new ArrayList<ArrayList<String>>();
        gameList.add(new ArrayList<String>());
        gameList.get(0).add("1");
        gameList.get(0).add("Knut Roger");
        gameList.get(0).add("player2ID");
        fontText = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        if (menuButton.isPressed()) {

        } else if (createGameBtn.isPressed()) {
//            gsm.set(gsm, new WaitForPlayerState(gsm, currentPlayer));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
//        gameList = multiplayer.getGames();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);

        if(gameList.size() > 0) {

            for (int i = 0, k = 350; i < gameList.size(); i++, k -= 30) {
                for (int j = 0, o = 470; j < gameList.get(i).size()-1; j++, o += 100) {
                    fontText.draw(sb, gameList.get(i).get(j), o, k);
                }
            }
        } else {
           fontText.draw(sb, "You are not connected to the database", Gdx.graphics.getWidth()/2-110,350);
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
        System.out.println("SelectPlayerOnlineState Disposed");
    }
}