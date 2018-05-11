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

import javax.xml.soap.Text;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


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
    private Map<String, TextButton> hash;

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
        hash = new HashMap<String, TextButton>();

        multiplayer = new MultiPlayer(playerData.getConnection());
        gameList = multiplayer.getGames();
        fontText = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        if (menuButton.isPressed()) {
            gsm.set(new StartGameState(gsm, currentPlayer, playerData));

        } else if (createGameBtn.isPressed()) {
            gsm.set(new WaitForPlayerState(gsm, currentPlayer, playerData, multiplayer));
        }
        for (Map.Entry<String, TextButton> entry : hash.entrySet()) {
            TextButton btn = entry.getValue();
            String value = entry.getKey();

            if (btn.isPressed()){
                multiplayer.joinGame(currentPlayer, value);
                String player1name = multiplayer.getOpponent(currentPlayer.name);
                Player player1 = playerData.getPlayer(player1name);
                gsm.set(new PlayState(gsm, 0, player1, currentPlayer, playerData, true, multiplayer));

            }
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
        hash.clear();
        if(gameList.size() > 0) {

            for (int i = 0, k = 350; i < gameList.size(); i++, k -= 30) {
                hash.put(gameList.get(i).get(0), new TextButton("join", skin));
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
                }
            }
        } else if (playerData.isOffline()) {
            fontText.draw(sb, "Du er ikke tilkoblet databasen", Gdx.graphics.getWidth()/2-80,350);
        } else {
           fontText.draw(sb, "Det er ingen aktive spill", Gdx.graphics.getWidth()/2-80,350);
        }
        int y = 330;
        for (TextButton btn : hash.values()){
            btn.getLabel().setFontScale(0.5f);
            btn.setSize(120, 30);
            btn.setPosition(800, y);
            stage.addActor(btn);
            y -= 30;
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
    }
}