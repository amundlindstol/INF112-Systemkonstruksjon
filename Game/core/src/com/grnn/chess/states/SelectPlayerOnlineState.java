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
import com.grnn.chess.PlayerData;
import com.grnn.chess.multiPlayer.MultiPlayer;
//import com.grnn.chess.multiPlayer.MultiPlayer;

import java.sql.Connection;
import java.util.ArrayList;


public class SelectPlayerOnlineState extends State {

    Connection connection;
    PlayerData playerData;
    private MultiPlayer multiplayer;
    private Skin skin;
    private Stage stage;
    private Texture background;
    private Player currentPlayer;
    private TextButton menuButton, createGameBtn;
    private int xPos;
    private int yPos;
    private ArrayList<ArrayList<String>> gameList;
    private ArrayList<Integer> playerRating;
    private BitmapFont fontText;
    private int searchForGameCnt;
    private Player p;

    public SelectPlayerOnlineState(GameStateManager gsm, Player currentPlayer, PlayerData playerData) {
        super(gsm);
        this.currentPlayer = currentPlayer;
        this.playerData = playerData;
        this.connection = playerData.getConnection();
        this.multiplayer = new MultiPlayer(connection);
        playerRating = new ArrayList<Integer>();

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


        gameList = new ArrayList<ArrayList<String>>();
//        gameList.add(new ArrayList<String>());
//        gameList.get(0).add("1");
//        gameList.get(0).add("Knut Roger");
//        gameList.get(0).add("player2ID");
        fontText = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        if (menuButton.isPressed()) {
            gsm.set(new StartGameState(gsm, currentPlayer, playerData));
        } else if (createGameBtn.isPressed() && !playerData.isOffline()) {
            gsm.set(new WaitForPlayerState(gsm, currentPlayer, playerData));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

        if (searchForGameCnt % 300 == 0) {
            gameList.clear();
            gameList = multiplayer.getGames();
        }
        searchForGameCnt++;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);

        if(gameList.size() > 0) {
            for (int i = 0, k = 350; i < gameList.size(); i++, k -= 30) {
                for (int j = 0, o = 470; j < gameList.get(i).size(); j++, o += 100) {
                    fontText.draw(sb, gameList.get(i).get(j), o, k);
                }
                if (searchForGameCnt % 300 == 0) {
                    p = playerData.getPlayerFromDatabase(gameList.get(i).get(1));
                    if (p != null)
                        playerRating.add(i, p.getRating());
                }
                if (playerRating.size() > 0) {
                    fontText.draw(sb, playerRating.get(i).toString(), 520, k);
                    System.out.println(playerRating.get(0));
                }
            }
        } else if (playerData.isOffline()) {
            fontText.draw(sb, "Du er ikke tilkoblet databasen", Gdx.graphics.getWidth()/2-80,350);
        } else {
           fontText.draw(sb, "Det er ingen aktive spill", Gdx.graphics.getWidth()/2-80,350);
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